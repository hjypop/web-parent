package com.hjy.model.flow;

public class RoleStatus {
	
	private String roleCode = "";        //  sc_flow_statuschange表的 role_id
	private String toStatus = "";		  //  sc_flow_statuschange表的 to_status
	
	public RoleStatus(String roleCode, String toStatus) {
		this.roleCode = roleCode;
		this.toStatus = toStatus;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getToStatus() {
		return toStatus;
	}
	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}
}
