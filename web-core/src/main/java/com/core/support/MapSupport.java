package com.core.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.core.helper.FormatHelper;
import com.core.helper.JsonHelper;
import com.core.iface.IBaseInstance;
import com.core.model.MDataMap;

public class MapSupport implements IBaseInstance {

	public final static MapSupport INSTANCE = new MapSupport();

	/**
	 * 获取序列化的map
	 * 
	 * @param mDataMap
	 * @return
	 */
	public MDataMap upSerializeMap(MDataMap mDataMap) {

		MDataMap mReturnMap = new MDataMap();

		for (String sKey : mDataMap.keySet()) {

			mReturnMap.put(FormatHelper.upReplaceSerialize(sKey), mDataMap.get(sKey));
		}

		return mReturnMap;

	}

	public String toJson(MDataMap mDataMap) {
		JsonHelper<MDataMap> jsonHelper = new JsonHelper<MDataMap>();

		return jsonHelper.ObjToString(mDataMap);
	}

	public <T> MDataMap formatObject(T t) {
		MDataMap map = new MDataMap();

		try {
			for (Field field : t.getClass().getDeclaredFields()) {

				field.setAccessible(true);

				String sValue = field.get(t).toString();
				map.put(field.getName(), sValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 转换MAPlist到list结构体
	 * 
	 * @param maps
	 * @param sSort
	 * @return
	 */
	public List<List<String>> convertMapsToLists(List<MDataMap> maps, String sSort) {
		List<List<String>> lReturnsArrayLists = new ArrayList<List<String>>();

		String[] sort = sSort.split(",");

		for (MDataMap map : maps) {
			List<String> list = new ArrayList<String>();
			for (String sKey : sort) {
				list.add(map.get(sKey));
			}

			lReturnsArrayLists.add(list);
		}

		return lReturnsArrayLists;

	}

}
