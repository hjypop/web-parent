package system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.service.system.IScStoreService;

/**
 * 
 * 类: ScStoreTest <br>
 * 描述: sc_store仓库表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午10:28:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class ScStoreTest {

	@Autowired
	private IScStoreService service;

	/**
	 * 
	 * 方法: findScStoreIsExists <br>
	 * 描述: 判断对象在sc_store中是否存在 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:28:42
	 */
	@Test
	public void findScStoreIsExists() {
		String storeCode = "C16";
		int flag = service.findScStoreIsExists(storeCode);
		System.out.println(flag);
	}
}
