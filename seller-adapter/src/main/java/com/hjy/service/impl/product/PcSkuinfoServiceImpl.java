package com.hjy.service.impl.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.product.IPcSkuinfoService;

/**
 * 
 * 类: PcSkuinfoServiceImpl <br>
 * 描述: 产品表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午2:43:21
 */
@Service
public class PcSkuinfoServiceImpl extends BaseServiceImpl<PcSkuinfo, Integer> implements IPcSkuinfoService {

	@Resource
	private IPcSkuinfoDao dao;

	/**
	 * 
	 * 方法: findSkuCodeByProductCode <br>
	 * 描述: TODO
	 * 
	 * @param productCode
	 * @return
	 * @see com.hjy.service.product.IPcSkuinfoService#findSkuCodeByProductCode(java.lang.String)
	 */
	@Override
	public String findSkuCodeByProductCode(String productCode) {
		return dao.findSkuCodeByProductCode(productCode);
	}
}
