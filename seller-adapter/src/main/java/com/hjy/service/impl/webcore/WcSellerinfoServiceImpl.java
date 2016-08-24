package com.hjy.service.impl.webcore;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hjy.dao.webcore.IWcSellerinfoDao;
import com.hjy.entity.webcore.WcSellerinfo;
import com.hjy.service.impl.BaseServiceImpl;
import com.hjy.service.webcore.IWcSellerinfoService;

/**
 * 
 * 类: WcSellerinfoServiceImpl <br>
 * 描述: 商家信息业务处理接口实现类 <br>
 * 作者: zhy<br>
 * 时间: 2016年8月17日 上午11:39:29
 */
@Service
public class WcSellerinfoServiceImpl extends BaseServiceImpl<WcSellerinfo, Integer> implements IWcSellerinfoService {

	@Autowired
	private IWcSellerinfoDao dao;

	
	
	/**
	 * @descriptions ajax 分页
	 * 
	 * @param request
	 * @param session 
	 * @date 2016年8月24日下午2:03:56
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxPageData(WcSellerinfo entity , HttpServletRequest request, HttpSession session) {
		JSONObject result = new JSONObject();
		String pageNum = request.getParameter("pageNum"); // 当前第几页
		String pageSize = request.getParameter("pageSize");  // 当前页所显示记录条数
		int num = 1;
		int size = 10;
		if (StringUtils.isNotBlank(pageNum)) {
			num = Integer.parseInt(pageNum);
		}
		if (StringUtils.isNotBlank(pageSize)) {
			size = Integer.parseInt(pageSize);
		}
		
		/*
		 * 如果分页参数当前页为空，默认为0，页面最大显示数为空，默认为10
		 */
		String sortString = "create_time.desc";
		Order.formString(sortString);
		PageHelper.startPage(num, size);
		List<WcSellerinfo> list = dao.queryPage(entity);
		if (list != null && list.size() > 0) {
			PageInfo<WcSellerinfo> pageList = new PageInfo<WcSellerinfo>(list);
			result.put("status", "success");
			result.put("data", pageList);
		}else{
			result.put("status", "error");
			result.put("msg", "没有查询到可以显示的数据");
		}
		result.put("entity", entity);
		return result; 
	}
	
	
	
	/**
	 * 
	 * 方法: selectBySellerCode <br>
	 * 描述: TODO
	 * 
	 * @param sellerCode
	 * @return
	 * @see com.hjy.service.webcore.IWcSellerinfoService#selectBySellerCode(java.lang.String)
	 */
	@Override
	public WcSellerinfo selectBySellerCode(String sellerCode) {
		return dao.selectBySellerCode(sellerCode);
	}

	/**
	 * 
	 * 方法: deleteBySellerCode <br>
	 * 描述: TODO
	 * 
	 * @param sellerCode
	 * @return
	 * @see com.hjy.service.webcore.IWcSellerinfoService#deleteBySellerCode(java.lang.String)
	 */
	@Override
	public int deleteBySellerCode(String sellerCode) {
		return dao.deleteBySellerCode(sellerCode);
	}

	/**
	 * 
	 * 方法: selectBySellerCodeByApi <br>
	 * 描述: TODO
	 * 
	 * @param sellerCode
	 * @return
	 * @see com.hjy.service.webcore.IWcSellerinfoService#selectBySellerCodeByApi(java.lang.String)
	 */
	@Override
	public WcSellerinfo selectBySellerCodeByApi(String sellerCode) {
		return dao.selectBySellerCodeByApi(sellerCode);
	}



}
