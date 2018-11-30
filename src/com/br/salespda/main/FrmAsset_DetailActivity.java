package com.br.salespda.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.br.salespda.R;
import com.br.salespda.basic.AssetDO;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.CItem;
import com.br.salespda.webservice.WebServiceManager;

public class FrmAsset_DetailActivity extends BaseActivity{
	
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
	
	AssetDO _Model = new AssetDO();
	private static List<String> _StatusList = null;
	Spinner cmbStatus;
	EditText txtCode;
	EditText txtName; 
	EditText txtAssetType; 
	EditText txtAbstract; 
	EditText txtStandard; 
	EditText txtDepartment; 
	EditText txtHowGetting; 
	EditText txtLocation; 
	EditText txtMonthUsed; 
	EditText txtDevalueRate; 
	EditText txtDevalueMethod; 
	EditText txtOriginalMoney; 
	EditText txtCount; 
	EditText txtStartUsingDate; 
	EditText txtManufactureDate; 
	EditText txtSupplier;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmasset_detail);
	
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
			
		try
		{
		Intent intent = this.getIntent(); 
		_Model=(AssetDO)intent.getSerializableExtra("_Model");
		Init();
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplication(), "程序出现异常:" + ex.getMessage(), 2000).show();
		}
	}
	
	  public void Init()
      {  
		  bt_menuLeft = (Button)findViewById(R.id.bt_menuLeft);
		  bt_menuRight = (Button)findViewById(R.id.bt_menuRight);  
		  tvtitle = (TextView)findViewById(R.id.tvtitle);
		  lv_menuLeft = (ListView)findViewById(R.id.lv_menuLeft);
		  lv_menuRight = (ListView)findViewById(R.id.lv_menuRight);
		  bt_menuLeft.setText("返回");
		  bt_menuRight.setVisibility(View.GONE);
		  tvtitle.setText("资产档案");
		  
		  initcmbStorehouse();
		  
		  txtCode = (EditText)findViewById(R.id.txtCode);
		  txtName = (EditText)findViewById(R.id.txtName); 
		  txtAssetType = (EditText)findViewById(R.id.txtAssetType); 
		  txtAbstract = (EditText)findViewById(R.id.txtAbstract); 
		  txtStandard = (EditText)findViewById(R.id.txtStandard); 
		  txtDepartment = (EditText)findViewById(R.id.txtDepartment); 
		  txtHowGetting = (EditText)findViewById(R.id.txtHowGetting); 
		  txtLocation = (EditText)findViewById(R.id.txtLocation); 
		  txtMonthUsed = (EditText)findViewById(R.id.txtMonthUsed); 
		  txtDevalueRate = (EditText)findViewById(R.id.txtDevalueRate); 
		  txtDevalueMethod = (EditText)findViewById(R.id.txtDevalueMethod); 
		  txtOriginalMoney = (EditText)findViewById(R.id.txtOriginalMoney); 
		  txtCount = (EditText)findViewById(R.id.txtCount); 
		  txtStartUsingDate = (EditText)findViewById(R.id.txtStartUsingDate); 
		  txtManufactureDate = (EditText)findViewById(R.id.txtManufactureDate); 
		  txtSupplier = (EditText)findViewById(R.id.txtSupplier);
		  
		  bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		  
		  if (this._Model != null)
          { 
			  int idx = getindex(_StatusList,_Model.Status);
              cmbStatus.setSelection(idx); 
              txtCode.setText(_Model.Code);
              txtName.setText(_Model.Name);
              txtAssetType.setText(_Model.AssetType);
              txtAbstract.setText(_Model.Abstract);
              txtStandard.setText(_Model.Standard);
              txtDepartment.setText(_Model.Department);
              txtHowGetting.setText(_Model.HowGetting);
              txtLocation.setText(_Model.Location);
              txtMonthUsed.setText(_Model.MonthUsed);
              txtDevalueRate.setText(_Model.DevalueRate);
              txtDevalueMethod.setText(_Model.DevalueMethod);
              txtOriginalMoney.setText(_Model.OriginalMoney);
              txtCount.setText(_Model.Count);
              txtStartUsingDate.setText(_Model.StartUsingDate);
              txtManufactureDate.setText(_Model.ManufactureDate);
              if(_Model.Supplier.contains("anyType"))
              {
            	  txtSupplier.setText("");
              }
              else
              {
            	  txtSupplier.setText(_Model.Supplier);
              }
          }
		  
      }
	  
		//左下菜单
		private OnClickListener bt_menuLeftClick = new OnClickListener() {
			
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
	  
	  private void initcmbStorehouse() {
			 
	   cmbStatus = (Spinner) findViewById(R.id.cmbStatus);  
		//获取使用状况
	   _StatusList  =  WebServiceManager.GetAllAssetStatus();  
		      
	   ArrayAdapter<String> myaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _StatusList);  
	   cmbStatus.setAdapter(myaAdapter); 
	 	    
	 }

	  private int getindex(List<String> list,String Status)
	  {
		  int idx = 0;
		  idx = list.indexOf(Status);
		  return idx;
	  }
	  
	  
	  
	  
	  
}
