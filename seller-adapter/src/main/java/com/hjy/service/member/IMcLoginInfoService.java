package com.hjy.service.member;

import com.hjy.entity.member.McLoginInfo;
import com.hjy.service.IBaseService;

/**
 * 
 * 类: IMcLoginInfoService <br>
 * 描述: 登陆信息表业务处理接口 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月30日 下午1:47:48
 */
public interface IMcLoginInfoService extends IBaseService<McLoginInfo, Integer> {
	/**
	 * 
	 * 方法: findLoginInfoByMemberCode <br>
	 * 描述: 根据用户编号查询登陆信息 <br>
	 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
	 * 时间: 2016年6月30日 下午1:54:09
	 * 
	 * @param memberCode
	 * @return
	 */
	McLoginInfo findLoginInfoByMemberCode(String memberCode);
}
