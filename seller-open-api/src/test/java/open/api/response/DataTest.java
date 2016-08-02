package open.api.response;

import com.hjy.base.IApiResponse;

public class DataTest implements IApiResponse {
	private String name;
	private String pwdss;
	private String msgss;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwdss() {
		return pwdss;
	}
	public void setPwdss(String pwdss) {
		this.pwdss = pwdss;
	}
	public String getMsgss() {
		return msgss;
	}
	public void setMsgss(String msgss) {
		this.msgss = msgss;
	}
}
