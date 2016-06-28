package com.hjy.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.system.IScStoreDao;
import com.hjy.entity.system.ScStore;
import com.hjy.service.system.IScStoreService;

/**
 * 
 * 类: ScStoreServiceImpl <br>
 * 描述: sc_store表业务逻辑处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午1:56:51
 */
@Service
public class ScStoreServiceImpl implements IScStoreService {

	@Autowired
	private IScStoreDao dao;

	/**
	 * 
	 * 方法: findScStoreIsExists <br>
	 * 描述: TODO
	 * 
	 * @param storeCode
	 * @return
	 * @see com.hjy.service.system.IScStoreService#findScStoreIsExists(java.lang.String)
	 */
	@Override
	public int findScStoreIsExists(String storeCode) {
		ScStore entity = new ScStore();
		entity.setStoreCode(storeCode);
		return dao.count(entity);
	}

	@Override
	public Integer insertSelective(ScStore entity) {
		return null;
	}

	@Override
	public Integer insertGotEntityId(ScStore entity) {

		return null;
	}

	@Override
	public Integer insertGotEntityUuid(ScStore entity) {

		return null;
	}

	@Override
	public Integer batchInsert(List<ScStore> list) {
		return null;
	}

	/**
	 * 
	 * 方法: updateSelective <br>
	 * 描述: TODO
	 * 
	 * @param entity
	 * @return
	 * @see com.hjy.service.IBaseService#updateSelective(java.lang.Object)
	 */
	@Override
	public Integer updateSelective(ScStore entity) {
		return dao.updateSelective(entity);
	}

	@Override
	public Integer batchUpdate(List<ScStore> list) {
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
	public ScStore find(Integer id) {
		return null;
	}

	@Override
	public List<ScStore> findList(ScStore entity) {
		return null;
	}

	@Override
	public JSONObject jsonList(ScStore entity) {
		return null;
	}

	@Override
	public <DTO> List<ScStore> findGroupList(DTO dto) {
		return null;
	}

	@Override
	public int count(ScStore entity) {
		return 0;
	}

	@Override
	public List<ScStore> queryPage(ScStore entity) {
		return null;
	}

	@Override
	public List<ScStore> like(ScStore entity) {
		return null;
	}

	@Override
	public Integer selectMaxId() {
		return null;
	}
}
