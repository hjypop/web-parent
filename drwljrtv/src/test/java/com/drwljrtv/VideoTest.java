package com.drwljrtv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.core.system.PureNetUtil;
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
		JSONArray array = service.getVideos(request);
		System.out.println(array);
	}

	public void t() {
		String data = PureNetUtil.post("http://www.bdysgz.net/actions/mobile_api.php?cmd=get_videos", null);
		System.out.println(data);
	}
}
