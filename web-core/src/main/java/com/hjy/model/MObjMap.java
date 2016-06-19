package com.hjy.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MObjMap<K, V> extends ConcurrentHashMap<K, V> {

	private static final long serialVersionUID = 2251639910081587304L;

	/**
	 * #alias# upKeys
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<K> getKeys() {

		List<K> lReturnsKs = new ArrayList<K>();
		Enumeration<K> eKey = this.keys();

		while (eKey.hasMoreElements()) {
			lReturnsKs.add(eKey.nextElement());
		}
		return lReturnsKs;

	}
	
	
	/**
	 * 该方法适合批量添加  注意V必须能转换成K时才能调用
	 * #alias# inAllValues
	 * @param vInputs
	 */
	@SuppressWarnings("unchecked")
	public void initKeyValues(V... vInputs)
	{

		for(int i=0,j=vInputs.length;i<j;i=i+2)
		{
			this.put((K)vInputs[i], vInputs[i+1]);
		}

	}
}
