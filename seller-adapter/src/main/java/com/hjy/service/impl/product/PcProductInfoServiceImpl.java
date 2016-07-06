package com.hjy.service.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.product.IPcProductinfoServivce;

/**
 * 
 * 类: PcProductInfoServiceImpl <br>
 * 描述: 商品信息业务逻辑处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 上午10:05:39
 */
@Service
public class PcProductInfoServiceImpl extends BaseServiceImpl<PcProductinfo, Integer>
		implements IPcProductinfoServivce {

	@Resource
	private IPcProductinfoDao dao;

	/**
	 * 
	 * 方法: findProductCodeOld <br>
	 * 描述: TODO
	 * 
	 * @param info
	 * @return
	 * @see com.hjy.service.product.IPcProductinfoServivce#findProductCodeOld(com.hjy.entity.product.PcProductinfo)
	 */
	@Override
	public List<String> findProductCodeOld(PcProductinfo info) {
		return dao.findProductCodeOld(info);
	}

	/**
	 * 
	 * 方法: findProductCodeByOldCode <br>
	 * 描述: TODO
	 * 
	 * @param productCodeOld
	 * @return
	 * @see com.hjy.service.product.IPcProductinfoServivce#findProductCodeByOldCode(java.lang.String)
	 */
	@Override
	public String findProductCodeByOldCode(String productCodeOld) {
		return dao.findProductCodeByOldCode(productCodeOld);
	}

}
