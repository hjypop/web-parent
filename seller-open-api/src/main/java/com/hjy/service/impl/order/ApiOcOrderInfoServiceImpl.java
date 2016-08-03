package com.hjy.service.impl.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.IUserInfoDao;
import com.hjy.dao.order.IOcOrderinfoDao;
import com.hjy.entity.order.OcOrderinfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.order.IApiOcOrderInfoService;

@Service("apiOcOrderInfoService")
public class ApiOcOrderInfoServiceImpl extends BaseServiceImpl<OcOrderinfo, Integer> implements IApiOcOrderInfoService{

	@Resource
	private IOcOrderinfoDao dao; 
	
}



























