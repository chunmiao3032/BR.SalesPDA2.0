package com.br.salespda.main;

import com.br.salespda.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class frm_ScanBarcodeActivity extends Activity{
	
	
	TextView tvBarcode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_frm_scanbarcode);
		//tvBarcode = (TextView)findViewById(R.id.tvBarcode);
		finish();
		
	}

}
