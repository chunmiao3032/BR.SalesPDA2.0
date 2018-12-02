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
		Toast.makeText(getApplicationContext(),"����ͬ���ɹ�",2000).show();
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
		bt_menuLeft.setText("�˵�");
		bt_menuRight.setText("����");
		tvtitle.setText("��������");
		
		SetMenuLeft(); 
		 
		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		bt_menuRight.setOnClickListener(bt_menuRightClick); 
		
		cmbCorp = (Spinner)findViewById(R.id.cmbCorp);
		btnRun = (Button)findViewById(R.id.btnRun);
		
		btnRun.setOnClickListener(btnRun_Click);
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
				map.put("menuname", "��ֹͬ��");
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
			//��ֹͬ��
			case 0:
				 
				break; 
			}
			lv_menuLeft.setVisibility(View.GONE);
			set_lvleft = !set_lvleft;
		} 
	};
	
	//��ʼ����֯�ܹ������б�
	private void initcmbCorp() {
		 
		cmbCorp = (Spinner) findViewById(R.id.cmbCorp);  
		//��ȡ�����ֿ�
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
	              new AlertDialog.Builder(FrmAsset_DownloadActivity.this).setTitle("ϵͳ��ʾ") 		  
	 		     .setMessage("����ѡ������������")//������ʾ������  		  
	 		     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() { 
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
		loginDialog.setMessage("���������������Ե�...");
		loginDialog.setCancelable(false);
		loginDialog.show();
	}
	
	private void Down_load() {
		try
		{   
			//��¼��־
			//List<String> list_log = new ArrayList<String>();
			  
			 String CorpCode = ((CItem) cmbCorp.getSelectedItem()).GetCode();    
			  
			 //list_log.add("��ʼ�ӷ�������ȡ���й̶��ʲ���Ϣ" + getCurrentTime());
			  
			 //�ӷ�������ȡ���й̶��ʲ���Ϣ
//	         List<AssetDO> allAssets = WebServiceManager.GetAllAssets(CorpCode); 
	         Hashtable<String, AssetDO> allAssets = WebServiceManager.GetAllAssetsHash(CorpCode); 
	         
			 //list_log.add("�ӷ�������ȡ���й̶��ʲ���Ϣ����" + getCurrentTime());
			 
	         if ((allAssets == null) || (allAssets.size() == 0))
	         {
//	             Toast.makeText(getApplication(),"��������û����Ҫ���ص����ݡ�",2000).show();
	             return;
	         }
//****************************************************************************************	  	         
//	         List<String> first = new ArrayList<String>();           //��������ȡ���й̶��ʲ���Ϣ�ġ����롱�ֶ�
//	         List<String> allAssetBarcodes = new ArrayList<String>();//�������ݿ����й̶��ʲ���Ϣ�ġ����롱�ֶ�
//	         List<String> strArray3 = new ArrayList<String>();	//������ �ų� ����  = ����
//	         List<String> strArray4 = new ArrayList<String>();	//���� �ų� ������ = ��������
//	         List<String> strArray5 = new ArrayList<String>();	//������ �� ���ؽ��� = ����
	            
	         Hashtable<String,String> first = new Hashtable<String, String>();           //��������ȡ���й̶��ʲ���Ϣ�ġ����롱�ֶ�
	         Hashtable<String,String> allAssetBarcodes = new Hashtable<String, String>();//�������ݿ����й̶��ʲ���Ϣ�ġ����롱�ֶ�
	         Hashtable<String,String> strArray3 = new Hashtable<String, String>();	//������ �ų� ����  = ����
	         Hashtable<String,String> strArray4 = new Hashtable<String, String>();	//���� �ų� ������ = ��������
	         Hashtable<String,String> strArray5 = new Hashtable<String, String>();	//������ �� ���ؽ��� = ����
	         
	         Cursor cur_allAssetBarcodes = db_data.GetAllAssetBarcodes();
	             
//****************************************************************************************	  	         
	         //first  ������
//	         for(AssetDO asset : allAssets)
//	         {
//	        	 first.add(asset.Barcode);
//	         }
	          
	         Enumeration e = allAssets.elements(); //����value
	         while(e.hasMoreElements() )
	         { 
	        	 AssetDO assetdo = (AssetDO)e.nextElement();
	        	 first.put(assetdo.Barcode,assetdo.Barcode); 
	         }
//****************************************************************************************	  	        
	         //allAssetBarcodes  ����
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
	         //������ �ų� ����
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
	         //�����ų� ������ 
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
	         //������ �� ���ؽ���
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
//	             Toast.makeText(getApplication(), "���������Ѿ����£�û����Ҫ���ص����ݡ�",2000).show();
	             return;
	         }		 
//****************************************************************************************	         
//	         int idx = 0;
//	         //������ �ų� ����  = ����
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
//	        		 Log.i("ycmtest", "barcode=" + barcode + "strArray3=" + strArray3.size() + "�쳣��Ϣ=" + ex.getMessage());
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
	        		 Log.i("ycmtest", "barcode=" + barcode + "strArray3=" + strArray3.size() + "�쳣��Ϣ=" + ex.getMessage());
	        	 }
	         }
	         
//****************************************************************************************	 	         	         
//	         idx = 0;
//	         //���� ���շ��������±���
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
//	        		 Log.i("ycmtest", "barcode=" + barcode + "strArray5=" + strArray5.size() + "�쳣��Ϣ=" + ex.getMessage()); 
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
	        		 Log.i("ycmtest", "barcode=" + barcode + "strArray3=" + strArray3.size() + "�쳣��Ϣ=" + ex.getMessage());
	        	 }
	         }
	         
//****************************************************************************************	 	           	         
//ɾ�������з�����û�е�
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
//			Toast.makeText(this, "�쳣:" + ex.getMessage(), 2000).show();
			Log.i("ycmtest", "�쳣��Ϣ=" + ex.getMessage());
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

	//��ȡ��ǰʱ��
	public String getCurrentTime()
	 {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy��MM��dd��    HH:mm:ss");       
		 Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰʱ��       
		 String str = formatter.format(curDate);
		 return str;
	 }

	 
}
