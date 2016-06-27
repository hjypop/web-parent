package com.hjy.api;

import com.hjy.iface.IBaseResult;
import com.hjy.system.config.PropVisitor;

public class RootResultWeb extends RootResult {

	public void inErrorMessage(int iErrorCode, Object... sParms) {

		setCode(iErrorCode);

		setMessage(PropVisitor.getLogInfo(iErrorCode, sParms));
	}

	public boolean upFlagTrue() {
		return getCode() == 1;
	}

	/**
	 * 添加其他结果
	 * 
	 * @param iResult
	 */
	public void inOtherResult(IBaseResult iResult) {
		setCode(iResult.getCode());
		setMessage(iResult.getMessage());

	}

}
