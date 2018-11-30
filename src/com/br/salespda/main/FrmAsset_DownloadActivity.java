package com.br.salespda.main;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

//import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.br.salespda.R;
import com.br.salespda.basic.AssetDO;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.CItem;
import com.br.salespda.manager.DbManager_Data;
import com.br.salespda.webservice.WebServiceManager;

import java.sql.Date;
import java.text.SimpleDateFormat;       

public class FrmAsset_DownloadActivity extends BaseActivity{
	
	public Button bt_menuLeft;//左下菜单按钮
	public Button bt_menuRight;//右下菜单按钮
	
	public ListView lv_menuLeft;//左下菜单
	public ListView lv_menuRight;//右下菜单
	
	public SimpleAdapter MenuAdapterLeft;//左下菜单
	public ArrayList<HashMap<String, Object>> listmenuLeft;
	
	public SimpleAdapter MenuAdapterRight;//右下菜单
	public ArrayList<HashMap<String, Object>> listmenuRight;
	
	boolean set_lvleft = false;//左下菜单显示隐藏
	boolean set_lvright = false;//左下菜单显示隐藏
	
	Spinner cmbCorp;
	Button btnRun;
	ProgressDialog loginDialog;
	
	Handler cwjHandler = null;
	final Runnable mUpdateResults = new Runnable() {
	        public void run() {
	            updateUI();
	        } 
	    };
	
    private void updateUI() {
    	loginDialog.cancel();
		if (loginDialog != null && loginDialog.isShowing())
		{
			loginDialog.dismiss();
		} 
		Toast.makeText(getApplicationContext(),"数据同步成功",2000).show();
	}    
  
//	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmasset_download);
		 
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		cwjHandler = new Handler(getApplicationContext().getMainLooper());
		initial() ;
		initcmbCorp();
		 
	}
	
	private void initial() {
		bt_menuLeft = (Button)findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button)findViewById(R.id.bt_menuRight);  
		tvtitle = (TextView)findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView)findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView)findViewById(R.id.lv_menuRight);
		bt_menuLeft.setText("菜单");
		bt_menuRight.setText("返回");
		tvtitle.setText("数据下载");
		
		SetMenuLeft(); 
		 
		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		bt_menuRight.setOnClickListener(bt_menuRightClick); 
		
		cmbCorp = (Spinner)findViewById(R.id.cmbCorp);
		btnRun = (Button)findViewById(R.id.btnRun);
		
		btnRun.setOnClickListener(btnRun_Click);
	}
	
	//左下菜单
	private OnClickListener bt_menuLeftClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) { 
			try
			{ 
				if (set_lvleft)
				{
					lv_menuLeft.setVisibility(View.GONE);

				}
				else
				{
					lv_menuLeft.setVisibility(View.VISIBLE);
				}
				set_lvleft = !set_lvleft;
			}
			catch (Exception e)
			{ 
				e.toString();
			}
		}
	}; 
	
	//右下菜单
	private OnClickListener bt_menuRightClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{ 
					finish();
				}
				catch (Exception e)
				{ 
					e.toString();
				}
			}
		}; 
		
	private void SetMenuLeft()
		{	  
			try
			{
				listmenuLeft = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> map = new HashMap<String, Object>();
				 
				map = new HashMap<String, Object>();
				map.put("menuname", "终止同步");
				listmenuLeft.add(map); 
				String[] fromColumns = new String[] { "menuname" };
				int[] toLayoutIDs = new int[] { R.id.tv_menu };
				MenuAdapterLeft = new SimpleAdapter(this, listmenuLeft,
						R.layout.workmenu_model, fromColumns, toLayoutIDs);
				lv_menuLeft.setAdapter(MenuAdapterLeft); 
				  
			}
			catch (Exception e)
			{ 
			}

		}
	
	//左下菜单单击事件
	private OnItemClickListener lv_menuLeftclick = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{ 
			switch (arg2)
			{
			//终止同步
			case 0:
				 
				break; 
			}
			lv_menuLeft.setVisibility(View.GONE);
			set_lvleft = !set_lvleft;
		} 
	};
	
	//初始化组织架构下拉列表
	private void initcmbCorp() {
		 
		cmbCorp = (Spinner) findViewById(R.id.cmbCorp);  
		//获取发货仓库
	    List<CItem> lst =  WebServiceManager.GetAllCorporation();  
		      
	    ArrayAdapter<CItem> myaAdapter = new ArrayAdapter<CItem>(this, android.R.layout.simple_spinner_item, lst);  
	    cmbCorp.setAdapter(myaAdapter); 
	    //int ids = ((CItem) cmbStorehouse.getSelectedItem()).GetID(); 
	  
		    
		}

	private OnClickListener btnRun_Click = new OnClickListener()
	{ 
		@Override
		public void onClick(View v) 
		{
			  String ids = ((CItem)cmbCorp.getSelectedItem()).GetID();   
			  if (ids == null || ids.length() == 0)
	          { 
	              new AlertDialog.Builder(FrmAsset_DownloadActivity.this).setTitle("系统提示") 		  
	 		     .setMessage("请先选择所属机构。")//设置显示的内容  		  
	 		     .setPositiveButton("确定",new DialogInterface.OnClickListener() { 
	 		         @Override  	  
	 		         public void onClick(DialogInterface dialog, int which) { 
	 		        	 return;
	 		         }  	  
	 		     }).show();  
	          }
			  else
			  { 
				    loadProgressBar();
					new Thread()
					{ 
						public void run()
						{ 
							Down_load();
							cwjHandler.post(mUpdateResults);
						} 
					
					}.start();
				  //Down_load();
			  }
		}  
		
	};	 
	
	private void loadProgressBar()
	{
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("正在下载数据请稍等...");
		loginDialog.setCancelable(false);
		loginDialog.show();
	}
	
	private void Down_load() {
		try
		{   
			//记录日志
			//List<String> list_log = new ArrayList<String>();
			  
			 String CorpCode = ((CItem) cmbCorp.getSelectedItem()).GetCode();    
			  
			 //list_log.add("开始从服务器获取所有固定资产信息" + getCurrentTime());
			  
			 //从服务器获取所有固定资产信息
//	         List<AssetDO> allAssets = WebServiceManager.GetAllAssets(CorpCode); 
	         Hashtable<String, AssetDO> allAssets = WebServiceManager.GetAllAssetsHash(CorpCode); 
	         
			 //list_log.add("从服务器获取所有固定资产信息结束" + getCurrentTime());
			 
	         if ((allAssets == null) || (allAssets.size() == 0))
	         {
//	             Toast.makeText(getApplication(),"服务器上没有需要下载的数据。",2000).show();
	             return;
	         }
//****************************************************************************************	  	         
//	         List<String> first = new ArrayList<String>();           //服务器获取所有固定资产信息的“条码”字段
//	         List<String> allAssetBarcodes = new ArrayList<String>();//本地数据库所有固定资产信息的“条码”字段
//	         List<String> strArray3 = new ArrayList<String>();	//服务器 排除 本地  = 新增
//	         List<String> strArray4 = new ArrayList<String>();	//本地 排除 服务器 = 本地冗余
//	         List<String> strArray5 = new ArrayList<String>();	//服务器 与 本地交集 = 交集
	            
	         Hashtable<String,String> first = new Hashtable<String, String>();           //服务器获取所有固定资产信息的“条码”字段
	         Hashtable<String,String> allAssetBarcodes = new Hashtable<String, String>();//本地数据库所有固定资产信息的“条码”字段
	         Hashtable<String,String> strArray3 = new Hashtable<String, String>();	//服务器 排除 本地  = 新增
	         Hashtable<String,String> strArray4 = new Hashtable<String, String>();	//本地 排除 服务器 = 本地冗余
	         Hashtable<String,String> strArray5 = new Hashtable<String, String>();	//服务器 与 本地交集 = 交集
	         
	         Cursor cur_allAssetBarcodes = db_data.GetAllAssetBarcodes();
	             
//****************************************************************************************	  	         
	         //first  服务器
//	         for(AssetDO asset : allAssets)
//	         {
//	        	 first.add(asset.Barcode);
//	         }
	          
	         Enumeration e = allAssets.elements(); //遍历value
	         while(e.hasMoreElements() )
	         { 
	        	 AssetDO assetdo = (AssetDO)e.nextElement();
	        	 first.put(assetdo.Barcode,assetdo.Barcode); 
	         }
//****************************************************************************************	  	        
	         //allAssetBarcodes  本地
//	         if(cur_allAssetBarcodes != null && cur_allAssetBarcodes.getCount() > 0)
//	         {
//		         for(cur_allAssetBarcodes.moveToFirst();!cur_allAssetBarcodes.isAfterLast();cur_allAssetBarcodes.moveToNext())
//		         {   
//		             String barcode = cur_allAssetBarcodes.getString(cur_allAssetBarcodes.getColumnIndex("Barcode"));
//		             allAssetBarcodes.add(barcode);
//		         }
//	         }
	         if(cur_allAssetBarcodes != null && cur_allAssetBarcodes.getCount() > 0)
	         {
		         for(cur_allAssetBarcodes.moveToFirst();!cur_allAssetBarcodes.isAfterLast();cur_allAssetBarcodes.moveToNext())
		         {  
		        	 String barcode = cur_allAssetBarcodes.getString(cur_allAssetBarcodes.getColumnIndex("Barcode"));
		        	 allAssetBarcodes.put(barcode,barcode);
		         }
	         }
	         
//****************************************************************************************	  	         
	         //服务器 排除 本地
//	         for(String item : first)
//	         {
//	        	 if(!allAssetBarcodes.contains(item))
//	        	 {
//	        		 strArray3.add(item);
//	        	 }
//	         } 
	         Enumeration efirst = first.keys(); 
	         while(efirst.hasMoreElements())
	         {
	        	 String barcode = efirst.nextElement().toString();
	        	 if(!allAssetBarcodes.containsKey(barcode))
	        	 {
	        		 strArray3.put(barcode,barcode);
	        	 }
	         }
//****************************************************************************************	  	         
	         //本地排除 服务器 
//	         for(String item : allAssetBarcodes)
//	         {
//	        	 if(!first.contains(item))
//	        	 {
//	        		 strArray4.add(item);
//	        	 }
//	         }
	         Enumeration eallAssetBarcodes = allAssetBarcodes.keys(); 
	         while(eallAssetBarcodes.hasMoreElements())
	         {
	        	 String barcode = eallAssetBarcodes.nextElement().toString();
	        	 if(!first.containsKey(barcode))
	        	 {
	        		 strArray4.put(barcode,barcode);
	        	 }
	         }
//****************************************************************************************	  	         
	         //服务器 与 本地交集
//	         for(String item : first)
//	         {
//	        	 if(allAssetBarcodes.contains(item))
//	        	 {
//	        		 strArray5.add(item);
//	        	 }
//	         }  
	         Enumeration efirst1 = first.keys(); 
	         while(efirst1.hasMoreElements())
	         {
	        	 String barcode = efirst1.nextElement().toString();
	        	 if(allAssetBarcodes.containsKey(barcode))
	        	 {
	        		 strArray5.put(barcode, barcode);
	        	 }
	         }
//****************************************************************************************	  	                  
	         int num2 = (strArray3.size() + strArray5.size()) - strArray4.size();
	         if (num2 == 0)
	         {
//	             Toast.makeText(getApplication(), "本地数据已经最新，没有需要下载的数据。",2000).show();
	             return;
	         }		 
//****************************************************************************************	         
//	         int idx = 0;
//	         //服务器 排除 本地  = 新增
//	         for(String barcode : strArray3)
//	         {
//	        	 try
//	        	 {
//		        	 AssetDO tdo = getAssetDo(allAssets,barcode);
//		        	 if (tdo != null)
//		             {    
//		                 tdo.Photo = WebServiceManager.GetAssetPhoto(barcode); 
//		                 db_data.InsertAsset(tdo); 
//		                 idx++;
//		             }
//	        	 }
//	        	 catch(Exception ex)
//	        	 {
//	        		 Log.i("ycmtest", "barcode=" + barcode + "strArray3=" + strArray3.size() + "异常信息=" + ex.getMessage());
//	        	 }
//	         }
	         int idx = 0;
	         Enumeration estrArray3 = strArray3.keys(); 
	         while(estrArray3.hasMoreElements())
	         {
	        	 String barcode = estrArray3.nextElement().toString();
	        	 try
	        	 { 
		        	 AssetDO tdo = allAssets.get(barcode);
		        	 if (tdo != null)
		             {    
		                 tdo.Photo = WebServiceManager.GetAssetPhoto(barcode); 
		                 db_data.InsertAsset(tdo); 
		                 idx++;
		             }
	        	 }
	        	 catch(Exception ex)
	        	 {
	        		 Log.i("ycmtest", "barcode=" + barcode + "strArray3=" + strArray3.size() + "异常信息=" + ex.getMessage());
	        	 }
	         }
	         
//****************************************************************************************	 	         	         
//	         idx = 0;
//	         //交集 按照服务器更新本地
//	         for (String barcode : strArray5)
//	         {
//	        	 try
//	        	 {
//		        	 AssetDO tdo = getAssetDo(allAssets,barcode);
//		        	 if (tdo != null)
//		             {   
//		                 tdo.Photo = WebServiceManager.GetAssetPhoto(barcode); 
//		     			 db_data.UpdateAsset(tdo);  
//		                 idx++;
//		             }
//	        	 }
//	        	 catch(Exception ex)
//	        	 {
//	        		 Log.i("ycmtest", "barcode=" + barcode + "strArray5=" + strArray5.size() + "异常信息=" + ex.getMessage()); 
//	        	 }
//	         }
	         idx = 0;
	         Enumeration estrArray5 = strArray5.keys(); 
	         while(estrArray5.hasMoreElements())
	         {
	        	 String barcode = estrArray5.nextElement().toString();
	        	 try
	        	 { 
		        	 AssetDO tdo = allAssets.get(barcode);
		        	 if (tdo != null)
		             {    
		                 tdo.Photo = WebServiceManager.GetAssetPhoto(barcode); 
		                 db_data.UpdateAsset(tdo); 
		                 idx++;
		             }
	        	 }
	        	 catch(Exception ex)
	        	 {
	        		 Log.i("ycmtest", "barcode=" + barcode + "strArray3=" + strArray3.size() + "异常信息=" + ex.getMessage());
	        	 }
	         }
	         
//****************************************************************************************	 	           	         
//删除本地有服务器没有的
//	         for (String barcode : strArray4)
//	         { 
//	        	 db_data.DeleteAsset(barcode); 
//	         }
	         idx = 0;
	         Enumeration estrArray4 = strArray4.keys(); 
	         while(estrArray4.hasMoreElements())
	         {
	        	 String barcode = estrArray4.nextElement().toString();
	        	 db_data.DeleteAsset(barcode); 
	        	 idx++;
	         }
//****************************************************************************************	         
		}
		catch(Exception ex)
		{
//			Toast.makeText(this, "异常:" + ex.getMessage(), 2000).show();
			Log.i("ycmtest", "异常信息=" + ex.getMessage());
		}
           
	}
	
	
	private AssetDO getAssetDo(List<AssetDO> allAssets,String Barcode)
	{
		AssetDO entity = null;
		for(AssetDO item : allAssets)
		{
			if(item.Barcode.equals(Barcode))
			{
				entity = item;
				return entity;
			}
		}
		return entity;
	}
	
	@Override
	protected void onDestroy() { 
		super.onDestroy();
		
		try
		{
			loginDialog.cancel();
			if (loginDialog != null && loginDialog.isShowing())
			{
				loginDialog.dismiss();
			} 
		}
		catch(Exception ex)
		{
			
		}
	}

	//获取当前时间
	public String getCurrentTime()
	 {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");       
		 Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		 String str = formatter.format(curDate);
		 return str;
	 }

	 
}
