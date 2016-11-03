package com.core.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

	public static String toJson(Object oValue) {

		return new Gson().toJson(oValue);
	}

	public static <T> String toJsonDisableHtml(T oValue) {
		return new GsonBuilder().disableHtmlEscaping().create().toJson(oValue);
	}

	@SuppressWarnings("unchecked")
	public <T> T fromJson(String sJson, T t) {
		return (T) new Gson().fromJson(sJson, t.getClass());
	}

}
