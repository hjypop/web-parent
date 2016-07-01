package order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.entity.order.OcOrderinfo;
import com.hjy.service.order.IOcOrderinfoService;

/**
 * 
 * 类: OcOrderinfoTest <br>
 * 描述: 订单信息表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午10:14:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class OcOrderinfoTest {

	@Autowired
	private IOcOrderinfoService service;

	/**
	 * 
	 * 方法: findOrderInfoByOrderCode <br>
	 * 描述: 根据订单编号查询订单信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:15:03
	 */
	@Test
	public void findOrderInfoByOrderCode() {
		String orderCode = "DD140120100007";
		OcOrderinfo entity = service.findOrderInfoByOrderCode(orderCode);
		System.out.println(entity.getZid());
	}
}
