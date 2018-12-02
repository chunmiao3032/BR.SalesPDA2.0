package com.br.salespda.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.br.salespda.R;
import com.br.salespda.Crash.CrashApplication;
import com.br.salespda.basic.BaseActivity;
import com.br.salespda.basic.CItem;
import com.br.salespda.basic.Packing.CustomerInCorporationResult;
import com.br.salespda.basic.Packing.CustomerResult;
import com.br.salespda.common.UserInfo;
import com.br.salespda.webservice.WebServiceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FrmReceivingBill_CustomerActivity extends BaseActivity {

	String strUI = "";

	NfcAdapter nfcAdapter;
	Intent m_intent;
	Tag m_tagFromIntent;

	TextView tvCustomerID, tvCardID, tvCustomerCode, tvCustomerName, tvErrMsg;
	Button btOK;
	PendingIntent pi;

	WebServiceManager service;
	CrashApplication userInfo;

	boolean IsCustInCorpCheck = false;// �ͻ���������У��,true�ſ��Լ��������򲻿ɼ�������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frm_receiving_bill_customer);

		service = new WebServiceManager();
		userInfo = (CrashApplication) getApplicationContext();

		initCtl();

		// ��ʼ��NFC������
		nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
		if (nfcAdapter == null) {
			tvCustomerCode.setText("");

			return;
		}
		if (!nfcAdapter.isEnabled()) {
			tvCustomerCode.setText("");

			return;
		}

		pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		nfcAdapter.enableForegroundDispatch(this, pi, null, null); // ����}
	}

	// ------------------------------------------NFC------------------------------------------
	/**
	 * nfc�����󴦴���
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		// ��ǰapp����ǰ�˽������У����ʱ����intent���͹�������ôϵͳ�ͻ����onNewIntent�ص���������intent���͹���
		// ����ֻ��Ҫ������������intent�Ƿ���NFC��ص�intent������ǣ��͵��ô�����
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			processIntent(intent);
		}
	}

	// nfc���������߼�
	private void processIntent(Intent intent) {
		// ȡ����װ��intent�е�TAG
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		// ��һ�ַ�ʽ��ȡ����
		String CardId = ByteArrayToHexString(tagFromIntent.getId());

		// �ڶ��ַ�ʽ��ȡ����
		// byte[] myNFCID = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
		// String t = bytesToHexString(myNFCID);

		if (!CardId.equals(null) && CardId.trim().length() > 0) {
			//���ݿ��Ż�ȡ��������Ϣ
			CustomerResult custRlt = service.GetCustomer(userInfo, CardId);
			if (custRlt.Success) {
				
				tvCardID.setText(CardId);
				tvCustomerID.setText(custRlt.IDCardNO);

				tvCustomerCode.setText(custRlt.CustomerCode);
				tvCustomerName.setText(custRlt.CustomerName);

				List<CustomerInCorporationResult> cusInCorRltList = custRlt.CustomerInCorporationResults;
				CrashApplication userInfo = (CrashApplication) getApplicationContext();
				String corpID = userInfo.getUserCorporationID();

				for (CustomerInCorporationResult item : cusInCorRltList) {
					if (item.CorporationID.toString().equals(corpID)) {
						IsCustInCorpCheck = true;
						break;
					}
				}
				//���������û�������֯����������
				if (!IsCustInCorpCheck) {
					String msg = "�ÿͻ����������û����ڹ�����";
					Toast.makeText(getApplicationContext(), msg, 3000).show();
					tvErrMsg.setText(msg);
				}
				else//������У��ɹ� 
				{
					userInfo.setUserCustomer(custRlt);  
					
					btOK.performClick();
				}
			} else {
				String msg = "�쳣:" + custRlt.ErrMsg;
				Toast.makeText(getApplicationContext(), msg, 3000).show();
				tvErrMsg.setText(msg);
			}

		}
	}

	private String ByteArrayToHexString(byte[] inarray) {
		int i, j, in;
		String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F" };
		String out = "";
		for (j = 0; j < inarray.length; ++j) {
			in = (int) inarray[j] & 0xff;
			i = (in >> 4) & 0x0f;
			out += hex[i];
			i = in & 0x0f;
			out += hex[i];
		}
		return out;
	}

	private String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("0x");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			System.out.println(buffer);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}

	private static byte[] stringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	// ------------------------------------------NFC------------------------------------------
	private void initCtl() {

		tvCustomerCode = (TextView) findViewById(R.id.tvCustomerCode);
		tvCustomerName = (TextView) findViewById(R.id.tvCustomerName);
		tvCardID = (TextView) findViewById(R.id.tvCardID);
		tvCustomerID = (TextView) findViewById(R.id.tvCustomerID);
		tvErrMsg = (TextView) findViewById(R.id.tvErrMsg);

		btOK = (Button) findViewById(R.id.btOK);
		btOK.setOnClickListener(btOK_OnClick);

		tvtitle = (TextView) findViewById(R.id.tvtitle);
//		tvtitle.setText("ѡ��Ӧ��");
	}

	private OnClickListener btOK_OnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (IsCustInCorpCheck) {
				
				Intent intent = new Intent(
						FrmReceivingBill_CustomerActivity.this,
						FrmReceivingBill_FirstCheck_Bill.class);
				startActivity(intent);
			} else {
				CustomerResult customer = userInfo.getUserCustomer();
				if(customer == null)
				{
					String msg = "��ѡ��Ӧ�̣�";
					Toast.makeText(getApplicationContext(), "��ѡ��Ӧ�̣�", 3000)
							.show();
					tvErrMsg.setText(msg);
				}
			}
		}
	};

}
