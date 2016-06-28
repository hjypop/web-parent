package com.hjy.service.impl.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.system.IScStoreSkunumDao;
import com.hjy.entity.system.ScStoreSkunum;
import com.hjy.service.system.IScStoreSkunumService;

/**
 * 
 * 类: ScStoreSkunumServiceImpl <br>
 * 描述: sc_store_skunum业务逻辑处理接口实现类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月28日 下午1:16:55
 */
@Service
public class ScStoreSkunumServiceImpl implements IScStoreSkunumService {

	@Resource
	private IScStoreSkunumDao dao;

	/**
	 * 
	 * 方法: findScStoreSkunumByParams <br>
	 * 描述: TODO
	 * 
	 * @param param
	 * @return
	 * @see com.hjy.service.system.IScStoreSkunumService#findScStoreSkunumByParams(com.hjy.entity.system.ScStoreSkunum)
	 */
	@Override
	public ScStoreSkunum findScStoreSkunumByParams(ScStoreSkunum param) {
		return dao.findScStoreSkunumByParams(param);
	}

	/**
	 * 
	 * 方法: insertSelective <br>
	 * 描述: TODO
	 * 
	 * @param entity
	 * @return
	 * @see com.hjy.service.IBaseService#insertSelective(java.lang.Object)
	 */
	@Override
	public Integer insertSelective(ScStoreSkunum entity) {
		return dao.insertSelective(entity);
	}

	@Override
	public Integer insertGotEntityId(ScStoreSkunum entity) {
		return null;
	}

	@Override
	public Integer insertGotEntityUuid(ScStoreSkunum entity) {
		return null;
	}

	@Override
	public Integer batchInsert(List<ScStoreSkunum> list) {
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
	public Integer updateSelective(ScStoreSkunum entity) {
		return dao.updateSelective(entity);
	}

	@Override
	public Integer batchUpdate(List<ScStoreSkunum> list) {
		return null;
	}

	@Override
	public Integer deleteById(Integer id) {
		// TODO Auto-generated method stub
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
	public ScStoreSkunum find(Integer id) {
		return null;
	}

	@Override
	public List<ScStoreSkunum> findList(ScStoreSkunum entity) {
		return null;
	}

	@Override
	public JSONObject jsonList(ScStoreSkunum entity) {
		return null;
	}

	@Override
	public <DTO> List<ScStoreSkunum> findGroupList(DTO dto) {
		return null;
	}

	@Override
	public int count(ScStoreSkunum entity) {
		return 0;
	}

	@Override
	public List<ScStoreSkunum> queryPage(ScStoreSkunum entity) {
		return null;
	}

	@Override
	public List<ScStoreSkunum> like(ScStoreSkunum entity) {
		return null;
	}

	@Override
	public Integer selectMaxId() {
		return null;
	}
}
