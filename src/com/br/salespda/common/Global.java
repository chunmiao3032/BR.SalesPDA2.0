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
	 * 辽宁正式环境
	 */
//	public static String WebServiceUrl = "http://www.crbln.com/APPService/CRBPdaLNService.asmx";
//	public static String updateFileString = "http://www.crbln.com/AppService/AppFile/BR.SalesPDA.apk";//安装文件路径
//	public static String dbServer = "10";		//服务器序号 (11=设备系统测试,12=测试,10=正式,13=BG)//兰州20
	
	/**
	 * 辽宁正式环境,鞍山挑票,电信接口
	 */
	public static String WebServiceUrl = "http://59.46.62.138:9900/CRBPdaLNService.asmx";
	public static String updateFileString = "http://59.46.62.138:9900/AppFile/BR.SalesPDA.apk";//安装文件路径
	public static String dbServer = "10";		//服务器序号 (11=设备系统测试,12=测试,10=正式,13=BG)
	
	
	/**
	 * 甘青藏正式地址
	 */
//	public static String WebServiceUrl = "http://172.15.151.2/CRBPdaLNService.asmx";//兰州
//	public static String updateFileString = "http://172.15.151.2/AppFile/BR.SalesPDA.apk";//兰州安装文件路径
//	public static String dbServer = "20";		//服务器序号 (11=设备系统测试,12=测试,10=正式,13=BG)//兰州20
	
	/**
	 * 测试环境
	 */
//	public static String WebServiceUrl = "http://192.168.86.24:9999/CRBPdaLNService.asmx";
//	public static String updateFileString = "http://192.168.86.24:9999/AppFile/BR.SalesPDA.apk";//安装文件路径
//	public static String dbServer = "10";		//服务器序号 (11=设备系统测试,12=测试,10=正式,13=BG)//兰州20
	
	
	public static int SendDataTime = 20000; 
	public static String deviceId = "";		//设备id
	public static String clientVersion = "";//版本
	 
	public static String ID = "";		//用户id
	public static String UserCode = "";	//用户编码
	public static String UserName = "";	//用户名称
	public static String Password = "";	//用户密码
	public static String Remark  = "";	//用户备注
	public static String IsAdmin = "";	//是否是管理员
	
	public static List<SaleBillDo> _ModelList = new ArrayList<SaleBillDo>();//所有已扫描未提交的销货单 
	public static String Version;
	
	 
}
