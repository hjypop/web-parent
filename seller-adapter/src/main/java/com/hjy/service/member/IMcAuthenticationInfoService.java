package com.hjy.service.member;

import com.hjy.entity.member.McAuthenticationInfo;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IMcAuthenticationInfoService <br>
 * 描述: mc_authenticationInfo表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午6:32:09
 */
public interface IMcAuthenticationInfoService extends IBaseService<McAuthenticationInfo, Integer>{
	/**
	 * 
	 * 方法: updateCustomsStatus <br>
	 * 描述: TODO <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月30日 上午7:50:35
	 * @return
	 */
	int updateCustomsStatus(McAuthenticationInfo entity);
}
