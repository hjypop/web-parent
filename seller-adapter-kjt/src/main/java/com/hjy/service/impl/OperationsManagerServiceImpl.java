package com.hjy.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hjy.dao.product.IPcProductinfoDao;
import com.hjy.dao.product.IPcSkuinfoDao;
import com.hjy.dao.system.IScFlowBussinessHistoryDao;
import com.hjy.dao.system.IScFlowBussinesstypeDao;
import com.hjy.dao.system.IScFlowStatuschangeDao;
import com.hjy.entity.product.PcProductinfo;
import com.hjy.entity.product.PcSkuinfo;
import com.hjy.entity.system.ScFlowBussinessHistory;
import com.hjy.helper.DateHelper;
import com.hjy.helper.WebHelper;
import com.hjy.redis.core.RedisLaunch;
import com.hjy.redis.srnpr.ERedisSchema;
import com.hjy.service.IOperationsManagerService;

/**
 * @descriptions 执行运营人员的线上临时需求，如：一批商品下架、一批商品上架等等。
 *               这个类中的所有接口全部用于处理运营人员的线上临时需求，不对外开放。
 * 
 * @date 2016年8月15日下午5:08:01
 * @author Yangcl
 * @version 1.0.1
 */
@Service
public class OperationsManagerServiceImpl extends BaseServiceImpl<Object, Integer> implements IOperationsManagerService {
	private static Logger logger=Logger.getLogger(OperationsManagerServiceImpl.class);
	@Resource
	private IScFlowBussinesstypeDao scFlowBussinesstypeDao;

	@Resource
	private IScFlowStatuschangeDao scFlowStatuschangeDao;
	
	@Resource
	private IScFlowBussinessHistoryDao scFlowBussinessHistoryDao;
	
	@Resource
	private IPcProductinfoDao pcProductinfoDao;	
	
	@Resource
	private IPcSkuinfoDao pcSkuinfoDao;
	
	
	/**
	 * @descriptions 跨境通商品批量上架
	 * 
	 * @date 2016年8月15日下午5:10:43
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
	public JSONObject upStorage(String excelPath) {
		JSONObject result = new JSONObject();
		List<String> pathList = new ArrayList<>();
		pathList.add("//home//weblogic//3.xlsx");
		pathList.add("//home//weblogic//4.xlsx");
		
		for(String path : pathList){
			List<PcProductinfo> list = pcProductinfoDao.getListByProductCodeList(this.readExcel(path));
			for(PcProductinfo i : list){
				String uid=i.getUid();
				String toStatus="4497153900060002";
				String flowType = "449715390006";
				String userCode = "jobsystem";
				String lockcode = WebHelper.getInstance().addLock(10000 , "seller-adapter-kjt@" + i.getProductCode() + "@OperationsManagerServiceImpl.upStorage");      // 分布式锁
				if(StringUtils.isNotEmpty(lockcode)) {
					try {
						Integer count = pcProductinfoDao.updateProductStatus(new PcProductinfo(i.getUid() , i.getProductCode(), toStatus));
						System.out.println("count = " + count); 
						scFlowBussinessHistoryDao.insertSelective(new ScFlowBussinessHistory(
								UUID.randomUUID().toString().replace("-", ""),
								uid,
								flowType,
								userCode,
								DateHelper.formatDate(new Date()),
								i.getProductCode() + " - 技术需上架跨境通商品 - 跨境通已下架商品，实际商品库存充足，请协助再次上架 - 商品中心 - 马丽",
								toStatus							
								));
						boolean flag = this.redisReloadProductInfo(i.getProductCode());
						logger.info(i.getProductName() + "@"+ i.getProductCode() +"@缓存状态信息：" + flag); 
					} catch (Exception e) {
						logger.error("" , e);
					}finally{
						WebHelper.getInstance().unLock(lockcode);
					}
					
				}else{			// 处理机房断电、服务器宕机
					String message = "分布式锁生效，请删除锁：" + "seller-adapter-kjt@" + i.getProductCode() + "@OperationsManagerServiceImpl.upStorage";
					result.put("code", 14);
					result.put("desc", message);
					logger.error(message);
					return result; 
				}
			}
		}
		
		return result;
	}
	
	/**
	 * @descriptions 刷新Redis 
	 * 
	 * @param productCode_ 
	 * @date 2016年8月16日下午1:37:21
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private boolean redisReloadProductInfo(String productCode_){
		// 循环删除所有商品下关联的子活动
		for(String key : RedisLaunch.setFactory(ERedisSchema.ProductIcChildren).hgetAll(productCode_).keySet()){
			RedisLaunch.setFactory(ERedisSchema.IcSku).del(key);
		}
		// 删除所有Sku相关信息
		List<PcSkuinfo> skuList = pcSkuinfoDao.findList(new PcSkuinfo(productCode_)); 
		for(PcSkuinfo i : skuList){
			RedisLaunch.setFactory(ERedisSchema.IcSku).del(i.getSkuCode()); 
			RedisLaunch.setFactory(ERedisSchema.Stock).del(i.getSkuCode());
			RedisLaunch.setFactory(ERedisSchema.SkuStoreStock).del(i.getSkuCode());
		}
		RedisLaunch.setFactory(ERedisSchema.Product).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSku).del(productCode_);
		RedisLaunch.setFactory(ERedisSchema.ProductSales).del(productCode_);		//刷新销量缓存
		return true;
	}

	// for up_storage
	private List<String> readExcel(String fileName) {
		List<String> list = new ArrayList<>();

		boolean isE2007 = false; // 判断是否是excel2007格式
		if (fileName.endsWith("xlsx"))
			isE2007 = true;
		try {
			InputStream input = new FileInputStream(fileName); // 建立输入流
			Workbook wb = null;
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
			Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
			while (rows.hasNext()) {
				Row row = rows.next(); // 获得行数据
				if (row.getCell(14) != null && row.getCell(14).toString().trim().equals("否")) {
					list.add(String.valueOf(row.getCell(1)));
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return list;
	}

}
