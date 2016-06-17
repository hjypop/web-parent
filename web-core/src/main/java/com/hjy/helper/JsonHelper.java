package com.hjy.helper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

/**
 * JSON帮助类
 * 
 * @author HJY
 * 
 * @param <T>
 */
public class JsonHelper<T> {

	/**
	 * 转换
	 * 
	 * @param oInput
	 * @return
	 */
	public String ObjToString(T oInput) {
		ObjectMapper om = new ObjectMapper();
		om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		String sResponseString = null;
		try {
			sResponseString = om.writeValueAsString(oInput);
		} catch (JsonProcessingException e) {

			e.printStackTrace();

		}

		return sResponseString;
	}

	@SuppressWarnings("unchecked")
	public T StringToObj(String sInput, T t) {

		ObjectMapper om = new ObjectMapper();
		try {

			t = (T) om.readValue(sInput, t.getClass());
		} catch (Exception e) {

			e.printStackTrace();
		}

		return t;

	}

	public T StringToObjExp(String sInput, T t) throws IOException {

		ObjectMapper om = new ObjectMapper();
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		t = (T) om.readValue(sInput, t.getClass());

		return t;

	}

	public String GsonToJson(T oInput) {
		Gson gson = new Gson();

		return gson.toJson(oInput);
	}

	@SuppressWarnings("unchecked")
	public T GsonFromJson(String sInput, T t) {

		Gson gson = new Gson();
		t = (T) gson.fromJson(sInput, t.getClass());

		return t;

	}

}
