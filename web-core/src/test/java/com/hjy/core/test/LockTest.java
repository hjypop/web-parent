package com.hjy.core.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.service.ILockService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class LockTest {
	@Resource
	private ILockService lockService;
	
	@Test
	public void test() {
		String aaa = lockService.addLock("keycode22", 10, "UUID23243");
		 
		System.out.println("hello world " + aaa);  
	}
}
