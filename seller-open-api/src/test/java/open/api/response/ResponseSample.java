package open.api.response;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.hjy.response.ApiResponse;


public class ResponseSample {
	
	/**
	 * @descriptions 演示如何设置一个response消息体
	 * 但是无法强制类型转换为DataTest
	 * 
	 * @date 2016年8月2日上午11:41:01
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@Test
	public void responseMsgTest(){
		ApiResponse<DataTest> mr = new ApiResponse<DataTest>();
		mr.setCode("0");
		mr.setDesc("SUCCESS");
		DataTest dt = new DataTest();
		dt.setMsgss("data test msg");
		dt.setName("data test name");
		dt.setPwdss("123456"); 
		mr.setData(dt); 
		
		String string_ = JSON.toJSONString(mr);
		
		@SuppressWarnings("unchecked")
		ApiResponse<DataTest> object = JSON.parseObject(string_ , ApiResponse.class); 
		
		System.out.println(string_);
		System.out.println(object.getDesc()); 
	}
}













