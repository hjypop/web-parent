package com.hjy.core.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hjy.helper.WebHelper;
import com.hjy.service.ISystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/mybatis-config.xml" })
public class LockTest {
	@Resource
	private ISystemService service;
	
	@Test
	public void test() {
//		String keyid = WebHelper.getInstance().addLock("uuid-9988662", 10);
//		WebHelper.getInstance().unLock(keyid);
//		System.out.println(keyid);  
		
//		for(int i = 0; i < 10 ; i++) {
//			String webcode = WebHelper.getInstance().genUniqueCode("fairy-");
//			System.out.println(webcode); 
//		}
		
		Exception e = new RuntimeException("askdfjielznnvf asdlkffjie asdli adsfw asdfoaisdf asdflaiefd asdf  asdfeadsdf");
		WebHelper.getInstance().errorMessage("sCode", "sErrorType", 20, "sErrorSource", "setErrorInfo", e);
		
	}
}





















































