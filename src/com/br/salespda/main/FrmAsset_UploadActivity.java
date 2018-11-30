package com.br.salespda.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.br.salespda.R;
import com.br.salespda.basic.AssetDO;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.common.Global;
import com.br.salespda.manager.DbManager_Data;
import com.br.salespda.webservice.WebServiceManager;

public class FrmAsset_UploadActivity extends BaseActivity {

	public Button bt_menuLeft;// 左下菜单按钮
	public Button bt_menuRight;// 右下菜单按钮

	public ListView lv_menuLeft;// 左下菜单
	public ListView lv_menuRight;// 右下菜单

	public SimpleAdapter MenuAdapterLeft;// 左下菜单
	public ArrayList<HashMap<String, Object>> listmenuLeft;

	public SimpleAdapter MenuAdapterRight;// 右下菜单
	public ArrayList<HashMap<String, Object>> listmenuRight;

	boolean set_lvleft = false;// 左下菜单显示隐藏
	boolean set_lvright = false;// 左下菜单显示隐藏

	Button btnRun;
	TextView tvMsg;

	ProgressDialog loginDialog;

	Handler cwjHandler = null;
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			updateUI();
		}
	};

	private void updateUI() {
		loginDialog.cancel();
		if (loginDialog != null && loginDialog.isShowing()) {
			loginDialog.dismiss();
		}
		tvMsg.setText(_DoWorkMsg);
		Toast.makeText(getApplication(), "上传操作结束！", 2000).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmasset_upload);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			cwjHandler = new Handler(getApplication().getMainLooper());
			initial();
		} catch (Exception ex) {
			Toast.makeText(getApplication(), "程序异常:" + ex.getMessage(), 2000)
					.show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (loginDialog != null && loginDialog.isShowing()) {
			loginDialog.dismiss();
		}
	}

	private void initial() {
		bt_menuLeft = (Button) findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button) findViewById(R.id.bt_menuRight);
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView) findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView) findViewById(R.id.lv_menuRight);

		bt_menuLeft.setText("菜单");
		bt_menuRight.setText("返回");
		tvtitle.setText("数据上传");
		bt_menuLeft.setVisibility(View.INVISIBLE);

		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		bt_menuRight.setOnClickListener(bt_menuRightClick);
		SetMenuLeft();

		btnRun = (Button) findViewById(R.id.btnRun);
		btnRun.setOnClickListener(bt_RunClick);

		tvMsg = (TextView) findViewById(R.id.tvMsg);
	}

	private void SetMenuLeft() {
		try {
			listmenuLeft = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("menuname", "终止同步");
			listmenuLeft.add(map);

			String[] fromColumns = new String[] { "menuname" };
			int[] toLayoutIDs = new int[] { R.id.tv_menu };
			MenuAdapterLeft = new SimpleAdapter(this, listmenuLeft,
					R.layout.workmenu_model, fromColumns, toLayoutIDs);
			lv_menuLeft.setAdapter(MenuAdapterLeft);

		} catch (Exception e) {
		}
	}

	// 左下菜单
	private OnClickListener bt_menuLeftClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				if (set_lvleft) {
					lv_menuLeft.setVisibility(View.INVISIBLE);

				} else {
					lv_menuLeft.setVisibility(View.VISIBLE);
				}
				set_lvleft = !set_lvleft;
			} catch (Exception e) {
				e.toString();
			}
		}
	};

	// 右下菜单
	private OnClickListener bt_menuRightClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				finish();
			} catch (Exception e) {
				e.toString();
			}
		}
	};

	private OnClickListener bt_RunClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				tvMsg.setText("");
				_DoWorkMsg = "";
				iCntSucc = 0;
				iCntErr = 0;
				
				new AlertDialog.Builder(FrmAsset_UploadActivity.this)
						.setTitle("系统提示")
						.setMessage("确认要执行联网操作吗？\r\n\r\n点击[是]继续，点击[否]取消。")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										loadProgressBar();
										new Thread() {
											public void run() {
												DoWork();
												cwjHandler.post(mUpdateResults);
											}

										}.start();

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).show();

			} catch (Exception e) {
				e.toString();
			}
		}
	};

	String _DoWorkMsg = "";//记录上传每行的返回信息
	int iCntSucc = 0;//记录上传成功行数
	int iCntErr = 0;//记录上传失败行数

	protected void DoWork() { 
		DbManager_Data db = new DbManager_Data(getApplication());
		Cursor allAssetBarcodes = db.GetAllAssetBarcodes();
		int ic = allAssetBarcodes.getCount();
		if (ic == 0) {
			_DoWorkMsg += "没有需要上传的盘点记录！\n\r";
			return;
		}
		
		_DoWorkMsg += "共" + ic + "条记录需上传！\n\r";

		List<String> list_barcodes = new ArrayList<String>();

		for (allAssetBarcodes.moveToFirst(); !allAssetBarcodes.isAfterLast(); allAssetBarcodes
				.moveToNext()) {
			int idxBarcode = allAssetBarcodes.getColumnIndex("Barcode");
			String Barcode = allAssetBarcodes.getString(idxBarcode);
			list_barcodes.add(Barcode);
		}

		for (String Barcode : list_barcodes) {
			AssetDO asset = db.GetAsset(Barcode);

			if (asset != null
					&& (asset.InventoryDate != null && asset.InventoryDate
							.length() > 0))// && (asset.InventoryUser != null &&
											// asset.InventoryUser.length() >
											// 0))
			{
				if (asset.InventoryUser == null
						|| asset.InventoryUser.length() == 0) {
					asset.InventoryUser = Global.UserCode;
				}
				String strInventoryDate = asset.InventoryDate.replace(" ", "T");
				try {
					// 向服务器提交盘点记录
					String rlt = WebServiceManager.SubmitInventory(Barcode,
							asset.InventoryUser, strInventoryDate,
							asset.InventoryRemark);
					if (rlt == null || !rlt.equals("success"))// 提交失败
					{
						_DoWorkMsg += "[" + Barcode + "]盘点结果提交失败！失败信息(" + rlt
								+ ")\n\r";
						iCntErr++;
						continue;
					} else// 提交成功
					{
						iCntSucc++;
						db.UpdateAssetInventory(Barcode, null, null, null);
					}
				} catch (Exception ex) {
					_DoWorkMsg += "[" + Barcode + "]盘点结果提交异常！异常信息("
							+ ex.getMessage() + ")\n\r";
					iCntErr++;
					continue;
				}
			}
		}
		_DoWorkMsg += "上传完毕,成功" + iCntSucc + "条，失败" + iCntErr + "条\n\r";

	}

	private void loadProgressBar() {
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("正在上传数据请稍等...");
		loginDialog.setCancelable(false);
		loginDialog.show();
	}

}
