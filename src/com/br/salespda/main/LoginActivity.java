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
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.br.salespda.R;
import com.br.salespda.Crash.CrashApplication;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.ShowAlertDialog;
import com.br.salespda.common.Global;
import com.br.salespda.db.DBOpenHelper_Config;
import com.br.salespda.manager.DbManager_Config;
import com.br.salespda.manager.DeviceManager;
import com.br.salespda.webservice.SoapControl;

public class LoginActivity extends BaseActivity {
	Button btlogin;
	Button btexit;
	EditText cmbUserCode;
	EditText txtPassword;
	CheckBox chkRemember;
	TextView TxtVersion;
	Spinner cmbCorporation;
	private String version = "0.00";
	private ProgressDialog loginDialog;

	public Button bt_menuLeft;// 左下菜单按钮
	public Button bt_menuRight;// 右下菜单按钮

	public ListView lv_menuLeft;// 左下菜单
	public ListView lv_menuRight;// 右下菜单

	public SimpleAdapter MenuAdapterLeft;// 左下菜单
	public ArrayList<HashMap<String, Object>> listmenuLeft;

	public SimpleAdapter MenuAdapterRight;// 右下菜单
	public ArrayList<HashMap<String, Object>> listmenuRight;

	boolean set_lvleft = false;// 左下菜单显示隐藏

	Handler cwjHandler = null;
	final Runnable mUpdateResults = new Runnable() {
		public void run() {

			loginDialog.cancel();
			if (loginDialog != null && loginDialog.isShowing()) {
				loginDialog.dismiss();
			}
			LoginSystem();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		cwjHandler = new Handler();
		try {
			tvtitle = (TextView) findViewById(R.id.tvtitle);
			tvtitle.setText("企业信息管理平台");

			cmbUserCode = (EditText) findViewById(R.id.cmbUserCode);
			txtPassword = (EditText) findViewById(R.id.txtPassword);
			btlogin = (Button) findViewById(R.id.btlogin);
			btexit = (Button) findViewById(R.id.btexit);
			chkRemember = (CheckBox) findViewById(R.id.chkRemember);
			TxtVersion = (TextView) findViewById(R.id.TxtVersion);

			initial();

			btlogin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					DoLogin(true);
				}
			});

			btexit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});

			String version = this.getPackageManager().getPackageInfo(
					"com.br.salespda", 0).versionName;
			TxtVersion.setText(version);
			 
			try {
				checkAppVersion();
			} catch (Exception ex) {
				Toast.makeText(getApplication(), "异常：" + ex.getMessage(), 2000)
						.show();
			}

		} catch (Exception ex) {
			Toast.makeText(getApplication(), "登录异常:" + ex.getMessage(), 2000)
					.show();
		}

	}

	private void initial() {
		bt_menuLeft = (Button) findViewById(R.id.bt_menuLeft);
		bt_menuRight = (Button) findViewById(R.id.bt_menuRight);
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		lv_menuLeft = (ListView) findViewById(R.id.lv_menuLeft);
		lv_menuRight = (ListView) findViewById(R.id.lv_menuRight);
		cmbCorporation = (Spinner)findViewById(R.id.cmbCorporation);

		bt_menuLeft.setText("菜单");
		// bt_menuRight.setText("退出");
		tvtitle.setText("企业信息管理平台");

		bt_menuRight.setVisibility(View.GONE);

		SetMenuLeft();

		bt_menuLeft.setOnClickListener(bt_menuLeftClick);
		lv_menuLeft.setOnItemClickListener(lv_menuLeftclick);
		
		//初始化全局变量
		CrashApplication userinfo = (CrashApplication)getApplicationContext();
		
		String param = db_cfg.GetParam("WebServiceURL");
		if (param != null && param.length() > 0) {
			Global.WebServiceUrl = param; 
			Global.updateFileString = param.replace("CRBPdaLNService.asmx", "AppFile/BR.SalesPDA.apk");
			userinfo.setWebServiceUrl(param);
		}

		String param1 = db_cfg.GetParam("DbServerName");
		if (param1 != null && param1.length() > 0) {
			Global.dbServer = param1;
			userinfo.setDbServer(param1);
		}

		DeviceManager.getDeviceId(getApplicationContext());
		
		//initCorporation();

	}

	// 左下菜单
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

	private void SetMenuLeft() {
		try {
			listmenuLeft = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("menuname", "系统设置");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "在线升级");
			listmenuLeft.add(map);
			map = new HashMap<String, Object>();
			map.put("menuname", "关于");
			listmenuLeft.add(map);

			String[] fromColumns = new String[] { "menuname" };
			int[] toLayoutIDs = new int[] { R.id.tv_menu };
			MenuAdapterLeft = new SimpleAdapter(this, listmenuLeft,
					R.layout.workmenu_model, fromColumns, toLayoutIDs);
			lv_menuLeft.setAdapter(MenuAdapterLeft);

		} catch (Exception e) {
		}

	}

	// 左下菜单单击事件
	private OnItemClickListener lv_menuLeftclick = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch (arg2) {
			// 系统设置
			case 0:
				try {
					Intent intent = new Intent(LoginActivity.this,
							FrmMain_SettingActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplication(), "异常：" + e.getMessage(),
							2000).show();
				}
				break;
			// 在线升级
			case 1:
				try {
					checkAppVersion();
				} catch (Exception ex) {
					Toast.makeText(getApplication(), "异常：" + ex.getMessage(),
							2000).show();
				}
				break;
			// 关于
			case 2:
				break;
			}
			lv_menuLeft.setVisibility(View.GONE);
			set_lvleft = !set_lvleft;
		}
	};

	// 检查程序更新
	private void checkAppVersion() {
		// 获取版本信息
		try {
			String version = this.getPackageManager().getPackageInfo(
					"com.br.salespda", 0).versionName;
			Global.Version = version;
			
			CrashApplication userInfo = (CrashApplication) getApplicationContext();
			userInfo.setClientVersion(version);

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
					Toast.makeText(getApplication(), "当前版本已经是最新，无需升级。", 2000)
							.show();
					return;
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							LoginActivity.this)
							.setTitle("系统提示")
							.setMessage("检测到当前存在新版本，是否升级?")
							.setCancelable(false)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											downFile();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});
					builder.show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "获取程序版本号完毕！", 2000)
						.show();
			}
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
			Toast.makeText(getApplicationContext(), "获取程序版本异常！", 2000).show();
		}
	}

	void downFile() {
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("正在下载更新,请稍后...");
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
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()
						.toString() + "/BR.SalesPDA.apk")),
				"application/vnd.android.package-archive");
		startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
		 
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

	private void DoLogin(boolean isOnline) {
		final String userCode = cmbUserCode.getText().toString();
		final String text = txtPassword.getText().toString();
		boolean isPwdRemember = chkRemember.isChecked();
		if (userCode == null || userCode.length() == 0) {
			this.cmbUserCode.setFocusable(true);
			Toast.makeText(getApplicationContext(), "用户名不能为空。", 2000).show();
		}
		if (text == null || text.length() == 0) {
			this.txtPassword.setFocusable(true);
			Toast.makeText(getApplicationContext(), "密码不能为空。", 2000).show();
		}
		saveLogin(userCode, text);

		loadProgressBar();
		new Thread() {
			public void run() {
				LoginThead(userCode, text);
				cwjHandler.post(mUpdateResults);
			}

		}.start();

	}

	private void loadProgressBar() {
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("正在登录请稍等...");
		loginDialog.setCancelable(false);
		loginDialog.show();
	}

	private void LoginSystem() {

		if (isLogin) {
			try {
				Intent intent = new Intent(LoginActivity.this,
						FrmMainActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				e.toString();
			}
		} else {
			new AlertDialog.Builder(LoginActivity.this)
					.setTitle("系统提示")
					// 设置对话框标题
					.setMessage("" + ErrMsg)
					// 设置显示的内容
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {// 添加确定按钮
								public void onClick(DialogInterface dialog,
										int which) {// 确定按钮的响应事件

								}
							}).show();// 在按键响应事件中显示此对话框
		}

	}

	// 记录用户名密码
	private void saveLogin(String userCode, String text) {
		boolean isPwdRemember = chkRemember.isChecked();
		SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
		if (isPwdRemember) {
			SharedPreferences.Editor edit = remdname.edit();
			edit.putString("name", userCode);
			edit.putString("pass", text);
			edit.putBoolean("check", true);
			edit.commit();
		} else {
			SharedPreferences.Editor edit = remdname.edit();
			edit.putString("name", "");
			edit.putString("pass", "");
			edit.putBoolean("check", false);
			edit.commit();
		}
	}

	// 从配置文件中读取用户名密码
	@Override
	protected void onResume() {
		SharedPreferences remdname = getPreferences(Activity.MODE_PRIVATE);
		cmbUserCode.setText(remdname.getString("name", ""));
		txtPassword.setText(remdname.getString("pass", ""));
		boolean cheked = remdname.getBoolean("check", false);
		if (cheked) {
			chkRemember.setChecked(true);
		} else {
			chkRemember.setChecked(false);
		}
		super.onResume();
	}

	boolean isLogin = false;
	String ErrMsg = null;
	String ID = null;
	String IsAdmin = null;

	private void LoginThead(String userCode, String password) {
		try {
			// string userName, string password, string deviceId, string
			// clientVersion, int dbServer
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", userCode);
			map.put("password", password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass("Login",
					map);

			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					String ID = so.getPropertyAsString("ID");
					String UserCode = so.getPropertyAsString("UserCode");
					String UserName = so.getPropertyAsString("UserName");
//					String Password = so.getPropertyAsString("Password");
					String Remark = so.getPropertyAsString("Remark");
					String IsAdmin = so.getPropertyAsString("IsAdmin");

					Global.ID = ID;
					Global.UserCode = UserCode;
					Global.UserName = UserName;
					Global.Password = password;
					Global.Remark = Remark;
					Global.IsAdmin = IsAdmin;

					CrashApplication userInfo = (CrashApplication) getApplicationContext();
					userInfo.setUserCode(UserCode);
					userInfo.setUserName(UserName);
					userInfo.setPassword(password);
					userInfo.setRemark(Remark);
					userInfo.setUserID(ID);

					isLogin = true;
				} else {
					ErrMsg = so.getPropertyAsString("ErrMsg");
					ID = so.getPropertyAsString("ID");
					IsAdmin = so.getPropertyAsString("IsAdmin");
					isLogin = false;
				}
			}
		} catch (Exception ex) {
			ErrMsg = "异常:" + ex.getMessage();
			isLogin = false;

		}

	}

}
