package com.br.salespda.main;

import java.util.ArrayList;
import java.util.List;

import com.br.salespda.R;
import com.br.salespda.Crash.CrashApplication; 
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.CItem;
import com.br.salespda.basic.Packing.StorehouseListResult;
import com.br.salespda.basic.Packing.StorehouseResult;
import com.br.salespda.basic.Packing.UserInCorporationListResult;
import com.br.salespda.basic.Packing.UserInCorporationResult;
import com.br.salespda.common.CommonMethord;
import com.br.salespda.webservice.WebServiceManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FrmReceivingBill_StoreHouseActivity extends Activity {
	
	CrashApplication userInfo;
	
	Spinner cmbCorporation,cmbStorehouse;
	EditText txtBarcode;
	Button btOk;
	WebServiceManager service;
	
	List<StorehouseResult> _StoreHuseList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frm_receiving_bill_storehouse);
		
		service = new WebServiceManager();
		userInfo = (CrashApplication)getApplicationContext();
		
		initial();
		
		initialListener();
		
		getCorporation();
		
	}

	/**
	 * 根据用户名获取所属工厂
	 */
	private void getCorporation() {
		CrashApplication userInfo = (CrashApplication) getApplicationContext();
	    String userCode = userInfo.getUserCode();
		
	    UserInCorporationListResult rlt = service.PackingLogin(userInfo,userCode);
	    
	    if(rlt.Success)
	    {
	    	userInfo.setUserInCorpList(rlt.UserInCorporationResults);
	    	
	    	List<CItem> lst = new ArrayList<CItem>();
	    	List<UserInCorporationResult> corps = rlt.UserInCorporationResults;
	    	for(UserInCorporationResult item : corps)
	    	{
	    		CItem citem = new CItem(item.CorporationID, item.CorporationName, item.CorporationID);
	    		lst.add(citem);
	    	}
	    	 ArrayAdapter<CItem> myaAdapter = new ArrayAdapter<CItem>(this, android.R.layout.simple_spinner_dropdown_item, lst);  
			 cmbCorporation.setAdapter(myaAdapter); 
	    }
	    else 
	    {
	    	Toast.makeText(getApplicationContext(), rlt.ErrMsg,3000).show();
	    } 
	}
	
	//根据所选工厂获取 仓库
	private void getStoreHouse()
	{
		CrashApplication userInfo = (CrashApplication) getApplicationContext();
		CItem item = ((CItem) cmbCorporation.getSelectedItem());    
        if (item == null)
        {  
            new AlertDialog.Builder(FrmReceivingBill_StoreHouseActivity.this).setTitle("系统提示")//设置对话框标题  				  
		     .setMessage( "请先确定接收机构。")
		     .setPositiveButton("确定",null).show();//在按键响应事件中显示此对话框  
            return;
        } 
         
		String corporationID = ((CItem) cmbCorporation.getSelectedItem()).GetID();    
		userInfo.setUserCorporationID(corporationID);
		
		StorehouseListResult rlt = service.GetAllPackingStorehouse(userInfo,corporationID);
		if(rlt.Success)
		{
			List<CItem> lst = new ArrayList<CItem>();
			_StoreHuseList = rlt.StorehouseResults;
			 
			for(StorehouseResult sitem : _StoreHuseList)
	    	{
	    		CItem citem = new CItem(sitem.ID, sitem.StorehouseCode + sitem.StorehouseName, sitem.ID);
	    		lst.add(citem);
	    	}
	    	 ArrayAdapter<CItem> myaAdapter = new ArrayAdapter<CItem>(this, android.R.layout.simple_spinner_dropdown_item, lst);  
			 cmbStorehouse.setAdapter(myaAdapter); 
	    }
	    else 
	    {
	    	Toast.makeText(getApplicationContext(), rlt.ErrMsg,3000).show();
	    } 
	}
  
	private void initialListener() {
		 
		OnItemSelectedListener cmbCorporation_OnItemSelected = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				 
				getStoreHouse();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		OnItemSelectedListener cmbStorehouse_OnItemSelected = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		OnKeyListener txtBarcode_OnKey = new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				 
				if (txtBarcode.length() >= 5 && keyCode == KeyEvent.KEYCODE_ENTER) { 
					
					String barcode = txtBarcode.getText().toString();
					int idx = 0;
					for(StorehouseResult item :  _StoreHuseList)
					{ 
						if(item.StorehouseCode.toUpperCase().equals(barcode.toUpperCase()))
						{
							break; 
						}
						else
						{
							idx++;
						}
					}
					cmbStorehouse.setSelection(idx);
					
					txtBarcode.setText("");
					txtBarcode.requestFocus();
					txtBarcode.setFocusable(true);
					return true;
				}
				else if (txtBarcode.length() < 5 && keyCode == KeyEvent.KEYCODE_ENTER)
				{  
					Toast.makeText(getApplicationContext(), "条码格式不正确!", 3000).show();
					
					txtBarcode.setText(""); 
					txtBarcode.requestFocus();
					txtBarcode.setFocusable(true);
					return true;
				}
				else
				{
					return false;
				}				
			}
		};
		OnClickListener btOk_OnClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				String storeHouseID = ((CItem) cmbStorehouse.getSelectedItem()).GetID();   
				String corporationID = ((CItem) cmbCorporation.getSelectedItem()).GetID(); 
				
				String storeHouseName = ((CItem) cmbStorehouse.getSelectedItem()).GetValue();   
				String corporationName = ((CItem) cmbCorporation.getSelectedItem()).GetValue();  
				
				if(storeHouseID == null || corporationID == null)
				{ 
					(new CommonMethord()).ShowMsg(FrmReceivingBill_StoreHouseActivity.this, "请选择接收机构与复检现场！");
					return;
				}
				
				userInfo.setUserCorporationID(corporationID);
				userInfo.setUserStoreHouseID(storeHouseID);
				
				userInfo.setUserCorporationName(corporationName);
				userInfo.setUserStoreHouseName(storeHouseName);
				
				Intent intent = new Intent(FrmReceivingBill_StoreHouseActivity.this,
						FrmReceivingBill_CustomerActivity.class);
				startActivity(intent);
			}
		};
		
		cmbCorporation.setOnItemSelectedListener(cmbCorporation_OnItemSelected);	
		cmbStorehouse.setOnItemSelectedListener(cmbStorehouse_OnItemSelected);		
		txtBarcode.setOnKeyListener(txtBarcode_OnKey);		
		btOk.setOnClickListener(btOk_OnClick);
		 
	}

	private void initial() {
		  
		cmbCorporation = (Spinner)findViewById(R.id.cmbCorporation); 
		cmbStorehouse = (Spinner)findViewById(R.id.cmbStorehouse); 
		txtBarcode = (EditText)findViewById(R.id.txtBarcode); 
		btOk = (Button)findViewById(R.id.btOk); 
		
	}




}
