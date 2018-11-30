package com.br.salespda.main;
 
import com.br.salespda.R;
import com.br.salespda.Crash.CrashApplication;
import com.br.salespda.basic.Packing.FirstCheckBillResult;
import com.br.salespda.common.PrinterService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class FrmReceivingBill_SecondCheck_Bill extends Activity {
	
	CrashApplication userInfo;
	
	String _BilliD = null;
	FirstCheckBillResult _BillResult = null;
	
	TextView tvType;
	TextView tvCarNO;
	TextView tvFirstCheckUser;
	TextView tvFirstCheckDate;
	TextView tvProduct;
	
	TextView tvStoreHouse;
	TextView tvSecondChkUser;
	
	Button btSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frm_receiving_bill_secondcheck_bill);
		
		userInfo = (CrashApplication) getApplicationContext();
		
		Intent intentIn = getIntent(); 
		_BilliD = intentIn.getStringExtra("BillID");   
		_BillResult= (FirstCheckBillResult)intentIn.getSerializableExtra("BillTag");  
		
		initCtrl();
		
		initCtrlListener();
		
		initTextView(_BillResult);
	}

	private void initCtrlListener() {
		 
		OnClickListener btSubmit_OnClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 
			PrinterService.PrintSecondCheckBill(_BillResult);
				
			}
		};
		btSubmit.setOnClickListener(btSubmit_OnClick );
	}

	private void initTextView(FirstCheckBillResult billResult) {
		 
		tvType.setText(billResult.ReceivingBillTypeName);
		tvCarNO.setText(billResult.TruckNO);
		tvFirstCheckUser.setText(billResult.FirstCheckUser);
		tvFirstCheckDate.setText(billResult.FirstCheckDate);
		tvProduct.setText("(" + billResult.PackingCode + ")" + billResult.PackingName);
		
		String userName = userInfo.getUserName();
		tvSecondChkUser.setText(userName);
		String storeHouseName = userInfo.getUserStoreHouseName();
		tvStoreHouse.setText(storeHouseName);
	}

	private void initCtrl() {
		tvType = (TextView)findViewById(R.id.tvType);
		tvCarNO = (TextView)findViewById(R.id.tvCarNO);
		tvFirstCheckUser = (TextView)findViewById(R.id.tvFirstCheckUser);
		tvFirstCheckDate = (TextView)findViewById(R.id.tvFirstCheckDate);
		tvProduct = (TextView)findViewById(R.id.tvProduct);
		tvSecondChkUser = (TextView)findViewById(R.id.tvSecondChkUser);
		tvStoreHouse = (TextView)findViewById(R.id.tvStoreHouse);
		
		btSubmit = (Button)findViewById(R.id.btSubmit);
	}

}
