package system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.service.system.IScStoreSkunumService;

/**
 * 
 * 类: ScStoreSkunumTest <br>
 * 描述: sc_store_skunum库存信息 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年7月1日 上午10:27:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class ScStoreSkunumTest {

	@Autowired
	private IScStoreSkunumService service;

	/**
	 * 
	 * 方法: findScStoreSkunumByParams <br>
	 * 描述: 根据查询条件查询库存信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:27:13
	 */
	public void findScStoreSkunumByParams() {
		ScStoreSkunum param = new ScStoreSkunum();
		param.setSkuCode("119823");
		param.setStoreCode("C02");
		ScStoreSkunum entity = service.findScStoreSkunumByParams(param);
		System.out.println(JSON.toJSON(entity));
	}

	/**
	 * 
	 * 方法: updateSelective <br>
	 * 描述: 更新数据 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年7月1日 上午10:27:21
	 */
	@Test
	public void updateSelective() {
		ScStoreSkunum entity = new ScStoreSkunum();
		entity.setStockNum(Long.valueOf("1200"));
		entity.setZid(12358299);
		System.out.println(service.updateSelective(entity));
	}
}
