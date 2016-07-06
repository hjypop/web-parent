package com.hjy.service.impl.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.product.IPcProductinfoExtDao;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.product.IPcProductinfoExtService;

/**
 * 
 * 类: PcProductinfoExtServiceImpl <br>
 * 描述: 商品属性表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午2:43:02
 */
@Service
public class PcProductinfoExtServiceImpl extends BaseServiceImpl<PcProductinfoExt, Integer>
		implements IPcProductinfoExtService {

	@Resource
	private IPcProductinfoExtDao dao;

	@Override
	public Integer updatePcProductinfoExt(PcProductinfoExt entity) {
		return dao.updateSelective(entity);
	}

}
