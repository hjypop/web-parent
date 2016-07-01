package order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.entity.order.OcOrderaddress;
import com.hjy.service.order.IOcOrderadressService;

/**
 * 
 * 类: OcOrderadressTest <br>
 * 描述: 订单地址发票表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午10:02:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class OcOrderadressTest {

	@Autowired
	private IOcOrderadressService service;

	/**
	 * 
	 * 方法: findOrderAddressByOrderCode <br>
	 * 描述: 根据order_code查询 订单地址发票表 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:02:46
	 */
	@Test
	public void findOrderAddressByOrderCode() {
		String orderCode = "DD140212100028";
		OcOrderaddress entity = service.findOrderAddressByOrderCode(orderCode);
		System.out.println(entity.getZid());
	}
}
