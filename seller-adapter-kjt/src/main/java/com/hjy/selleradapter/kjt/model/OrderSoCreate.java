package com.hjy.selleradapter.kjt.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hjy.annotation.Inject;
import com.hjy.factory.UserFactory;
import com.hjy.model.RsyncResult;
import com.hjy.selleradapter.kjt.RsyncKjt;
import com.hjy.selleradapter.kjt.config.RsyncConfigOrderSoCreate;
import com.hjy.selleradapter.kjt.request.RsyncRequestOrderSoCreate;
import com.hjy.selleradapter.kjt.response.RsyncResponseOrderSoCreate;
import com.hjy.service.IOcOrderKjtListService;

/**
 * 创建订单
 * 
 * @author jlin
 *
 */
public class OrderSoCreate
		extends RsyncKjt<RsyncConfigOrderSoCreate, RsyncRequestOrderSoCreate, RsyncResponseOrderSoCreate> {

	@Inject
	private IOcOrderKjtListService ocOrderKjtListService; 
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
			DbUp.upTable("oc_order_kjt_list").dataUpdate(new MDataMap("order_code_seq", order_code, "rsync_desc", desc),
					"rsync_desc", "order_code_seq");

			// {"Code":"2","Desc":"您本月累计订单的数量超出了海关关于个人物品自用、合理数量的规定。请更换实名信息再购买！"}
			if (StringUtils.startsWith(desc, "您本月累计订单的数量超出了海关关于个人物品自用")) {

				String order_code_ori = (String) DbUp.upTable("oc_order_kjt_list").dataGet("order_code",
						"order_code_seq=:order_code_seq", new MDataMap("order_code_seq", order_code));
				DbUp.upTable("oc_order_kjt_list").dataUpdate(
						new MDataMap("order_code_seq", order_code, "sostatus", "65", "local_status", ""),
						"sostatus,local_status", "order_code_seq");
				creatReturnMoney(order_code, order_code_ori);

				MDataMap addressInfo = DbUp.upTable("oc_orderadress").oneWhere("auth_idcard_number", "",
						"order_code=:order_code", "order_code", order_code_ori);
				String auth_idcard_number = addressInfo.get("auth_idcard_number");
				MDataMap orderInfo = DbUp.upTable("oc_orderinfo").oneWhere("buyer_code,order_status", "",
						"order_code=:order_code", "order_code", order_code_ori);
				String buyer_code = orderInfo.get("buyer_code");
				String order_status = orderInfo.get("order_status");

				DbUp.upTable("mc_authenticationInfo").dataUpdate(new MDataMap("member_code", buyer_code,
						"idcard_number", auth_idcard_number, "customs_status", "1"), "customs_status",
						"member_code,idcard_number");

				List<Map<String, Object>> klist = DbUp.upTable("oc_order_kjt_list").dataSqlList(
						"SELECT rsync_desc from oc_order_kjt_list where order_code=:order_code GROUP BY rsync_desc",
						new MDataMap("order_code", order_code_ori));
				if (klist.size() == 1 && !"4497153900010006".equals(order_status)) {
					// 更新订单状态
					DbUp.upTable("oc_orderinfo").dataUpdate(
							new MDataMap("order_status", "4497153900010006", "order_code", order_code_ori),
							"order_status", "order_code");
					DbUp.upTable("lc_orderstatus").insert("code", order_code_ori, "info", "跨境通订单同步失败", "create_time",
							DateUtil.getSysDateTimeString(), "create_user", "system", "old_status", order_status,
							"now_status", "4497153900010006");
				}
			}

			rsyncResult.setResultCode(918519135);
			rsyncResult.setResultMessage(tResponse.getDesc());
			return rsyncResult;
		}

		Data data = tResponse.getData();

		String out_order_code = String.valueOf(data.getSOSysNo());
		String ProductAmount = String.valueOf(data.getProductAmount());
		String TaxAmount = String.valueOf(data.getTaxAmount());
		String ShippingAmount = String.valueOf(data.getShippingAmount());

		// 更新订单表
		DbUp.upTable("oc_order_kjt_list").dataUpdate(
				new MDataMap("order_code_seq", order_code, "order_code_out", out_order_code, "product_amount",
						ProductAmount, "tax_amount", TaxAmount, "shipping_amount", ShippingAmount, "update_time",
						DateUtil.getSysDateTimeString(), "rsync_desc", desc, "sostatus", "0"),
				"order_code_out,product_amount,tax_amount,shipping_amount,update_time,rsync_desc,sostatus",
				"order_code_seq");

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

		List<MDataMap> failDetails = DbUp.upTable("oc_order_kjt_detail").queryAll("", "",
				"order_code_seq=:order_code_seq", new MDataMap("order_code_seq", order_code_seq));
		for (MDataMap mDataMap : failDetails) {
			String sku_code = mDataMap.get("sku_code");
			BigDecimal sku_num = new BigDecimal(mDataMap.get("sku_num"));
			MDataMap detailInfo = DbUp.upTable("oc_orderdetail").one("order_code", order_code, "sku_code", sku_code);
			BigDecimal sku_price = new BigDecimal(detailInfo.get("sku_price"));
			BigDecimal group_price = new BigDecimal(detailInfo.get("group_price"));

			expected_return_money = expected_return_money.add(sku_num.multiply(sku_price));
			expected_return_group_money = expected_return_group_money.add(sku_num.multiply(group_price));
		}

		MDataMap orderInfo = DbUp.upTable("oc_orderinfo").one("order_code", order_code);

		String money_no = WebHelper.upCode("RTM");
		if (expected_return_money.compareTo(BigDecimal.ZERO) > 0) {
			// 生成退款单
			MDataMap map = new MDataMap();
			map.put("return_money_code", money_no);
			map.put("return_goods_code", "");
			map.put("buyer_code", orderInfo.get("buyer_code"));
			map.put("seller_code", orderInfo.get("seller_code"));
			map.put("small_seller_code", orderInfo.get("small_seller_code"));
			map.put("contacts", "");// 联系人
			map.put("status", "4497153900040003");
			map.put("return_money", expected_return_money.toString());
			map.put("mobile", (String) DbUp.upTable("mc_login_info").dataGet("login_name", "member_code=:member_code",
					new MDataMap("member_code", orderInfo.get("buyer_code"))));
			map.put("create_time", DateUtil.getSysDateTimeString());
			map.put("poundage", "0");
			map.put("order_code", order_code);
			map.put("pay_method", "449716200001");
			map.put("online_money", expected_return_money.toString());
			DbUp.upTable("oc_return_money").dataInsert(map);

			// 创建流水日志
			MDataMap logMap = new MDataMap();
			logMap.put("return_money_no", money_no);
			logMap.put("info", "跨境通订单失败，直接生成退款单");
			logMap.put("create_time", DateUtil.getSysDateTimeString());
			String create_user = "";
			try {
				create_user = UserFactory.INSTANCE.create().getLoginName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logMap.put("create_user", create_user);
			logMap.put("status", map.get("status"));
			DbUp.upTable("lc_return_money_status").dataInsert(logMap);
		}

		// 自动退还微公社余额
		if (expected_return_group_money.compareTo(BigDecimal.ZERO) > 0) {

			// 退返微公社部分
			GroupRefundInput groupRefundInput = new GroupRefundInput();
			// groupRefundInput.setTradeCode(money_no);
			groupRefundInput.setTradeCode(DbUp.upTable("oc_order_pay")
					.one("order_code", order_code, "pay_type", "449746280009").get("pay_sequenceid"));
			groupRefundInput.setMemberCode(orderInfo.get("buyer_code"));
			groupRefundInput.setRefundMoney(expected_return_group_money.toString());
			groupRefundInput.setOrderCode(order_code);
			groupRefundInput.setRefundTime(DateUtil.getSysDateTimeString());
			groupRefundInput.setRemark("跨境通自动退还微公社余额");
			groupRefundInput.setBusinessTradeCode(money_no);// 一个流水值退一次
			// new GroupPayService().groupRefundSome(groupRefundInput,
			// orderInfo.get("seller_code"));

			ApiCallSupport<GroupRefundInput, GroupRefundResult> apiCallSupport = new ApiCallSupport<GroupRefundInput, GroupRefundResult>();
			GroupRefundResult refundResult = null;
			try {
				refundResult = apiCallSupport.doCallApi(bConfig("xmassystem.group_pay_url"),
						bConfig("xmassystem.group_pay_refund_face"), bConfig("xmassystem.group_pay_key"),
						bConfig("xmassystem.group_pay_pass"), groupRefundInput, new GroupRefundResult());
			} catch (Exception e) {
				// 此处暂时流程，退款失败，不影响总流程
				e.printStackTrace();
			}
		}

	}
}
