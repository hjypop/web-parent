package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.dao.product.IPcCategoryinfoDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class PcCategoryinfoTest {

	@Autowired
	private IPcCategoryinfoDao dao;
	@Test
	public void getPcCategoryinfoByCode(){
		String code = "449716030003";
		System.out.println(dao.getPcCategoryinfoByCode(code));
	}
}
