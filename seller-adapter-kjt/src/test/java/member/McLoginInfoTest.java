package member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.entity.member.McLoginInfo;
import com.hjy.service.member.IMcLoginInfoService;

/**
 * 
 * 类: McLoginInfoTest <br>
 * 描述: 登陆信息表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午9:49:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class McLoginInfoTest {

	@Autowired
	private IMcLoginInfoService service;

	/**
	 * 
	 * 方法: findLoginInfoByMemberCode <br>
	 * 描述: 根据用户编号查询登陆信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午9:50:53
	 */
	@Test
	public void findLoginInfoByMemberCode() {
		String memberCode = "MI140701100001";
		McLoginInfo info = service.findLoginInfoByMemberCode(memberCode);
		System.out.println(info.getZid());
	}
}
