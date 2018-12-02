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

	public Button bt_menuLeft;// ���²˵���ť
	public Button bt_menuRight;// ���²˵���ť

	public ListView lv_menuLeft;// ���²˵�
	public ListView lv_menuRight;// ���²˵�

	public SimpleAdapter MenuAdapterLeft;// ���²˵�
	public ArrayList<HashMap<String, Object>> listmenuLeft;

	public SimpleAdapter MenuAdapterRight;// ���²˵�
	public ArrayList<HashMap<String, Object>> listmenuRight;

	boolean set_lvleft = false;// ���²˵���ʾ����
	boolean set_lvright = false;// ���²˵���ʾ����

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
		Toast.makeText(getApplication(), "�ϴ�����������", 2000).show();
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
			Toast.makeText(getApplication(), "�����쳣:" + ex.getMessage(), 2000)
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

		bt_menuLeft.setText("�˵�");
		bt_menuRight.setText("����");
		tvtitle.setText("�����ϴ�");
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
			map.put("menuname", "��ֹͬ��");
			listmenuLeft.add(map);

			String[] fromColumns = new String[] { "menuname" };
			int[] toLayoutIDs = new int[] { R.id.tv_menu };
			MenuAdapterLeft = new SimpleAdapter(this, listmenuLeft,
					R.layout.workmenu_model, fromColumns, toLayoutIDs);
			lv_menuLeft.setAdapter(MenuAdapterLeft);

		} catch (Exception e) {
		}
	}

	// ���²˵�
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

	// ���²˵�
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
						.setTitle("ϵͳ��ʾ")
						.setMessage("ȷ��Ҫִ������������\r\n\r\n���[��]���������[��]ȡ����")
						.setPositiveButton("ȷ��",
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
						.setNegativeButton("ȡ��",
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

	String _DoWorkMsg = "";//��¼�ϴ�ÿ�еķ�����Ϣ
	int iCntSucc = 0;//��¼�ϴ��ɹ�����
	int iCntErr = 0;//��¼�ϴ�ʧ������

	protected void DoWork() { 
		DbManager_Data db = new DbManager_Data(getApplication());
		Cursor allAssetBarcodes = db.GetAllAssetBarcodes();
		int ic = allAssetBarcodes.getCount();
		if (ic == 0) {
			_DoWorkMsg += "û����Ҫ�ϴ����̵��¼��\n\r";
			return;
		}
		
		_DoWorkMsg += "��" + ic + "����¼���ϴ���\n\r";

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
					// ��������ύ�̵��¼
					String rlt = WebServiceManager.SubmitInventory(Barcode,
							asset.InventoryUser, strInventoryDate,
							asset.InventoryRemark);
					if (rlt == null || !rlt.equals("success"))// �ύʧ��
					{
						_DoWorkMsg += "[" + Barcode + "]�̵����ύʧ�ܣ�ʧ����Ϣ(" + rlt
								+ ")\n\r";
						iCntErr++;
						continue;
					} else// �ύ�ɹ�
					{
						iCntSucc++;
						db.UpdateAssetInventory(Barcode, null, null, null);
					}
				} catch (Exception ex) {
					_DoWorkMsg += "[" + Barcode + "]�̵����ύ�쳣���쳣��Ϣ("
							+ ex.getMessage() + ")\n\r";
					iCntErr++;
					continue;
				}
			}
		}
		_DoWorkMsg += "�ϴ����,�ɹ�" + iCntSucc + "����ʧ��" + iCntErr + "��\n\r";

	}

	private void loadProgressBar() {
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("�����ϴ��������Ե�...");
		loginDialog.setCancelable(false);
		loginDialog.show();
	}

}
