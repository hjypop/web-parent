package com.core.common.product;

/**
 * @descriptions 此类可以删除
 * 
 * @author Yangcl
 * @date 2016年6月26日-下午10:05:40
 * @version 1.0.0
 */
public class SkuCommon {
	
	public final static String ProductHead = "8016";
	public final static String SKUHead = "8019";
	public final static String ProductFlowHead = "PF";
	
	/**
	 * 
	 */
	public final static String FirstSplitStr="$$";
	/**
	 * 
	 */
	public final static String SecondSplitStr="#$#";
	
	/**
	 * 库存变动类型-下单提交
	 */
	public final static String SkuStockChangeTypeOrderCommit="1";
	/**
	 * 库存变动类型-下单回滚
	 */
	public final static String SkuStockChangeTypeOrderRollBack="2";
	
	/**
	 * sku添加
	 */
	public final static String SkuStockChangeTypeCreateProduct="3";
	/**
	 *人工修改库存 
	 */
	public final static String SkuStockChangeTypeChangeProduct="4";
	
	
	
	/**
	 * 成功标志
	 */
	public final static int SuccessFlag=1;
	
	
	public final static String FlowStatusInit = "0";
	public final static String FlowStatusXG = "1";
	
	public final static String ProAddInit = "10";//新增商品未审核状态
	public final static String ProAddOr = "11";//终审通过状态
	public final static String ProAddOrRe = "15";//终审未通过状态
	
	public final static String ProUpaInit = "20";//修改商品未审核状态
	public final static String ProUpaOr = "22";//终审通过状态
	public final static String ProUpaOrRe = "25";//终审未通过状态

}
