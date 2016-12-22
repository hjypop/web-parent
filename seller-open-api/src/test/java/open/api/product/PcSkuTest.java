package open.api.product;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.dao.IApiSkuInfoDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class PcSkuTest {

	@Autowired
	private IApiSkuInfoDao dao;

	public void findSkuByProductCode() {
		dao.findSkuByProductCode("107700");
	}

	@Test
	public void findSkuDataByProductCode() {
		String codes = "107700,114197,118732";
		dao.findSkuDataByProductCode(Arrays.asList(codes.split(",")));
	}
}
