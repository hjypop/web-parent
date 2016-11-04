package com.core.pojo.entity.system;

import com.core.pojo.entity.BaseEntity;

public class SysLock extends BaseEntity {
	private String keyid;
	private String keycode;
	
	public String getKeyid() {
		return keyid;
	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	public String getKeycode() {
		return keycode;
	}
	public void setKeycode(String keycode) {
		this.keycode = keycode;
	}
	
}
