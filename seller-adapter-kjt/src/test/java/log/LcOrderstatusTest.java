package log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.common.DateUtil;
import com.hjy.entity.log.LcOrderstatus;
import com.hjy.service.log.ILcOrderstatusService;

/**
 * 
 * 类: LcOrderstatusTest <br>
 * 描述: 订单状态日志表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午9:13:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class LcOrderstatusTest {

	@Autowired
	private ILcOrderstatusService service;

	/**
	 * 
	 * 方法: insertSelective <br>
	 * 描述: 添加新数据 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午9:37:21
	 */
	@Test
	public void insertSelective() {
		LcOrderstatus entity = new LcOrderstatus();
		entity.setCode("DD140211100031");
		entity.setInfo("测试用例");
		entity.setCreateTime(DateUtil.getSysTimeString());
		entity.setCreateUser("system");
		entity.setOldStatus("4497153900010001");
		entity.setNowStatus("4497153900010001");
		int result = service.insertSelective(entity);
		System.out.println(result);
	}
}
