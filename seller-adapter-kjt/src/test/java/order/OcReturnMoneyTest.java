package order;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.common.DateUtil;
import com.hjy.entity.order.OcReturnMoney;
import com.hjy.service.order.IOcReturnMoneyService;

/**
 * 
 * 类: OcReturnMoneyTest <br>
 * 描述: 退款管理表测试用例 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午10:18:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class OcReturnMoneyTest {

	@Autowired
	private IOcReturnMoneyService service;

	/**
	 * 
	 * 方法: insertSelective <br>
	 * 描述: 添加新数据 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:18:35
	 */
	@Test
	public void insertSelective() {
		OcReturnMoney orm = new OcReturnMoney();
		orm.setReturnMoneyCode("");
		orm.setReturnGoodsCode("");
		orm.setBuyerCode("");
		orm.setSellerCode("");
		orm.setSmallSellerCode("");
		orm.setContacts("");
		orm.setStatus("4497153900040003");
		orm.setMobile("");
		orm.setReturnedMoney(BigDecimal.ZERO);
		orm.setCreateTime(DateUtil.getSysDateTimeString());
		orm.setPoundage(BigDecimal.ZERO);
		orm.setOrderCode("");
		orm.setPayMethod("449716200001");
		orm.setOnlineMoney(BigDecimal.ZERO);
		int result = service.insertSelective(orm);
		System.out.println(result);
	}
}
