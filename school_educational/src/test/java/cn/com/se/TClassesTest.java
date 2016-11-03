package cn.com.se;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.core.base.BaseTest;
import com.core.common.DateUtil;
import com.core.helper.WebHelper;
import com.se.dao.lesson.TClassesDao;
import com.se.model.lesson.TClasses;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class TClassesTest extends BaseTest {

	@Autowired
	private TClassesDao mapper;

	@Test
	public void insert() {
		TClasses entity = new TClasses();
		entity.setUuid(WebHelper.getInstance().genUuid());
		entity.setCode(WebHelper.getInstance().genUniqueCode("C"));
		entity.setName("小学一年级（1）班");
		entity.setCreateUser("system");
		entity.setCreateTime(DateUtil.getSysDateTimeString());
		entity.setUpdateUser("system");
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		mapper.insertSelective(entity);
	}
}
