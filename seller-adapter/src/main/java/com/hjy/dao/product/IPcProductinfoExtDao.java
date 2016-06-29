package com.hjy.dao.product;

import org.apache.ibatis.annotations.Param;

import com.hjy.dao.BaseDao;
import com.hjy.entity.product.PcProductinfoExt;
import com.hjy.entity.product.PcProductinfoExtExample;

public interface IPcProductinfoExtDao extends BaseDao<PcProductinfoExt , Integer> {

	int updateByExampleSelective(@Param("record") PcProductinfoExt record, @Param("example") PcProductinfoExtExample example);
}
