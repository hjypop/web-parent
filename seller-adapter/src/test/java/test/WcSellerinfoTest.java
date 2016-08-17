package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcSellerinfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class WcSellerinfoTest {

	@Autowired
	private IWcSellerinfoDao dao;

	@Test
	public void list() {
		WcSellerinfo entity = new WcSellerinfo();
		List<WcSellerinfo> list = dao.queryPage(entity);
		System.out.println(list);
	}
}
