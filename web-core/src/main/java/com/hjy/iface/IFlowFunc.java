package com.hjy.iface;

import com.hjy.api.RootResult;
import com.hjy.model.MDataMap;

public interface IFlowFunc {

	/**
	 *  流程流转处理函数,在处理之前调用的
	 * @param flowCode  流程Code
	 * @param outCode	外部Code
	 * @param fromStatus 起始状态
	 * @param toStatus   流转的结束状态
	 * @return
	 */
	RootResult BeforeFlowChange(String flowCode , String outCode , String fromStatus , String toStatus , MDataMap mSubMap);
	
	
	/**
	 *  流程流转处理函数,在处理之后调用的
	 * @param flowCode  流程Code
	 * @param outCode	外部Code
	 * @param fromStatus 起始状态
	 * @param toStatus   流转的结束状态
	 * @return
	 */
	RootResult afterFlowChange(String flowCode , String outCode , String fromStatus , String toStatus , MDataMap mSubMap);
	
}
