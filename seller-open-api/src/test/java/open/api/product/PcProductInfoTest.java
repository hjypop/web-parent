package open.api.product;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.base.BaseTest;
import com.hjy.common.DateUtil;
import com.hjy.controller.DataInit;
import com.hjy.dao.api.IApiProductInfoDao;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.helper.SignHelper;
import com.hjy.helper.WebHelper;
import com.hjy.request.Request;
import com.hjy.service.product.IApiProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class PcProductInfoTest extends BaseTest {

	@Autowired
	private IApiProductInfoDao dao;
	@Autowired
	private IApiProductService service;

	public void getProductDescByCode() {
		String productCode = "127483";
		System.out.println(JSON.toJSON(dao.getProductDescByCode(productCode)));
	}

	public void getProductPicByCode() {
		String productCode = "127483";
		System.out.println(JSON.toJSON(dao.getProductPicByCode(productCode)));
	}

	public void findProductBySellerProductype() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("LD", "LD");
		map.put("sellerType", "4497478100050003");
		map.put("startTime", "2016-05-31 00:00:00");
		map.put("endTime", "2016-06-31 23:59:59");
		List<PcProductinfo> list = dao.findProductBySellerProductype(map);
		System.out.println(list.size());
	}
	
	public void pushProduct() {
		System.out.println(DateUtil.getSysDateTimeString());
		WcSellerinfo seller = new WcSellerinfo();
		seller.setCommission(
				"[{\"type\":\"LD\",\"commission\":\"12\"},{\"type\":\"4497478100050001\",\"commission\":\"33\"}]");
		seller.setPriceType(0);
		JSONObject obj = service.pushProduct(seller, "2016-05-31", "2016-06-31");
		System.out.println(DateUtil.getSysDateTimeString());
		System.out.println(obj.toJSONString());
	}

	@Test
	public void pushSkuStock() {
		String productCodes = "101392,100793";
		WcSellerinfo seller = new WcSellerinfo();
		seller.setSellerCode("22911");
		System.out.println(service.pushSkuStock(seller, productCodes));
	}

	public void pushProductPrice() {
		System.out.println(DateUtil.getSysDateTimeString());
		WcSellerinfo seller = new WcSellerinfo();
		seller.setCommission(
				"[{\"type\":\"LD\",\"commission\":\"12\"},{\"type\":\"4497478100050001\",\"commission\":\"33\"}]");
		seller.setPriceType(0);
		String productCodes = "8016408754,798107";
		JSONObject obj = service.pushProductPrice(seller, productCodes);
		System.out.println(DateUtil.getSysDateTimeString());
		System.out.println(obj.toJSONString());
	}

	public void isSign() {
		boolean flag = false;
		try {

			Request request = DataInit.orderInfoBatchInsertTest();
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", request.getAppid());
			map.put("data", URLEncoder.encode(request.getData(), "UTF-8"));
			map.put("method", request.getMethod());
			map.put("timestamp", request.getTimestamp());
			map.put("nonce", request.getNonce());
			List<String> list = new ArrayList<String>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (entry.getValue() != "") {
					list.add(entry.getKey() + "=" + entry.getValue() + "&");
				}
			}
			Collections.sort(list); // 对List内容进行排序
			StringBuffer str = new StringBuffer();
			for (String nameString : list) {
				str.append(nameString);
			}
			str.append(request.getAppSecret());
			String sign = SignHelper.md5Sign(str.toString());
			System.out.println(sign);
			if (sign.equals(request.getSign())) {
				System.out.println("sign校验成功");
			} else {
				System.out.println("sign校验失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
