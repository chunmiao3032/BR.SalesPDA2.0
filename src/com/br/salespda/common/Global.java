package com.br.salespda.common;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.br.salespda.basic.SaleBillDo;
import com.br.salespda.manager.DbManager_Config;

public class Global {

	public static String _ConnString4Config = "/sdcard/Android/data/BR.SalesPDA/cfg.db";
	public static String _ConnString4Data = "/sdcard/Android/data/BR.SalesPDA/data.db"; 
	
	/**
	 * ������ʽ����
	 */
//	public static String WebServiceUrl = "http://www.crbln.com/APPService/CRBPdaLNService.asmx";
//	public static String updateFileString = "http://www.crbln.com/AppService/AppFile/BR.SalesPDA.apk";//��װ�ļ�·��
//	public static String dbServer = "10";		//��������� (11=�豸ϵͳ����,12=����,10=��ʽ,13=BG)//����20
	
	/**
	 * ������ʽ����,��ɽ��Ʊ,���Žӿ�
	 */
	public static String WebServiceUrl = "http://59.46.62.138:9900/CRBPdaLNService.asmx";
	public static String updateFileString = "http://59.46.62.138:9900/AppFile/BR.SalesPDA.apk";//��װ�ļ�·��
	public static String dbServer = "10";		//��������� (11=�豸ϵͳ����,12=����,10=��ʽ,13=BG)
	
	
	/**
	 * �������ʽ��ַ
	 */
//	public static String WebServiceUrl = "http://172.15.151.2/CRBPdaLNService.asmx";//����
//	public static String updateFileString = "http://172.15.151.2/AppFile/BR.SalesPDA.apk";//���ݰ�װ�ļ�·��
//	public static String dbServer = "20";		//��������� (11=�豸ϵͳ����,12=����,10=��ʽ,13=BG)//����20
	
	/**
	 * ���Ի���
	 */
//	public static String WebServiceUrl = "http://192.168.86.24:9999/CRBPdaLNService.asmx";
//	public static String updateFileString = "http://192.168.86.24:9999/AppFile/BR.SalesPDA.apk";//��װ�ļ�·��
//	public static String dbServer = "10";		//��������� (11=�豸ϵͳ����,12=����,10=��ʽ,13=BG)//����20
	
	
	public static int SendDataTime = 20000; 
	public static String deviceId = "";		//�豸id
	public static String clientVersion = "";//�汾
	 
	public static String ID = "";		//�û�id
	public static String UserCode = "";	//�û�����
	public static String UserName = "";	//�û�����
	public static String Password = "";	//�û�����
	public static String Remark  = "";	//�û���ע
	public static String IsAdmin = "";	//�Ƿ��ǹ���Ա
	
	public static List<SaleBillDo> _ModelList = new ArrayList<SaleBillDo>();//������ɨ��δ�ύ�������� 
	public static String Version;
	
	 
}
