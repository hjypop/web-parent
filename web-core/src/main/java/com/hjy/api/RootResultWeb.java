package com.hjy.api;

import com.hjy.iface.IBaseResult;
import com.srnpr.zapcom.topdo.TopUp;

public class RootResultWeb extends RootResult {

	public void inErrorMessage(int iErrorCode, Object... sParms) {

		setCode(iErrorCode);

		setResultMessage(TopUp.upLogInfo(iErrorCode, sParms));
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
