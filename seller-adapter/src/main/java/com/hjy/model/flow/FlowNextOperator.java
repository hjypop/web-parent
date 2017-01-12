package com.hjy.model.flow;

public class FlowNextOperator {
	private String nextOperator="";  // 下一审批人
	private String nextOperatorStatus = "";			// 下一审批人权限
	
	public String getNextOperator() {
		return nextOperator;
	}
	public void setNextOperator(String nextOperator) {
		this.nextOperator = nextOperator;
	}
	public String getNextOperatorStatus() {
		return nextOperatorStatus;
	}
	public void setNextOperatorStatus(String nextOperatorStatus) {
		this.nextOperatorStatus = nextOperatorStatus;
	}
}
