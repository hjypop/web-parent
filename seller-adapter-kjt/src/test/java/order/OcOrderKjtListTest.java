package order;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.dao.IOcOrderKjtListDao;
import com.hjy.entity.OcOrderKjtList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class OcOrderKjtListTest {

	@Autowired
	private IOcOrderKjtListDao dao;

	@Test
	public void findList() {
		String sostatus = "0,1,4,41,45";
		List<OcOrderKjtList> list = dao.findOrderListByStatus(sostatus);
		System.out.println(list.size());
	}
}
