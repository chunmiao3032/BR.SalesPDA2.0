package com.br.salespda.main;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.br.salespda.R;
import com.br.salespda.Crash.CrashApplication;
import com.br.salespda.basic.AssetDO;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.HadwareControll;
import com.br.salespda.common.CommonMethord;
import com.br.salespda.common.Global;
import com.br.salespda.manager.DbManager_Data;
import com.br.salespda.webservice.WebServiceManager;

public class FrmAssetActivity extends BaseActivity {

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

	boolean bHandInputMode = false;// �ֹ�¼��ģʽ

	private boolean set_lv = false;

	public EditText txtBarcode;

	RelativeLayout rleft;
	RelativeLayout rright;
	RelativeLayout allmenu;

	LinearLayout llmenu;

	TextView txtCode;
	TextView txtName;
	TextView txtDept;
	TextView txtLocation;
	TextView txtStartDate;

	ImageView imgPhoto;
	private AssetDO _Model = null;
	DbManager_Data db = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frmasset);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		db = new DbManager_Data(getApplication());
		initial();

	}

	private void initial() {
		bt_menuLeft = (Button) findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button) findViewById(R.id.bt_menuRight);
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView) findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView) findViewById(R.id.lv_menuRight);

		bt_menuLeft.setText("����");
		bt_menuRight.setText("ģʽ");

		tvtitle.setText("�ʲ��̵�");
		// bt_menuRight.setVisibility(View.INVISIBLE);

		SetMenuLeft();
		SetMenuRight();

		txtBarcode = (EditText) findViewById(R.id.txtBarcode);
		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		bt_menuRight.setOnClickListener(bt_menuRightClick);

		lv_menuLeft.setOnItemClickListener(lv_menuLeft_Click);
		lv_menuRight.setOnItemClickListener(lv_menuRight_Click);

		rleft = (RelativeLayout) findViewById(R.id.rleft);
		rright = (RelativeLayout) findViewById(R.id.rright);
		llmenu = (LinearLayout) findViewById(R.id.llmenu);

		txtCode = (TextView) findViewById(R.id.txtCode);
		txtName = (TextView) findViewById(R.id.txtName);
		txtDept = (TextView) findViewById(R.id.txtDept);
		txtLocation = (TextView) findViewById(R.id.txtLocation);
		txtStartDate = (TextView) findViewById(R.id.txtStartDate);
		
		allmenu = (RelativeLayout) findViewById(R.id.allmenu);

		imgPhoto = (ImageView) findViewById(R.id.imgPhoto);

		txtBarcode.setOnKeyListener(et_tmkey);
		txtBarcode.setFocusableInTouchMode(true);
		txtBarcode.requestFocus();
		txtBarcode.setInputType(InputType.TYPE_CLASS_NUMBER);

	}

	private OnKeyListener et_tmkey = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			try {
				if (keyCode == 4) {
					return false;
				}
				String barcode = txtBarcode.getText().toString().trim();
				// if(barcode.length() < 8)
				// {
				// return false;
				// }
				// if ((keyCode == 140 || keyCode == 141) && event.getAction()
				// == KeyEvent.ACTION_DOWN)
				// {
				// txtBarcode.setText("");
				// }

				// ɨ��ģʽ
				if (!bHandInputMode) {
					if (barcode.length() >= 8) {
						if (barcode.length() == 0) {
							new AlertDialog.Builder(FrmAssetActivity.this)
									.setTitle("ϵͳ��ʾ")// ���öԻ������
									.setMessage("���벻��Ϊ�գ���¼���������֡�")// ������ʾ������
									.setPositiveButton(
											"ȷ��",
											new DialogInterface.OnClickListener() {// ���ȷ����ť
												public void onClick(
														DialogInterface dialog,
														int which) {// ȷ����ť����Ӧ�¼�

												}
											}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
						} else {
							DoWork(barcode);
						}
						// Intent intent = new Intent(FrmAssetActivity.this,
						// frm_ScanBarcodeActivity.class);
						// startActivityForResult(intent, 1);

						txtBarcode.setText("");
						txtBarcode.requestFocus();
						txtBarcode.setFocusable(true);
						return true;
					} else {
						txtBarcode.requestFocus();
						txtBarcode.setFocusable(true);
						return true;
					}
				}
				else//������ģʽ
				{
					if (barcode.length() >= 8 && keyCode == KeyEvent.KEYCODE_ENTER) { 
						DoWork(barcode); 
						
						txtBarcode.setText("");
						txtBarcode.requestFocus();
						txtBarcode.setFocusable(true);
						return true;
					}
					else if (barcode.length() < 8 && keyCode == KeyEvent.KEYCODE_ENTER)
					{  
						if(barcode.length() > 0 && barcode.length() < 8)
						{
							Toast.makeText(getApplicationContext(), "�����ʽ����ȷ!", 3000).show();
						}
						
						txtBarcode.setText(""); 
						txtBarcode.requestFocus();
						txtBarcode.setFocusable(true);
						return true;
					}
					else
					{
						return false;
					}					
					
				}
					
			} catch (Exception ex) {
				Toast.makeText(getApplicationContext(), "aaaa" + ex.getMessage(), 2000).show();
				txtBarcode.requestFocus();
				txtBarcode.setFocusable(true);
				return false;
			} 
		}

	};

	/**
	 * ԭonkey���� // public boolean onKey(View v, int keyCode, KeyEvent event) //
	 * { // try // { // String barcode = txtBarcode.getText().toString().trim();
	 * // if ((keyCode == 140 || keyCode == 141) && event.getAction() ==
	 * KeyEvent.ACTION_DOWN) // { // txtBarcode.setText(""); // } // if (keyCode
	 * == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
	 * // { // if(barcode.length() == 0) // { // new
	 * AlertDialog.Builder(FrmAssetActivity.this).setTitle("ϵͳ��ʾ")//���öԻ������ //
	 * .setMessage("���벻��Ϊ�գ���¼���������֡�")//������ʾ������ // .setPositiveButton("ȷ��",new
	 * DialogInterface.OnClickListener() {//���ȷ����ť // public void
	 * onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼� // // } //
	 * }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի��� // } // else // { // DoWork(barcode); // } //
	 * Intent intent = new Intent(FrmAssetActivity.this, //
	 * frm_ScanBarcodeActivity.class); // startActivityForResult(intent, 1); //
	 * // txtBarcode.requestFocus(); // return true; // } // else // { //
	 * txtBarcode.requestFocus(); // return false; // } // } // catch(Exception
	 * ex) // { // return false; // } // // } };
	 */

	private void DoWork(String barcode) {
		if (barcode == null || barcode.length() == 0) {
			Toast.makeText(getApplication(), "��ȡ����ʧ��,�����ԣ�", 2000).show();
			return;
		}
		AssetDO asset = db.GetAsset(barcode);
		if (asset == null) {
			Toast.makeText(getApplication(), "�ʲ�����[" + barcode + "]�����ڣ�", 2000)
					.show();
			return;
		}
		txtCode.setText(asset.Code);
		txtName.setText(asset.Name);
		txtDept.setText(asset.Department);
		txtLocation.setText(asset.Location);
		txtStartDate.setText(asset.StartUsingDate);
		if ((asset.Photo != null) && (asset.Photo.length > 0)) {
			// byte [] photo_tmp = CommonMethord.unjzlib(asset.Photo);
			Bitmap photo = CommonMethord.Bytes2Bimap(asset.Photo);
			imgPhoto.setImageBitmap(photo);

			// InputStream is = CommonMethord.Byte2InputStream(asset.Photo);
			// Bitmap photo = CommonMethord.InputStream2Bitmap(is);
			// imgPhoto.setImageBitmap(photo);

			// Drawable dw = CommonMethord.Bytes2Drawable(asset.Photo);
			// Bitmap photo = CommonMethord.drawable2Bitmap(dw);
			// imgPhoto.setImageBitmap(photo);

			// ����ȡ������ת����drawable
			// Bitmap bitmap = BitmapFactory.decodeByteArray(asset.Photo, 0,
			// asset.Photo.length, null);
			// BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
			// Drawable drawable = bitmapDrawable;
			// imgPhoto.setImageDrawable(drawable);
		} else {
			imgPhoto.setImageBitmap(null);
		}
		this._Model = asset;
		if (asset.InventoryDate != null && asset.InventoryDate.length() != 0) {
			Toast.makeText(getApplication(),
					"�ʲ�����[" + barcode + "]���̵㣬�����ظ�������", 2000).show();

		} else {
			String inventoryUser = "";
			if (Global.UserCode == null || Global.UserCode.trim().length() == 0) {
				CrashApplication userinfo = (CrashApplication) getApplicationContext();
				inventoryUser = userinfo.getUserCode();
			} else {
				inventoryUser = Global.UserCode;
			}
			String inventoryRemark = !bHandInputMode ? "ɨ������" : "¼������";
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			String date = sDateFormat.format(new java.util.Date());
			db.UpdateAssetInventory(barcode, inventoryUser, date,
					inventoryRemark);

			Toast.makeText(getApplication(), "�ʲ�����[" + barcode + "]�̵�ɹ���", 2000)
					.show();
		}

	}

	// ���²˵�
	private OnClickListener bt_menuLeftClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				if (set_lvleft) {
					allmenu.setVisibility(View.GONE);
					llmenu.setVisibility(View.GONE);
					rleft.setVisibility(View.INVISIBLE);

				} else {
					allmenu.setVisibility(View.VISIBLE);
					llmenu.setVisibility(View.VISIBLE);
					rleft.setVisibility(View.VISIBLE);
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
				if (set_lvright) {
					allmenu.setVisibility(View.GONE);
					llmenu.setVisibility(View.GONE);
					rright.setVisibility(View.INVISIBLE);

				} else {
					allmenu.setVisibility(View.VISIBLE);
					llmenu.setVisibility(View.VISIBLE);
					rright.setVisibility(View.VISIBLE);
				}
				set_lvright = !set_lvright;
			} catch (Exception e) {
				e.toString();
			}
		}
	};

	// ���²˵�ѡ���¼�

	private OnItemClickListener lv_menuLeft_Click = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			switch (arg2) {
			case 0:// ����ɨ��
				BlankOut();
				break;
			case 1:// ��ϸ����
				try {
					Go_FrmAsset_Detail();
				} catch (Exception e) {
					Toast.makeText(getApplication(), "����ϸ�����쳣��", 2000).show();
				}
				break;
			case 2:// �̵����
				try {
					Intent intent = new Intent(FrmAssetActivity.this,
							FrmAsset_StateActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplication(), "����ϸ�����쳣��", 2000).show();
				}
				break;
			case 3:// ��������
				try {
					Intent intent = new Intent(FrmAssetActivity.this,
							FrmAsset_DownloadActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplication(), "�����������쳣��", 2000).show();
				}
				break;
			case 4: // ����ϴ�
				try {
					Intent intent = new Intent(FrmAssetActivity.this,
							FrmAsset_UploadActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplication(), "�����������쳣��", 2000).show();
				}
				break;
			default:
				break;
			}
			allmenu.setVisibility(View.GONE);
			llmenu.setVisibility(View.GONE);
			rleft.setVisibility(View.INVISIBLE);
			set_lvleft = !set_lvleft;
		}

	};

	private OnItemClickListener lv_menuRight_Click = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			switch (arg2) {
			case 0:// ¼��ģʽ
				bHandInputMode = true;
				txtBarcode.setBackgroundColor(Color.YELLOW);
				break;
			case 1:// ɨ��ģʽ
				bHandInputMode = false;
				txtBarcode.setBackgroundColor(Color.WHITE);
				break;
			default:
				break;
			}
			allmenu.setVisibility(View.GONE);
			llmenu.setVisibility(View.GONE);
			rright.setVisibility(View.INVISIBLE);

			set_lvright = !set_lvright;
		}

	};

	private void SetMenuLeft() {
		try {
			listmenuLeft = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("menuname", "����ɨ��");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "��ϸ����");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "�̵����");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "��������");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "����ϴ�");
			listmenuLeft.add(map);

			String[] fromColumns = new String[] { "menuname" };
			int[] toLayoutIDs = new int[] { R.id.tv_menu };
			MenuAdapterLeft = new SimpleAdapter(this, listmenuLeft,
					R.layout.workmenu_model, fromColumns, toLayoutIDs);
			lv_menuLeft.setAdapter(MenuAdapterLeft);

		} catch (Exception e) {
		}

	}

	private void SetMenuRight() {
		try {
			listmenuRight = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("menuname", "¼��ģʽ");
			listmenuRight.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "ɨ��ģʽ");
			listmenuRight.add(map);

			String[] fromColumns = new String[] { "menuname" };
			int[] toLayoutIDs = new int[] { R.id.tv_menu };
			MenuAdapterRight = new SimpleAdapter(this, listmenuRight,
					R.layout.workmenu_model, fromColumns, toLayoutIDs);
			lv_menuRight.setAdapter(MenuAdapterRight);

		} catch (Exception e) {
		}

	}

	// ����ɨ��
	private void BlankOut() {
		try {
			if (this._Model == null) {
				Toast.makeText(getApplication(), "��ǰû����Ҫ�������̵��¼��", 2000).show();
				return;
			}
			new AlertDialog.Builder(FrmAssetActivity.this)
					.setTitle("ϵͳ��ʾ")
					.setMessage("ȷ��Ҫ������ǰ���̵��¼��")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									db.UpdateAssetInventory(_Model.Barcode, "",
											"", "");
									cleanText();
									Toast.makeText(getApplication(), "�����ɹ���",
											2000).show();

								}
							})
					.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();
		} catch (Exception ex) {
			Toast.makeText(getApplication(), "�����쳣��" + ex.getMessage(), 2000)
					.show();
		}

	}

	private void cleanText() {
		txtBarcode.setText("");
		imgPhoto.setImageBitmap(null);
		txtCode.setText("");
		txtName.setText("");
		txtDept.setText("");
		txtLocation.setText("");
		txtStartDate.setText("");
	}

	private void Go_FrmAsset_Detail() {
		try {
			if (this._Model == null) {
				Toast.makeText(getApplication(), "����ɨ�裬Ȼ��鿴�ù̶��ʲ�����ϸ���ϡ�", 2000)
						.show();
				return;
			}
			try {
				Intent intent = new Intent(FrmAssetActivity.this,
						FrmAsset_DetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("_Model", (Serializable) _Model);
				intent.putExtras(bundle);
				startActivity(intent);
			} catch (Exception e) {
				e.toString();
			}
		} catch (Exception ex) {
			Toast.makeText(getApplication(), "�����쳣��" + ex.getMessage(), 2000)
					.show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			// txtBarcode.setText("");
			txtBarcode.setFocusable(true);
			// txtBarcode.setFocusableInTouchMode(false);
			txtBarcode.requestFocus();
			break;
		}
	}

}
