package log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.dao.ILcRsyncKjtLogDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class LcRsyncKjtLogTest {

	@Autowired
	private ILcRsyncKjtLogDao dao;
	@Test
	public void findLatelyStatusData() {

		String rsyncTarget = "Product.ProductPriceBatchGet";
		System.out.println(dao.findLatelyStatusData(rsyncTarget));
	}
}
