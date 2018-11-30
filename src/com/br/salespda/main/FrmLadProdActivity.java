package com.br.salespda.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.br.salespda.R;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.BatchNO;
import com.br.salespda.basic.CItem;
import com.br.salespda.basic.SaleBillDo;
import com.br.salespda.common.CommonMethord;
import com.br.salespda.common.Global;
import com.br.salespda.manager.DbManager_Data;
import com.br.salespda.webservice.SoapControl;
import com.br.salespda.webservice.WebServiceManager;

public class FrmLadProdActivity extends BaseActivity {

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
    TextView txtProduct;
    TextView txtCustomer;
    TextView txtBillNO;
	private SaleBillDo _CurrentBill = null; 
	
	DbManager_Data db = null;
    
	
    @Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmladprod);
		
		try
		{
			StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			db = new DbManager_Data(getApplication());
			
			initial();
			
			initGlobalList();
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "异常:" + ex.getMessage(), 2000).show();
		}
		 
	}

	private void initial() {
		try
		{
			bt_menuLeft = (Button)findViewById(R.id.bt_menuLeft);
			bt_menuRight = (Button)findViewById(R.id.bt_menuRight);  
			tvtitle = (TextView)findViewById(R.id.tvtitle);
			lv_menuLeft = (ListView)findViewById(R.id.lv_menuLeft);
			lv_menuRight = (ListView)findViewById(R.id.lv_menuRight);
			bt_menuLeft.setText("操作");
			bt_menuRight.setText("批号");
			tvtitle.setText("成品酒发货");
			
			cmbStorehouse = (Spinner)findViewById(R.id.cmbStorehouse);
			txtBarcode = (EditText)findViewById(R.id.txtBarcode);
			txtCount = (TextView)findViewById(R.id.txtCount);
			txtProduct = (TextView)findViewById(R.id.txtProduct);
			txtCustomer = (TextView)findViewById(R.id.txtCustomer);
			txtBillNO = (TextView)findViewById(R.id.txtBillNO);
	   
			SetMenuLeft(); 
			 
			bt_menuLeft.setOnClickListener(bt_menuLeftClick);
			bt_menuRight.setOnClickListener(bt_menuRightClick); 
			cmbStorehouse.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					 
				}
	
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			lv_menuLeft.setOnItemClickListener(lv_menuLeftclick);
			
			//初始化当前发货仓库
			initcmbStorehouse();
					 
			txtBarcode.setOnKeyListener(et_tmkey);
			txtBarcode.setFocusableInTouchMode(true); //因测试注释掉，测试后需要去掉注释
			txtBarcode.requestFocus(); 
			txtBarcode.setInputType(InputType.TYPE_NULL);//因测试注释掉，测试后需要去掉注释
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "异常:" + ex.getMessage(), 2000).show();
		}
	}
	 
	 /* 从数据库中读取销货单信息及批号信息,并放入全局变量中
	 * @return
	 */
	private void initGlobalList()
	{
		List<SaleBillDo> saleBillDoList = new ArrayList<SaleBillDo>();
		Cursor cur_SaleBills = db.GetSaleBills();
		if(cur_SaleBills == null)
		{
			return;
		}
		for(cur_SaleBills.moveToFirst();!cur_SaleBills.isAfterLast();cur_SaleBills.moveToNext())
		{
			   SaleBillDo salebillDo = new SaleBillDo(); 
			   int iProductCode = cur_SaleBills.getColumnIndex("ProductCode"); 
			   salebillDo.ProductCode = cur_SaleBills.getString(iProductCode);
			   int iProductName = cur_SaleBills.getColumnIndex("ProductName"); 
			   salebillDo.ProductName = cur_SaleBills.getString(iProductName);
			   int iProductShortName = cur_SaleBills.getColumnIndex("ProductShortName"); 
			   salebillDo.ProductShortName = cur_SaleBills.getString(iProductShortName);
			   int iCharacter = cur_SaleBills.getColumnIndex("Character"); 
			   salebillDo.Character = cur_SaleBills.getString(iCharacter);
			   int iCount = cur_SaleBills.getColumnIndex("Count"); 
			   salebillDo.Count = cur_SaleBills.getString(iCount);
			   int iCustomerID = cur_SaleBills.getColumnIndex("CustomerID"); 
			   salebillDo.CustomerID = cur_SaleBills.getString(iCustomerID);
			   int iCustomerCode = cur_SaleBills.getColumnIndex("CustomerCode"); 
			   salebillDo.CustomerCode = cur_SaleBills.getString(iCustomerCode);
			   int iCustomerName = cur_SaleBills.getColumnIndex("CustomerName"); 
			   salebillDo.CustomerName = cur_SaleBills.getString(iCustomerName);
			   int iID = cur_SaleBills.getColumnIndex("ID"); 
			   salebillDo.ID = cur_SaleBills.getString(iID);
			   int iMakeDate = cur_SaleBills.getColumnIndex("MakeDate"); 
			   salebillDo.MakeDate = cur_SaleBills.getString(iMakeDate);
			   int iMakeUser = cur_SaleBills.getColumnIndex("MakeUser"); 
			   salebillDo.MakeUser = cur_SaleBills.getString(iMakeUser);
			   int iProductID = cur_SaleBills.getColumnIndex("ProductID"); 
			   salebillDo.ProductID = cur_SaleBills.getString(iProductID);
			   int iRemark = cur_SaleBills.getColumnIndex("Remark"); 
			   salebillDo.Remark = cur_SaleBills.getString(iRemark);
			   int iSalesBillNO = cur_SaleBills.getColumnIndex("SalesBillNO");
			   String saleBillNO = cur_SaleBills.getString(iSalesBillNO);
			   salebillDo.SalesBillNO = saleBillNO;
			   int iTruckNO = cur_SaleBills.getColumnIndex("TruckNO"); 
			   salebillDo.TruckNO = cur_SaleBills.getString(iTruckNO);
			   int iNumPerUnit = cur_SaleBills.getColumnIndex("NumPerUnit"); 
			   salebillDo.NumPerUnit = cur_SaleBills.getString(iNumPerUnit);
			   //获取销货单对应的批号
			   Cursor cur_BatchNO = db.GetBatchNo(saleBillNO);
			   if(cur_BatchNO != null)
			   { 
				   if(salebillDo.BatchNoList == null)
				   {
					   salebillDo.BatchNoList = new ArrayList<BatchNO>();
				   }
				   for(cur_BatchNO.moveToFirst();!cur_BatchNO.isAfterLast();cur_BatchNO.moveToNext())
				   { 
					   BatchNO batchNo = new BatchNO();
					   int iLineNo = cur_BatchNO.getColumnIndex("LineNo"); 
					   batchNo.LineNo = cur_BatchNO.getString(iLineNo);
					   
					   int iStartDate = cur_BatchNO.getColumnIndex("StartDate"); 
					   String strStartDate = cur_BatchNO.getString(iStartDate);
					   Date dtStartDate = CommonMethord.StrToDate(strStartDate); 
					   batchNo.StartDate = dtStartDate;
					   
					   int iEndDate = cur_BatchNO.getColumnIndex("EndDate"); 
					   String strEndDate = cur_BatchNO.getString(iEndDate);
					   Date dtEndDate = CommonMethord.StrToDate(strEndDate); 
					   batchNo.EndDate = dtEndDate;
					  
					   int iStartIdx = cur_BatchNO.getColumnIndex("StartIdx"); 
					   batchNo.StartIdx = cur_BatchNO.getString(iStartIdx);
					   int iEndIdx = cur_BatchNO.getColumnIndex("EndIdx"); 
					   batchNo.EndIdx = cur_BatchNO.getString(iEndIdx);
					   salebillDo.BatchNoList.add(batchNo); 
				   }
			   }
			   saleBillDoList.add(salebillDo);
		}  
		if(Global._ModelList == null || Global._ModelList.size() == 0)
		{
			Global._ModelList.addAll(saleBillDoList);
		}
		else
		{
			for(SaleBillDo item : saleBillDoList)
			{
				Boolean isHave = false;
				SaleBillDo _tmp = new SaleBillDo();
				for(SaleBillDo item1 : Global._ModelList)
				{
					if(item.SalesBillNO.equals(item1.SalesBillNO))
					{
						_tmp = item1;
						isHave = true;
						break;
					}
				}
				if(!isHave)
				{
					Global._ModelList.add(_tmp);
				}
			}
			
		}
	}
	 
	
	private void initcmbStorehouse() {
		 
		cmbStorehouse = (Spinner) findViewById(R.id.cmbStorehouse);  
		//获取发货仓库
	    List<CItem> lst =  WebServiceManager.GetAllStorehouse("product");  
		      
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
			catch (Exception ex)
			{ 
				Toast.makeText(getApplication(), "异常:" + ex.getMessage(), 2000).show();
			}
		}
	}; 
	
	//右下菜单
	private OnClickListener bt_menuRightClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try
				{ 
					Intent intent= new Intent(FrmLadProdActivity.this,FrmLadProd_BillIndexActivity.class);
	                startActivity(intent);
				}
				catch (Exception ex)
				{ 
					Toast.makeText(getApplication(), "异常:" + ex.getMessage(), 2000).show();
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
				map = new HashMap<String, Object>();
				map.put("menuname", "重置");
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
//						new AlertDialog.Builder(FrmLadProdActivity.this).setTitle("系统提示")//设置对话框标题  				  
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
//					Intent intent = new Intent(FrmLadProdActivity.this,
//							frm_ScanBarcodeActivity.class); 
//					startActivityForResult(intent, 1);
					new Handler().postDelayed(
							new Runnable() {public void run() { 
								txtBarcode.requestFocus(); 
								txtBarcode.setText("");
							}  
						}, 200);  
//					txtBarcode.requestFocus();
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
            new AlertDialog.Builder(FrmLadProdActivity.this).setTitle("系统提示")//设置对话框标题  				  
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
            new AlertDialog.Builder(FrmLadProdActivity.this).setTitle("系统提示")//设置对话框标题  				  
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
        	_CurrentBill = WebServiceManager.TakeoutSalesBill(barcode, ids);
        	 if (this._CurrentBill.ErrMsg == null)
             {
                 txtBarcode.setText(barcode);
                 txtCount.setText(_CurrentBill.Count);
                 txtProduct.setText(_CurrentBill.ProductName);
                 txtCustomer.setText(_CurrentBill.CustomerName);
                 txtBillNO.setText(_CurrentBill.SalesBillNO); 
                 
                 WebServiceManager.AddBill(_CurrentBill);
                 Cursor cur_SaleBill = db.GetSaleBill(_CurrentBill.SalesBillNO);
                 if(cur_SaleBill == null)
                 {
                	 db.InsertSaleBill(_CurrentBill);
                 }
                 Toast.makeText(getApplicationContext(), "出库操作成功！", 2000).show();
             }
        	 else
        	 {  
        		 SaleBillDo CurrentBill = new SaleBillDo();
                 CurrentBill = WebServiceManager.GetSalesBill(barcode);
                 if (CurrentBill.ErrMsg == null)
                 {
                	 Toast.makeText(getApplicationContext(), "不能重复发货！" + _CurrentBill.ErrMsg, 2000).show();
                     txtBarcode.setText(barcode); 
                     txtCount.setText(CurrentBill.Count);
                     txtProduct.setText(CurrentBill.ProductName);  
                     txtCustomer.setText(CurrentBill.CustomerName);  
                     txtBillNO.setText(CurrentBill.SalesBillNO);  
                    
                 } 
                 else
                 {
                	 Toast.makeText(getApplicationContext(), "没有获取到销货单信息！" + CurrentBill.ErrMsg, 2000).show();
                 }
        	 }
        	 
        }
        catch(Exception ex)
        {
        	Toast.makeText(getApplicationContext(), "获取条码信息异常！" + ex.getMessage(), 2000).show();
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
			//重置
			case 2: 
				 reset();
				break;
			}
			lv_menuLeft.setVisibility(View.GONE);
			set_lvleft = !set_lvleft;
		} 
	};
	
	//重置
	private void reset()
	{
		try
		{ 
			this._CurrentBill = null;
	        this.txtBarcode.setText("");
	        this.txtCount.setText("");
	        this.txtCustomer.setText("");
	        this.txtProduct.setText("");
	        this.txtBillNO.setText("");
	        this.txtBarcode.requestFocus();
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "异常:" + ex.getMessage(), 2000).show();
		}
	}
	//启动输入模式
	private void openInputMode() {
		//txtBarcode.setInputType(InputType.TYPE_CLASS_NUMBER);
		txtBarcode.setEnabled(true); 
		txtBarcode.requestFocus();
	}
	
	private void BlankOut()
	{
		 try
         {
             final String barcode = this.txtBarcode.getText().toString().trim();
             if (barcode == null || barcode.length() == 0)
             {
                 Toast.makeText(getApplicationContext(),"当前没有需要撤销的单据。",2000).show();
             }
             
             new AlertDialog.Builder(FrmLadProdActivity.this).setTitle("系统提示") 		  
		     .setMessage("确定要撤销单据" + barcode + "的发货出库吗？！")//设置显示的内容  		  
		     .setPositiveButton("确定",new DialogInterface.OnClickListener() { 
		         @Override  	  
		         public void onClick(DialogInterface dialog, int which) { 
		        	 String rlt = WebServiceManager.BlankoutTakingout(barcode);
	                 if (rlt.equals("success"))
	                 { 
	                	 WebServiceManager.RemoveBill(_CurrentBill);
	                	 Toast.makeText(getApplicationContext(), "操作成功！" , 2000).show();
	                 }
	                 else
	                 {
	                	 Toast.makeText(getApplicationContext(), "操作失败！请重试！" + rlt , 2000).show();
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case 1:
			txtBarcode.requestFocus(); 
			txtBarcode.setText("");
			break;
		}
	}
}
