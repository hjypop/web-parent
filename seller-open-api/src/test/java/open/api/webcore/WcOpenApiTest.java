package open.api.webcore;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.common.DateUtil;
import com.hjy.dao.webcore.IWcOpenApiDao;
import com.hjy.entity.webcore.WcOpenApi;
import com.hjy.helper.WebHelper;
import com.hjy.service.webcore.IWcOpenApiService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class WcOpenApiTest {

	@Autowired
	private IWcOpenApiDao service;

	@Test
	public void insert() {
		WcOpenApi entity = new WcOpenApi();
		entity.setUid(UUID.randomUUID().toString().replace("-", ""));
		entity.setApiCode("1608251000000");
		entity.setApiName("根据时间推送产品信息");
		entity.setMethod("Product.pushProducts");
		entity.setDescription("根据时间范围查询编辑过的产品列表，同步推送到第三方平台");
		entity.setCreator("system");
		entity.setCreateTime(DateUtil.getSysDateTimeString());
		entity.setUpdator("system");
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		service.insertSelective(entity);
	}
}
