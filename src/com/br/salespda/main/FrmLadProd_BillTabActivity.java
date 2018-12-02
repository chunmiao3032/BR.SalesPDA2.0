package com.br.salespda.main;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 

import android.R.bool;
import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.br.salespda.basic.SaleBillDo;
import com.br.salespda.common.CommonMethord;
import com.br.salespda.common.Global; 
import com.br.salespda.manager.DbManager_Data;
import com.br.salespda.webservice.WebServiceManager;

public class FrmLadProd_BillTabActivity extends BaseActivity{

	public Button bt_menuLeft;//���²˵���ť
	public Button bt_menuRight;//���²˵���ť
	
	public ListView lv_menuLeft;//���²˵�
	public ListView lv_menuRight;//���²˵�
	
	public SimpleAdapter MenuAdapterLeft;//���²˵�
	public ArrayList<HashMap<String, Object>> listmenuLeft;
	
	public SimpleAdapter MenuAdapterRight;//���²˵�
	public ArrayList<HashMap<String, Object>> listmenuRight;
	
	TextView txtBillNO;
	TextView txtCount;
	TextView txtCustomer;
	TextView txtProduct;
	
	EditText txtLineNO;
	EditText txtBeginYMD;
	EditText txtBeginHMS;
	EditText txtEndYMD;
	EditText txtEndHMS;
	ListView pnlFull;
	
	TextView tvNo; 
	
	private List<String> data_listBegin;
	private ArrayAdapter<String> arr_adapterBegin;
	private List<String> data_listEnd;
	private ArrayAdapter<String> arr_adapterEnd;
	
	private SaleBillDo _SaleBillDo;
	private int _ihmslen = 0;
	
	 DbManager_Data db = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		
		try
		{
			requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��title   
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȥ��Activity�����״̬��
			 
			setContentView(R.layout.layout_frmladprod_billtab);
		  
			StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			db = new DbManager_Data(getApplication());
			
			initial(); 
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
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
			bt_menuLeft.setText("�ύ");
			bt_menuRight.setText("����");
			bt_menuLeft.setOnClickListener(bt_menuLeftClick);
			bt_menuRight.setOnClickListener(bt_menuRightClick);
			tvtitle.setText("���ż�¼");
			
			txtBillNO = (TextView)findViewById(R.id.txtBillNO);
			txtCount = (TextView)findViewById(R.id.txtCount);
			txtCustomer = (TextView)findViewById(R.id.txtCustomer);
			txtProduct = (TextView)findViewById(R.id.txtProduct);
			
			txtLineNO = (EditText)findViewById(R.id.txtLineNO);
			txtLineNO.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)}); 
			
			txtBeginYMD = (EditText)findViewById(R.id.txtBeginYMD);
			txtBeginYMD.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); 
			 
			txtEndYMD = (EditText)findViewById(R.id.txtEndYMD);
			txtEndYMD.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); 
			
			txtBeginHMS = (EditText)findViewById(R.id.txtBeginHMS); //
			txtEndHMS = (EditText)findViewById(R.id.txtEndHMS);	//
			 
			
			// �����ע�͵������Ժ���Ҫȥ��ע��
			txtLineNO.setInputType(InputType.TYPE_NULL);
			txtBeginYMD.setInputType(InputType.TYPE_NULL);
			txtEndYMD.setInputType(InputType.TYPE_NULL);
			txtBeginHMS.setInputType(InputType.TYPE_NULL);
			txtEndHMS.setInputType(InputType.TYPE_NULL);
				    
			pnlFull = (ListView)findViewById(R.id.pnlFull);
			
			tvNo = (TextView)findViewById(R.id.tvNo); 
			
			//�ı���İ����¼�
			txtBeginHMS.setOnKeyListener(txtBeginHMS_OnKeyDown);
			txtEndHMS.setOnKeyListener(txtEndHMS_OnKeyDown);
			txtLineNO.setOnKeyListener(txtLineNO_OnKeyDown);
			txtBeginYMD.setOnKeyListener(txtBeginYMD_OnKeyDown);
			txtEndYMD.setOnKeyListener(txtEndYMD_OnKeyDown);
			 
			//�ı���ĵ���¼�
			txtBeginHMS.setOnClickListener(txtBeginHMS_OnFocus);
			txtEndHMS.setOnClickListener(txtEndHMS_OnFocus);
			txtLineNO.setOnClickListener(txtLineNo_OnFocus);
			txtBeginYMD.setOnClickListener(txtBeginYMD_OnFocus);
			txtEndYMD.setOnClickListener(txtEndYMD_OnFocus);
			
			//����
			//String year = WebServiceManager.GetServerDate(); 
	       
			Intent intent1 = getIntent(); 
	        String SalesBillNO = intent1.getStringExtra("SalesBillNO");  
	        String ProductShortName = intent1.getStringExtra("ProductShortName");
	        
	        initUI(SalesBillNO,ProductShortName);
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
		}
	}
	
	/**
	 * �����������źͲ�Ʒ����������Ϣ��ʾ��������
	 * @param SalesBillNO
	 * @param ProductShortName
	 */
	private void initUI(String SalesBillNO,String ProductShortName)
	{
		try
		{
		
			for(SaleBillDo sbo : Global._ModelList)  
	        {     
	            if(sbo.SalesBillNO.equals(SalesBillNO) && sbo.ProductShortName.equals(ProductShortName))
	            {
	            	_SaleBillDo = sbo; 
	      		    if (sbo.ProductName.contains("ֽ��"))
	                { 
	      		    	_ihmslen = 9;
		      			this.txtBeginHMS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)}); 
		      			this.txtEndHMS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});  
	                }
	                else
	                { 
	                	_ihmslen = 7;
	                    this.txtBeginHMS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)}); 
		      			this.txtEndHMS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});  
	                }
	      		  
	      		    String foot = sbo.SalesBillNO.substring(sbo.SalesBillNO.length()-3);
	      		    tvNo.setText( foot + "-" + sbo.Character);
	      		  
	            	txtBillNO.setText(sbo.SalesBillNO);
	            	 
	            	setCount(sbo);
	            	
	            	txtCustomer.setText(sbo.CustomerName);
	            	txtProduct.setText(sbo.ProductName + "(" + sbo.Character + ")");
	            	initialBatchNoListView(sbo.BatchNoList);
	            }
	        }
		}
		catch(Exception ex)
		{
			
		}
	}

	/**
	 * ��������
	 * @param sbo
	 */
	private void setCount(SaleBillDo sbo) {
		try
		{
			if(sbo.NumPerUnit == null || sbo.NumPerUnit.trim().length() == 0 || sbo.NumPerUnit.equals("0"))
			{
				Toast.makeText(getApplication(), "�쳣:�ò�Ʒ�����̶�Ӧ����û��ά����", 2000).show();
				return;
			}
			else
			{	 
				double iCount = Double.parseDouble(sbo.Count)/Double.parseDouble(sbo.NumPerUnit);
				double iBatchNoCount = 0.0;
				if(sbo.BatchNoList != null && sbo.BatchNoList.size() > 0)
				{
					iBatchNoCount = Double.valueOf(sbo.BatchNoList.size());
				}
				double DValue = iCount - iBatchNoCount > 0 ? iCount - iBatchNoCount : 0; 
				 
				if(Math.round(DValue)-DValue==0)  
				{
					txtCount.setText(String.valueOf((long)DValue)); 
				}
				else
				{
					txtCount.setText(String.format("%.1f", DValue));
				}
				
			}
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
		}
	}
	 
	
	/*
	 * ��ʼ������list
	 */
	private void initialBatchNoListView(List<BatchNO> BatchNoList) { 
		try
		{
			if(BatchNoList == null || BatchNoList.size() == 0)
			{
				SimpleAdapter mSimpleAdapter = null; 
				pnlFull.setAdapter(mSimpleAdapter);
				return;
			}
			ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String,String>>();
			int i = 1;
			for(BatchNO sbo : BatchNoList)  
	        {    
	            HashMap<String, String> map = new HashMap<String, String>();  
	            map.put("tvidx", String.valueOf(i)); 
	            map.put("tvBatchNo", sbo.ToString(sbo.StartDate,sbo.EndDate));  
	            listItem.add(map);  
	            i++;
	        } 
			ArrayList<HashMap<String, String>> listItemDesc = new ArrayList<HashMap<String,String>>();
			for(int j = listItem.size();j > 0;j--)
			{
				listItemDesc.add(listItem.get(j-1)); 
			}
			SimpleAdapter mSimpleAdapter = new SimpleAdapter(
					this,
					listItemDesc,//��Ҫ�󶨵�����                
					R.layout.layout_frmladprod_billtab_item,//ÿһ�еĲ���//��̬�����е�����Դ�ļ���Ӧ�����岼�ֵ�View��
					new String[] {"tvidx","tvBatchNo"},   
					new int[] {R.id.tvidx,R.id.tvBatchNo}  
			);
			pnlFull.setAdapter(mSimpleAdapter);
			
			pnlFull.setOnItemClickListener(pnlFull_ItemClick);
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
		}
	}
	 
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{ 
			case 1: 
				txtLineNO.requestFocus(); 
				txtLineNO.setText("");
				break;
		}
	}
	
	private OnKeyListener txtLineNO_OnKeyDown = new OnKeyListener() {
		
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			try
			{
				if(keyCode == KeyEvent.KEYCODE_ENTER)
				{  
					if(txtLineNO.getText().toString().trim().length() > 0 && onEnter())
					{
						txtLineNO.setText("");
						txtBeginYMD.setText("");
						txtBeginHMS.setText(""); 
						txtEndYMD.setText("");
						txtEndHMS.setText(""); 
						
						setCount(_SaleBillDo);
	 
						txtLineNO.setFocusable(true);
						txtLineNO.setFocusableInTouchMode(true);
						txtLineNO.requestFocus();
						txtLineNO.findFocus(); 
					}
					else if(txtLineNO.getText().toString().trim().length() == 0 )
					{ 
						new Handler().postDelayed(
								new Runnable() {public void run() { 
									txtLineNO.requestFocus();  
								}  
							}, 200);   
					}
				}
				else if(keyCode == KeyEvent.KEYCODE_1)
				{
					txtLineNO.setText("A");
					txtBeginYMD.requestFocus();
				} 
				else if(keyCode == KeyEvent.KEYCODE_2)
				{
					txtLineNO.setText("B");
					txtBeginYMD.requestFocus();
				}
				else if(keyCode == KeyEvent.KEYCODE_3)
				{
					txtLineNO.setText("C");
					txtBeginYMD.requestFocus();
				}
				else if(keyCode == KeyEvent.KEYCODE_4)
				{
					txtLineNO.setText("D");
					txtBeginYMD.requestFocus();
				}
				else if(keyCode == KeyEvent.KEYCODE_5)
				{
					txtLineNO.setText("E");
					txtBeginYMD.requestFocus();
				}
				else if(keyCode == KeyEvent.KEYCODE_6)
				{
					txtLineNO.setText("F");
					txtBeginYMD.requestFocus();
				}
				else if(keyCode == KeyEvent.KEYCODE_7)
				{
					txtLineNO.setText("G");
					txtBeginYMD.requestFocus();
				}
				else if(keyCode == KeyEvent.KEYCODE_8)
				{
					txtLineNO.setText("H");
					txtBeginYMD.requestFocus();
				}
				else if(keyCode == KeyEvent.KEYCODE_9)
				{
					txtLineNO.setText("I");
					txtBeginYMD.requestFocus();
				}  
			}
			catch(Exception ex)
			{
				Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
			}
			return false;
		}
	};
	
	private OnKeyListener txtBeginYMD_OnKeyDown = new OnKeyListener(){

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			try
			{
				if(keyCode == KeyEvent.KEYCODE_ENTER){ 
					if(txtBeginYMD.getText().length() == 6 && onEnter())
					{ 
						txtLineNO.setText("");
						txtBeginYMD.setText("");
						txtBeginHMS.setText(""); 
						txtEndYMD.setText("");
						txtEndHMS.setText(""); 
						
						setCount(_SaleBillDo);
						 
						txtLineNO.setFocusable(true);
						txtLineNO.setFocusableInTouchMode(true);
						txtLineNO.requestFocus();
						txtLineNO.findFocus(); 
					}
					else
					{ 
						new Handler().postDelayed(
								new Runnable() {public void run() { 
									txtBeginYMD.requestFocus(); 
								}  
							}, 200);   
						
					}
				}
				else if(keyCode == KeyEvent.KEYCODE_0 || keyCode == KeyEvent.KEYCODE_1|| keyCode == KeyEvent.KEYCODE_2
						|| keyCode == KeyEvent.KEYCODE_3|| keyCode == KeyEvent.KEYCODE_4|| keyCode == KeyEvent.KEYCODE_5
						|| keyCode == KeyEvent.KEYCODE_6|| keyCode == KeyEvent.KEYCODE_7|| keyCode == KeyEvent.KEYCODE_8
						|| keyCode == KeyEvent.KEYCODE_9)
				{
					if(txtBeginYMD.getText().toString().trim().length() == 6)
					{
						txtBeginHMS.requestFocus();
					}
					if(txtBeginYMD.getText().toString().trim().length() > 6)
					{
						return false;
					}
				}
			}
			catch(Exception ex)
			{
				Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
			}
			return false;
		}

	};
	
    private OnKeyListener txtBeginHMS_OnKeyDown = new OnKeyListener() {
		 
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			try
			{
				if(keyCode == KeyEvent.KEYCODE_ENTER){ 
					if(txtBeginYMD.getText().toString().length() > 0)
					{
						txtEndYMD.setText(txtBeginYMD.getText());
					}
					if(txtBeginHMS.getText().toString().length() > 0)
					{
						Integer iBeginHMS = Integer.parseInt(txtBeginHMS.getText().toString());
						Integer iNumPerUnit = Integer.parseInt(_SaleBillDo.NumPerUnit);
						 
						txtEndHMS.setText(String.valueOf(iBeginHMS - iNumPerUnit));
					}
					
					if(txtBeginHMS.getText().toString().length() == 0)
					{
						new Handler().postDelayed(
								new Runnable() {public void run() { 
									txtBeginHMS.requestFocus(); 
								}  
							}, 200); 
					}
					if(txtBeginHMS.getText().toString().length() == _ihmslen && onEnter())
					{
						txtLineNO.setText("");
						txtBeginYMD.setText("");
						txtBeginHMS.setText(""); 
						txtEndYMD.setText("");
						txtEndHMS.setText(""); 
	 
						txtLineNO.setFocusable(true);
						txtLineNO.setFocusableInTouchMode(true);
						txtLineNO.requestFocus();
						txtLineNO.findFocus();  
					} 
				}
				else if(keyCode == KeyEvent.KEYCODE_0 || keyCode == KeyEvent.KEYCODE_1|| keyCode == KeyEvent.KEYCODE_2
						|| keyCode == KeyEvent.KEYCODE_3|| keyCode == KeyEvent.KEYCODE_4|| keyCode == KeyEvent.KEYCODE_5
						|| keyCode == KeyEvent.KEYCODE_6|| keyCode == KeyEvent.KEYCODE_7|| keyCode == KeyEvent.KEYCODE_8
						|| keyCode == KeyEvent.KEYCODE_9)
				{
					if(txtBeginHMS.getText().toString().length() == _ihmslen)
					{
						if(txtBeginYMD.getText().toString().length() > 0)
						{
							txtEndYMD.setText(txtBeginYMD.getText());
						}
						if(txtBeginHMS.getText().toString().length() > 0 && _SaleBillDo.NumPerUnit != null && 
								!_SaleBillDo.NumPerUnit.equals("0") && _SaleBillDo.NumPerUnit.trim().length() > 0)
						{
							Integer iBeginHMS = Integer.parseInt(txtBeginHMS.getText().toString());
							Integer iNumPerUnit = Integer.parseInt(_SaleBillDo.NumPerUnit);
							  
							txtEndHMS.setText(String.valueOf(iBeginHMS - iNumPerUnit));
						  
							txtEndHMS.requestFocus(); 
							txtEndHMS.setSelection(txtEndHMS.getText().length()); 
						}
						else
						{
							txtEndHMS.requestFocus(); 
						}
					} 
				}
			}
			catch(Exception ex)
			{
				Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
			}
			return false; 
		}
	};
	 
	private OnKeyListener txtEndYMD_OnKeyDown = new OnKeyListener(){

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			try
			{
				if(keyCode == KeyEvent.KEYCODE_ENTER){ 
					if(txtEndYMD.getText().length() == 6 && onEnter())
					{ 
						txtLineNO.setText("");
						txtBeginYMD.setText("");
						txtBeginHMS.setText(""); 
						txtEndYMD.setText("");
						txtEndHMS.setText(""); 
						
						setCount(_SaleBillDo);
						 
						txtLineNO.setFocusable(true);
						txtLineNO.setFocusableInTouchMode(true);
						txtLineNO.requestFocus();
						txtLineNO.findFocus(); 
					}
					else
					{ 
						new Handler().postDelayed(
								new Runnable() {public void run() { 
									txtEndYMD.requestFocus(); 
								}  
							}, 200);  
					}
				}
				else if(keyCode == KeyEvent.KEYCODE_0 || keyCode == KeyEvent.KEYCODE_1|| keyCode == KeyEvent.KEYCODE_2
						|| keyCode == KeyEvent.KEYCODE_3|| keyCode == KeyEvent.KEYCODE_4|| keyCode == KeyEvent.KEYCODE_5
						|| keyCode == KeyEvent.KEYCODE_6|| keyCode == KeyEvent.KEYCODE_7|| keyCode == KeyEvent.KEYCODE_8
						|| keyCode == KeyEvent.KEYCODE_9)
				{
					if(txtEndYMD.getText().toString().length() == 6)
					{
						txtEndHMS.requestFocus();
					}
				}
			}
			catch(Exception ex)
			{
				Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
			}
			return false;
		}

	};	
	
    private OnKeyListener txtEndHMS_OnKeyDown = new OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			try
			{
				if(keyCode == KeyEvent.KEYCODE_ENTER){ 
					if(txtEndHMS.getText().length() == _ihmslen && onEnter())
					{ 
						txtLineNO.setText("");
						txtBeginYMD.setText("");
						txtBeginHMS.setText(""); 
						txtEndYMD.setText("");
						txtEndHMS.setText(""); 
						
						setCount(_SaleBillDo);
						
						new Handler().postDelayed(
								new Runnable() {public void run() {
	//								Intent intent = new Intent(FrmLadProd_BillTabActivity.this,
	//										frm_ScanBarcodeActivity.class); 
	//							    startActivityForResult(intent, 1); 
									txtLineNO.requestFocus(); 
									txtLineNO.setText("");
								}  
							}, 200);  
						 
						return true; 
					}
					else if(txtEndHMS.getText().length() == 0)
					{  
						new Handler().postDelayed(
								new Runnable() {public void run() { 
									txtLineNO.requestFocus(); 
								}  
							}, 200);   
						return true; 
					}
				}
			}
			catch(Exception ex)
			{
				Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
			}
			return false; 
		}
	};
	
    
	
	private Boolean onEnter()
	{  
		try
		{
	         if (txtLineNO.getText().toString().trim().length() == 0)
	         {
	             Toast.makeText(getApplication(),"�����߲���Ϊ�ա�",2000).show();
	             return false;
	         }
	         if (txtBeginYMD.getText().toString().trim().length() == 0 || txtBeginHMS.getText().toString().trim().length() == 0)
	         { 
	        	 Toast.makeText(getApplication(),"��ʼ���Ų���Ϊ�ա�",2000).show();
	        	 return false;
	         }
	         else
	         {
	        	 if(!CommonMethord.isValidDate(txtBeginYMD.getText().toString()))
	        	 {
	        		 Toast.makeText(getApplication(),"��ʼ���ű��������ڸ�ʽ��",2000).show();
	            	 return false;
	        	 }
	         }
	         if (txtEndYMD.getText().toString().trim().length() == 0 || txtEndHMS.getText().toString().trim().length() == 0)
	         {
	        	 Toast.makeText(getApplication(),"��ֹ���Ų���Ϊ�ա�",2000).show();
	        	 return false;
	         }
	         else
	         {
	        	 if(!CommonMethord.isValidDate(txtEndYMD.getText().toString()))
	        	 {
	        		 Toast.makeText(getApplication(),"��ֹ���ű��������ڸ�ʽ��",2000).show();
	            	 return false;
	        	 }
	         }
	         
	         BatchNO b = new BatchNO(); 
	         b.LineNo = txtLineNO.getText().toString();
	         b.StartDate = CommonMethord.StrToDateShort(txtBeginYMD.getText().toString());
			 b.StartIdx = txtBeginHMS.getText().toString();
			 b.EndDate = CommonMethord.StrToDateShort(txtEndYMD.getText().toString());
			 b.EndIdx = txtEndHMS.getText().toString();
	         
			 if(_SaleBillDo.BatchNoList == null)
			 {
				 _SaleBillDo.BatchNoList = new ArrayList<BatchNO>();
			 }
			 for(BatchNO item : _SaleBillDo.BatchNoList)
			 {
				  if(item.ToString(item.StartDate,item.EndDate).equals(b.ToString(item.StartDate,item.EndDate)))
				  {
					  Toast.makeText(getApplication(),"������¼���ظ���" + b.ToString(item.StartDate,item.EndDate),2000).show();
			          return false;
				  }
			 }
			 _SaleBillDo.BatchNoList.add(b);
			 db.InsertBatchNo(_SaleBillDo, b);//д�����ݿ�
			 
			 initialBatchNoListView(_SaleBillDo.BatchNoList);//���¼�������list
	 
			 txtLineNO.setText("");
			txtBeginYMD.setText("");
			txtBeginHMS.setText(""); 
			txtEndYMD.setText("");
			txtEndHMS.setText(""); 
			
			setCount(_SaleBillDo);
	//		txtLineNO.requestFocus(); 
				
			return true;
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "�쳣:" + ex.getMessage(), 2000).show();
			return false;
		}
	}
	 
	 
	
	OnClickListener txtLineNo_OnFocus = new OnClickListener() {
		  
		@Override
		public void onClick(View v) {
			txtLineNO.requestFocus(); 
			txtLineNO.setSelection(txtLineNO.getText().length());
		}
	};
	OnClickListener txtBeginYMD_OnFocus = new OnClickListener() {
		  
		@Override
		public void onClick(View v) {
			txtBeginYMD.requestFocus(); 
			txtBeginYMD.setSelection(txtBeginYMD.getText().length());
		}
	};
	OnClickListener txtBeginHMS_OnFocus = new OnClickListener() {
		 
		@Override
		public void onClick(View v) {
			txtBeginHMS.requestFocus(); 
			txtBeginHMS.setSelection(txtBeginHMS.getText().length());
		}
	};
	OnClickListener txtEndHMS_OnFocus = new OnClickListener() {
		 
		@Override
		public void onClick(View v) {
			txtEndHMS.requestFocus(); 
			txtEndHMS.setSelection(txtEndHMS.getText().length());
		}
	};
	OnClickListener txtEndYMD_OnFocus = new OnClickListener() {
		 
		@Override
		public void onClick(View v) {
			txtEndYMD.requestFocus(); 
			txtEndYMD.setSelection(txtEndYMD.getText().length());
		}
	};
	
	
	
	
	/**
	 * ���²˵�
	 */
	private OnClickListener bt_menuLeftClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) { 
			try
			{  
				new AlertDialog.Builder(FrmLadProd_BillTabActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������  				  
			     .setMessage( "ȷ��Ҫ���ύ��?")//������ʾ������  	
			     .setNegativeButton("ȡ��", null)
			     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
		    	  public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
		    		 
		    		   //�ύ
						submit();
		    		  
			         }   
			     }) 
			     .show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���  
				
			}
			catch (Exception e)
			{ 
				e.toString();
			}
		}
	}; 
	
	/**
	 * ���²˵�
	 */
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
	
	
	/**
	 * �ύ
	 */
	protected void submit() {
		  List<String> lineNos = new ArrayList<String>();
          List<String> beginDates = new ArrayList<String>();
          List<String> beginNos = new ArrayList<String>();
          List<String> endDates = new ArrayList<String>();
          List<String> endNos = new ArrayList<String>();
         
          for (BatchNO batchno : _SaleBillDo.BatchNoList)
          {
        	  if(batchno != null)
        	  {
	              lineNos.add(batchno.LineNo);
	              beginDates.add(CommonMethord.DateToStr(batchno.StartDate));
	              beginNos.add(batchno.StartIdx);
	              endDates.add(CommonMethord.DateToStr(batchno.EndDate));
	              endNos.add(batchno.EndIdx);
        	  }
          }
          if(lineNos.size() == 0 && beginDates.size() == 0 && beginNos.size() == 0 && endDates.size() == 0 && endNos.size() == 0)
          {
        	  Toast.makeText(getApplication(), "û������������Ҫ�ύ��", 2000).show();
        	  return;
          }
          String result = WebServiceManager.SubmitBatchNO(_SaleBillDo.SalesBillNO, _SaleBillDo.BatchNoList.size(),
                  lineNos, beginDates, beginNos, endDates, endNos);
         if (!result.equals("success"))
         {
        	 Toast.makeText(getApplication(), result, 2000).show();
        	 return;
         }
         else
         { 
        	 Toast.makeText(getApplication(), "�ύ�ɹ�!", 2000).show();
        	 
             Global._ModelList.remove(_SaleBillDo);
             db.DeleteSaleBill(_SaleBillDo.SalesBillNO);
             
		     if (Global._ModelList.size() == 0)
		     { 
		    	finish(); 
			    Intent intent1= new Intent(FrmLadProd_BillTabActivity.this,FrmLadProdActivity.class); 
			    startActivity(intent1);  
		        return;
		     }
		     else if (Global._ModelList.size() > 0)
		     {
		    	finish();
			    Intent intent2= new Intent(FrmLadProd_BillTabActivity.this,FrmLadProd_BillIndexActivity.class); 
			    startActivity(intent2); 
		        return;
		     }
		     
		     //List<SaleBillDo> listSaleBillNo = getHaveBatchNoSalesBillNO(Global._ModelList);
 

         }
             
	} 
	
	/**
	 * ɾ������	
	 */
	/**
	 * 
	 */
	private OnItemClickListener pnlFull_ItemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			 
			new AlertDialog.Builder(FrmLadProd_BillTabActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������  				  
		     .setMessage( "�Ƿ�Ҫɾ��ѡ�е���?")//������ʾ������  	
		     .setNegativeButton("ȡ��", null)
		     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
	    	  public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
	    		 
	    		  HashMap<String,String> map=(HashMap<String,String>)pnlFull.getItemAtPosition(arg2);   
	              String tvBatchNo=map.get("tvBatchNo");   
	              for(BatchNO item : _SaleBillDo.BatchNoList)
	              {
	            	  if(item.ToString(item.StartDate,item.EndDate).equals(tvBatchNo))
	            	  {
	            		  _SaleBillDo.BatchNoList.remove(item);
	            		  db.DeleteBatchNo(_SaleBillDo.SalesBillNO, item);//�����ݿ���ɾ��
	            		  
	            		  initialBatchNoListView(_SaleBillDo.BatchNoList);
	            		  return;
	            	  }
	              }
		         }  
	    	  
		     }) 
		     .show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���  
			
		}
	};
	
	/**
	 * ��ȡ10���������ŵ�������
	 * @param ModelList �������б�
	 * @return
	 */
	private List<SaleBillDo> getHaveBatchNoSalesBillNO(List<SaleBillDo> ModelList)
	{
		 List<SaleBillDo> billnoList = new ArrayList<SaleBillDo>();
		 for(SaleBillDo sbo : Global._ModelList)  
	     {  
			 if(billnoList.size() <= 10)
			 {
//				 if(sbo.BatchNoList != null && sbo.BatchNoList.size() > 0)
//				 {
					 billnoList.add(sbo);
//				 }
			 }
			 else
			 {
				 break;
			 }
	     }
		 return billnoList;
	}
	
	 
 
 
	
}
