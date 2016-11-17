package com.drwljrtv;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.drwljrtv.model.Video;
import com.drwljrtv.request.video.GetVideos;
import com.drwljrtv.service.video.IVideoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class VideoTest {

	@Autowired
	private IVideoService service;

	@Test
	public void getVideos() {
		GetVideos request = new GetVideos();
		request.setTag(1);
		List<Video> array = service.getVideos(request);
		System.out.println(JSON.toJSON(array));
	}
}
