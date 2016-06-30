package com.hjy.selleradapter.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hjy.annotation.Inject;
import com.hjy.common.DateUtil;
import com.hjy.entity.OcOrderKjtDetail;
import com.hjy.entity.OcOrderKjtList;
import com.hjy.entity.log.LcOrderstatus;
import com.hjy.entity.member.McAuthenticationInfo;
import com.hjy.entity.order.OcOrderaddress;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.FormatHelper;
import com.hjy.helper.WebHelper;
import com.hjy.model.MDataMap;
import com.hjy.model.RsyncResult;
import com.hjy.selleradapter.kjt.RsyncKjt;
import com.hjy.selleradapter.kjt.config.RsyncConfigOrderStatus;
import com.hjy.selleradapter.kjt.request.RsyncRequestOrderStatus;
import com.hjy.selleradapter.kjt.response.RsyncResponseOrderStatus;
import com.hjy.selleradapter.kjt.response.RsyncResponseOrderStatus.SoOrder;
import com.hjy.service.IOcOrderKjtDetailService;
import com.hjy.service.IOcOrderKjtListService;
import com.hjy.service.log.ILcOrderstatusService;
import com.hjy.service.member.IMcAuthenticationInfoService;
import com.hjy.service.order.IOcOrderadressService;
import com.hjy.service.order.IOcOrderinfoService;
import com.hjy.support.MailSupport;

/**
 * 
 * 类: TraceOrder <br>
 * 描述: 同步订单状态 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午4:50:30
 */
public class TraceOrder extends RsyncKjt<RsyncConfigOrderStatus, RsyncRequestOrderStatus, RsyncResponseOrderStatus> {

	@Inject
	private IOcOrderKjtListService ocOrderKjtListService;
	@Inject
	private IOcOrderadressService ocOrderadressService;
	@Inject
	private IOcOrderinfoService ocOrderinfoService;
	@Inject
	private IMcAuthenticationInfoService mcAuthenticationInfoService;
	@Inject
	private ILcOrderstatusService lcOrderstatusService;
	@Inject
	private IOcOrderKjtDetailService ocOrderKjtDetailService;
	private final static RsyncConfigOrderStatus RSYNC_CONFIG_TRACE_ORDER = new RsyncConfigOrderStatus();
	private RsyncRequestOrderStatus rsyncRequestTraceOrder = new RsyncRequestOrderStatus();

	@Override
	public RsyncConfigOrderStatus upConfig() {
		return RSYNC_CONFIG_TRACE_ORDER;
	}

	@Override
	public RsyncRequestOrderStatus upRsyncRequest() {
		return rsyncRequestTraceOrder;
	}

	@Override
	public RsyncResult doProcess(RsyncRequestOrderStatus tRequest, RsyncResponseOrderStatus tResponse) {

		RsyncResult rsyncResult = new RsyncResult();

		if (!"0".equals(tResponse.getCode())) {
			rsyncResult.setCode(918519135);
			rsyncResult.setMessage(tResponse.getDesc());
			return rsyncResult;
		}

		List<SoOrder> list = tResponse.getData().getTraceOrderList();

		if (list != null && list.size() > 0) {
			for (SoOrder soOrder : list) {
				int soid = soOrder.getSOID();
				int sostatus = soOrder.getSOStatus();
				String order_code_out = String.valueOf(soid);
				// 根据外部订单号查询跨境通订单信息
				OcOrderKjtList order = ocOrderKjtListService.findOrderByOutCode(order_code_out);
				String order_code = order.getOrderCode();
				// 根据order_code查询订单信息
				OcOrderinfo orderInfo = ocOrderinfoService.findOrderInfoByOrderCode(order_code);
				String order_code_seq = order.getOrderCodeSeq();
				if (StringUtils.isBlank(order.getSostatus()) || Integer.valueOf(order.getSostatus()) != sostatus) {
					OcOrderKjtList editOrder = new OcOrderKjtList();
					editOrder.setSostatus(String.valueOf(sostatus));
					editOrder.setLocalStatus(orderStatusMapper(sostatus));
					editOrder.setOrderCode(order_code);
					editOrder.setUpdateTime(DateUtil.getSysDateTimeString());
					// 根据订单编号更新订单
					ocOrderKjtListService.updateSelective(editOrder);
					if (sostatus == 65 || sostatus == 42) {
						OcOrderaddress address = ocOrderadressService.findOrderAddressByOrderCode(order_code);
						String auth_idcard_number = address.getAuthIdcardNumber();
						if (StringUtils.isNotBlank(auth_idcard_number)) {
							if (orderInfo.getBuyerCode() != null && !"".equals(orderInfo.getBuyerCode())) {
								String buyer_code = orderInfo.getBuyerCode();
								// 修改通关状态; 0正常 1 通关失败
								McAuthenticationInfo mai = new McAuthenticationInfo();
								mai.setMemberCode(buyer_code);
								mai.setCustomsStatus(sostatus == 65 ? 1 : 0);
								mai.setIdcardNumber(auth_idcard_number);
								mcAuthenticationInfoService.updateCustomsStatus(mai);
							}
						}
					}
				}

				// 更新本地订单状态

				// 全部对应交易成功 交易成功
				// 部分对应已发货 已发货
				// 全部对应下单成功-未发货 下单成功-未发货
				// 全部对应交易失败 交易失败

				// 4497153900010002 下单成功-未发货
				// 4497153900010003 已发货
				// 4497153900010005 交易成功
				// 4497153900010006 交易失败

				List<String> localStatusList = ocOrderKjtListService.findLocalStatusByOrderCode(order_code);
				// 根据order_code查询跨境通订单列表集合
				String status = "";
				if (localStatusList.size() == 1) {
					status = (String) localStatusList.get(0);
				} else {
					// 此处映射状态不全，暂时不考虑其他
					status = "4497153900010003";
				}

				if (StringUtils.isNotBlank(status) && !status.equals(orderInfo.getOrderStatus())
						&& !"4497153900010005".equals(orderInfo.getOrderStatus())) {

					// 更新oc_orderinfo订单状态信息
					OcOrderinfo ocOrderInfo = new OcOrderinfo();
					ocOrderInfo.setOrderCode(order_code);
					ocOrderInfo.setOrderStatus(status);
					ocOrderInfo.setUpdateTime(DateUtil.getSysDateTimeString());
					ocOrderinfoService.updateSelective(ocOrderInfo);
					// 添加订单状态日志表记录
					LcOrderstatus lcOrderstatus = new LcOrderstatus();
					lcOrderstatus.setCode(order_code);
					lcOrderstatus.setCreateTime(DateUtil.getSysDateTimeString());
					lcOrderstatus.setCreateUser("system");
					lcOrderstatus.setOldStatus(orderInfo.getOrderStatus());
					lcOrderstatus.setNowStatus(status);
					lcOrderstatusService.insertSelective(lcOrderstatus);
				}

				if (sostatus == -4 || sostatus == -1 || sostatus == 6 || sostatus == 65) {
					creatReturnMoney(order_code_seq, order_code);
				}

				// 判断发个邮件通知一下
				if (sostatus == -4 || sostatus == -1 || sostatus == 6 || sostatus == 65 || sostatus == 7) {
					sendMail(order_code_seq, order_code, order_code_out, sostatus);
				}
			}
		}

		return rsyncResult;
	}

	@Override
	public RsyncResponseOrderStatus upResponseObject() {
		return new RsyncResponseOrderStatus();
	}

	private void sendMail(String order_code_seq, String order_code, String order_code_out, int sostatus) {

		String receives[] = getConfig("groupcenter.kjt_orderstata_sendMail_receives").split(",");
		String title = getConfig("groupcenter.kjt_orderstata_sendMail_title");
		String content = getConfig("groupcenter.kjt_orderstata_sendMail_content");

		for (String receive : receives) {
			if (StringUtils.isNotBlank(receive)) {
				MailSupport.INSTANCE.sendMail(receive,
						FormatHelper.formatString(title, order_code, order_code_seq, getStatusDesc(sostatus),
								order_code_out),
						FormatHelper.formatString(content, order_code, order_code_seq, getStatusDesc(sostatus),
								order_code_out));
			}
		}
	}

	private static Map<Integer, String> statusMap = null;

	private synchronized static String getStatusDesc(int sostatus) {
		if (statusMap == null) {
			statusMap = new HashMap<Integer, String>();
			statusMap.put(-4, "系统作废");
			statusMap.put(-1, "作废");
			statusMap.put(0, "待审核");
			statusMap.put(1, "待出库");
			statusMap.put(4, "已出库待申报");
			statusMap.put(41, "已申报待通关");
			statusMap.put(45, "已通过发往顾客");
			statusMap.put(5, "订单完成");
			statusMap.put(6, "申报失败订单作废");
			statusMap.put(65, "通关失败订单作废");
			statusMap.put(7, "订单拒收	");
		}
		return statusMap.get(sostatus);
	}

	// 此处不牵扯运费的问题
	private void creatReturnMoney(String order_code_seq, String order_code) {

		BigDecimal expected_return_money = BigDecimal.ZERO;

		List<OcOrderKjtDetail> details = ocOrderKjtDetailService.findOrderDetailByCodeSeq(order_code_seq);
		if (details != null && details.size() > 0) {
			for (OcOrderKjtDetail ocOrderKjtDetail : details) {
				String sku_code = ocOrderKjtDetail.getSkuCode();
				BigDecimal sku_num = BigDecimal.valueOf(ocOrderKjtDetail.getSkuNum());
				expected_return_money = expected_return_money.add(sku_num.multiply(ocOrderKjtDetail.getSkuPrice()));
			}
		}
		OcOrderinfo order = ocOrderinfoService.findOrderInfoByOrderCode(order_code);
		String money_no = WebHelper.getInstance().genUniqueCode("RTM");
		if (expected_return_money.compareTo(BigDecimal.ZERO) > 0) {
			// 生成退款单
			MDataMap map = new MDataMap();
			map.put("return_money_code", money_no);
			map.put("return_goods_code", "");
			map.put("buyer_code", order.getBuyerCode());
			map.put("seller_code", order.getSellerCode());
			map.put("small_seller_code", order.getSmallSellerCode());
			map.put("contacts", "");// 联系人
			map.put("status", "4497153900040003");
			map.put("return_money", expected_return_money.toString());
			// map.put("mobile", (String)
			// DbUp.upTable("mc_login_info").dataGet("login_name",
			// "member_code=:member_code",
			// new MDataMap("member_code", orderInfo.get("buyer_code"))));
			map.put("create_time", DateUtil.getSysDateTimeString());
			map.put("poundage", "0");
			map.put("order_code", order_code);
			map.put("pay_method", "449716200001");
			map.put("online_money", expected_return_money.toString());
			// DbUp.upTable("oc_return_money").dataInsert(map);

			// 创建流水日志
			MDataMap logMap = new MDataMap();
			logMap.put("return_money_no", money_no);
			logMap.put("info", "跨境通订单失败，直接生成退款单");
			logMap.put("create_time", DateUtil.getSysDateTimeString());
			String create_user = "";
			try {
				create_user = null;// UserFactory.INSTANCE.create().getLoginName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logMap.put("create_user", create_user);
			logMap.put("status", map.get("status"));
			// DbUp.upTable("lc_return_money_status").dataInsert(logMap);
		}

	}
	// -4 系统作废 交易失败
	// -1 作废 交易失败
	// 0 待审核 下单成功-未发货
	// 1 待出库 下单成功-未发货
	// 4 已出库待申报 下单成功-未发货 已发货
	// 41 已申报待通关 下单成功-未发货 已发货
	// 45 已通过发往顾客 已发货
	// 5 订单完成 交易成功
	// 6 申报失败订单作废 交易失败
	// 65 通关失败订单作废 交易失败
	// 7 订单拒收 交易失败

	// 4497153900010001 下单成功-未付款
	// 4497153900010002 下单成功-未发货
	// 4497153900010003 已发货
	// 4497153900010004 已收货
	// 4497153900010005 交易成功
	// 4497153900010006 交易失败

	/**
	 * 
	 * 方法: orderStatusMapper <br>
	 * 描述: 迁移com.cmall.groupcenter.service.OrderForKJT的orderStatusMapper <br>
	 * 订单状态映射<br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月27日 下午4:47:05
	 * 
	 * @param ostatus
	 * @return
	 */
	public static String orderStatusMapper(int ostatus) {

		String status = null;

		switch (ostatus) {
		case -4:
			status = "4497153900010006";
			break;
		case -1:
			status = "4497153900010006";
			break;
		case 0:
			status = "4497153900010002";
			break;
		case 1:
			status = "4497153900010002";
			break;
		case 4:
			status = "4497153900010003";
			break;
		case 41:
			status = "4497153900010003";
			break;
		case 45:
			status = "4497153900010003";
			break;
		case 5:
			status = "4497153900010005";
			break;
		case 6:
			status = "4497153900010006";
			break;
		case 65:
			status = "4497153900010006";
			break;
		case 7:
			status = "4497153900010006";
			break;
		default:
			status = "";
			break;
		}
		return status;
	}
}
