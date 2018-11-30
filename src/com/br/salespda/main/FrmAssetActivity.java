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

	boolean bHandInputMode = false;// 手工录入模式

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

		bt_menuLeft.setText("操作");
		bt_menuRight.setText("模式");

		tvtitle.setText("资产盘点");
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

				// 扫描模式
				if (!bHandInputMode) {
					if (barcode.length() >= 8) {
						if (barcode.length() == 0) {
							new AlertDialog.Builder(FrmAssetActivity.this)
									.setTitle("系统提示")// 设置对话框标题
									.setMessage("条码不能为空，请录入条码数字。")// 设置显示的内容
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {// 添加确定按钮
												public void onClick(
														DialogInterface dialog,
														int which) {// 确定按钮的响应事件

												}
											}).show();// 在按键响应事件中显示此对话框
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
				else//手输入模式
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
							Toast.makeText(getApplicationContext(), "条码格式不正确!", 3000).show();
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
	 * 原onkey方法 // public boolean onKey(View v, int keyCode, KeyEvent event) //
	 * { // try // { // String barcode = txtBarcode.getText().toString().trim();
	 * // if ((keyCode == 140 || keyCode == 141) && event.getAction() ==
	 * KeyEvent.ACTION_DOWN) // { // txtBarcode.setText(""); // } // if (keyCode
	 * == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
	 * // { // if(barcode.length() == 0) // { // new
	 * AlertDialog.Builder(FrmAssetActivity.this).setTitle("系统提示")//设置对话框标题 //
	 * .setMessage("条码不能为空，请录入条码数字。")//设置显示的内容 // .setPositiveButton("确定",new
	 * DialogInterface.OnClickListener() {//添加确定按钮 // public void
	 * onClick(DialogInterface dialog, int which) {//确定按钮的响应事件 // // } //
	 * }).show();//在按键响应事件中显示此对话框 // } // else // { // DoWork(barcode); // } //
	 * Intent intent = new Intent(FrmAssetActivity.this, //
	 * frm_ScanBarcodeActivity.class); // startActivityForResult(intent, 1); //
	 * // txtBarcode.requestFocus(); // return true; // } // else // { //
	 * txtBarcode.requestFocus(); // return false; // } // } // catch(Exception
	 * ex) // { // return false; // } // // } };
	 */

	private void DoWork(String barcode) {
		if (barcode == null || barcode.length() == 0) {
			Toast.makeText(getApplication(), "读取条码失败,请重试！", 2000).show();
			return;
		}
		AssetDO asset = db.GetAsset(barcode);
		if (asset == null) {
			Toast.makeText(getApplication(), "资产档案[" + barcode + "]不存在！", 2000)
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

			// 将获取的数据转换成drawable
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
					"资产档案[" + barcode + "]已盘点，无需重复操作！", 2000).show();

		} else {
			String inventoryUser = "";
			if (Global.UserCode == null || Global.UserCode.trim().length() == 0) {
				CrashApplication userinfo = (CrashApplication) getApplicationContext();
				inventoryUser = userinfo.getUserCode();
			} else {
				inventoryUser = Global.UserCode;
			}
			String inventoryRemark = !bHandInputMode ? "扫描条码" : "录入条码";
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			String date = sDateFormat.format(new java.util.Date());
			db.UpdateAssetInventory(barcode, inventoryUser, date,
					inventoryRemark);

			Toast.makeText(getApplication(), "资产档案[" + barcode + "]盘点成功！", 2000)
					.show();
		}

	}

	// 左下菜单
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

	// 右下菜单
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

	// 左下菜单选择事件

	private OnItemClickListener lv_menuLeft_Click = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			switch (arg2) {
			case 0:// 撤销扫描
				BlankOut();
				break;
			case 1:// 详细资料
				try {
					Go_FrmAsset_Detail();
				} catch (Exception e) {
					Toast.makeText(getApplication(), "打开详细资料异常：", 2000).show();
				}
				break;
			case 2:// 盘点进度
				try {
					Intent intent = new Intent(FrmAssetActivity.this,
							FrmAsset_StateActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplication(), "打开详细资料异常：", 2000).show();
				}
				break;
			case 3:// 数据下载
				try {
					Intent intent = new Intent(FrmAssetActivity.this,
							FrmAsset_DownloadActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplication(), "打开数据下载异常：", 2000).show();
				}
				break;
			case 4: // 结果上传
				try {
					Intent intent = new Intent(FrmAssetActivity.this,
							FrmAsset_UploadActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplication(), "打开数据下载异常：", 2000).show();
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
			case 0:// 录入模式
				bHandInputMode = true;
				txtBarcode.setBackgroundColor(Color.YELLOW);
				break;
			case 1:// 扫描模式
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
			map.put("menuname", "撤销扫描");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "详细资料");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "盘点进度");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "数据下载");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "结果上传");
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
			map.put("menuname", "录入模式");
			listmenuRight.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "扫描模式");
			listmenuRight.add(map);

			String[] fromColumns = new String[] { "menuname" };
			int[] toLayoutIDs = new int[] { R.id.tv_menu };
			MenuAdapterRight = new SimpleAdapter(this, listmenuRight,
					R.layout.workmenu_model, fromColumns, toLayoutIDs);
			lv_menuRight.setAdapter(MenuAdapterRight);

		} catch (Exception e) {
		}

	}

	// 撤销扫描
	private void BlankOut() {
		try {
			if (this._Model == null) {
				Toast.makeText(getApplication(), "当前没有需要撤销的盘点记录。", 2000).show();
				return;
			}
			new AlertDialog.Builder(FrmAssetActivity.this)
					.setTitle("系统提示")
					.setMessage("确定要撤销当前的盘点记录吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									db.UpdateAssetInventory(_Model.Barcode, "",
											"", "");
									cleanText();
									Toast.makeText(getApplication(), "操作成功！",
											2000).show();

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).show();
		} catch (Exception ex) {
			Toast.makeText(getApplication(), "操作异常！" + ex.getMessage(), 2000)
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
				Toast.makeText(getApplication(), "请先扫描，然后查看该固定资产的详细资料。", 2000)
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
			Toast.makeText(getApplication(), "操作异常。" + ex.getMessage(), 2000)
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
