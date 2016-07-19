package com.hjy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.Inject;
import com.hjy.base.BaseClass;
import com.hjy.common.DateUtil;
import com.hjy.dao.IOcOrderKjtDetailDao;
import com.hjy.dao.IOcOrderKjtListDao;
import com.hjy.dao.IOcOrderKjtListDataDao;
import com.hjy.dao.member.IMcAuthenticationInfoDao;
import com.hjy.dao.order.IOcExpressEetailDao;
import com.hjy.dao.order.IOcOrderActivityDao;
import com.hjy.dao.order.IOcOrderPayDao;
import com.hjy.dao.order.IOcOrderShipmentsDao;
import com.hjy.dao.order.IOcOrderaddressDao;
import com.hjy.dao.order.IOcOrderdetailDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcProductinfoExtDao;
import com.hjy.dao.system.IScTmpDao;
import com.hjy.entity.OcOrderKjtDetail;
import com.hjy.entity.OcOrderKjtList;
import com.hjy.entity.OcOrderKjtListData;
import com.hjy.entity.member.McAuthenticationInfo;
import com.hjy.entity.order.OcOrderActivity;
import com.hjy.entity.order.OcOrderPay;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.entity.order.OcOrderaddress;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.system.ScTmp;
import com.hjy.helper.WebHelper;
import com.hjy.model.AuthenticationInfo;
import com.hjy.model.MDataMap;
import com.hjy.model.order.Express;
import com.hjy.model.order.Order;
import com.hjy.model.order.OrderAddress;
import com.hjy.model.order.OrderDetail;
import com.hjy.selleradapter.kjt.model.OrderSoCreate;
import com.hjy.selleradapter.kjt.model.SOAuthenticationInfo;
import com.hjy.selleradapter.kjt.model.SOItemInfo;
import com.hjy.selleradapter.kjt.model.SOPayInfo;
import com.hjy.selleradapter.kjt.model.SOShippingInfo;
import com.hjy.selleradapter.kjt.request.RsyncRequestOrderSoCreate;
import com.hjy.support.MailSupport;

/**
 * 跨境通订单 | properties配置信息核对完成
 * 
 * @author jlin
 *
 */
public class OrderForKJT extends BaseClass {

	@Inject
	private IOcOrderKjtListDao ocOrderKjtListDao;
	@Inject
	private IOcOrderKjtListDataDao ocOrderKjtListDataDao;
	@Inject
	private IOcOrderKjtDetailDao ocOrderKjtDetailDao;
	@Inject
	private IOcOrderinfoDao ocOrderinfoDao;
	@Inject
	private IOcOrderaddressDao ocOrderaddressDao;
	@Inject
	private IOcOrderActivityDao ocOrderActivityDao;
	@Inject
	private IOcOrderPayDao ocOrderPayDao;
	@Inject
	private IOcOrderdetailDao ocOrderdetailDao;
	@Inject
	private IOcOrderShipmentsDao ocOrderShipmentsDao;
	@Inject
	private IOcExpressEetailDao ocExpressEetailDao;
	@Inject
	private IPcProductinfoDao pcProductinfoDao;
	@Inject
	private IPcProductinfoExtDao pcProductinfoExtDao;
	@Inject
	private IMcAuthenticationInfoDao mcAuthenticationInfoDao;
	@Inject
	private IScTmpDao scTmpDao;

	/**
	 * 同步跨境通订单
	 * 
	 * @param order
	 */
	public boolean rsyncOrder(String order_code) {
		boolean process_succ = true;
		List<OcOrderKjtList> list = ocOrderKjtListDao.findListByOrderCode(order_code);
		if (list.size() > 0) {
			for (OcOrderKjtList okl : list) {
				String order_code_seq = okl.getOrderCodeSeq();
				String order_code_out = okl.getOrderCodeOut();
				if (StringUtils.isBlank(order_code_out)) {
					OcOrderKjtListData okld = new OcOrderKjtListData();
					okld.setOrderCodeSeq(order_code_seq);
					okld = ocOrderKjtListDataDao.findByType(okld);
					if (okld != null) {
						Order order = JSON.parseObject(okld.getRequestClazz(), Order.class);
						if (!rsyncOrder(order)) {
							process_succ = false;
						}
					}
				}
			}

		} else {
			List<Order> orderList = groupOrder(order_code);
			for (int i = 0; i < orderList.size(); i++) {
				Order order = orderList.get(i);
				String now = DateUtil.getSysDateTimeString();

				OcOrderKjtList okl = new OcOrderKjtList();
				okl.setUid(UUID.randomUUID().toString().replace("-", ""));
				okl.setOrderCodeSeq(order.getOrderCode());
				okl.setOrderCode(order_code);
				okl.setCreateTime(now);
				okl.setUpdateTime(now);
//				okl.setOrderCodeOut(order.getOrderCo);          Order 中无此字段
//				okl.setProductAmount(order.getProd);				 Order 中无此字段
//				okl.setTaxAmount(order.getTa);							 Order 中无此字段
//				okl.setShippingAmount(order.getSh);				 Order 中无此字段
				ocOrderKjtListDao.insertSelective(okl);

				OcOrderKjtListData okld = new OcOrderKjtListData();
				okld.setUid(UUID.randomUUID().toString().replace("-", ""));
				okld.setOrderCodeSeq(order.getOrderCode());
				okld.setRequestClazz(JSON.toJSONString(order));
				ocOrderKjtListDataDao.insertSelective(okld);

				List<OrderDetail> details = order.getProductList();
				for (OrderDetail orderDetail : details) {
					OcOrderKjtDetail okd = new OcOrderKjtDetail();
					okd.setUid(UUID.randomUUID().toString().replace("-", ""));
					okd.setOrderCode(order_code);
					okd.setOrderCodeSeq(order.getOrderCode());
					okd.setProductCode(orderDetail.getProductCode());
					okd.setSkuCode(orderDetail.getSkuCode());
					okd.setSkuName(orderDetail.getSkuName());
					okd.setSkuPrice(orderDetail.getSkuPrice());
					okd.setSkuNum(orderDetail.getSkuNum());
					okd.setProductCodeOut(orderDetail.getProductCodeOut());
					ocOrderKjtDetailDao.insertSelective(okd);
				}
			}

			for (Order order : orderList) {
				if (!rsyncOrder(order)) {
					process_succ = false;
				}
			}
		}

		return process_succ;
	}

	/**
	 * 拆单组单
	 * 
	 * @param order_code
	 */
	public List<Order> groupOrder(String order_code) {

		List<Order> orderList = new ArrayList<Order>();
		// 获取订单信息
		Order order = this.getOrder(order_code);
		List<OrderDetail> detailList = order.getProductList();

		// 此处更新商品的价格为 成本价
		for(int i = 0 ; i < detailList.size() ; i ++){
			PcProductinfo info = new PcProductinfo();
			info.setProductCode(detailList.get(i).getProductCode());
			PcProductinfo product = pcProductinfoDao.findByType(info);
			if(product != null){
				detailList.get(i).setCostPrice(product.getCostPrice() == null ? BigDecimal.ZERO : product.getCostPrice());
			}
		}

		// 拆单1 按商品拆单
		Map<String, List<OrderDetail>> listOrderMap1 = new HashMap<String, List<OrderDetail>>();
		for (OrderDetail orderDetail : detailList) {
			PcProductinfoExt extInfo = new PcProductinfoExt();
			extInfo.setProductCode(orderDetail.getProductCode());
			PcProductinfoExt productExt = pcProductinfoExtDao.findByType(extInfo);
			if(productExt == null){
				continue;
			}
			StringBuffer theKey = new StringBuffer();
			theKey.append(productExt).append("_").append(productExt.getDlrId()).append("_")
					.append(productExt.getProductStoreType()).append("_").append(productExt.getKjtSellerCode())
					.append("_").append(getWarehouseID(orderDetail.getStoreCode()));
			List<OrderDetail> olist = listOrderMap1.get(theKey.toString());
			if (olist == null) {
				olist = new ArrayList<OrderDetail>();
				listOrderMap1.put(theKey.toString(), olist);
			}
			olist.add(orderDetail);
		}

		int order_code_seq = 0;// 订单序列
		// 拆单2 按金额组单 循环太多 代码以后优化
		for (Map.Entry<String, List<OrderDetail>> map : listOrderMap1.entrySet()) {
			List<OrderDetail> list = map.getValue();
			List<OrderDetail> groupDetailBe = new ArrayList<OrderDetail>();
			for (OrderDetail orderDetail : list) {
				for (int i = 0; i < orderDetail.getSkuNum(); i++) {
					OrderDetail orderDetailc = SerializationUtils.clone(orderDetail);
					orderDetailc.setSkuNum(1);
					groupDetailBe.add(orderDetailc);
				}
			}

			// List<Map<String,OrderDetail>>
			// groupDetailAf=group(map.getKey(),groupDetailBe);
			List<Map<String, OrderDetail>> groupDetailAf = group(groupDetailBe);

			// 组建订单信息
			for (Map<String, OrderDetail> omap : groupDetailAf) {

				BigDecimal orderMoney = BigDecimal.ZERO;// 重新计算订单金额

				List<OrderDetail> detailListNew = new ArrayList<OrderDetail>();
				for (Map.Entry<String, OrderDetail> map2 : omap.entrySet()) {
					OrderDetail detail = map2.getValue();
					detailListNew.add(detail);
					orderMoney = orderMoney
							.add(detail.getCostPrice().multiply(new BigDecimal(String.valueOf(detail.getSkuNum()))));
				}

				Order norder = new Order();// 新订单
				norder.setOrderCode(order_code + "#" + (++order_code_seq));
				norder.setOrderMoney(orderMoney);

				norder.setAddress(order.getAddress());
				norder.setProductList(detailListNew);
				norder.setOcOrderPayList(order.getOcOrderPayList());

				orderList.add(norder);
			}
		}

		return orderList;
	}

	/**
	 * 获取订单
	 * 
	 * @param orderCode
	 * @return
	 */
	private Order getOrder(String orderCode) {
		Order order = new Order();
		OcOrderinfo i = ocOrderinfoDao.findOrderInfoByOrderCode(orderCode);
		if (i != null) {
			order.setUid(i.getUid());
			order.setOrderCode(i.getOrderCode());
			order.setOrderSource(i.getOrderSource());
			order.setOrderType(i.getOrderType());
			order.setOrderStatus(i.getOrderStatus());
			order.setSellerCode(i.getSellerCode());
			order.setBuyerCode(i.getBuyerCode());
			order.setPayType(i.getPayType());
			order.setSendType(i.getSendType());
			order.setProductMoney(i.getProductMoney());
			order.setTransportMoney(i.getTransportMoney());
			order.setPromotionMoney(i.getPromotionMoney());
			order.setOrderMoney(i.getOrderMoney());
			order.setPayedMoney(i.getPayedMoney());
			order.setCreateTime(i.getCreateTime());
			order.setUpdateTime(i.getUpdateTime());
			// order.setProductName() 数据库有 order实体没有
			order.setFreeTransportMoney(i.getFreeTransportMoney());
			order.setDueMoney(i.getDueMoney());
			order.setOrderChannel(i.getOrderChannel());
			order.setAppVersion(i.getAppVersion());
			// order.setDeleteFlag() 数据库有 order实体没有
			// order.setOutOrderCode 数据库有 order实体没有
			// order.setBigOrderCode 数据库有 order实体没有
			// order.setOrderStatusExt 数据库有 order实体没有
			order.setSmallSellerCode(i.getSmallSellerCode());
			order.setOrderSeq(i.getOrderSeq());
			// order.setOrderAuditStatus 数据库有 order实体没有
			order.setLowOrder(i.getLowOrder());
			// order.setRoomId 数据库有 order实体没有
			// order.setAnchorId 数据库有 order实体没有

			order.setAddress(this.getOrderAddressByOrderCode(orderCode));
			order.setActivityList(this.getOrderActivityListByOrderCode(orderCode));
			order.setOcOrderPayList(this.getOrderPayListByOrderCode(orderCode));
			order.setProductList(this.getOrderDetaiListByOrderCode(orderCode));
			order.setOcorderShipments(this.getOcOrderShipmentsByOrderCode(orderCode));
			order.setExpressList(this.getExpressList(orderCode));
		}
		return order;
	}

	/**
	 * 获取订单地址通过商品编号
	 * 
	 * @param orderCode
	 * @return
	 */
	private OrderAddress getOrderAddressByOrderCode(String orderCode) {
		OrderAddress a = new OrderAddress();
		OcOrderaddress i = ocOrderaddressDao.findOrderAddressByOrderCode(orderCode);
		if (i != null) {
			a.setOrderCode(i.getOrderCode());
			a.setAreaCode(i.getAreaCode());
			a.setAddress(i.getAddress());
			a.setPostCode(i.getPostcode());
			a.setMobilephone(i.getMobilephone());
			a.setTelephone(i.getTelephone());
			a.setReceivePerson(i.getReceivePerson());
			a.setEmail(i.getEmail());
			a.setInvoiceTitle(i.getInvoiceTitle());
			a.setInvoiceType(i.getInvoiceType());
			a.setInvoiceContent(i.getInvoiceContent());
			a.setFlagInvoice(i.getFlagInvoice().toString());
			a.setRemark(i.getRemark());
			// a.setIN invoiceStatus
			a.setAuthTrueName(i.getAuthTrueName());
			a.setAuthIdcardType(i.getAuthIdcardType());
			a.setAuthIdcardNumber(i.getAuthIdcardNumber());
			a.setAuthPhoneNumber(i.getAuthPhoneNumber());
			a.setAuthEmail(i.getAuthEmail());
			a.setAuthAddress(i.getAuthAddress());
		}
		return a;
	}

	/**
	 * 获取订单的活动list，通过商品编号
	 * 
	 * @param orderCode
	 * @return
	 */
	private List<OcOrderActivity> getOrderActivityListByOrderCode(String orderCode) {
		OcOrderActivity entity = new OcOrderActivity();
		entity.setOrderCode(orderCode);
		List<OcOrderActivity> list = ocOrderActivityDao.findList(entity);
		if (list == null) {
			list = new ArrayList<OcOrderActivity>();
		}
		return list;
	}

	/**
	 * 获取订单的支付list，通过商品编号
	 * 
	 * @param orderCode
	 * @return
	 */
	private List<OcOrderPay> getOrderPayListByOrderCode(String orderCode) {
		OcOrderPay entity = new OcOrderPay();
		entity.setOrderCode(orderCode);
		List<OcOrderPay> list = ocOrderPayDao.findList(entity);
		if (list == null) {
			list = new ArrayList<OcOrderPay>();
		}
		return list;
	}

	/**
	 * 获取订单的明细,通过商品编号
	 * 
	 * @param orderCode
	 * @return
	 */
	private List<OrderDetail> getOrderDetaiListByOrderCode(String orderCode) {
		OrderDetail entity = new OrderDetail();
		entity.setOrderCode(orderCode);
		List<OrderDetail> list = ocOrderdetailDao.findList(entity);
		if (list == null) {
			list = new ArrayList<OrderDetail>();
		}
		return list;
	}

	/**
	 * 获取订单运输信息
	 * 
	 * @param orderCode
	 * @return
	 */
	private OcOrderShipments getOcOrderShipmentsByOrderCode(String orderCode) {
		OcOrderShipments entity = new OcOrderShipments();
		entity.setOrderCode(orderCode);
		entity = ocOrderShipmentsDao.findByType(entity);
		if (entity == null) {
			entity = new OcOrderShipments();
		}
		return entity;
	}

	private List<Express> getExpressList(String orderCode) {
		List<Express> list = ocExpressEetailDao.selectByOrderCode(orderCode);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				OcOrderShipments entity = new OcOrderShipments();
				entity.setOrderCode(orderCode);
				if(list.get(i).getWaybill() != null){
					entity.setWaybill(list.get(i).getWaybill());
				}
				entity = ocOrderShipmentsDao.findByType(entity);
				if(entity != null){
					list.get(i).setLogisticseName(entity.getLogisticseName());
				}else{
					list.get(i).setLogisticseName("");
				}
			}
		}
		return list;
	}

	/**
	 * 同步跨境通订单，不拆单，直发
	 * 
	 * @param order
	 */
	public boolean rsyncOrder(Order order) {
		try {
			Thread.sleep(1000);    // 防止调用过快 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 判断订单状态 //领导强制添加：即使发货单写一半，也不再同步订单到跨境通
		OcOrderinfo entity = new OcOrderinfo();
		entity.setOrderCode(order.getAddress().getOrderCode());
		entity.setOrderStatus("4497153900010002");
		/**
		 * 根据 systemcenter.sc_define 表中的定义，串号意义如下：
		 * 4497153900010001 下单成功-未付款
		 * 4497153900010002 下单成功-未发货
		 * 4497153900010003 已发货
		 * 4497153900010004 已收货
		 * 4497153900010005 交易成功
		 * 4497153900010006 交易失败
		 * 4497153900010007 交易无效
		 */
		Integer count = ocOrderinfoDao.countByOrderCode(entity);
		if (count == null || count < 1) {
			return true;
		}

		OrderSoCreate orderSoCreate = new OrderSoCreate();

		OrderAddress orderAddress = order.getAddress();
		List<OrderDetail> detailList = order.getProductList();
		List<OcOrderPay> payList = order.getOcOrderPayList();

		OrderDetail orderDetail0 = detailList.get(0);
		OcOrderPay ocOrderPay = payList.get(payList.size() - 1);

		// 组装报文
		RsyncRequestOrderSoCreate requestOrderSoCreate = orderSoCreate.upRsyncRequest();

		requestOrderSoCreate.setSaleChannelSysNo(Long.valueOf(getConfig("seller_adapter_kjt.rsync_kjt_SaleChannelSysNo")));
		requestOrderSoCreate.setMerchantOrderID(order.getOrderCode());
		requestOrderSoCreate.setServerType(getServerType(orderDetail0.getProductCode())); // 直邮商品
																							// S01
																							// 自贸商品
																							// S02
		requestOrderSoCreate.setWarehouseID(getWarehouseID(orderDetail0.getStoreCode()));

		SOPayInfo soPayInfo = new SOPayInfo();
		soPayInfo.setShippingAmount(BigDecimal.ZERO);
		soPayInfo.setTaxAmount(BigDecimal.ZERO);
		soPayInfo.setCommissionAmount(BigDecimal.ZERO);
		soPayInfo.setProductAmount(order.getOrderMoney());

		// 添加对0元订单的支持 ，没有支付信息时，默认为支付宝
		String payType = PayTypeMapper.get(ocOrderPay.getPayType());
		if (StringUtils.isBlank(payType)) {
			soPayInfo.setPayTypeSysNo(112);
			soPayInfo.setPaySerialNumber(
					WebHelper.getInstance().genUniqueCode("88") + WebHelper.getInstance().genUniqueCode("88"));
		} else {
			soPayInfo.setPayTypeSysNo(Long.valueOf(payType));
			soPayInfo.setPaySerialNumber(
					WebHelper.getInstance().genUniqueCode("88") + ocOrderPay.getPaySequenceid().substring(14));
		}

		requestOrderSoCreate.setPayInfo(soPayInfo);

		SOShippingInfo soShippingInfo = new SOShippingInfo();
		soShippingInfo.setReceiveName(orderAddress.getReceivePerson());
		soShippingInfo.setReceivePhone(orderAddress.getMobilephone());
		soShippingInfo.setReceiveAddress(orderAddress.getAddress());
		soShippingInfo.setReceiveAreaCode(orderAddress.getAreaCode());
		soShippingInfo.setReceiveZip(orderAddress.getPostCode());
		soShippingInfo.setSenderName("");
		soShippingInfo.setSenderTel("");
		soShippingInfo.setSenderCompanyName("");
		soShippingInfo.setSenderAddr("");
		soShippingInfo.setSenderZip("");
		soShippingInfo.setSenderCity("");
		soShippingInfo.setSenderProvince("");
		soShippingInfo.setSenderCountry("");
		soShippingInfo.setReceiveAreaName(getAreaName(orderAddress.getAreaCode()));

		String kjt_shipTypeID = getConfig("seller_adapter_kjt.kjt_shipTypeID");
		soShippingInfo.setShipTypeID("-1".equals(kjt_shipTypeID) ? "" : kjt_shipTypeID);

		requestOrderSoCreate.setShippingInfo(soShippingInfo);

		SOAuthenticationInfo authenticationInfo = new SOAuthenticationInfo();
		if (StringUtils.isBlank(orderAddress.getAuthIdcardNumber())
				|| StringUtils.isBlank(orderAddress.getAuthEmail())) {
			// 没有 从系统取
			// 过度时期 ，需要系统认证
			AuthenticationInfo authen = getAuth(order.getOrderMoney());
			if (authen == null) {
				return false;
			}

			authenticationInfo.setName(authen.getTrue_name());
			authenticationInfo.setIDCardType(Integer.valueOf(IDcardMapper.get(authen.getIdcard_type())));
			authenticationInfo.setIDCardNumber(authen.getIdcard_number());
			authenticationInfo.setPhoneNumber(authen.getPhone_number());
			authenticationInfo.setEmail(authen.getEmail());
			authenticationInfo.setAddress(authen.getAddress());

		} else {// 如果有
			authenticationInfo.setName(orderAddress.getAuthTrueName());
			authenticationInfo.setIDCardType(Integer.valueOf(IDcardMapper.get(orderAddress.getAuthIdcardType())));
			authenticationInfo.setIDCardNumber(orderAddress.getAuthIdcardNumber());
			authenticationInfo.setPhoneNumber(orderAddress.getAuthPhoneNumber());
			authenticationInfo.setEmail(orderAddress.getAuthEmail());
			authenticationInfo.setAddress(orderAddress.getAuthAddress());
		}

		requestOrderSoCreate.setAuthenticationInfo(authenticationInfo);

		List<SOItemInfo> itemList = new ArrayList<SOItemInfo>();
		for (OrderDetail orderDetail : detailList) {
			SOItemInfo soItemInfo = new SOItemInfo();
			PcProductinfo info = new PcProductinfo();
			info.setProductCode(orderDetail.getProductCode());
			
			PcProductinfo product = pcProductinfoDao.findByType(info);
			soItemInfo.setProductID(product.getProductCodeOld());
			soItemInfo.setQuantity(orderDetail.getSkuNum());
			soItemInfo.setSalePrice(orderDetail.getCostPrice());// 商品售价
																// Sum(SalePrice*
																// Quantity)=PayInfo.ProductAmount

			// 需求：TaxAmount=0
			soItemInfo.setTaxPrice(BigDecimal.ZERO); // Sum(TaxPrice *
														// Quantity)=PayInfo.
														// TaxAmount
			itemList.add(soItemInfo);
		}

		requestOrderSoCreate.setItemList(itemList);

		// 同步
		orderSoCreate.doRsync();

		return orderSoCreate.responseSucc();
	}

	private String getAreaName(String area_code) {
		ScTmp p = new ScTmp();
		p.setCode(area_code.subSequence(0, 2) + "0000");
		p = scTmpDao.findByType(p);

		ScTmp c = new ScTmp();
		c.setCode(area_code.subSequence(0, 4) + "00");
		c = scTmpDao.findByType(c);

		ScTmp a = new ScTmp();
		a.setCode(area_code);
		a = scTmpDao.findByType(a);

		String prov = p.getName();
		String city = c.getName();
		String area = a.getName();

		return prov + "," + (StringUtils.startsWith(city, "省直辖县级行政区划") ? area : city) + "," + area;
	}

	/**
	 * S01：一般进口 S02：保税区进口 为空默认 S02
	 * 
	 * @param order
	 * @return
	 */
	private String getServerType(String product_code) {
		String type = "S02";
		// 直邮商品 S01 自贸商品 S02
		PcProductinfoExt entity = new PcProductinfoExt();
		entity.setProductCode(product_code);
		entity = pcProductinfoExtDao.findByType(entity);
		String product_trade_type = entity.getProductTradeType();

		// 0 = 直邮 1 = 自贸
		if (StringUtils.isNotBlank(product_trade_type)) {
			if ("0".equals(product_trade_type)) {
				type = "S01";
			}
		}
		return type;
	}

	/**
	 * 订单出库仓库在Kjt平台的编号
	 * 
	 * @param store_code
	 * @return
	 */
	private int getWarehouseID(String store_code) {
		try{
			if (StringUtils.isNotBlank(store_code)) {
				return Integer.valueOf(store_code.substring(0, store_code.indexOf("_")));
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	/**
	 * 支付方式
	 * 
	 * @param payType
	 * @return
	 */
	// 111: 东方支付
	// 112: 支付宝
	// 114: 财付通
	// 117: 银联支付
	// 118: 微信支付
	private static MDataMap PayTypeMapper = new MDataMap("449746280003", "112", "449746280005", "118");

	/**
	 * 随机获取一个可以用的真实认证信息
	 * 
	 * @param order_price
	 * @return
	 */
	private AuthenticationInfo getAuth(BigDecimal order_price) {
		McAuthenticationInfo entity = new McAuthenticationInfo();
		entity.setSurmoney(order_price);
		entity = mcAuthenticationInfoDao.findByOrderPrice(entity);
		if (entity != null) {
			McAuthenticationInfo entity_ = new McAuthenticationInfo();
			entity_.setSurmoney(order_price);
			entity.setAuthCode(entity.getAuthCode());
			mcAuthenticationInfoDao.updateSurmoneyByAuthCode(entity_);

			AuthenticationInfo info = new AuthenticationInfo();
			info.setAuth_code(entity.getAuthCode());
			info.setTrue_name(entity.getTrueName());
			info.setIdcard_type(entity.getIdcardType());
			info.setIdcard_number(entity.getIdcardNumber());
			info.setPhone_number(entity.getPhoneNumber());
			info.setEmail(entity.getEmail());
			info.setAddress(entity.getAddress());
			return info;
		} else {
			// 发邮件通知
			sendMailForAuth();
		}
		return null;
	}

	/**
	 * 证件类型转换
	 * 
	 * @param idcard_type
	 * @return
	 */
	private static MDataMap IDcardMapper = new MDataMap("4497465200090001", "0");

	/**
	 * 拆单规则变更： 每单商品总成本金额不能大于2000元
	 * 
	 * @param skuList
	 * @return
	 */
	private List<Map<String, OrderDetail>> group(List<OrderDetail> skuList) {

		sort(skuList);

		List<Map<String, OrderDetail>> list = new ArrayList<Map<String, OrderDetail>>();

		BigDecimal max_sum_price = new BigDecimal("50");
		// 单个订单最大订单金额
		BigDecimal maxCostPrice = new BigDecimal("2000");

		BigDecimal tprice = BigDecimal.ZERO;
		Map<String, OrderDetail> skus = new HashMap<String, OrderDetail>();

		for (OrderDetail sku : skuList) {

			if (BigDecimal.ZERO.compareTo(sku.getCostPrice()) >= 0
					|| BigDecimal.ZERO.compareTo(sku.getTaxRate()) >= 0) {
				String product_code = sku.getProductCode();

				PcProductinfo entity = new PcProductinfo();
				entity.setProductCode(product_code);
				entity = pcProductinfoDao.findByType(entity);
				if(entity != null){
					sku.setCostPrice(entity.getCostPrice());
					sku.setTaxRate(entity.getTaxRate());
				}else{
					sku.setCostPrice(new BigDecimal(0.00));
					sku.setTaxRate(new BigDecimal(0.00));
				}
			}

			// 根据完税价每单不超过2000的规则拆单
			tprice = tprice.add(sku.getCostPrice()); 
			if (tprice.compareTo(maxCostPrice) > 0) {      // if(tprice.compareTo(maxCostPrice) > 0)
				tprice = sku.getCostPrice();
				skus = new HashMap<String, OrderDetail>();
				list.add(skus);
			}

			if (list.size() < 1) {
				list.add(skus);
			}

			OrderDetail sku_old = skus.get(sku.getSkuCode());
			if (sku_old != null) {
				sku_old.setSkuNum(sku_old.getSkuNum() + sku.getSkuNum());
			} else {
				skus.put(sku.getSkuCode(), sku);
			}
		}

		return list;
	}

	private void sort(List<OrderDetail> list) {
		Collections.sort(list, new Comparator<OrderDetail>() {
			public int compare(OrderDetail sku1, OrderDetail sku2) {

				BigDecimal txp1 = sku1.getCostPrice().multiply(sku1.getTaxRate());
				BigDecimal txp2 = sku2.getCostPrice().multiply(sku2.getTaxRate());

				if (txp1.compareTo(txp2) > 0) {
					return 1;
				} else if (txp1.compareTo(txp2) == 0) {
					return 0;
				} else {
					return -1;
				}
			}
		});
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
	 * 订单状态映射
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

	private void sendMailForAuth() {

		String receives[] = getConfig("seller_adapter_kjt.kjt_auth_sendMail_receives").split(",");
		String title = getConfig("seller_adapter_kjt.kjt_auth_sendMail_title");
		String content = getConfig("seller_adapter_kjt.kjt_auth_sendMail_content");

		for (String receive : receives) {
			if (StringUtils.isNotBlank(receive)) {
				MailSupport.INSTANCE.sendMail(receive, title, content);
			}
		}
	}

}
