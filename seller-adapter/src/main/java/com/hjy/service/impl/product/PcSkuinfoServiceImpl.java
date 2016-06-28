package com.hjy.service.impl.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.service.product.IPcSkuinfoService;

/**
 * 
 * 类: PcSkuinfoServiceImpl <br>
 * 描述: 产品表业务处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午2:43:21
 */
@Service
public class PcSkuinfoServiceImpl implements IPcSkuinfoService {

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

	@Override
	public Integer insertSelective(PcSkuinfo entity) {
		
		return null;
	}

	@Override
	public Integer insertGotEntityId(PcSkuinfo entity) {
		
		return null;
	}

	@Override
	public Integer insertGotEntityUuid(PcSkuinfo entity) {
		
		return null;
	}

	@Override
	public Integer batchInsert(List<PcSkuinfo> list) {
		
		return null;
	}

	@Override
	public Integer updateSelective(PcSkuinfo entity) {
		
		return null;
	}

	@Override
	public Integer batchUpdate(List<PcSkuinfo> list) {
		
		return null;
	}

	@Override
	public Integer deleteById(Integer id) {
		
		return null;
	}

	@Override
	public Integer batchDelete(List<Integer> list) {
		
		return null;
	}

	@Override
	public <DTO> void deleteByCondition(DTO dto) {
		
		
	}

	@Override
	public PcSkuinfo find(Integer id) {
		
		return null;
	}

	@Override
	public List<PcSkuinfo> findList(PcSkuinfo entity) {
		
		return null;
	}

	@Override
	public JSONObject jsonList(PcSkuinfo entity) {
		
		return null;
	}

	@Override
	public <DTO> List<PcSkuinfo> findGroupList(DTO dto) {
		
		return null;
	}

	@Override
	public int count(PcSkuinfo entity) {
		
		return 0;
	}

	@Override
	public List<PcSkuinfo> queryPage(PcSkuinfo entity) {
		
		return null;
	}

	@Override
	public List<PcSkuinfo> like(PcSkuinfo entity) {
		
		return null;
	}

	@Override
	public Integer selectMaxId() {
		
		return null;
	}
}
