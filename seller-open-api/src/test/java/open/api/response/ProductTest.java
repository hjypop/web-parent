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
import com.hjy.base.BaseTest;
import com.hjy.dto.product.PcSkuInfo;
import com.hjy.dto.product.ProductInfo;
import com.hjy.dto.product.Productdescription;
import com.hjy.request.RequestProducts;
import com.hjy.response.product.ResponseProduct;
import com.hjy.service.product.IApiProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class ProductTest extends BaseTest {

	@Autowired
	private IApiProductService service;

	@Test
	public void addProduct() {
		ProductInfo product = new ProductInfo();
		product.setProductCode("外部商品编码");
		product.setProductName("测试商品");
		product.setProductShortname("测试商品");
		product.setProductWeight(BigDecimal.ZERO);
		product.setCostPrice(BigDecimal.TEN);
		product.setMainPicUrl("www.baidu.com");
		product.setLabels("测试openapi商品");
		product.setProductVolumeItem("10,10,10");
		product.setProductVolume(BigDecimal.ONE);
		product.setBrandCode("10002");
		product.setOperate(0);
		/**
		 * 描述
		 */
		Productdescription description = new Productdescription();
		description.setDescriptionInfo("描述信息");
		description.setDescriptionPic("描述图片");
		description.setKeyword("关键字");
		product.setDescription(description);
		/**
		 * 图片
		 */
		List<String> pic = new ArrayList<String>();
		pic.add("添加商品图片");
		product.setPcPicList(pic);
		/**
		 * sku
		 */
		List<PcSkuInfo> skuList = new ArrayList<PcSkuInfo>();
		PcSkuInfo sku = new PcSkuInfo();
		sku.setSellPrice(BigDecimal.ONE);
		sku.setSkuName("sku名称");
		sku.setSkuCode("外部sku编号");
		sku.setSkuPicUrl("商品的Sku的图片信息");
		sku.setStockNum(Long.valueOf("10"));
		sku.setSkuAdv("sku广告语");
		sku.setSellPrice(BigDecimal.TEN);
		sku.setSecurityStockNum(0);
		sku.setStockNum(0);
		sku.setQrcodeLink("二维码图片链接");
		sku.setMiniOrder(1);
		skuList.add(sku);
		product.setSkuInfoList(skuList);
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		productList.add(product);
		RequestProducts request = new RequestProducts();
		request.setProductInfos(productList);
		request.setTotal(productList.size());
		JSONObject obj = (JSONObject) JSON.toJSON(request);
		System.out.println(obj.toJSONString());
		ResponseProduct response = service.syncProductList(obj.toJSONString());
		System.out.println(JSON.toJSON(response));
	}
}
