package cn.com.se;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hjy.base.BaseTest;
import com.hjy.common.DateUtil;
import com.hjy.helper.WebHelper;

import cn.com.se.mapper.lesson.TClassesMapper;
import cn.com.se.model.lesson.TClasses;

public class TClassesTest extends BaseTest {

	@Autowired
	private TClassesMapper mapper;

	@Test
	public void insert() {
		TClasses entity = new TClasses();
		entity.setUuid(WebHelper.getInstance().genUuid());
		entity.setCode(WebHelper.getInstance().genUniqueCode("C"));
		entity.setCreateUser("system");
		entity.setCreateTime(DateUtil.getSysDateTimeString());
		entity.setUpdateUser("system");
		entity.setUpdateTime(DateUtil.getSysDateTimeString());
		mapper.insertSelective(entity);
	}
}
