package com.br.salespda.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.br.salespda.R;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.BatchNO;
import com.br.salespda.basic.SaleBillDo;
import com.br.salespda.common.CommonMethord;
import com.br.salespda.common.Global;
import com.br.salespda.manager.DbManager_Data;

public class FrmLadProd_BillIndexActivity extends BaseActivity{
	
	
	public Button bt_menuLeft;//左下菜单按钮
	public Button bt_menuRight;//右下菜单按钮
	
	public ListView lv_menuLeft;//左下菜单
	public ListView lv_menuRight;//右下菜单
	
	public SimpleAdapter MenuAdapterLeft;//左下菜单
	public ArrayList<HashMap<String, Object>> listmenuLeft;
	
	public SimpleAdapter MenuAdapterRight;//右下菜单
	public ArrayList<HashMap<String, Object>> listmenuRight;
	
	TextView lblBillNum;
	ListView pnlFull;
	
	DbManager_Data db = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmladprod_billindex);
		
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		db = new DbManager_Data(getApplication());
		 
		initial();
		initialListView();
	}
	
	@Override
	protected void onRestart() { 
		super.onRestart();
		 
			 initialListView(); 
	}
	  
	private void initialListView() { 
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();
		int i = 1;
		for( SaleBillDo sbo : Global._ModelList)  
        {    
            HashMap<String, Object> map = new HashMap<String, Object>();  
            map.put("tvidx", i);
            if(!sbo.TruckNO.contains("anyType"))
            {
            	map.put("tvTruckNO", sbo.TruckNO);
            }
            else
            {
            	map.put("tvTruckNO", " ");
            }
            map.put("tvProductShortName",sbo.ProductShortName); 
            map.put("tvBillCode",sbo.SalesBillNO);
            listItem.add(map);  
            i++;
        } 
		SimpleAdapter mSimpleAdapter = new SimpleAdapter(
				this,
				listItem,//需要绑定的数据                
				R.layout.layout_frmladprod_billindex_item,//每一行的布局//动态数组中的数据源的键对应到定义布局的View中
				new String[] {"tvidx","tvTruckNO", "tvProductShortName","tvBillCode"},   
				new int[] {R.id.tvidx,R.id.tvTruckNO,R.id.tvProductShortName,R.id.tvBillCode}  
		);
		pnlFull.setAdapter(mSimpleAdapter);
		
		lblBillNum.setText(String.valueOf(listItem.size()));
	}



	private void initial() {
		bt_menuLeft = (Button)findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button)findViewById(R.id.bt_menuRight);  
		tvtitle = (TextView)findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView)findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView)findViewById(R.id.lv_menuRight);
		bt_menuLeft.setText("刷新");
		bt_menuRight.setText("返回");
		tvtitle.setText("提货车辆");
		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		bt_menuRight.setOnClickListener(bt_menuRightClick);
		
		lblBillNum = (TextView)findViewById(R.id.lblBillNum);
		pnlFull = (ListView)findViewById(R.id.pnlFull);
		pnlFull.setOnItemClickListener(pnlFull_itemClick);
	}
	
	//左下菜单
	private OnClickListener bt_menuLeftClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) { 
			try
			{  
				initialListView();
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
	
    //填充LISTVIEW
 

	private OnItemClickListener pnlFull_itemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) { 
			 //获得选中项的HashMap对象   
            HashMap<String,String> map=(HashMap<String,String>)pnlFull.getItemAtPosition(arg2);   
            String SalesBillNO=map.get("tvBillCode");   
            String ProductShortName=map.get("tvProductShortName");   
            
            Intent intent= new Intent(FrmLadProd_BillIndexActivity.this,FrmLadProd_BillTabActivity.class);
            intent.putExtra("SalesBillNO", SalesBillNO);
            intent.putExtra("ProductShortName", ProductShortName);
            startActivity(intent); 
		}
	};
	
	 
	
	

	
	
	
	
	
}
	
	
	
	
	
	
	
	


