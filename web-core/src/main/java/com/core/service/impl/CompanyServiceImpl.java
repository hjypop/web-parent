package com.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.core.dao.ICompanyDao;
import com.core.pojo.entity.system.Company;
import com.core.service.ICompanyService;


@Service("companyService")
public class CompanyServiceImpl extends BaseServiceImpl<Company, Integer> implements ICompanyService {
	@Resource
	private ICompanyDao companyDao;
}
