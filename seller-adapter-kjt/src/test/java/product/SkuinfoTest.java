package product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.service.product.IPcSkuinfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class SkuinfoTest {

	@Autowired
	private IPcSkuinfoService service;

	@Test
	public void findSkuCodeByProductCode() {
		String productCode = "107700";
		System.out.println(service.findSkuCodeByProductCode(productCode));
	}
}
