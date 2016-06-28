package product;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.entity.product.PcProductinfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class ProductTest {

	@Autowired
	private IPcProductinfoDao dao;

	public void findProductCodeOld() {
		PcProductinfo info = new PcProductinfo();
		info.setSellerCode("SI2003");
		info.setSmallSellerCode("SF03KJT");
		List<String> list = dao.findProductCodeOld(info);
		System.out.println(list.size());
	}

	@Test
	public void findProductCodeByOldCode() {
		String productCodeOld = "168HKE338400002";
		System.out.println(dao.findProductCodeByOldCode(productCodeOld));
	}
}
