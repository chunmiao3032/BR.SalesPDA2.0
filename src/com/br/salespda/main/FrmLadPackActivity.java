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
	
	public Button bt_menuLeft;//���²˵���ť
	public Button bt_menuRight;//���²˵���ť
	
	public ListView lv_menuLeft;//���²˵�
	public ListView lv_menuRight;//���²˵�
	
	public SimpleAdapter MenuAdapterLeft;//���²˵�
	public ArrayList<HashMap<String, Object>> listmenuLeft;
	
	public SimpleAdapter MenuAdapterRight;//���²˵�
	public ArrayList<HashMap<String, Object>> listmenuRight;
	
	boolean set_lvleft = false;//���²˵���ʾ����
	boolean set_lvright = false;//���²˵���ʾ����
	
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
		
		bt_menuLeft.setText("����");
		bt_menuRight.setText("����");
		tvtitle.setText("��װ�﷢��");
		
		//��ʼ����ǰ�����ֿ�
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

	//��ʼ�������ֿ�
	private void initcmbStorehouse() {
		cmbStorehouse = (Spinner) findViewById(R.id.cmbStorehouse);  
		//��ȡ�����ֿ�
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

	//���²˵�
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
	
	//���²˵�
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
			map.put("menuname", "��������");			
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "��������ģʽ");
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

	//���²˵������¼�
	private OnItemClickListener lv_menuLeftclick = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{ 
			switch (arg2)
			{
			//��������
			case 0:
				BlankOut();
				break;
			//��������ģʽ
			case 1:
				openInputMode();
				break;  
			} 
			lv_menuLeft.setVisibility(View.GONE);
			set_lvleft = !set_lvleft;
		}

		
	};
	
	//�����¼�
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
//						new AlertDialog.Builder(FrmLadPackActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������  				  
//					     .setMessage("���벻��Ϊ�գ���¼���������֡�")//������ʾ������  		  
//					     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
//				    	  public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
//					              
//					         }  	  
//					     }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���    
						Toast.makeText(getApplication(), "���벻��Ϊ�գ�", 2000).show();
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
            new AlertDialog.Builder(FrmLadPackActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������  				  
		     .setMessage( "���벻��Ϊ�գ�����������ʶ��ʧ�ܡ���")
		     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
	    	  public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
		             
		         }  	  
		     }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���  
            return;
        } 
		CItem item = ((CItem) cmbStorehouse.getSelectedItem());    
        if (item == null)
        {  
            new AlertDialog.Builder(FrmLadPackActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������  				  
		     .setMessage( "�����趨�����ֿ⡣")
		     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
	    	  public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
		             
		         }  	  
		     }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���  
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
                  
                 Toast.makeText(getApplicationContext(), "��������ɹ���", 2000).show();
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
                     Toast.makeText(getApplicationContext(), "�����ظ�������" + _CurrentBill.ErrMsg, 2000).show();
                 } 
                 else
                 {
                	 Toast.makeText(getApplicationContext(), "û�л�ȡ����������Ϣ��" + _CurrentBill.ErrMsg, 2000).show();
                 }
        	 }
        	 
        }
        catch(Exception ex)
        {
        	Toast.makeText(getApplicationContext(), "��ȡ������Ϣ�쳣��" + ex.getMessage(), 2000).show();
        }
		
	} 
	
	//��������
	private void BlankOut() {
		 try
         {
             final String barcode = this.txtBarcode.getText().toString().trim();
             if (barcode == null || barcode.length() == 0)
             {
                 Toast.makeText(getApplicationContext(),"��ǰû����Ҫ�����ĵ��ݡ�",2000).show();
             }
             
             new AlertDialog.Builder(FrmLadPackActivity.this).setTitle("ϵͳ��ʾ") 		  
		     .setMessage("ȷ��Ҫ��������" + barcode + "�ķ��������𣿣�")//������ʾ������  		  
		     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() { 
		         @Override  	  
		         public void onClick(DialogInterface dialog, int which) { 
		        	 String rlt = WebServiceManager.BlankoutTakingout(barcode);
		        	 if (rlt.equals("success"))
	                 { 
	                	 Toast.makeText(getApplicationContext(), "�����ɹ���" , 2000).show();
	                 }
	                 else
	                 {
	                	 Toast.makeText(getApplicationContext(), "����ʧ�ܣ�" + rlt , 2000).show();
	                 }
		         }  	  
		     })
		     .setNegativeButton("ȡ��",new DialogInterface.OnClickListener() { 
		         @Override  	  
		         public void onClick(DialogInterface dialog, int which) { 
		             
		         }  	  
		     }).show();  
              
         }
         catch (Exception ex)
         { 
        	 Toast.makeText(getApplicationContext(), "�����쳣:" + ex.getMessage(), 2000).show();
         }
		
	}
	//����
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
	//��������ģʽ
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

		
		
		
		
		