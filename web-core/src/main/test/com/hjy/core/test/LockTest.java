package com.hjy.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.helper.WebHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class LockTest {
	
	@Test
	public void test() {
		String uuid = WebHelper.getInstance().addLock("test", 10);
		System.out.println(uuid);
	}
}
