package com.hjy.selleradapter.job;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hjy.helper.WebHelper;
import com.hjy.quartz.job.RootJob;
import com.hjy.selleradapter.kjt.RsyncKjtProductInventoryById;
import com.hjy.selleradapter.kjt.model.InventoryPageModel;

/**
 * 
 * alias JobForKtjChannelInventory<br>
 * 类: SyncInventory <br>
 * 描述: 向跨境通发送数据 product_ids商品ID，多个商品ID用英文逗号（,）分隔，最多20个商品ID saleChannelSysNo 渠道编号
 * <br>
 * 跨境通商品库存同步<br>
 * 
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午5:27:16
 */
public class JobForInventory extends RootJob {

	@Override
	public void doExecute(JobExecutionContext context) {
		String product_id = "";
		String product_ids = "";
		String newproduct_id = "";
		int totalpage;
		String infosql = "select product_code_old from pc_productinfo where seller_code='SI2003' and small_seller_code='SF03KJT' ";
		List<Map<String, Object>> list = null;// DbUp.upTable("pc_productinfo").dataSqlList(infosql,
												// null);
		InventoryPageModel cpp = new InventoryPageModel(list, 20);
		totalpage = cpp.getTotalPages();
		if (list != null) {
			for (int i = 0; i < totalpage; i++) {
				String lockCode = WebHelper.getInstance().addLock(1, "JobForKtjChannelInventory156554");// 跨境通接口请求时间限制为500ms，现在锁1秒
				if (StringUtils.isNotBlank(lockCode)) {
					StringBuffer sb = new StringBuffer();
					int startNum = (i * 20);
					int endNum = (i + 1) * 20;
					for (int j = startNum; j < endNum; j++) {
						if (j >= list.size())
							break;
						product_id = (String) list.get(j).get("product_code_old");
						newproduct_id = product_id.replaceAll("'", "");
						sb.append(newproduct_id).append(",");
					}
					product_ids = sb.toString().substring(0, sb.toString().length() - 1);
					RsyncKjtProductInventoryById rsyncChannel = new RsyncKjtProductInventoryById();
					rsyncChannel.upRsyncRequest().setProductIDs(product_ids);
					rsyncChannel.upRsyncRequest()
							.setSaleChannelSysNo(getConfig("groupcenter.rsync_kjt_SaleChannelSysNo"));
					rsyncChannel.doRsync();
				} else {
					i--;
				}
			}
		}
	}

}