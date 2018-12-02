package com.br.salespda.common;

import android.app.Application;

public class UserInfo extends Application {

	private String UserID = "";  
	private String UserCode = "";
	private String UserName = "";
	private String Password = "";
	private String Remark = ""; 
	
	private String DeviceId = ""; // �豸id
	private String ClientVersion = "";// �汾 
	/*
	* sqllite���ݿ��ַ
	*/
	private String ConnString4Data = "";// sqllite���ݿ��ַ
	/*
	* appʹ��WebServiceURL
	*/
	private String WebServiceUrl = "";//appʹ��WebServiceURL
	/*
	* ���µ�ַUrl
	*/
	private String UpdateFileString = ""; //���µ�ַUrl
	/*
	* ���ݷ���ʱ��
	*/
	private int SendDataTime = 0;//���ݷ���ʱ��
	/*
	* ��������� (11=�豸ϵͳ����,12=����,10=��ʽ,13=BG)
	*/
	private String DbServer = "10";//��������� (11=�豸ϵͳ����,12=����,10=��ʽ,13=BG)

	public void setDbServer(String dbServer) {
		this.DbServer = dbServer;
	}

	public String getDbServer() {
		return this.DbServer;
	}
	
	public void setUserID(String userID) {
		this.UserID = userID;
	}

	public String getUserID() {
		return this.UserID;
	}
	
	public void setUserCode(String userCode) {
		this.UserCode = userCode;
	}

	public String getUserCode() {
		return this.UserCode;
	}
	
	public void setUserName(String UserName) {
		this.UserName = UserName;
	}

	public String getUserName() {
		return this.UserName;
	}
	
	public void setPassword(String Password) {
		this.Password = Password;
	}

	public String getPassword() {
		return this.Password;
	}
	
	public void setRemark(String Remark) {
		this.Remark = Remark;
	}

	public String getRemark() {
		return this.Remark;
	}
	
	public void setDeviceId(String DeviceId) {
		this.DeviceId = DeviceId;
	}

	public String getDeviceId() {
		return this.DeviceId;
	}
	
	public void setClientVersion(String ClientVersion) {
		this.ClientVersion = ClientVersion;
	}

	public String getClientVersion() {
		return this.ClientVersion;
	}
	
	public String getConnString4Data() {
		return "/sdcard/Android/data/Packing/data.db";
	}
	
	public String getWebServiceUrl() {
		return "http://211.149.157.146:9888/WebService.asmx";
	}
	
	public String getUpdateFileString() {
		return "http://211.149.157.146:9888/WebViewSuper.apk";
	}
	
	public int getSendDataTime() {
		return 20000;
	}

}
