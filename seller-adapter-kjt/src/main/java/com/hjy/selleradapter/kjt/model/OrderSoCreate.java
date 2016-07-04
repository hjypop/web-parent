package com.hjy.selleradapter.kjt.model;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hjy.annotation.Inject;
import com.hjy.common.DateUtil;
import com.hjy.entity.OcOrderKjtDetail;
import com.hjy.entity.OcOrderKjtList;
import com.hjy.entity.log.LcOrderstatus;
import com.hjy.entity.log.LcReturnMoneyStatus;
import com.hjy.entity.member.McAuthenticationInfo;
import com.hjy.entity.member.McLoginInfo;
import com.hjy.entity.order.OcOrderaddress;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.entity.order.OcReturnMoney;
import com.hjy.factory.UserFactory;
import com.hjy.helper.WebHelper;
import com.hjy.model.RsyncResult;
import com.hjy.selleradapter.kjt.RsyncKjt;
import com.hjy.selleradapter.kjt.config.RsyncConfigOrderSoCreate;
import com.hjy.selleradapter.kjt.request.RsyncRequestOrderSoCreate;
import com.hjy.selleradapter.kjt.response.RsyncResponseOrderSoCreate;
import com.hjy.selleradapter.kjt.response.RsyncResponseOrderSoCreate.Data;
import com.hjy.service.IOcOrderKjtDetailService;
import com.hjy.service.IOcOrderKjtListService;
import com.hjy.service.log.ILcOrderstatusService;
import com.hjy.service.log.ILcReturnMoneyStatusService;
import com.hjy.service.member.IMcAuthenticationInfoService;
import com.hjy.service.member.IMcLoginInfoService;
import com.hjy.service.order.IOcOrderadressService;
import com.hjy.service.order.IOcOrderinfoService;
import com.hjy.service.order.IOcReturnMoneyService;

/**
 * 创建订单 | properties配置信息核对完成
 * 
 * @author jlin
 *
 */
public class OrderSoCreate extends RsyncKjt<RsyncConfigOrderSoCreate, RsyncRequestOrderSoCreate, RsyncResponseOrderSoCreate> {

	@Inject
	private IOcOrderKjtListService ocOrderKjtListService;
	@Inject
	private IOcOrderadressService ocOrderadressService;
	@Inject
	private IOcOrderinfoService ocOrderinfoService;
	@Inject
	private IMcAuthenticationInfoService authenticationInfoService;
	@Inject
	private ILcOrderstatusService lcOrderstatusService;
	@Inject
	private IOcOrderKjtDetailService ocOrderKjtDetailService;
	@Inject
	private IOcReturnMoneyService ocReturnMoneyService;
	@Inject
	private IMcLoginInfoService mcLoginInfoService;
	@Inject
	private ILcReturnMoneyStatusService lcReturnMoneyStatusService;

	private final static RsyncConfigOrderSoCreate RSYNC_CONFIG_ORDER_SO_CREATE = new RsyncConfigOrderSoCreate();
	private RsyncRequestOrderSoCreate rsyncRequestOrderSoCreate = new RsyncRequestOrderSoCreate();

	@Override
	public RsyncConfigOrderSoCreate upConfig() {
		return RSYNC_CONFIG_ORDER_SO_CREATE;
	}

	@Override
	public RsyncRequestOrderSoCreate upRsyncRequest() {
		return rsyncRequestOrderSoCreate;
	}

	@Override
	public RsyncResult doProcess(RsyncRequestOrderSoCreate tRequest, RsyncResponseOrderSoCreate tResponse) {

		RsyncResult rsyncResult = new RsyncResult();
		String order_code = tRequest.getMerchantOrderID();
		String desc = tResponse.getDesc();

		if (!"0".equals(tResponse.getCode())) {
			// 根据序列订单号修改订单编号 2016-07-02 zhy
			OcOrderKjtList kjtList = new OcOrderKjtList();
			kjtList.setOrderCodeSeq(order_code);
			kjtList.setRsyncDesc(desc);
			ocOrderKjtListService.updateCodeByCodeSeq(kjtList);
			// {"Code":"2","Desc":"您本月累计订单的数量超出了海关关于个人物品自用、合理数量的规定。请更换实名信息再购买！"}
			if (StringUtils.startsWith(desc, "您本月累计订单的数量超出了海关关于个人物品自用")) {

				// 根据序列订单号查询订单编号2016-07-03 zhy
				OcOrderKjtList kjtOrder = ocOrderKjtListService.findOrderListByCodeSeq(order_code);
				String order_code_ori = kjtOrder.getOrderCode();
				// 根据序列订单号编辑跨境通订单2016-07-03 zhy
				kjtOrder.setSostatus("65");
				kjtOrder.setLocalStatus("");
				ocOrderKjtListService.updateCodeByCodeSeq(kjtOrder);
				creatReturnMoney(order_code, order_code_ori);

				// 根据order_code查询 订单地址发票表 2016-07-03 zhy
				OcOrderaddress addressInfo = ocOrderadressService.findOrderAddressByOrderCode(order_code_ori);
				String auth_idcard_number = addressInfo.getAuthIdcardNumber();
				OcOrderinfo orderInfo = ocOrderinfoService.findOrderInfoByOrderCode(order_code_ori);
				String buyer_code = orderInfo.getBuyerCode();
				String order_status = orderInfo.getOrderStatus();

				// 根据member_code和idcard_number修改customs_status 2016-07-03 zhy
				McAuthenticationInfo authenticationInfo = new McAuthenticationInfo();
				authenticationInfo.setMemberCode(buyer_code);
				authenticationInfo.setIdcardNumber(auth_idcard_number);
				authenticationInfo.setCustomsStatus(1);
				authenticationInfoService.updateCustomsStatus(authenticationInfo);

				// 根据订单编号查询订单集合 2016-07-03 zhy
				List<OcOrderKjtList> klist = ocOrderKjtListService.findListByOrderCode(order_code_ori);
				if (klist.size() == 1 && !"4497153900010006".equals(order_status)) {
					// 更新订单状态
					OcOrderinfo ocOrderinfo = new OcOrderinfo();
					ocOrderinfo.setOrderStatus("4497153900010006");
					ocOrderinfo.setOrderCode(order_code_ori);
					ocOrderinfoService.updateSelective(ocOrderinfo);
					// 添加订单状态日志表2016-07-03 zhy
					LcOrderstatus lcOrderstatus = new LcOrderstatus();
					lcOrderstatus.setCode(order_code_ori);
					lcOrderstatus.setInfo("跨境通订单同步失败");
					lcOrderstatus.setCreateTime(DateUtil.getSysDateTimeString());
					lcOrderstatus.setCreateUser("system");
					lcOrderstatus.setOldStatus(order_status);
					lcOrderstatus.setNowStatus("4497153900010006");
					lcOrderstatusService.insertSelective(lcOrderstatus);

				}
			}

			rsyncResult.setCode(100009135);
			rsyncResult.setMessage(tResponse.getDesc());
			return rsyncResult;
		}

		Data data = tResponse.getData();

		// 更新订单表 2016-07-03 zhy
		OcOrderKjtList updateKjtOrder = new OcOrderKjtList();
		updateKjtOrder.setOrderCodeSeq(order_code);
		updateKjtOrder.setOrderCodeOut(String.valueOf(data.getSOSysNo()));
		updateKjtOrder.setProductAmount(data.getProductAmount());
		updateKjtOrder.setTaxAmount(data.getTaxAmount());
		updateKjtOrder.setShippingAmount(data.getShippingAmount());
		updateKjtOrder.setUpdateTime(DateUtil.getSysDateTimeString());
		updateKjtOrder.setRsyncDesc(desc);
		updateKjtOrder.setSostatus("0");
		ocOrderKjtListService.updateCodeByCodeSeq(updateKjtOrder);
		responseSu = true;
		return rsyncResult;
	}

	private boolean responseSu = false;

	public boolean responseSucc() {

		return responseSu;
	}

	@Override
	public RsyncResponseOrderSoCreate upResponseObject() {
		return new RsyncResponseOrderSoCreate();
	}

	// 此处不牵扯运费的问题
	private void creatReturnMoney(String order_code_seq, String order_code) {
		BigDecimal expected_return_group_money = BigDecimal.ZERO;
		BigDecimal expected_return_money = BigDecimal.ZERO;
		List<OcOrderKjtDetail> failDetails = ocOrderKjtDetailService.findOrderDetailByCodeSeq(order_code_seq);
		for (OcOrderKjtDetail detailInfo : failDetails) {
			String sku_code = detailInfo.getSkuCode();
			BigDecimal sku_num = new BigDecimal(detailInfo.getSkuNum());
			BigDecimal sku_price = detailInfo.getSkuPrice();
			// 没有group_price字段 2016-07-03 zhy
			BigDecimal group_price = BigDecimal.ZERO;
			expected_return_money = expected_return_money.add(sku_num.multiply(sku_price));
			expected_return_group_money = expected_return_group_money.add(sku_num.multiply(group_price));
		}

		// 根据订单编号查询订单信息 2016-07-03 zhy
		OcOrderinfo orderInfo = ocOrderinfoService.findOrderInfoByOrderCode(order_code);
		String money_no = WebHelper.getInstance().genUniqueCode("RTM");
		if (expected_return_money.compareTo(BigDecimal.ZERO) > 0) {
			// 生成退款单2016-07-03 zhy
			OcReturnMoney ocReturnMoney = new OcReturnMoney();
			ocReturnMoney.setReturnMoneyCode(money_no);
			ocReturnMoney.setReturnGoodsCode(orderInfo.getBuyerCode());
			ocReturnMoney.setSellerCode(orderInfo.getSellerCode());
			ocReturnMoney.setSmallSellerCode(orderInfo.getSmallSellerCode());
			ocReturnMoney.setContacts("");
			ocReturnMoney.setStatus("4497153900040003");
			ocReturnMoney.setReturnMoney(expected_return_money);
			// 查询手机号2016-07-03 zhy
			McLoginInfo mcLoginInfo = mcLoginInfoService.findLoginInfoByMemberCode(orderInfo.getBuyerCode());
			ocReturnMoney.setMobile(mcLoginInfo.getLoginName());
			ocReturnMoney.setCreateTime(DateUtil.getSysDateTimeString());
			ocReturnMoney.setPoundage(BigDecimal.valueOf(0));
			ocReturnMoney.setOrderCode(order_code);
			ocReturnMoney.setPayMethod("449716200001");
			ocReturnMoney.setOnlineMoney(expected_return_money);
			ocReturnMoneyService.insertSelective(ocReturnMoney);
			// 创建流水日志 2016-07-03 zhy
			String create_user = "";
			try {
				create_user = UserFactory.INSTANCE.create().getLoginName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			LcReturnMoneyStatus lcReturnMoneyStatus = new LcReturnMoneyStatus();
			lcReturnMoneyStatus.setReturnMoneyNo(money_no);
			lcReturnMoneyStatus.setInfo("");
			lcReturnMoneyStatus.setCreateTime(DateUtil.getSysDateTimeString());
			lcReturnMoneyStatus.setCreateUser(create_user);
			lcReturnMoneyStatus.setStatus(ocReturnMoney.getStatus());
			lcReturnMoneyStatusService.insertSelective(lcReturnMoneyStatus);
		}
	}
}
