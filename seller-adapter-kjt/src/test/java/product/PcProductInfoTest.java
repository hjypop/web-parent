package product;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.entity.product.PcProductinfo;

/**
 * 
 * 类: PcProductInfoTest <br>
 * 描述: 商品信息表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午10:25:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class PcProductInfoTest {

	@Autowired
	private IPcProductinfoDao dao;

	/**
	 * 
	 * 方法: findProductCodeOld <br>
	 * 描述: 根据卖家编号和第三方商户编号,查询旧的商品编号 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:26:14
	 */
	public void findProductCodeOld() {
		PcProductinfo info = new PcProductinfo();
		info.setSellerCode("SI2003");
		info.setSmallSellerCode("SF03KJT");
		List<String> list = dao.findProductCodeOld(info);
		System.out.println(list.size());
	}

	/**
	 * 
	 * 方法: findProductCodeByOldCode <br>
	 * 描述: 根据旧编号获取商品编码 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:26:22
	 */
	@Test
	public void findProductCodeByOldCode() {
		String productCodeOld = "168HKE338400002";
		System.out.println(dao.findProductCodeByOldCode(productCodeOld));
	}
}
