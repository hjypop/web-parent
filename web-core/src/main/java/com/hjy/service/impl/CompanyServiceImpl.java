package com.hjy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjy.dao.ICompanyDao;
import com.hjy.entity.project.Company;
import com.hjy.service.ICompanyService;


@Service("companyService")
public class CompanyServiceImpl extends BaseServiceImpl<Company, Integer> implements ICompanyService {
	@Resource
	private ICompanyDao companyDao;
}
