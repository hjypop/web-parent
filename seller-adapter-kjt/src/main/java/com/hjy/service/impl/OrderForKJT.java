package com.hjy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SerializationUtils;

import com.alibaba.fastjson.JSON;
import com.hjy.annotation.Inject;
import com.hjy.base.BaseClass;
import com.hjy.common.DateUtil;
import com.hjy.dao.IOcOrderKjtDetailDao;
import com.hjy.dao.IOcOrderKjtListDao;
import com.hjy.dao.IOcOrderKjtListDataDao;
import com.hjy.dao.order.IOcOrderActivityDao;
import com.hjy.dao.order.IOcOrderPayDao;
import com.hjy.dao.order.IOcOrderaddressDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.OcOrderKjtDetail;
import com.hjy.entity.OcOrderKjtList;
import com.hjy.entity.OcOrderKjtListData;
import com.hjy.entity.order.OcOrderActivity;
import com.hjy.entity.order.OcOrderPay;
import com.hjy.entity.order.OcOrderShipments;
import com.hjy.entity.order.OcOrderaddress;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.helper.WebHelper;
import com.hjy.model.MDataMap;
import com.hjy.model.order.Express;
import com.hjy.model.order.Order;
import com.hjy.model.order.OrderAddress;
import com.hjy.model.order.OrderDetail;
import com.hjy.support.MailSupport;
import com.hjy.support.SerializeSupport;

/**
 * 跨境通订单
 * @author jlin
 *
 */
public class OrderForKJT extends BaseClass {
	
	@Inject
	private IOcOrderKjtListDao  ocOrderKjtListDao;
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
	
	
	
	/**
	 * 同步跨境通订单
	 * @param order
	 */
	public boolean rsyncOrder(String order_code) {
		boolean process_succ=true; 
		List<OcOrderKjtList> list = ocOrderKjtListDao.findListByOrderCode(order_code);
		if(list.size()>0){
			for (OcOrderKjtList okl : list) {
				String order_code_seq = okl.getOrderCodeSeq();
				String order_code_out = okl.getOrderCodeOut();
				if(StringUtils.isBlank(order_code_out)){
					OcOrderKjtListData okld = new OcOrderKjtListData();
					okld.setOrderCodeSeq(order_code_seq);
					okld = ocOrderKjtListDataDao.findByType(okld);
					if(okld!=null){
						Order order = JSON.parseObject(okld.getRequestClazz() , Order.class);
						if(!rsyncOrder(order)){
							process_succ=false;
						}
					}
				}
			}
			
		}else{
			List<Order> orderList = groupOrder(order_code);
			for (int i = 0; i < orderList.size(); i++) {
				Order order = orderList.get(i);
				String now=DateUtil.getSysDateTimeString();
				
				OcOrderKjtList okl = new OcOrderKjtList();
				okl.setOrderCodeSeq(order.getOrderCode());
				okl.setOrderCode(order_code);
				okl.setCreateTime(now);
				okl.setUpdateTime(now);
				ocOrderKjtListDao.insertSelective(okl);
				
				OcOrderKjtListData okld = new OcOrderKjtListData();
				okld.setOrderCodeSeq(order.getOrderCode());
				okld.setRequestClazz(JSON.toJSONString(order)); 
				ocOrderKjtListDataDao.insertSelective(okld);
				
				List<OrderDetail> details=order.getProductList();
				for (OrderDetail orderDetail : details) {
					OcOrderKjtDetail okd = new OcOrderKjtDetail();
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
				if(!rsyncOrder(order)){
					process_succ=false;
				}
			}
		}
		
		return process_succ;
	}
	

	
	
	/**
	 * 拆单组单
	 * @param order_code
	 */
	public List<Order> groupOrder(String order_code){
		
		List<Order> orderList=new ArrayList<Order>();
		//获取订单信息
		Order order = this.getOrder(order_code);
		List<OrderDetail> detailList = order.getProductList();
		
		//此处更新商品的价格为 成本价
		for (OrderDetail orderDetail : detailList) {
			BigDecimal costPrice=(BigDecimal)DbUp.upTable("pc_productinfo").dataGet("cost_price", "product_code=:product_code", new MDataMap("product_code",orderDetail.getProductCode()));
			orderDetail.setCostPrice(costPrice==null?BigDecimal.ZERO:costPrice);
		}
		
		
		//拆单1 按商品拆单
		Map<String, List<OrderDetail>> listOrderMap1=new HashMap<String, List<OrderDetail>>();
		for (OrderDetail orderDetail : detailList) {
			
			MDataMap pext=DbUp.upTable("pc_productinfo_ext").oneWhere("dlr_id,product_trade_type,product_store_type,kjt_seller_code", "", "product_code=:product_code", "product_code",orderDetail.getProductCode());
			String theKey=pext.get("product_trade_type")+"_"+pext.get("dlr_id")+"_"+pext.get("product_store_type")+"_"+pext.get("kjt_seller_code")+"_"+getWarehouseID(orderDetail.getStoreCode());
			
			List<OrderDetail> olist=listOrderMap1.get(theKey);
			if(olist==null){
				olist = new ArrayList<OrderDetail>();
				listOrderMap1.put(theKey, olist);
			}
			olist.add(orderDetail);
		}
		
		int order_code_seq=0;//订单序列
		//拆单2 按金额组单   循环太多 代码以后优化
		for (Map.Entry<String, List<OrderDetail>> map : listOrderMap1.entrySet()) {
			List<OrderDetail> list=map.getValue();
			List<OrderDetail> groupDetailBe=new ArrayList<OrderDetail>();
			for (OrderDetail orderDetail : list) {
				for (int i = 0; i < orderDetail.getSkuNum(); i++) {
					OrderDetail orderDetailc=SerializationUtils.clone(orderDetail);
					orderDetailc.setSkuNum(1);
					groupDetailBe.add(orderDetailc);
				}
			}
			
//			List<Map<String,OrderDetail>> groupDetailAf=group(map.getKey(),groupDetailBe);
			List<Map<String,OrderDetail>> groupDetailAf=group(groupDetailBe);
			
			//组建订单信息
			for (Map<String,OrderDetail> omap : groupDetailAf) {
				
				BigDecimal orderMoney = BigDecimal.ZERO;//重新计算订单金额
				
				List<OrderDetail> detailListNew= new ArrayList<OrderDetail>();
				for (Map.Entry<String,OrderDetail> map2 : omap.entrySet()) {
					OrderDetail detail = map2.getValue();
					detailListNew.add(detail);
					orderMoney=orderMoney.add(detail.getCostPrice().multiply(new BigDecimal(String.valueOf(detail.getSkuNum()))));
				}
				
				Order norder=new Order();//新订单
				norder.setOrderCode(order_code+"#"+(++order_code_seq));
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
//			order.setProductName()      数据库有 order实体没有
			order.setFreeTransportMoney(i.getFreeTransportMoney());
			order.setDueMoney(i.getDueMoney());
			order.setOrderChannel(i.getOrderChannel());
			order.setAppVersion(i.getAppVersion());
//			order.setDeleteFlag()      数据库有 order实体没有  
//			order.setOutOrderCode      数据库有 order实体没有  
//			order.setBigOrderCode      数据库有 order实体没有  
//			order.setOrderStatusExt      数据库有 order实体没有  
			order.setSmallSellerCode(i.getSmallSellerCode());
			order.setOrderSeq(i.getOrderSeq());
//			order.setOrderAuditStatus      数据库有 order实体没有  
			order.setLowOrder(i.getLowOrder());
//			order.setRoomId      数据库有 order实体没有  
//			order.setAnchorId      数据库有 order实体没有  
			
			
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
//			a.setIN invoiceStatus
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
		if(list == null){
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
		if(list == null){
			list  = new ArrayList<OcOrderPay>();
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
		List<OrderDetail> ret = new ArrayList<OrderDetail>();
		MDataMap mapParam = new MDataMap();
		mapParam.put("order_code", orderCode);
		List<MDataMap> listMap = DbUp.upTable("oc_orderdetail").query("", "", "order_code=:order_code", mapParam, -1, -1);
		if (listMap != null) {
			int size = listMap.size();
			SerializeSupport ss = new SerializeSupport<OrderDetail>();
			for (int j = 0; j < size; j++) {
				OrderDetail pic = new OrderDetail();
				ss.serialize(listMap.get(j), pic);
				ret.add(pic);
			}
		}
		return ret;
	}
	
	/**
	 * 获取订单运输信息
	 * 
	 * @param orderCode
	 * @return
	 */
	private OcOrderShipments getOcOrderShipmentsByOrderCode(String orderCode) {
		OcOrderShipments ocOrderShipments = null;
		MDataMap dm = DbUp.upTable("oc_order_shipments").one("order_code", orderCode);
		if (dm != null) {
			SerializeSupport ss = new SerializeSupport<OcOrderShipments>();
			OcOrderShipments pic = new OcOrderShipments();
			ss.serialize(dm, pic);
			ocOrderShipments = pic;
		}
		return ocOrderShipments;
	}
	
	private List<Express> getExpressList(String orderCode) {
		List<Express> ret = new ArrayList<Express>();
		MDataMap mapParam = new MDataMap();
		mapParam.put("order_code", orderCode);
		List<MDataMap> listMap = DbUp.upTable("oc_express_detail").query("", "time asc", "order_code=:order_code", mapParam, -1, -1);
		int size = listMap.size();
		SerializeSupport ss = new SerializeSupport<Express>();
		MDataMap express = new MDataMap();
		for (int j = 0; j < size; j++) {
			Express pic = new Express();
			ss.serialize(listMap.get(j), pic);
			ret.add(pic);
			if (express.containsKey(pic.getWaybill())) {
				pic.setLogisticseName(express.get(pic.getWaybill()));
			} else {
				MDataMap mdOne = DbUp.upTable("oc_order_shipments").one(
						"order_code", orderCode, "waybill", pic.getWaybill());
				if (mdOne != null) {
					pic.setLogisticseName(mdOne.get("logisticse_name"));
				}
				express.put(pic.getWaybill(), pic.getLogisticseName());
			}
		}
		return ret;
	}
	
	
	
	
	
	
	
	/**
	 * 同步跨境通订单，不拆单，直发
	 * @param order
	 */
	public boolean rsyncOrder(Order order){
		
		//判断订单状态  //领导强制添加：即使发货单写一半，也不再同步订单到跨境通
		if(DbUp.upTable("oc_orderinfo").count("order_code",order.getAddress().getOrderCode(),"order_status","4497153900010002")<1){
			return true;
		}
		
		OrderSoCreate orderSoCreate = new OrderSoCreate();
		
		OrderAddress orderAddress= order.getAddress();
		List<OrderDetail> detailList = order.getProductList();
		List<OcOrderPay> payList=order.getOcOrderPayList();
		
		OrderDetail orderDetail0=detailList.get(0);
		OcOrderPay ocOrderPay = payList.get(payList.size()-1);
		
		
		//组装报文
		RsyncRequestOrderSoCreate requestOrderSoCreate = orderSoCreate.upRsyncRequest();
		
		requestOrderSoCreate.setSaleChannelSysNo(Long.valueOf(getConfig("groupcenter.rsync_kjt_SaleChannelSysNo")));
		requestOrderSoCreate.setMerchantOrderID(order.getOrderCode());
		requestOrderSoCreate.setServerType(getServerType(orderDetail0.getProductCode())); // 直邮商品 S01   自贸商品 S02
		requestOrderSoCreate.setWarehouseID(getWarehouseID(orderDetail0.getStoreCode()));
		
		SOPayInfo soPayInfo = new SOPayInfo();
		soPayInfo.setShippingAmount(BigDecimal.ZERO);
		soPayInfo.setTaxAmount(BigDecimal.ZERO);
		soPayInfo.setCommissionAmount(BigDecimal.ZERO);
		soPayInfo.setProductAmount(order.getOrderMoney());
		
		
		//添加对0元订单的支持 ，没有支付信息时，默认为支付宝
		String payType=PayTypeMapper.get(ocOrderPay.getPayType());
		if(StringUtils.isBlank(payType)){
			soPayInfo.setPayTypeSysNo(112);
			soPayInfo.setPaySerialNumber(WebHelper.upCode("88")+WebHelper.upCode("88"));
		}else{
			soPayInfo.setPayTypeSysNo(Long.valueOf(payType));
			soPayInfo.setPaySerialNumber(WebHelper.upCode("88")+ocOrderPay.getPaySequenceid().substring(14));
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
		
		String kjt_shipTypeID=getConfig("groupcenter.kjt_shipTypeID");
		soShippingInfo.setShipTypeID("-1".equals(kjt_shipTypeID)?"":kjt_shipTypeID);
		
		requestOrderSoCreate.setShippingInfo(soShippingInfo);
		
		SOAuthenticationInfo authenticationInfo = new SOAuthenticationInfo();
		if(StringUtils.isBlank(orderAddress.getAuthIdcardNumber())||StringUtils.isBlank(orderAddress.getAuthEmail())){
			//没有 从系统取
			//过度时期 ，需要系统认证
			AuthenticationInfo authen = getAuth(order.getOrderMoney());
			if(authen==null){
				return false;
			}
			
			authenticationInfo.setName(authen.getTrue_name());
			authenticationInfo.setIDCardType(Integer.valueOf(IDcardMapper.get(authen.getIdcard_type())));
			authenticationInfo.setIDCardNumber(authen.getIdcard_number());
			authenticationInfo.setPhoneNumber(authen.getPhone_number());
			authenticationInfo.setEmail(authen.getEmail());
			authenticationInfo.setAddress(authen.getAddress());
			
		}else{//如果有
			authenticationInfo.setName(orderAddress.getAuthTrueName());
			authenticationInfo.setIDCardType(Integer.valueOf(IDcardMapper.get(orderAddress.getAuthIdcardType())));
			authenticationInfo.setIDCardNumber(orderAddress.getAuthIdcardNumber());
			authenticationInfo.setPhoneNumber(orderAddress.getAuthPhoneNumber());
			authenticationInfo.setEmail(orderAddress.getAuthEmail());
			authenticationInfo.setAddress(orderAddress.getAuthAddress());
		}
		
		
		
		requestOrderSoCreate.setAuthenticationInfo(authenticationInfo);
		
		List<SOItemInfo> itemList= new ArrayList<SOItemInfo>();
		for (OrderDetail orderDetail : detailList) {
			SOItemInfo soItemInfo = new SOItemInfo();
			soItemInfo.setProductID((String)DbUp.upTable("pc_productinfo").dataGet("product_code_old", "", new MDataMap("product_code",orderDetail.getProductCode())));
			soItemInfo.setQuantity(orderDetail.getSkuNum());
			soItemInfo.setSalePrice(orderDetail.getCostPrice());//商品售价   Sum(SalePrice* Quantity)=PayInfo.ProductAmount
			
			//需求：TaxAmount=0 
			soItemInfo.setTaxPrice(BigDecimal.ZERO); // Sum(TaxPrice * Quantity)=PayInfo. TaxAmount
			itemList.add(soItemInfo);
		}
		
		requestOrderSoCreate.setItemList(itemList);
		
		//同步
		orderSoCreate.doRsync();
		
		return orderSoCreate.responseSucc();
	}
	private String getAreaName(String area_code){
		String prov=DbUp.upTable("sc_tmp").one("code", area_code.subSequence(0, 2)+ "0000").get("name");
		String city=DbUp.upTable("sc_tmp").one("code", area_code.subSequence(0, 4)+ "00").get("name");
		String area=DbUp.upTable("sc_tmp").one("code", area_code).get("name");
//		Map<String, Object> map = DbUp.upTable("sc_tmp").dataSqlOne(" SELECT CONCAT((SELECT name from sc_tmp WHERE code=:code1 LIMIT 0,1 ),',',(SELECT DISTINCT name from sc_tmp WHERE code=:code2 LIMIT 0,1 ),',',(SELECT DISTINCT name from sc_tmp WHERE code=:code3 LIMIT 0,1 )) as code from sc_tmp LIMIT 0,1  ",new MDataMap("code1", area_code.subSequence(0, 2)+ "0000", "code2", area_code.subSequence(0, 4)+ "00", "code3", area_code));
		return prov+","+(StringUtils.startsWith(city, "省直辖县级行政区划")?area:city)+","+area;
	}
	
	/**
	 * S01：一般进口 S02：保税区进口 为空默认 S02
	 * @param order
	 * @return
	 */
	private String getServerType(String product_code){
		String type="S02";
		//直邮商品 S01   自贸商品 S02
		String product_trade_type=(String)DbUp.upTable("pc_productinfo_ext").dataGet("product_trade_type", "product_code=:product_code", new MDataMap("product_code",product_code));
//		0 = 直邮 1 = 自贸
		if(StringUtils.isNotBlank(product_trade_type)){
			if("0".equals(product_trade_type)){
				type="S01";
			}
		}
		return type;
	}
	
	/**
	 * 订单出库仓库在Kjt平台的编号
	 * @param store_code
	 * @return
	 */
	private int getWarehouseID(String store_code) {
		if(StringUtils.isNotBlank(store_code)){
			return Integer.valueOf(store_code.substring(0,store_code.indexOf("_")));
		}
		return -1;
	}
	
	
	/**
	 * 支付方式
	 * @param payType
	 * @return
	 */
//							111: 东方支付
//	112: 支付宝
//							114: 财付通
//							117: 银联支付
//	118: 微信支付
	private static MDataMap PayTypeMapper = new MDataMap("449746280003","112","449746280005","118");
	
	/**
	 * 随机获取一个可以用的真实认证信息
	 * @param order_price
	 * @return
	 */
	private AuthenticationInfo getAuth(BigDecimal order_price){
		MDataMap dataMap = DbUp.upTable("mc_authenticationInfo").oneWhere("", "surmoney desc", "surmoney>=:order_price", "order_price",String.valueOf(order_price));
		
		if (dataMap!=null) {
			
			DbUp.upTable("mc_authenticationInfo").dataExec("update mc_authenticationInfo set surmoney=surmoney-"+order_price+" where auth_code=:auth_code", new MDataMap("auth_code",dataMap.get("auth_code")));
			
			SerializeSupport<AuthenticationInfo> ss = new SerializeSupport<AuthenticationInfo>();
			AuthenticationInfo authenticationInfo = new AuthenticationInfo();
			ss.serialize(dataMap, authenticationInfo);
			return authenticationInfo;
		}else{
			//发邮件通知
			sendMailForAuth();
		}
		return null;
	}
	
	/**
	 * 证件类型转换
	 * @param idcard_type
	 * @return
	 */
	private static MDataMap IDcardMapper = new MDataMap("4497465200090001","0");
	
	
	
	
	/**
	 * 拆单规则变更： 每单商品总成本金额不能大于2000元
	 * @param skuList
	 * @return
	 */
	private List<Map<String,OrderDetail>> group (List<OrderDetail> skuList){
		
		sort(skuList);
		
		
		List<Map<String,OrderDetail>> list = new ArrayList<Map<String,OrderDetail>>();
		
		BigDecimal max_sum_price=new BigDecimal("50");
		// 单个订单最大订单金额
		BigDecimal maxCostPrice = new BigDecimal("2000");
		
		BigDecimal tprice = BigDecimal.ZERO;
		Map<String,OrderDetail> skus=new HashMap<String,OrderDetail>();
		
		for (OrderDetail sku : skuList) {
			
			if(BigDecimal.ZERO.compareTo(sku.getCostPrice())>=0||BigDecimal.ZERO.compareTo(sku.getTaxRate())>=0){
				
				String product_code=sku.getProductCode();
				MDataMap proMap=DbUp.upTable("pc_productinfo").one("product_code",product_code);
				sku.setCostPrice(new BigDecimal(proMap.get("cost_price")));
				sku.setTaxRate(new BigDecimal(proMap.get("tax_rate")));
			}
			
			// 根据完税价每单不超过2000的规则拆单
			tprice = tprice.add(sku.getCostPrice());
			if(tprice.compareTo(maxCostPrice) > 0){
				tprice = sku.getCostPrice();
				skus = new HashMap<String,OrderDetail>();
				list.add(skus);
			}
			
			if(list.size() < 1){
				list.add(skus);
			}
			
			OrderDetail sku_old=skus.get(sku.getSkuCode());
			if(sku_old!=null){
				sku_old.setSkuNum(sku_old.getSkuNum()+sku.getSkuNum());
			}else{
				skus.put(sku.getSkuCode(),sku);
			}
		}
		
		return list;
	}
	
	private void sort(List<OrderDetail> list) {
		Collections.sort(list, new Comparator<OrderDetail>() {
			public int compare(OrderDetail sku1, OrderDetail sku2) {
				
				BigDecimal txp1=sku1.getCostPrice().multiply(sku1.getTaxRate());
				BigDecimal txp2=sku2.getCostPrice().multiply(sku2.getTaxRate());

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
	
//	-4	系统作废	交易失败
//	-1	作废	交易失败
//	0	待审核	下单成功-未发货
//	1	待出库	下单成功-未发货
//	4	已出库待申报	下单成功-未发货  已发货
//	41	已申报待通关	下单成功-未发货  已发货
//	45	已通过发往顾客	已发货
//	5	订单完成	交易成功
//	6	申报失败订单作废	交易失败
//	65	通关失败订单作废	交易失败
//	7	订单拒收	交易失败

//	4497153900010001	下单成功-未付款
//	4497153900010002	下单成功-未发货
//	4497153900010003	已发货
//	4497153900010004	已收货
//	4497153900010005	交易成功
//	4497153900010006	交易失败
	
	/**
	 * 订单状态映射
	 * @param ostatus
	 * @return
	 */
	public static String orderStatusMapper(int ostatus){
		
		String status=null;
		
		switch (ostatus) {
		case -4:
			status="4497153900010006";
			break;
		case -1:
			status="4497153900010006";
			break;
		case 0:
			status="4497153900010002";
			break;
		case 1:
			status="4497153900010002";
			break;
		case 4:
			status="4497153900010003";
			break;	
		case 41:
			status="4497153900010003";
			break;		
		case 45:
			status="4497153900010003";
			break;	
		case 5:
			status="4497153900010005";
			break;	
		case 6:
			status="4497153900010006";
			break;	
		case 65:
			status="4497153900010006";
			break;	
		case 7:
			status="4497153900010006";
			break;	
		default:
			 status="";
			break;
		}
		return status;
	}
	
	private void sendMailForAuth(){
		
		String receives[]= getConfig("groupcenter.kjt_auth_sendMail_receives").split(",");
		String title= getConfig("groupcenter.kjt_auth_sendMail_title");
		String content= getConfig("groupcenter.kjt_auth_sendMail_content");
		
		for (String receive : receives) {
			if(StringUtils.isNotBlank(receive)){
				MailSupport.INSTANCE.sendMail(receive, title, content);
			}
		}
	}
	
}



























