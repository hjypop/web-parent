package com.hjy.dao.webcore;

import java.util.List;
import java.util.Map;

import com.hjy.dao.BaseDao;
import com.hjy.entity.webcore.WcSellerinfo;

/**
 * 
 * 类: IWcSellerinfoDao <br>
 * 描述: 商家信息表数据库访问接口 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 上午11:37:49
 */
public interface IWcSellerinfoDao extends BaseDao<WcSellerinfo, Integer> {

	/**
	 * 
	 * 方法: selectBySellerCode <br>
	 * 描述: 根据商家编号查询商家信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:51:18
	 * 
	 * @param sellerCode
	 * @return
	 */
	WcSellerinfo selectBySellerCode(String sellerCode);

	/**
	 * 
	 * 方法: deleteBySellerCode <br>
	 * 描述: 根据商家编号删除商家信息 <br>
	 * 作者: zhy<br>
	 * 时间: 2016年8月17日 下午1:56:03
	 * 
	 * @param sellerCode
	 * @return
	 */
	int deleteBySellerCode(String sellerCode);

	/**
	 * 
	 * 方法: selectBySellerCodeByApi <br>
	 * 描述: 根据商户编号查询已开通openapi的商户信息 作者: zhy<br>
	 * 时间: 2016年8月18日 上午11:48:20
	 * 
	 * @param sellerCode
	 * @return
	 */
	WcSellerinfo selectBySellerCodeByApi(String sellerCode);

	/**
	 * @description: 获取惠家有要进行报关的商户列表    
	 * 需要惠家有去报关的跨境商户 small_seller_code@商户海关备案编号@报关地点，多个跨境商户以英文逗号分隔  | 报关地点固定13个
	 * JobForKjCustomsDeclaration.java报关的定时任务需要使用这个配置
	 * JobForSendCustomsDeclaration.java报关的定时任务需要使用这个配置
	 * @return
	 * @author Yangcl 
	 * @date 2016年10月31日 下午4:49:37 
	 * @version 1.0.0.1
	 */
	public List<WcSellerinfo> getCustomsDeclarationSellerList();
	
	
	
}














