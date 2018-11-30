package com.br.salespda.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.br.salespda.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.br.salespda.Crash.CrashApplication;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.CItem;
import com.br.salespda.basic.DbServerDO;
import com.br.salespda.common.Global;
import com.br.salespda.manager.DbManager_Config;
import com.br.salespda.webservice.WebServiceManager;

public class FrmMain_SettingActivity extends BaseActivity {

	public Button bt_menuLeft;//左下菜单按钮
	public Button bt_menuRight;//右下菜单按钮
	
	public ListView lv_menuLeft;//左下菜单
	public ListView lv_menuRight;//右下菜单
	
	public SimpleAdapter MenuAdapterLeft;//左下菜单
	public ArrayList<HashMap<String, Object>> listmenuLeft;
	
	public SimpleAdapter MenuAdapterRight;//右下菜单
	public ArrayList<HashMap<String, Object>> listmenuRight;
	Spinner cmbServerList;
	TextView txtServiceURL; 
	List<DbServerDO> lst = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmmain_setting);
		
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		try
		{
			//initcmbServerList();
			initial();
			
			  
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), ex.getMessage(), 2000).show();
		}
		 
	}
	
	 private int getindex(List<DbServerDO> lst,DbServerDO defaultDbServer)
	  {
		  int idx = 0;
		  //idx = lst.indexOf(defaultDbServer);
		  for(DbServerDO item : lst)
		  { 
			  if(item.SalesServerName.equals(defaultDbServer.SalesServerName))
			  {
				  break;
			  }
			  idx = idx + 1;
		  }
		  if(idx == lst.size())
		  {
			   return 0;
		  }
		return idx; 
	  }

	private void initcmbServerList() {
		cmbServerList = (Spinner) findViewById(R.id.cmbServerList);  
		//List<String> lst = new ArrayList<String>();
	    lst =  WebServiceManager.GetAllDbServer();  
		      
	    ArrayAdapter<DbServerDO> myaAdapter = new ArrayAdapter<DbServerDO>(this, android.R.layout.simple_spinner_item, lst);  
	    cmbServerList.setAdapter(myaAdapter); 
		
	}

	private void initial() {
		bt_menuLeft = (Button)findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button)findViewById(R.id.bt_menuRight);  
		tvtitle = (TextView)findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView)findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView)findViewById(R.id.lv_menuRight);
		
		bt_menuLeft.setText("确定");
		bt_menuRight.setText("重置");
		tvtitle.setText("系统设置");
		
		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		bt_menuRight.setOnClickListener(bt_menuRightClick);
		
		cmbServerList = (Spinner)findViewById(R.id.cmbServerList);
		txtServiceURL = (TextView)findViewById(R.id.txtServiceURL);
		
		 String param = db_cfg.GetParam("WebServiceURL");
         if (param != null && param.length() > 0)
         {
             txtServiceURL.setText(param);
             
             Global.WebServiceUrl = param;
             CrashApplication userInfo = (CrashApplication) getApplicationContext();
		     userInfo.setWebServiceUrl(param);
             
         }
         else
         { 
             txtServiceURL.setText(Global.WebServiceUrl); 
         }
         
         initcmbServerList();
         
         DbServerDO defaultDbServer = null;
	     String param1 = db_cfg.GetParam("DbServerName");
		 if (param1 != null && param1.length() > 0)
         {
             defaultDbServer = new DbServerDO();
             defaultDbServer.SalesServerName = param1;
         }
         else
         {
             defaultDbServer = WebServiceManager.GetDefaultDbServer(); 
         }  
		
         int position = getindex(lst,defaultDbServer); 
         this.cmbServerList.setSelection(position);  
	}
	
	//左下菜单
	private OnClickListener bt_menuLeftClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				 
					try
		            {
		                DbServerDO selectedItem = (DbServerDO) cmbServerList.getSelectedItem();
		                if ((selectedItem != null) )
		                { 
		                    db_cfg.SetParam("DbServerName", selectedItem.SalesServerName);
		                    
		                    String dbServer = selectedItem.SalesServerName;
		                    Global.dbServer =dbServer;
		                    
		                    CrashApplication userInfo = (CrashApplication) getApplicationContext();
							userInfo.setDbServer(dbServer);
							
		                }
		                String text = txtServiceURL.getText().toString();
		                if (text != null && text.length() > 0)
		                { 
		                	db_cfg.SetParam("WebServiceURL", text);
		                	Global.WebServiceUrl = text;
		                	
		                	CrashApplication userInfo = (CrashApplication) getApplicationContext();
							userInfo.setWebServiceUrl(text);
		                }
		                finish();
		            }
		            catch (Exception ex)
		            { 
		                Toast.makeText(getApplication(), ex.getMessage(), 2000).show();
		            } 
			}
		}; 
		
	//右下菜单
	private OnClickListener bt_menuRightClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{  
					
				}
				catch (Exception e)
				{ 
					e.toString();
				}
			}
		}; 
		
	
}
