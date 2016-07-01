package member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.entity.member.McAuthenticationInfo;
import com.hjy.service.member.IMcAuthenticationInfoService;

/**
 * 
 * 类: McAuthenticationInfoTest <br>
 * 描述: mc_authenticationInfo表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午9:56:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class McAuthenticationInfoTest {

	@Autowired
	private IMcAuthenticationInfoService service;

	/**
	 * 
	 * 方法: updateCustomsStatus <br>
	 * 描述: 修改通关状态 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午9:57:42
	 */
	@Test
	public void updateCustomsStatus() {
		McAuthenticationInfo entity = new McAuthenticationInfo();
		entity.setMemberCode("MI140918100001");
		entity.setIdcardNumber("4497465200090001");
		entity.setCustomsStatus(0);
		int result = service.updateCustomsStatus(entity);
		System.out.println(result);
	}
}
