package com.br.salespda.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.br.salespda.R;
import com.br.salespda.Crash.CrashApplication;
import com.br.salespda.basic.CItem;
import com.br.salespda.basic.SpecialSimpleAdapter;
import com.br.salespda.basic.Packing.CustomerResult;
import com.br.salespda.basic.Packing.FirstCheckBillListResult;
import com.br.salespda.basic.Packing.FirstCheckBillResult;
import com.br.salespda.basic.Packing.StorehouseResult;
import com.br.salespda.common.CommonMethord;
import com.br.salespda.webservice.WebServiceManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class FrmReceivingBill_FirstCheck_Bill extends Activity {

	CrashApplication userInfo;
	CustomerResult _Customer;// 用户选择的经销商
	String _CorporationID;// 用户所属组织机构
	String _CustomerID;// 用户所属组织机构

	ListView lvbillUnCheck;// 未复检未打印
	ListView lvbillCheck;// 已复检未打印
	TextView tvCustomer;
	RadioGroup radioGroup;
	RadioButton rdobtFstChk;// 未复检未打印
	RadioButton rdobtSecChk;// 已复检未打印
	WebServiceManager service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_frm_receiving_bill_firstcheck_bill);

		// 初始化控件
		initCtl();
		initLinstener();
		
		service = new WebServiceManager();
		userInfo = (CrashApplication) getApplicationContext();
		_Customer = userInfo.getUserCustomer();
		if (_Customer != null) {
			_CustomerID = _Customer.ID;
			tvCustomer.setText(_Customer.CustomerCode + " "
					+ _Customer.CustomerName);
		}
		_CorporationID = userInfo.getUserCorporationID();

		// 获取初检单
		List<FirstCheckBillResult> firstCheckBillList = getFirstCheckBill(false);

		// 绑定初检单到listview
		initListViewUnCheck(firstCheckBillList);

		
	}

	/**
	 * 初始化复检单 [未复检未打印]
	 * 
	 * @param firstCheckBillList
	 */
	private void initListViewUnCheck(
			List<FirstCheckBillResult> firstCheckBillList) {

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		for (FirstCheckBillResult item : firstCheckBillList) {
			String tvBillID = item.BillID;
			String tvBusinessType = item.ReceivingBillTypeName;
			String tvPackName = item.PackingName;
			String tvCarNo = item.TruckNO;
			String tvTransType = item.ReturnMethod;
			String tvFirstCheckUser = item.FirstCheckUser;
			String tvFirstCheckDate = item.FirstCheckDate;
			String tvRemark = item.Remark;

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tvBillID", tvBillID);
			map.put("tvBusinessType", tvBusinessType);
			map.put("tvPackName", tvPackName);
			map.put("tvCarNo", tvCarNo);
			map.put("tvTransType", tvTransType);
			map.put("tvFirstCheckUser", tvFirstCheckUser);
			map.put("tvFirstCheckDate", tvFirstCheckDate);
			map.put("tvRemark", tvRemark);
			map.put("tag",item);
			listItem.add(map);
		}

		SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listItem,// 需要绑定的数据
				R.layout.layout_frm_receiving_bill_firstcheck_bill_item,// 每一行的布局
				new String[] { "tvBillID","tvBusinessType", "tvPackName", "tvCarNo",
						"tvTransType", "tvFirstCheckUser", "tvFirstCheckDate",
						"tvRemark" }, new int[] { 
						R.id.tvBillID,R.id.tvBusinessType,
						R.id.tvPackName, R.id.tvCarNo, R.id.tvTransType,
						R.id.tvFirstCheckUser, R.id.tvFirstCheckDate,
						R.id.tvRemark });
		lvbillUnCheck.setAdapter(mSimpleAdapter);
	}

	/**
	 * 初始化复检单 [已复检未打印]
	 * 
	 * @param firstCheckBillList
	 */
	private void initListViewCheck(List<FirstCheckBillResult> firstCheckBillList) {

		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

		for (FirstCheckBillResult item : firstCheckBillList) {
			String tvBillID = item.BillID;
			String tvBusinessType = item.ReceivingBillTypeName;
			String tvPackName = item.PackingName;
			String tvCarNo = item.TruckNO;
			String tvTransType = item.ReturnMethod;
			String tvFirstCheckUser = item.FirstCheckUser;
			String tvFirstCheckDate = item.FirstCheckDate;
			String tvRemark = item.Remark;

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tvBillID", tvBillID);
			map.put("tvBusinessType", tvBusinessType);
			map.put("tvPackName", tvPackName);
			map.put("tvCarNo", tvCarNo);
			map.put("tvTransType", tvTransType);
			map.put("tvFirstCheckUser", tvFirstCheckUser);
			map.put("tvFirstCheckDate", tvFirstCheckDate);
			map.put("tvRemark", tvRemark);

			listItem.add(map);
		}

		SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listItem,// 需要绑定的数据
				R.layout.layout_frm_receiving_bill_firstcheck_bill_item,// 每一行的布局
				new String[] { "tvBillID", "tvBusinessType", "tvPackName",
						"tvCarNo", "tvTransType", "tvFirstCheckUser",
						"tvFirstCheckDate", "tvRemark" }, new int[] {
						R.id.tvBillID, R.id.tvBusinessType, R.id.tvPackName,
						R.id.tvCarNo, R.id.tvTransType, R.id.tvFirstCheckUser,
						R.id.tvFirstCheckDate, R.id.tvRemark });
		lvbillCheck.setAdapter(mSimpleAdapter);
	}

	/**
	 * 获取客户初检单【未复检未打印】
	 * 
	 * @param IsSecondCheck
	 *            是否已复检,已复检=true,未复检=false
	 * @return
	 */
	private List<FirstCheckBillResult> getFirstCheckBill(Boolean IsSecondCheck) {

		FirstCheckBillListResult billList = service.GetFirstCheckBill(userInfo,
				_CustomerID, _CorporationID, IsSecondCheck);

		List<FirstCheckBillResult> firstCheckBillList = null;
		if (billList != null) {
			firstCheckBillList = billList.FirstCheckBillResults;
		}
		return firstCheckBillList;
	}

	/**
	 * 初始化控件
	 */
	private void initCtl() {

		radioGroup = (RadioGroup) findViewById(R.id.radioGroupID);
		lvbillUnCheck = (ListView) findViewById(R.id.lvbillUnCheck);
		lvbillCheck = (ListView) findViewById(R.id.lvbillCheck);
		tvCustomer = (TextView) findViewById(R.id.tvCustomer);
		rdobtFstChk = (RadioButton) findViewById(R.id.rdobtFstChk);// 未复检未打印
		rdobtSecChk = (RadioButton) findViewById(R.id.rdobtSecChk);// 已复检未打印
	}

	/**
	 * 初始化事件
	 */
	private void initLinstener() {

		OnCheckedChangeListener radioGroup_OnClick = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == rdobtFstChk.getId()) {

					lvbillUnCheck.setVisibility(View.VISIBLE);
					lvbillCheck.setVisibility(View.GONE);
					// 获取初检单
					List<FirstCheckBillResult> firstCheckBillList = getFirstCheckBill(false);
					// 绑定初检单到listview
					initListViewUnCheck(firstCheckBillList);

				} else if (checkedId == rdobtSecChk.getId()) {

					lvbillUnCheck.setVisibility(View.GONE);
					lvbillCheck.setVisibility(View.VISIBLE);

					// 获取初检单
					List<FirstCheckBillResult> firstCheckBillList = getFirstCheckBill(true);
					// 绑定初检单到listview
					initListViewCheck(firstCheckBillList);
				}

			}
		};
		radioGroup.setOnCheckedChangeListener(radioGroup_OnClick);

	  
		OnItemClickListener lvbillUnCheck_OnSelected = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
	 	  
				// 获得选中项的HashMap对象
				HashMap<String, String> map = (HashMap<String, String>) lvbillUnCheck
						.getItemAtPosition(arg2);
				String BillID = map.get("tvBillID");
				Object tag = map.get("tag");
				FirstCheckBillResult billResult = null;
				if(tag != null)
				{
					billResult = (FirstCheckBillResult)tag;
				}

				Intent intent = new Intent(
						FrmReceivingBill_FirstCheck_Bill.this,
						FrmReceivingBill_SecondCheck_Bill.class);
				intent.putExtra("BillID", BillID);
				intent.putExtra("BillTag",billResult);
				startActivity(intent);
				
			}
		};
		lvbillUnCheck.setOnItemClickListener(lvbillUnCheck_OnSelected );
	}
}
