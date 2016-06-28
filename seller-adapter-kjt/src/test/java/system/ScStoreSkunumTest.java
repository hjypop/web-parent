package system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.service.system.IScStoreSkunumService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class ScStoreSkunumTest {

	@Autowired
	private IScStoreSkunumService service;

	public void findScStoreSkunumByParams() {
		ScStoreSkunum param = new ScStoreSkunum();
		param.setSkuCode("119823");
		param.setStoreCode("C02");
		ScStoreSkunum entity = service.findScStoreSkunumByParams(param);
		System.out.println(JSON.toJSON(entity));
	}

	@Test
	public void updateSelective() {
		ScStoreSkunum entity = new ScStoreSkunum();
		entity.setStockNum(Long.valueOf("1200"));
		entity.setZid(12358299);
		System.out.println(service.updateSelective(entity));
	}
}
