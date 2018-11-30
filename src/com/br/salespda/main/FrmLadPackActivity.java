package com.br.salespda.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.br.salespda.R;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.CItem;
import com.br.salespda.basic.PackBillResult;
import com.br.salespda.basic.SaleBillDo;
import com.br.salespda.webservice.WebServiceManager;

public class FrmLadPackActivity extends BaseActivity {
	
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
	
	Spinner cmbStorehouse;
	EditText txtBarcode;
    TextView txtCount;
    TextView txtPacking;
    TextView txtCustomer;
    TextView txtBillNO;
	private PackBillResult _CurrentBill = null; 

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmladpack);
		
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		initial();
	}


	private void initial() {
		bt_menuLeft = (Button)findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button)findViewById(R.id.bt_menuRight);  
		tvtitle = (TextView)findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView)findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView)findViewById(R.id.lv_menuRight);
		
		bt_menuLeft.setText("操作");
		bt_menuRight.setText("重置");
		tvtitle.setText("包装物发货");
		
		//初始化当前发货仓库
		initcmbStorehouse();
		SetMenuLeft(); 
		 
		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		bt_menuRight.setOnClickListener(bt_menuRightClick);
		
		lv_menuLeft.setOnItemClickListener(lv_menuLeftclick);
		
		cmbStorehouse = (Spinner)findViewById(R.id.cmbStorehouse);
		txtBarcode = (EditText)findViewById(R.id.txtBarcode);
		txtCount = (TextView)findViewById(R.id.txtCount);
		txtPacking = (TextView)findViewById(R.id.txtPacking);
		txtCustomer = (TextView)findViewById(R.id.txtCustomer);
		txtBillNO = (TextView)findViewById(R.id.txtBillNO);
		
		txtBarcode.setOnKeyListener(et_tmkey);
		txtBarcode.setFocusableInTouchMode(true); 
		txtBarcode.requestFocus();
		txtBarcode.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	//初始化发货仓库
	private void initcmbStorehouse() {
		cmbStorehouse = (Spinner) findViewById(R.id.cmbStorehouse);  
		//获取发货仓库
	    List<CItem> lst =  WebServiceManager.GetAllStorehouse("packing");  
		      
		    ArrayAdapter<CItem> myaAdapter = new ArrayAdapter<CItem>(this, android.R.layout.simple_spinner_item, lst);  
		    cmbStorehouse.setAdapter(myaAdapter);  
		    cmbStorehouse.setOnItemSelectedListener(new OnItemSelectedListener() {  		        
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) { 
//		            int ids = ((CItem) cmbStorehouse.getSelectedItem()).GetID();    
				} 
				@Override
				public void onNothingSelected(AdapterView<?> arg0) { 
				}  
		    });  
		
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
			 reset();
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
			map.put("menuname", "撤销发货");			
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "启动输入模式");
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
			//撤销发货
			case 0:
				BlankOut();
				break;
			//启动输入模式
			case 1:
				openInputMode();
				break;  
			} 
			lv_menuLeft.setVisibility(View.GONE);
			set_lvleft = !set_lvleft;
		}

		
	};
	
	//条码事件
	private OnKeyListener et_tmkey = new OnKeyListener()
	{ 
		public boolean onKey(View v, int keyCode, KeyEvent event)
		{
			try
			{
				String barcode = txtBarcode.getText().toString().trim();
				if ((keyCode == 140 || keyCode == 141) && event.getAction() == KeyEvent.ACTION_DOWN)
				{
					txtBarcode.setText(""); 
				}
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) 
				{ 
					if(barcode.length() == 0)
					{
//						new AlertDialog.Builder(FrmLadPackActivity.this).setTitle("系统提示")//设置对话框标题  				  
//					     .setMessage("条码不能为空，请录入条码数字。")//设置显示的内容  		  
//					     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
//				    	  public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
//					              
//					         }  	  
//					     }).show();//在按键响应事件中显示此对话框    
						Toast.makeText(getApplication(), "条码不能为空！", 2000).show();
					}
					else
					{
						DoWork(barcode); 
					}   
//					Intent intent = new Intent(FrmLadPackActivity.this,
//							frm_ScanBarcodeActivity.class); 
//					startActivityForResult(intent, 1);				
//					txtBarcode.requestFocus();
					new Handler().postDelayed(
							new Runnable() {public void run() { 
								txtBarcode.requestFocus(); 
								txtBarcode.setText("");
							}  
						}, 200); 
					return true;
				}
				else
				{ 
					new Handler().postDelayed(
							new Runnable() {public void run() { 
								txtBarcode.requestFocus(); 
								//txtBarcode.setText("");
							}  
						}, 200); 
					return false;
				}
			}
			catch(Exception ex)
			{
				new Handler().postDelayed(
						new Runnable() {public void run() { 
							txtBarcode.requestFocus(); 
							txtBarcode.setText("");
						}  
					}, 200); 
				return false; 
			}
		} 
	
	};
	
	private void DoWork(String barcode) {
		if (barcode.length() == 0)
        {   
            new AlertDialog.Builder(FrmLadPackActivity.this).setTitle("系统提示")//设置对话框标题  				  
		     .setMessage( "条码不能为空：可能是条码识别失败。。")
		     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
	    	  public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
		             
		         }  	  
		     }).show();//在按键响应事件中显示此对话框  
            return;
        } 
		CItem item = ((CItem) cmbStorehouse.getSelectedItem());    
        if (item == null)
        {  
            new AlertDialog.Builder(FrmLadPackActivity.this).setTitle("系统提示")//设置对话框标题  				  
		     .setMessage( "请先设定发货仓库。")
		     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
	    	  public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
		             
		         }  	  
		     }).show();//在按键响应事件中显示此对话框  
            return;
        }
        try
        {
        	String ids = ((CItem) cmbStorehouse.getSelectedItem()).GetID();   
        	_CurrentBill = WebServiceManager.TakeoutPackBill(barcode, ids);
        	 if (this._CurrentBill.ErrMsg == null)
             {
                 txtBarcode.setText(barcode);
                 txtCount.setText(_CurrentBill.Count);
                 txtPacking.setText(_CurrentBill.PackingName);
                 txtCustomer.setText(_CurrentBill.CustomerName);
                 txtBillNO.setText(_CurrentBill.SalesBillNO); 
                  
                 Toast.makeText(getApplicationContext(), "出库操作成功！", 2000).show();
             }
        	 else
        	 {  
        		 PackBillResult CurrentBill = new PackBillResult();
                 CurrentBill = WebServiceManager.GetPackBill(barcode);
                 if (CurrentBill.ErrMsg == null)
                 {
                     txtBarcode.setText(barcode); 
                     txtCount.setText(CurrentBill.Count);
                     txtPacking.setText(CurrentBill.PackingName);  
                     txtCustomer.setText(CurrentBill.CustomerName);  
                     txtBillNO.setText(CurrentBill.SalesBillNO);  
                     txtBarcode.requestFocus();
                     Toast.makeText(getApplicationContext(), "不能重复发货！" + _CurrentBill.ErrMsg, 2000).show();
                 } 
                 else
                 {
                	 Toast.makeText(getApplicationContext(), "没有获取到销货单信息！" + _CurrentBill.ErrMsg, 2000).show();
                 }
        	 }
        	 
        }
        catch(Exception ex)
        {
        	Toast.makeText(getApplicationContext(), "获取条码信息异常！" + ex.getMessage(), 2000).show();
        }
		
	} 
	
	//撤销发货
	private void BlankOut() {
		 try
         {
             final String barcode = this.txtBarcode.getText().toString().trim();
             if (barcode == null || barcode.length() == 0)
             {
                 Toast.makeText(getApplicationContext(),"当前没有需要撤销的单据。",2000).show();
             }
             
             new AlertDialog.Builder(FrmLadPackActivity.this).setTitle("系统提示") 		  
		     .setMessage("确定要撤销单据" + barcode + "的发货出库吗？！")//设置显示的内容  		  
		     .setPositiveButton("确定",new DialogInterface.OnClickListener() { 
		         @Override  	  
		         public void onClick(DialogInterface dialog, int which) { 
		        	 String rlt = WebServiceManager.BlankoutTakingout(barcode);
		        	 if (rlt.equals("success"))
	                 { 
	                	 Toast.makeText(getApplicationContext(), "操作成功！" , 2000).show();
	                 }
	                 else
	                 {
	                	 Toast.makeText(getApplicationContext(), "操作失败！" + rlt , 2000).show();
	                 }
		         }  	  
		     })
		     .setNegativeButton("取消",new DialogInterface.OnClickListener() { 
		         @Override  	  
		         public void onClick(DialogInterface dialog, int which) { 
		             
		         }  	  
		     }).show();  
              
         }
         catch (Exception ex)
         { 
        	 Toast.makeText(getApplicationContext(), "操作异常:" + ex.getMessage(), 2000).show();
         }
		
	}
	//重置
	private void reset()
	{
		 this._CurrentBill = null;
         this.txtBarcode.setText("");
         this.txtCount.setText("");
         this.txtCustomer.setText("");
         this.txtPacking.setText("");
         this.txtBillNO.setText("");
         this.txtBarcode.requestFocus();
	}
	//启动输入模式
	private void openInputMode() {
		//txtBarcode.setInputType(InputType.TYPE_CLASS_NUMBER);
		txtBarcode.setEnabled(true); 
		txtBarcode.requestFocus();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case 1:
			txtBarcode.requestFocus(); 
			//txtBarcode.setText("");
			break;
		}
	}


}

		
		
		
		
		