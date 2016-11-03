package com.core.api;

import com.core.iface.IBaseResult;
import com.core.system.config.PropVisitor;

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
