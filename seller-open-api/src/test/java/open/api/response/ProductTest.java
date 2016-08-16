package open.api.response;

import java.math.BigDecimal;
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
import com.hjy.common.bill.HexUtil;
import com.hjy.common.bill.MD5Util;
import com.hjy.dto.product.PcSkuInfo;
import com.hjy.dto.product.ProductInfo;
import com.hjy.dto.product.Productdescription;
import com.hjy.helper.SignHelper;
import com.hjy.request.Request;
import com.hjy.request.RequestProduct;
import com.hjy.request.RequestProducts;
import com.hjy.service.product.IApiProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class ProductTest extends BaseTest {

	@Autowired
	private IApiProductService service;

	@Test
	public void syncProductList() {
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		for (int i = 1; i < 6; i++) {
			ProductInfo product = new ProductInfo();
			product.setProductOutCode("WBPD00" + i);
			product.setProductName("测试商品-" + i);
			product.setProductShortname("测试商品-" + i);
			product.setProductWeight(BigDecimal.ZERO);
			product.setCostPrice(BigDecimal.TEN);
			product.setMainPicUrl("www.baidu.com");
			product.setLabels("测试openapi商品-" + i);
			product.setProductVolumeItem("10,10,10");
			product.setProductVolume(BigDecimal.ONE);
			product.setBrandCode("10002");
			product.setOperate(0);
			/**
			 * 描述
			 */
			Productdescription description = new Productdescription();
			description.setDescriptionInfo("描述信息-" + i);
			description.setDescriptionPic("描述图片-" + i);
			description.setKeyword("关键字-" + i);
			product.setDescription(description);
			/**
			 * 图片
			 */
			List<String> pic = new ArrayList<String>();
			pic.add("添加商品图片-" + i);
			product.setPcPicList(pic);
			/**
			 * sku
			 */
			List<PcSkuInfo> skuList = new ArrayList<PcSkuInfo>();
			for (int j = 1; j < 6; j++) {
				PcSkuInfo sku = new PcSkuInfo();
				sku.setSellPrice(BigDecimal.ONE);
				sku.setSkuName("sku名称-" + j);
				sku.setSkuCode("WBSKU00" + j);
				sku.setSkuPicUrl("商品的Sku的图片信息-" + j);
				sku.setStockNum(Long.valueOf("10"));
				sku.setSkuAdv("sku广告语-" + j);
				sku.setSellPrice(BigDecimal.TEN);
				sku.setSecurityStockNum(10);
				sku.setStockNum(10);
				sku.setQrcodeLink("二维码图片链接-" + j);
				sku.setMiniOrder(1);
				skuList.add(sku);
			}
			product.setSkuInfoList(skuList);
			productList.add(product);
		}

		RequestProducts request = new RequestProducts();
		request.setProductInfos(productList);
		request.setTotal(productList.size());
		JSONObject obj = (JSONObject) JSON.toJSON(request);
		/**
		 * 生成requeset请求对象
		 */
		try {
			Request req = new Request();
			req.setAppid("appid-order-list");
			req.setAppSecret("1122334");
			req.setData(obj.toJSONString());
			req.setMethod("Product.batchProductsSkuStore");
			req.setNonce("13332");
			req.setTimestamp("1471339012182");
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", req.getAppid());
			map.put("data", URLEncoder.encode(req.getData(), "UTF-8"));
			map.put("method", req.getMethod());
			map.put("timestamp", req.getTimestamp());
			map.put("nonce", req.getNonce());
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
			str.append(req.getAppSecret());
			String sign = HexUtil.toHexString(MD5Util.md5(str.toString()));
			req.setSign(sign);
			System.out.println(str.toString());
			System.out.println(sign);
			System.out.println(JSON.toJSON(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addProduct() {
		ProductInfo product = new ProductInfo();
		product.setProductOutCode("WBPD001");
		product.setProductName("添加-测试商品");
		product.setProductShortname("添加-测试商品");
		product.setProductWeight(BigDecimal.ZERO);
		product.setCostPrice(BigDecimal.TEN);
		product.setMainPicUrl("www.baidu.com");
		product.setLabels("添加-测试openapi商品");
		product.setProductVolumeItem("10,10,10");
		product.setProductVolume(BigDecimal.ONE);
		product.setBrandCode("10002");
		product.setOperate(0);
		/**
		 * 描述
		 */
		Productdescription description = new Productdescription();
		description.setDescriptionInfo("添加-描述信息");
		description.setDescriptionPic("添加-描述图片");
		description.setKeyword("添加-关键字");
		product.setDescription(description);
		/**
		 * 图片
		 */
		List<String> pic = new ArrayList<String>();
		pic.add("添加-商品图片");
		product.setPcPicList(pic);
		/**
		 * sku
		 */
		List<PcSkuInfo> skuList = new ArrayList<PcSkuInfo>();
		PcSkuInfo sku = new PcSkuInfo();
		sku.setSellPrice(BigDecimal.ONE);
		sku.setSkuName("添加-sku名称");
		sku.setSkuCode("WBSKU001");
		sku.setSkuPicUrl("添加-商品的Sku的图片信息");
		sku.setStockNum(Long.valueOf("10"));
		sku.setSkuAdv("添加-sku广告语");
		sku.setSellPrice(BigDecimal.TEN);
		sku.setSecurityStockNum(0);
		sku.setStockNum(10);
		sku.setQrcodeLink("添加-二维码图片链接");
		sku.setMiniOrder(1);
		skuList.add(sku);
		product.setSkuInfoList(skuList);
		/**
		 * 生成requeset请求对象
		 */
		Request req = new Request();
		req.setAppid("");
		req.setAppSecret("");
		req.setData(JSON.toJSONString(product));
		req.setMethod("Product.addProduct");
		req.setNonce("13332");
		req.setTimestamp(DateUtil.getSysDateTimestamp().getTime() + "");
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", req.getAppid());
		map.put("data", req.getData());
		map.put("method", req.getMethod());
		map.put("timestamp", req.getTimestamp());
		map.put("nonce", req.getNonce());
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
		str.append(req.getAppSecret());
		String sign = SignHelper.md5Sign(str.toString());
		req.setSign(sign);
		System.out.println(JSON.toJSON(req));
		// System.out.println(JSON.toJSON(req));
		// JSONObject response = service.addProduct(obj.toJSONString(),
		// "SI2003");
		// System.out.println(response.toJSONString());
	}

	public void editProduct() {
		ProductInfo product = new ProductInfo();
		product.setProductOutCode("WBPD001");
		product.setProductName("编辑-测试商品");
		product.setProductShortname("编辑-测试商品");
		product.setProductWeight(BigDecimal.ZERO);
		product.setCostPrice(BigDecimal.TEN);
		product.setMainPicUrl("www.baidu.com");
		product.setLabels("编辑-测试openapi商品");
		product.setProductVolumeItem("10,10,10");
		product.setProductVolume(BigDecimal.ONE);
		product.setBrandCode("10002");
		product.setOperate(1);
		/**
		 * 描述
		 */
		Productdescription description = new Productdescription();
		description.setDescriptionInfo("编辑-描述信息");
		description.setDescriptionPic("编辑-描述图片");
		description.setKeyword("编辑-关键字");
		product.setDescription(description);
		/**
		 * 图片
		 */
		List<String> pic = new ArrayList<String>();
		pic.add("编辑-商品图片");
		product.setPcPicList(pic);
		/**
		 * sku
		 */
		List<PcSkuInfo> skuList = new ArrayList<PcSkuInfo>();
		PcSkuInfo sku = new PcSkuInfo();
		sku.setSellPrice(BigDecimal.ONE);
		sku.setSkuName("编辑-sku名称");
		sku.setSkuCode("WBSKU001");
		sku.setSkuPicUrl("编辑-商品的Sku的图片信息");
		sku.setStockNum(Long.valueOf("10"));
		sku.setSkuAdv("编辑-sku广告语");
		sku.setSellPrice(BigDecimal.TEN);
		sku.setSecurityStockNum(20);
		sku.setStockNum(20);
		sku.setQrcodeLink("编辑-二维码图片链接");
		sku.setMiniOrder(1);
		skuList.add(sku);
		product.setSkuInfoList(skuList);
		/**
		 * 生成requeset请求对象
		 */
		Request req = new Request();
		req.setAppid("");
		req.setAppSecret("");
		req.setData(JSON.toJSONString(product));
		req.setMethod("Product.editProduct");
		req.setNonce("13332");
		req.setTimestamp(DateUtil.getSysDateTimestamp().getTime() + "");
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", req.getAppid());
		map.put("data", req.getData());
		map.put("method", req.getMethod());
		map.put("timestamp", req.getTimestamp());
		map.put("nonce", req.getNonce());
		List<String> list = new ArrayList<String>();
		RequestProduct request = new RequestProduct();
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
		String sign = HexUtil.toHexString(MD5Util.md5(str.toString()));
		req.setSign(sign);
		System.out.println(req.getData());
		// JSONObject response = service.editProduct(obj.toJSONString(),
		// "SI2003");
		// System.out.println(JSON.toJSON(response));
	}

	public void syncProductPrice() {
		ProductInfo product = new ProductInfo();
		product.setProductOutCode("WBPD001");
		product.setMarketPrice(BigDecimal.ONE);
		product.setCostPrice(BigDecimal.ONE);
		List<PcSkuInfo> skuList = new ArrayList<PcSkuInfo>();
		for (int i = 1; i < 3; i++) {
			PcSkuInfo sku = new PcSkuInfo();
			sku.setSkuCode("WBSKU00" + i);
			sku.setSellPrice(BigDecimal.valueOf(Double.valueOf("35.55")));
			skuList.add(sku);
		}
		product.setSkuInfoList(skuList);
		List<ProductInfo> products = new ArrayList<ProductInfo>();
		products.add(product);
		/**
		 * 生成requeset请求对象
		 */
		Request req = new Request();
		req.setAppid("");
		req.setAppSecret("");
		req.setData(JSON.toJSONString(products));
		req.setMethod("Product.batchProductsPrice");
		req.setNonce("13332");
		req.setTimestamp(DateUtil.getSysDateTimestamp().getTime() + "");
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", req.getAppid());
		map.put("data", req.getData());
		map.put("method", req.getMethod());
		map.put("timestamp", req.getTimestamp());
		map.put("nonce", req.getNonce());
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
		str.append(req.getAppSecret());
		String sign = SignHelper.md5Sign(str.toString());
		// HexUtil.toHexString(MD5Util.md5(str.toString()));
		System.out.println(str.toString());
		req.setSign(sign);
		// System.out.println(req.getData());
		System.out.println(JSON.toJSON(req));
		// JSONObject response = service.batchProductsPrice(obj.toJSONString(),
		// "SI2003");
		// System.out.print ln(response.toJSONString());
	}

	public void syncSkuStore() {
		try {
			ProductInfo product = new ProductInfo();
			product.setProductOutCode("WBPD001");
			List<PcSkuInfo> skuList = new ArrayList<PcSkuInfo>();
			for (int i = 1; i < 3; i++) {
				PcSkuInfo sku = new PcSkuInfo();
				sku.setSkuCode("WBSKU00" + i);
				sku.setStockNum(100);
				skuList.add(sku);
			}
			product.setSkuInfoList(skuList);
			RequestProducts request = new RequestProducts();
			List<ProductInfo> products = new ArrayList<ProductInfo>();
			products.add(product);
			request.setProductInfos(products);
			JSONObject obj = (JSONObject) JSON.toJSON(request);
			/**
			 * 生成requeset请求对象
			 */
			Request req = new Request();
			req.setAppid("appid-order-list");
			req.setAppSecret("1122334");
			req.setData(obj.toJSONString());
			req.setMethod("Product.batchProductsSkuStore");
			req.setNonce("13332");
			req.setTimestamp("1471339012182");
			Map<String, String> map = new HashMap<String, String>();
			map.put("appid", req.getAppid());
			map.put("data", URLEncoder.encode(req.getData(), "UTF-8"));
			map.put("method", req.getMethod());
			map.put("timestamp", req.getTimestamp());
			map.put("nonce", req.getNonce());
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
			str.append(req.getAppSecret());
			String sign = SignHelper.md5Sign(str.toString());
			System.out.println(sign);
			System.out.println(str.toString());
			System.out.println("------------------");
			req.setSign(sign);
			System.out.println(JSON.toJSON(req));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
