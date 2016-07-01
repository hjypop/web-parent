package product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.service.product.IPcSkuinfoService;

/**
 * 
 * 类: PcSkuinfoTest <br>
 * 描述: 产品表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午10:25:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class PcSkuinfoTest {

	@Autowired
	private IPcSkuinfoService service;

	/**
	 * 
	 * 方法: findSkuCodeByProductCode <br>
	 * 描述: 根据商品编号查询产品编号 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:25:11
	 */
	@Test
	public void findSkuCodeByProductCode() {
		String productCode = "107700";
		System.out.println(service.findSkuCodeByProductCode(productCode));
	}
}
