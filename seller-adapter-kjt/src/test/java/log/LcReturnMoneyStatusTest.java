package log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.common.DateUtil;
import com.hjy.entity.log.LcReturnMoneyStatus;
import com.hjy.service.log.ILcReturnMoneyStatusService;

/**
 * 
 * 类: LcReturnMoneyStatusTest <br>
 * 描述: 退款状态日子表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午9:40:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class LcReturnMoneyStatusTest {

	@Autowired
	private ILcReturnMoneyStatusService service;

	/**
	 * 
	 * 方法: insertSelective <br>
	 * 描述: 添加新数据 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午9:40:28
	 */
	@Test
	public void insertSelective() {
		LcReturnMoneyStatus entity = new LcReturnMoneyStatus();
		entity.setReturnMoneyNo("RTM131127100003");
		entity.setInfo("测试用例");
		entity.setCreateUser("s");
		entity.setCreateTime(DateUtil.getSysDateTimeString());
		entity.setStatus("4497153900040003");
		int result = service.insertSelective(entity);
		System.out.println(result);
	}
}
