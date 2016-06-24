package com.hjy.iface;
/**
 * @descriptions 基础同步参数设置
 * 
 * @date 2016年6月24日下午2:42:30
 * @author Yangcl
 * @version 1.0.1
 */
public interface IRsyncDateCheck {
	/**
	 * @descriptions 最原始的开始同步时间 
	 * 如果没有成功过 则以此时间为标准
	 * 
	 * @return
	 * @date 2016年6月24日下午2:42:39
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public String getBaseStartTime();

	/**
	 * @descriptions 最大间隔秒数 
	 * 该参数是为了防止压力过大 一般设置为一天
	 * 
	 * @return
	 * @date 2016年6月24日下午2:42:55
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public int getMaxStepSecond();

	/**
	 * @descriptions 回退时间 
	 * 该参数会将开始时间减去该秒数 以兼容特定非同步成功状态
	 * 
	 * @return
	 * @date 2016年6月24日下午2:43:12
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public int getBackSecond();
}
