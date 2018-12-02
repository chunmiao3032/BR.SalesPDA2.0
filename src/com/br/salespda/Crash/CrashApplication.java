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
	        // ע��crashHandler  
	        crashHandler.init(getApplicationContext());  
	        // ������ǰû���͵ı���(��ѡ)  
	        //crashHandler.sendPreviousReportsToServer();  
	    }  
	
		private String UserCode = "";
		private String UserName = "";
		private String Password = "";
		private String Remark = "";
		private String UserID = "";   
		private String DeviceId = ""; // �豸id
		private String ClientVersion = "";// �汾 	  
		private String ConnString4Data = "/sdcard/Android/data/Packing/data.db";// sqllite���ݿ��ַ 
		private String WebServiceUrl = "http://59.46.62.138:9900/CRBPdaLNService.asmx";//appʹ��WebServiceURL
		private String UpdateFileString = "http://59.46.62.138:9900/AppFile/BR.SalesPDA.apk";//��װ�ļ�·�� 
		private int SendDataTime = 20000;//���ݷ���ʱ�� 
		private String DbServer = "10";//��������� (11=�豸ϵͳ����,12=����,10=��ʽ,13=BG)

		//��װ����㵥���-----------------------------------------------------------------
		private List<UserInCorporationResult> UserInCorpList;//�û����������б�
		private String UserCorporationID = "";//�û�ѡ��Ĺ���ID
		private String UserStoreHouseID = "";//�û�ѡ��ֿ�ID
		private String UserCorporationName = "";//�û�ѡ��Ĺ���ID
		private String UserStoreHouseName = "";//�û�ѡ��ֿ�ID
		private CustomerResult UserCustomer = null;//�û�ѡ������
		
		/**
		 * ��װ����㵥�У������û���ѡ��Ĳֿ�����
		 * @param UserStoreHouseName
		 */
		public void setUserStoreHouseName(String UserStoreHouseName)
		{
			 this.UserStoreHouseName = UserStoreHouseName;
		}
		
		/**
		 * ��װ����㵥�У���ȡ�û���ѡ��Ĳֿ�����
		 * @return
		 */
		public String getUserStoreHouseName()
		{
			return this.UserStoreHouseName;
		}
		
		/**
		  * ��װ����㵥�У������û���ѡ��Ĺ�������
		  * @param UserCorporationName
		  */
		public void setUserCorporationName(String UserCorporationName)
		{
			 this.UserCorporationName = UserCorporationName;
		}
		
		/**
		 * ��װ����㵥�У���ȡ�û���ѡ��Ĺ�������
		 * @return
		 */
		public String getUserCorporationName()
		{
			return this.UserCorporationName;
		}
		
		
		
		/**
		 * ��װ����㵥�У������û���ѡ��Ĳֿ�id
		 * @param UserStoreHouseID
		 */
		public void setUserStoreHouseID(String UserStoreHouseID)
		{
			 this.UserStoreHouseID = UserStoreHouseID;
		}
		
		/**
		 * ��װ����㵥�У���ȡ�û���ѡ��Ĳֿ�id
		 * @return
		 */
		public String getUserStoreHouseID()
		{
			return this.UserStoreHouseID;
		}
		
		/**
		  * ��װ����㵥�У������û���ѡ��Ĳֿ�id
		  * @param UserCorporationID
		  */
		public void setUserCorporationID(String UserCorporationID)
		{
			 this.UserCorporationID = UserCorporationID;
		}
		
		/**
		 * ��װ����㵥�У���ȡ�û���ѡ��Ĺ���id
		 * @return
		 */
		public String getUserCorporationID()
		{
			return this.UserCorporationID;
		}
		
		/**
		 * ��װ����㵥�У������û����������б�
		 * @param UserInCorpList
		 */
		public void setUserInCorpList(List<UserInCorporationResult> UserInCorpList)
		{
			 this.UserInCorpList = UserInCorpList;
		}
		
		/**
		 * ��װ����㵥�У���ȡ�û����������б�
		 * @return
		 */
		public List<UserInCorporationResult> getUserInCorpList()
		{
			 return this.UserInCorpList;
		}
		
		/**
		 * ��װ����㵥�У������û�ѡ��ľ�����
		 * @param userCustomer
		 */
		public void setUserCustomer(CustomerResult userCustomer)
		{
			 this.UserCustomer = userCustomer;
		}
		
		/**
		 * ��װ����㵥�У���ȡ�û�ѡ��ľ�����
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
 