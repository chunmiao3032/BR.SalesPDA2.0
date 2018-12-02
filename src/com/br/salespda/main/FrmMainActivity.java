package com.br.salespda.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.serialization.SoapPrimitive;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnKeyListener;

import com.br.salespda.R;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.common.Global;
import com.br.salespda.webservice.SoapControl;

public class FrmMainActivity extends BaseActivity {

	public Button bt_menuLeft;// ���²˵���ť
	public Button bt_menuRight;// ���²˵���ť

	public ListView lv_menuLeft;// ���²˵�
	public ListView lv_menuRight;// ���²˵�

	public SimpleAdapter MenuAdapterLeft;// ���²˵�
	public ArrayList<HashMap<String, Object>> listmenuLeft;

	public SimpleAdapter MenuAdapterRight;// ���²˵�
	public ArrayList<HashMap<String, Object>> listmenuRight;

	boolean set_lvleft = false;// ���²˵���ʾ����

//	Button btnProductLading;
//	Button btnPackingLading;
//	Button btnAsset;
	ImageButton btnSecondCheck;

	private ProgressDialog loginDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmmain);

		initial();

	}

	private void initial() {
		bt_menuLeft = (Button) findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button) findViewById(R.id.bt_menuRight);
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView) findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView) findViewById(R.id.lv_menuRight);

		bt_menuLeft.setText("�˵�");
		bt_menuRight.setText("�˳�");
		tvtitle.setText("��ҵ��Ϣ����ƽ̨");

		SetMenuLeft();

		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		bt_menuRight.setOnClickListener(bt_menuRightClick);

		// ��Ʒ�Ʒ���
//		btnProductLading = (Button) findViewById(R.id.btnProductLading);
//		btnProductLading.setOnClickListener(btnProductLading_Click);
		// ��װ�﷢��
//		btnPackingLading = (Button) findViewById(R.id.btnPackingLading);
//		btnPackingLading.setOnClickListener(btnPackingLading_Click);
		// �̶��ʲ��̵�
//		btnAsset = (Button) findViewById(R.id.btnAsset);
//		btnAsset.setOnClickListener(btnAsset_Click);
		// ��װ�︴��
		btnSecondCheck = (ImageButton) findViewById(R.id.btnSecondCheck);
		btnSecondCheck.setOnClickListener(btnSecondCheck_Click);

		lv_menuLeft.setOnItemClickListener(lv_menuLeftclick);
	}

	// ���²˵�
	private OnClickListener bt_menuLeftClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				if (set_lvleft) {
					lv_menuLeft.setVisibility(View.GONE);

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

	private void SetMenuLeft() {
		try {
			listmenuLeft = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("menuname", "ϵͳ����");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "��������");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "����");
			listmenuLeft.add(map);

			String[] fromColumns = new String[] { "menuname" };
			int[] toLayoutIDs = new int[] { R.id.tv_menu };
			MenuAdapterLeft = new SimpleAdapter(this, listmenuLeft,
					R.layout.workmenu_model, fromColumns, toLayoutIDs);
			lv_menuLeft.setAdapter(MenuAdapterLeft);

		} catch (Exception e) {
		}

	}

	// ��Ʒ�Ʒ���
	private OnClickListener btnProductLading_Click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				Intent intent = new Intent(FrmMainActivity.this,
						FrmLadProdActivity.class);
				startActivity(intent);
				// new
				// AlertDialog.Builder(FrmMainActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������
				// .setMessage( "��ģ������Ȩ��")//������ʾ������
				// .setPositiveButton("ȷ��",new DialogInterface.OnClickListener()
				// {//���ȷ����ť
				// public void onClick(DialogInterface dialog, int which)
				// {//ȷ����ť����Ӧ�¼�
				//
				// }
				// }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���
			} catch (Exception e) {
				e.toString();
			}
		}
	};

	// ��װ�﷢��
	private OnClickListener btnPackingLading_Click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				Intent intent = new Intent(FrmMainActivity.this,
						FrmLadPackActivity.class);
				startActivity(intent);

				// new
				// AlertDialog.Builder(FrmMainActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������
				// .setMessage( "��ģ������Ȩ��")//������ʾ������
				// .setPositiveButton("ȷ��",new DialogInterface.OnClickListener()
				// {//���ȷ����ť
				// public void onClick(DialogInterface dialog, int which)
				// {//ȷ����ť����Ӧ�¼�
				//
				// }
				// }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���

			} catch (Exception e) {
				e.toString();
			}
		}
	};

	// �̶��ʲ��̵�
	private OnClickListener btnAsset_Click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				Intent intent = new Intent(FrmMainActivity.this,
						FrmAssetActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				e.toString();
			}
		}
	};

	//��װ�︴��
	private OnClickListener btnSecondCheck_Click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				Intent intent = new Intent(FrmMainActivity.this,
						FrmReceivingBill_StoreHouseActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				e.toString();
			}
		}
	};

	// ���²˵������¼�
	private OnItemClickListener lv_menuLeftclick = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch (arg2) {
			// ϵͳ����
			case 0:
				try {
					Intent intent = new Intent(FrmMainActivity.this,
							FrmMain_SettingActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplication(), "�쳣��" + e.getMessage(),
							2000).show();
				}
				break;
			// ��������
			case 1:
				try {
					checkAppVersion();
				} catch (Exception ex) {
					Toast.makeText(getApplication(), "�쳣��" + ex.getMessage(),
							2000).show();
				}
				break;
			// ����
			case 2:
				break;
			}
			lv_menuLeft.setVisibility(View.GONE);
			set_lvleft = !set_lvleft;
		}
	};

	// ���������
	private void checkAppVersion() {
		// ��ȡ�汾��Ϣ
		try {
			String version = this.getPackageManager().getPackageInfo(
					"com.br.salespda", 0).versionName;
			Global.Version = version;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appName", "SalesPDA");
			map.put("deviceId", Global.deviceId);
			map.put("dbServer", Global.dbServer);
			SoapPrimitive sp = SoapControl.ExecuteWebMethod("GetLastVersion",
					map, Global.SendDataTime);

			if (sp != null) {
				String ver_Server = sp.toString().trim();
				// float retValue = Float.parseFloat(sp.toString().trim());
				if (CompareVersion(ver_Server, version) <= 0) {
					Toast.makeText(getApplication(), "��ǰ�汾�Ѿ������£�����������", 2000)
							.show();
					return;
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							FrmMainActivity.this)
							.setTitle("ϵͳ��ʾ")
							.setMessage("��⵽��ǰ�����°汾���Ƿ�����?")
							.setCancelable(false)
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											downFile();
										}
									})
							.setNegativeButton("ȡ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});
					builder.show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "��ȡ����汾����ϣ�", 2000)
						.show();
			}
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
			Toast.makeText(getApplicationContext(), "��ȡ����汾�쳣��", 2000).show();
		}
	}

	void downFile() {
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("�������ظ���,���Ժ�...");
		loginDialog.setCanceledOnTouchOutside(false);
		loginDialog.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();

				HttpGet get = new HttpGet(Global.updateFileString);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {

						File file = new File(
								Environment.getExternalStorageDirectory(),
								"BR.SalesPDA.apk");
						fileOutputStream = new FileOutputStream(file);

						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {

							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {

							}
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					update();
					loginDialog.cancel();
				} catch (ClientProtocolException e) {
					loginDialog.cancel();
					e.printStackTrace();

				} catch (IOException e) {
					loginDialog.cancel();
					e.printStackTrace();
				}
			}
		}.start();
	}

	protected void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()
						.toString() + "/BR.SalesPDA.apk")),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	public static int CompareVersion(String ver1, String ver2) {
		String[] strArray = ver1.split("\\.");
		String[] strArray2 = ver2.split("\\.");
		for (int i = 0; i < Math.min(strArray.length, strArray2.length); i++) {
			int num2 = Integer.valueOf(strArray[i]);
			int num3 = Integer.valueOf(strArray2[i]);
			if (num2 > num3) {
				return 1;
			}
			if (num2 < num3) {
				return -1;
			}
		}
		return 0;
	}

}
