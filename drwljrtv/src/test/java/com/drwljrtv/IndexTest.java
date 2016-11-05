package com.drwljrtv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.drwljrtv.service.IndexService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class IndexTest {

	@Autowired
	private IndexService service;

	@Test
	public void getShufflingImages() {
		System.out.println(service.getShufflingImages());
	}
}
