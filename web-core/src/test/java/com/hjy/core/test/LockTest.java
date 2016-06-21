package com.hjy.core.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.helper.WebHelper;
import com.hjy.helper.WebHelper2;
import com.hjy.service.ILockService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class LockTest {
	@Resource
	private ILockService lockService;
	
	@Test
	public void test() {
//		String uuid = WebHelper.getInstance().addLock("UUIDtestwde2229w1", 10);
//		String uuid = WebHelper.unLock("e34a89ef5b1f4152a368e2dfaefb9a53");
//		System.out.println(uuid);
		
		WebHelper2 wh1 = WebHelper2.getInstance();
		WebHelper2 wh2 = WebHelper2.getInstance();
		WebHelper2 wh3 = WebHelper2.getInstance();
		
		System.out.println(wh1.toString() + wh2.toString() + wh3.toString()); 
	}
}
