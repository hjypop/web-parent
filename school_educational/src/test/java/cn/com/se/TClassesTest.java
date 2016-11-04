package cn.com.se;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
		String[]school = new String[]{"小学","中学"};
		String[] primaryGrade = new String[]{"一年级","二年级","三年级","四年级","五年级","六年级"};
		String[] middleGrade = new String[]{"一年级","二年级","三年级"};
		for (int i = 1; i <= 10; i++) {
			TClasses entity = new TClasses();
			entity.setUuid(WebHelper.getInstance().genUuid());
			entity.setCode(WebHelper.getInstance().genUniqueCode("C"));
			entity.setSchoolName("小学");
			entity.setGradeName("一年级");
			entity.setClassName(i + "班");
			entity.setCreateUser("system");
			entity.setCreateTime(DateUtil.getSysDateTimeString());
			entity.setUpdateUser("system");
			entity.setUpdateTime(DateUtil.getSysDateTimeString());
			mapper.insertSelective(entity);
		}
	}
}
