package com.hjy.dao.system;

import java.util.List;
import java.util.Map;

import com.hjy.dao.BaseDao;
import com.hjy.entity.system.ScFlowStatuschange;

public interface IScFlowStatuschangeDao extends BaseDao<ScFlowStatuschange, Integer> {

	public List<ScFlowStatuschange> findListByFlowTyp(Map<String , String> map); 
	
	
}
