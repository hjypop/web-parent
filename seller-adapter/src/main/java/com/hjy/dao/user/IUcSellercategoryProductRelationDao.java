package com.hjy.dao.user;

import com.hjy.dao.BaseDao;
import com.hjy.entity.user.UcSellercategoryProductRelation;

public interface IUcSellercategoryProductRelationDao  extends BaseDao<UcSellercategoryProductRelation, Integer>  {
	public int  deleteByProductCode(UcSellercategoryProductRelation spr); 
}
