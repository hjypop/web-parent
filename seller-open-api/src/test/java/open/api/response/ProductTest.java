package open.api.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjy.annotation.Inject;
import com.hjy.dto.product.PcSkuInfo;
import com.hjy.dto.product.ProductInfo;
import com.hjy.dto.product.Productdescription;
import com.hjy.request.RequestProduct;
import com.hjy.service.product.IApiProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class ProductTest {

	@Autowired
	private IApiProductService service;

	@Test
	public void addProduct() {
		ProductInfo product = new ProductInfo();
		product.setProductCode("88911933");
		product.setProductName("测试商品");
		product.setProductShortname("测试商品");
		product.setProductWeight(BigDecimal.ZERO);
		product.setCostPrice(BigDecimal.TEN);
		product.setMainPicUrl("www.baidu.com");
		product.setLabels("测试openapi商品");
		product.setProductVolumeItem("10,10,10");
		product.setProductVolume(BigDecimal.ONE);
		product.setBrandCode("10002");
		/**
		 * 描述
		 */
		Productdescription description = new Productdescription();
		product.setDescription(description);
		/**
		 * 图片
		 */
		List<String> pic = new ArrayList<String>();
		product.setPcPicList(pic);
		/**
		 * sku
		 */
		List<PcSkuInfo> skuList = new ArrayList<PcSkuInfo>();
		PcSkuInfo sku = new PcSkuInfo();
		sku.setSellPrice(BigDecimal.ONE);
		sku.setStockNum(Long.valueOf("10"));
		sku.setSkuAdv("sku广告语");
		skuList.add(sku);
		product.setSkuInfoList(skuList);
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		productList.add(product);
		RequestProduct request = new RequestProduct();
		request.setProductInfos(productList);
		request.setTotal(productList.size());
		JSONObject obj = (JSONObject) JSON.toJSON(request);
		// System.out.println(obj.toJSONString());
		service.addProduct(obj.toJSONString());
	}
}
