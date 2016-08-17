package com.hjy.service.impl.webcore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.webcore.IWcSellerinfoService;

/**
 * 
 * 类: WcSellerinfoServiceImpl <br>
 * 描述: 商家信息业务处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 上午11:39:29
 */
@Service
public class WcSellerinfoServiceImpl extends BaseServiceImpl<WcSellerinfo, Integer> implements IWcSellerinfoService {

	@Autowired
	private IWcSellerinfoDao dao;

	/**
	 * 
	 * 方法: selectBySellerCode <br>
	 * 描述: TODO
	 * 
	 * @param sellerCode
	 * @return
	 * @see com.hjy.service.webcore.IWcSellerinfoService#selectBySellerCode(java.lang.String)
	 */
	@Override
	public WcSellerinfo selectBySellerCode(String sellerCode) {
		return dao.selectBySellerCode(sellerCode);
	}

	/**
	 * 
	 * 方法: deleteBySellerCode <br>
	 * 描述: TODO
	 * 
	 * @param sellerCode
	 * @return
	 * @see com.hjy.service.webcore.IWcSellerinfoService#deleteBySellerCode(java.lang.String)
	 */
	@Override
	public int deleteBySellerCode(String sellerCode) {
		return dao.deleteBySellerCode(sellerCode);
	}

}
