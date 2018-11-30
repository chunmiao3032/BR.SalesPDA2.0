package com.br.salespda.Crash;

import java.util.ArrayList;
import java.util.List;

import com.br.salespda.basic.Packing.CustomerResult;
import com.br.salespda.basic.Packing.UserInCorporationResult;

import android.app.Activity;
import android.app.Application;  

public class CrashApplication extends Application {  
   
	  @Override  
	    public void onCreate() {  
	        super.onCreate();  
	        CrashHandler crashHandler = CrashHandler.getInstance();  
	        // 注册crashHandler  
	        crashHandler.init(getApplicationContext());  
	        // 发送以前没发送的报告(可选)  
	        //crashHandler.sendPreviousReportsToServer();  
	    }  
	
		private String UserCode = "";
		private String UserName = "";
		private String Password = "";
		private String Remark = "";
		private String UserID = "";   
		private String DeviceId = ""; // 设备id
		private String ClientVersion = "";// 版本 	  
		private String ConnString4Data = "/sdcard/Android/data/Packing/data.db";// sqllite数据库地址 
		private String WebServiceUrl = "http://59.46.62.138:9900/CRBPdaLNService.asmx";//app使用WebServiceURL
		private String UpdateFileString = "http://59.46.62.138:9900/AppFile/BR.SalesPDA.apk";//安装文件路径 
		private int SendDataTime = 20000;//数据发送时间 
		private String DbServer = "10";//服务器序号 (11=设备系统测试,12=测试,10=正式,13=BG)

		//包装物结算单相关-----------------------------------------------------------------
		private List<UserInCorporationResult> UserInCorpList;//用户所属工厂列表
		private String UserCorporationID = "";//用户选择的工厂ID
		private String UserStoreHouseID = "";//用户选择仓库ID
		private String UserCorporationName = "";//用户选择的工厂ID
		private String UserStoreHouseName = "";//用户选择仓库ID
		private CustomerResult UserCustomer = null;//用户选择经销商
		
		/**
		 * 包装物结算单中，设置用户所选择的仓库名称
		 * @param UserStoreHouseName
		 */
		public void setUserStoreHouseName(String UserStoreHouseName)
		{
			 this.UserStoreHouseName = UserStoreHouseName;
		}
		
		/**
		 * 包装物结算单中，获取用户所选择的仓库名称
		 * @return
		 */
		public String getUserStoreHouseName()
		{
			return this.UserStoreHouseName;
		}
		
		/**
		  * 包装物结算单中，设置用户所选择的工厂名称
		  * @param UserCorporationName
		  */
		public void setUserCorporationName(String UserCorporationName)
		{
			 this.UserCorporationName = UserCorporationName;
		}
		
		/**
		 * 包装物结算单中，获取用户所选择的工厂名称
		 * @return
		 */
		public String getUserCorporationName()
		{
			return this.UserCorporationName;
		}
		
		
		
		/**
		 * 包装物结算单中，设置用户所选择的仓库id
		 * @param UserStoreHouseID
		 */
		public void setUserStoreHouseID(String UserStoreHouseID)
		{
			 this.UserStoreHouseID = UserStoreHouseID;
		}
		
		/**
		 * 包装物结算单中，获取用户所选择的仓库id
		 * @return
		 */
		public String getUserStoreHouseID()
		{
			return this.UserStoreHouseID;
		}
		
		/**
		  * 包装物结算单中，设置用户所选择的仓库id
		  * @param UserCorporationID
		  */
		public void setUserCorporationID(String UserCorporationID)
		{
			 this.UserCorporationID = UserCorporationID;
		}
		
		/**
		 * 包装物结算单中，获取用户所选择的工厂id
		 * @return
		 */
		public String getUserCorporationID()
		{
			return this.UserCorporationID;
		}
		
		/**
		 * 包装物结算单中，设置用户所属工厂列表
		 * @param UserInCorpList
		 */
		public void setUserInCorpList(List<UserInCorporationResult> UserInCorpList)
		{
			 this.UserInCorpList = UserInCorpList;
		}
		
		/**
		 * 包装物结算单中，获取用户所属工厂列表
		 * @return
		 */
		public List<UserInCorporationResult> getUserInCorpList()
		{
			 return this.UserInCorpList;
		}
		
		/**
		 * 包装物结算单中，设置用户选择的经销商
		 * @param userCustomer
		 */
		public void setUserCustomer(CustomerResult userCustomer)
		{
			 this.UserCustomer = userCustomer;
		}
		
		/**
		 * 包装物结算单中，获取用户选择的经销商
		 * @return
		 */
		public CustomerResult getUserCustomer()
		{
			return this.UserCustomer;
		}
		
		//--------------------------------------------------------------------------------
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
			return this.ConnString4Data;
		}
		
		
		
		public void setWebServiceUrl(String WebServiceUrl) {
			this.WebServiceUrl = WebServiceUrl;
			this.UpdateFileString = this.WebServiceUrl.replace("CRBPdaLNService.asmx", "AppFile/BR.SalesPDA.apk");
		}
		
		public String getWebServiceUrl() {
			return this.WebServiceUrl;
		}

		
		public String getUpdateFileString() {
			return this.WebServiceUrl.replace("CRBPdaLNService.asmx", "AppFile/BR.SalesPDA.apk");
		}
		
		public int getSendDataTime() {
			return this.SendDataTime;
		}

	}
 