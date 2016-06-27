package com.hjy.selleradapter.kjt.response;

import java.util.List;

/**
 * 类: RsyncResponseTraceOrder <br>
 * 描述: 定时同步跨境通订单状态响应参数类 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午3:15:19
 */
public class RsyncResponseOrderStatus extends RsyncKjtResponseBase {

	private Data Data;

	public Data getData() {
		return Data;
	}

	public void setData(Data data) {
		Data = data;
	}

	public static class Data {
		private List<SoOrder> TraceOrderList;

		public List<SoOrder> getTraceOrderList() {
			return TraceOrderList;
		}

		public void setTraceOrderList(List<SoOrder> traceOrderList) {
			TraceOrderList = traceOrderList;
		}

	}

	public static class SoOrder {
		private int SOID;
		private int SOStatus;
		private List<Solog> Logs;

		public int getSOID() {
			return SOID;
		}

		public void setSOID(int sOID) {
			SOID = sOID;
		}

		public int getSOStatus() {
			return SOStatus;
		}

		public void setSOStatus(int sOStatus) {
			SOStatus = sOStatus;
		}

		public List<Solog> getLogs() {
			return Logs;
		}

		public void setLogs(List<Solog> logs) {
			Logs = logs;
		}
	}

	public static class Solog {
		private String OptTime;
		private int OptType;
		private String OptNote;

		public String getOptTime() {
			return OptTime;
		}

		public void setOptTime(String optTime) {
			OptTime = optTime;
		}

		public int getOptType() {
			return OptType;
		}

		public void setOptType(int optType) {
			OptType = optType;
		}

		public String getOptNote() {
			return OptNote;
		}

		public void setOptNote(String optNote) {
			OptNote = optNote;
		}
	}

}
