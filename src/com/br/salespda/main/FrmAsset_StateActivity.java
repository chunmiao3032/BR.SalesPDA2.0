package com.br.salespda.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.br.salespda.R;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.manager.DbManager_Data;

public class FrmAsset_StateActivity extends BaseActivity{
	
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
	
	Spinner cmbDept;
	ListView dgvList;
	DbManager_Data db = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmasset_state);
		
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		db = new DbManager_Data(getApplication());
		initial();
	}
	
	private void initial() {
		bt_menuLeft = (Button)findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button)findViewById(R.id.bt_menuRight);  
		tvtitle = (TextView)findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView)findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView)findViewById(R.id.lv_menuRight);
		
		bt_menuLeft.setText("ˢ��");
		bt_menuRight.setVisibility(View.GONE);
		tvtitle.setText("�̵���Ȳ�ѯ");
		
		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		
		initAllDepartments();
		
		dgvList = (ListView)findViewById(R.id.dgvList);
		DoWork();
	}
	
	private void initAllDepartments() {
		cmbDept = (Spinner)findViewById(R.id.cmbDept);
		List<String> list_dept = new ArrayList<String>();
		
		Cursor cursor_Dept = db.GetAllDepartments();
		
		for(cursor_Dept.moveToFirst();!cursor_Dept.isAfterLast();cursor_Dept.moveToNext())
		{
		    int nameColumn = cursor_Dept.getColumnIndex("Department"); 
		    String name = cursor_Dept.getString(nameColumn); 
		    list_dept.add(name);
		}
		ArrayAdapter<String> myaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_dept);  
		cmbDept.setAdapter(myaAdapter); 
		
	}

	//���²˵�
	private OnClickListener bt_menuLeftClick = new OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				try
				{ 
					DoWork(); 
				}
				catch (Exception e)
				{ 
					e.toString();
				}
			}
		}; 
	
	private void DoWork()
	{
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();
		String deptName = cmbDept.getSelectedItem().toString(); 
		Cursor cur_asset = db.GetAssets(deptName);
		
		for(cur_asset.moveToFirst();!cur_asset.isAfterLast();cur_asset.moveToNext())
		{
			//Barcode,Code,Name,Department,Location,Status,InventoryUser,InventoryDate
		    int idatetime = cur_asset.getColumnIndex("InventoryDate"); 
		    String datetime = cur_asset.getString(idatetime);
		    int ibarcode = cur_asset.getColumnIndex("Barcode"); 
		    String barcode = cur_asset.getString(ibarcode);
		     
		    String status =  (datetime != null && datetime.length() > 0) ? "��":""; 
		    int iassetname = cur_asset.getColumnIndex("Name"); 
		    String assetname = cur_asset.getString(iassetname);
		    
		    HashMap<String, Object> map = new HashMap<String, Object>();  
		    map.put("datetime",datetime);
		    map.put("status",status);
		    map.put("assetname",assetname); 
		    map.put("barcode",barcode);
		     
		    listItem.add(map);
		}
		
		SimpleAdapter mSimpleAdapter = new SimpleAdapter(
				this,
				listItem,//��Ҫ�󶨵�����                
				R.layout.layout_frmasset_state_item,//ÿһ�еĲ���//��̬�����е�����Դ�ļ���Ӧ�����岼�ֵ�View��
				new String[] {"barcode","assetname", "status","datetime"},   
				new int[] {R.id.barcode,R.id.assetname,R.id.status,R.id.datetime}  
		);
		dgvList.setAdapter(mSimpleAdapter);
		
	}

}
