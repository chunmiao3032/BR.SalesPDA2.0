package com.br.salespda.webservice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.Time;
import android.util.Log;

import com.br.salespda.Crash.CrashApplication;
import com.br.salespda.basic.AssetDO;
import com.br.salespda.basic.CItem;
import com.br.salespda.basic.CorporationDO;
import com.br.salespda.basic.DbServerDO;
import com.br.salespda.basic.PackBillResult;
import com.br.salespda.basic.SaleBillDo;
import com.br.salespda.basic.Packing.CustomerInCorporationResult;
import com.br.salespda.basic.Packing.CustomerResult;
import com.br.salespda.basic.Packing.FirstCheckBillListResult;
import com.br.salespda.basic.Packing.FirstCheckBillResult;
import com.br.salespda.basic.Packing.StorehouseListResult;
import com.br.salespda.basic.Packing.StorehouseResult;
import com.br.salespda.basic.Packing.UserInCorporationListResult;
import com.br.salespda.basic.Packing.UserInCorporationResult;
import com.br.salespda.basic.Packing.UserResultPacking;
import com.br.salespda.common.CommonMethord;
import com.br.salespda.common.Global;

public class WebServiceManager {
	
	public WebServiceManager()
	{
		
	}
	/*
	 * 获取发货仓库
	 */
	public static List<CItem> GetAllStorehouse(String storehouseType) {
		List<CItem> lst = new ArrayList<CItem>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			if (storehouseType.toLowerCase().equals("product")
					|| storehouseType.toLowerCase().equals("packing")) {
				SoapObject result = SoapControl.ExecuteWebMethodReturnClass(
						"GetAllStorehouse4Prod", map);
				SoapObject soap_StorehouseResults = (SoapObject) result
						.getProperty("StorehouseResults");
				int ic = soap_StorehouseResults.getPropertyCount();
				for (int i = 0; i < ic; i++) {
					SoapObject soap_StorehouseResult = (SoapObject) soap_StorehouseResults
							.getProperty(i);
					String id = soap_StorehouseResult.getProperty("ID")
							.toString();
					String StorehouseCode = soap_StorehouseResult.getProperty(
							"StorehouseCode").toString();
					String StorehouseName = soap_StorehouseResult.getProperty(
							"StorehouseName").toString();

					CItem item = new CItem(id, StorehouseCode + "|"
							+ StorehouseName, StorehouseCode);
					lst.add(item);
				}
				if (storehouseType.toLowerCase().equals("packing")) {
					SoapObject result1 = SoapControl
							.ExecuteWebMethodReturnClass(
									"GetAllStorehouse4Pack", map);
					SoapObject soap_StorehouseResults1 = (SoapObject) result1
							.getProperty("StorehouseResults");
					int ic1 = soap_StorehouseResults1.getPropertyCount();
					for (int i = 0; i < ic1; i++) {
						SoapObject soap_StorehouseResult = (SoapObject) soap_StorehouseResults1
								.getProperty(i);
						String id = soap_StorehouseResult.getProperty("ID")
								.toString();
						String StorehouseCode = soap_StorehouseResult
								.getProperty("StorehouseCode").toString();
						String StorehouseName = soap_StorehouseResult
								.getProperty("StorehouseName").toString();

						CItem item = new CItem(id, StorehouseCode + "|"
								+ StorehouseName, StorehouseCode);
						lst.add(item);
					}
				}
			}

		} catch (Exception ex) {
			Log.i("001x001", "异常：" + ex.getMessage());
			return lst;
		}
		return lst;
	}

	public static SaleBillDo TakeoutSalesBill(String barcode,
			String storehouseId) {
		SaleBillDo _CurrentBill = new SaleBillDo();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("barcode", barcode);
			map.put("storehouseId", storehouseId);
			map.put("productBatchNO", "");
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					"TakeoutSalesBill", map);

			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					String ID = "";
					String CustomerID = "";
					String ProductID = "";
					String SalesBillNO = "";
					String Count = "";
					String MakeDate = "";
					String MakeUser = "";
					String Remark = "";
					String CustomerCode = "";
					String CustomerName = "";
					String ProductCode = "";
					String ProductName = "";
					String ProductShortName = "";
					String Character = "";
					String TruckNO = "";
					String NumPerUnit = "";

					if (so.hasProperty("ID")) {
						ID = so.getPropertyAsString("ID");
					}
					if (so.hasProperty("CustomerID")) {
						CustomerID = so.getPropertyAsString("CustomerID");
					}
					if (so.hasProperty("ProductID")) {
						ProductID = so.getPropertyAsString("ProductID");
					}
					if (so.hasProperty("SalesBillNO")) {
						SalesBillNO = so.getPropertyAsString("SalesBillNO");
					}
					if (so.hasProperty("Count")) {
						Count = so.getPropertyAsString("Count");
					}
					if (so.hasProperty("MakeDate")) {
						MakeDate = so.getPropertyAsString("MakeDate");
					}
					if (so.hasProperty("MakeUser")) {
						MakeUser = so.getPropertyAsString("MakeUser");
					}
					if (so.hasProperty("Remark")) {
						Remark = so.getPropertyAsString("Remark");
					}
					if (so.hasProperty("CustomerCode")) {
						CustomerCode = so.getPropertyAsString("CustomerCode");
					}
					if (so.hasProperty("CustomerName")) {
						CustomerName = so.getPropertyAsString("CustomerName");
					}
					if (so.hasProperty("ProductCode")) {
						ProductCode = so.getPropertyAsString("ProductCode");
					}
					if (so.hasProperty("ProductName")) {
						ProductName = so.getPropertyAsString("ProductName");
					}
					if (so.hasProperty("ProductShortName")) {
						ProductShortName = so
								.getPropertyAsString("ProductShortName");
						if (ProductShortName.contains("anyType{}")) {
							ProductShortName = so.getPropertyAsString(
									"ProductName").substring(0, 10);
						}
					}
					if (so.hasProperty("Character")) {
						Character = so.getPropertyAsString("Character");
					}
					if (so.hasProperty("TruckNO")) {
						TruckNO = so.getPropertyAsString("TruckNO");
					}
					if (so.hasProperty("NumPerUnit")) {
						NumPerUnit = so.getPropertyAsString("NumPerUnit");
					}

					_CurrentBill.ID = ID;
					_CurrentBill.CustomerID = CustomerID;
					_CurrentBill.ProductID = ProductID;
					_CurrentBill.SalesBillNO = SalesBillNO;
					_CurrentBill.Count = Count;
					_CurrentBill.MakeDate = MakeDate;
					_CurrentBill.MakeUser = MakeUser;
					_CurrentBill.Remark = Remark;
					_CurrentBill.CustomerCode = CustomerCode;
					_CurrentBill.CustomerName = CustomerName;
					_CurrentBill.ProductCode = ProductCode;
					_CurrentBill.ProductName = ProductName;
					_CurrentBill.ProductShortName = ProductShortName;
					_CurrentBill.Character = Character;
					_CurrentBill.TruckNO = TruckNO;
					_CurrentBill.NumPerUnit = NumPerUnit;
				} else if (Success.equals("false")) {
					_CurrentBill.ErrMsg = "异常:"
							+ so.getPropertyAsString("ErrMsg");
				}

			} else if (so == null) {
				_CurrentBill.ErrMsg = "没有获取到信息,请重试!";
			}
		} catch (Exception ex) {
			_CurrentBill.ErrMsg = "异常:" + ex.getMessage();
			return _CurrentBill;
		}
		return _CurrentBill;
	}

	public static PackBillResult TakeoutPackBill(String barcode,
			String storehouseId) {
		PackBillResult _CurrentBill = new PackBillResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("barcode", barcode);
			map.put("storehouseId", storehouseId);
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					"TakeoutPackingBill", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					String ID = "";
					String CustomerID = "";
					String PackingID = "";
					String SalesBillNO = "";
					String Count = "";
					String MakeDate = "";
					String MakeUser = "";
					String Remark = "";
					String CustomerCode = "";
					String CustomerName = "";
					String PackingCode = "";
					String PackingName = "";
					String TruckNO = "";

					if (so.hasProperty("ID")) {
						ID = so.getPropertyAsString("ID");
					}
					if (so.hasProperty("CustomerID")) {
						CustomerID = so.getPropertyAsString("CustomerID");//
					}
					if (so.hasProperty("PackingID")) {
						PackingID = so.getPropertyAsString("PackingID");//
					}
					if (so.hasProperty("SalesBillNO")) {
						SalesBillNO = so.getPropertyAsString("SalesBillNO");//
					}
					if (so.hasProperty("Count")) {
						Count = so.getPropertyAsString("Count");//
					}
					if (so.hasProperty("MakeDate")) {
						MakeDate = so.getPropertyAsString("MakeDate");//
					}
					if (so.hasProperty("MakeUser")) {
						MakeUser = so.getPropertyAsString("MakeUser");//
					}
					if (so.hasProperty("Remark")) {
						Remark = so.getPropertyAsString("Remark");//
					}
					if (so.hasProperty("CustomerCode")) {
						CustomerCode = so.getPropertyAsString("CustomerCode");//
					}
					if (so.hasProperty("CustomerName")) {
						CustomerName = so.getPropertyAsString("CustomerName");//
					}
					if (so.hasProperty("PackingCode")) {
						PackingCode = so.getPropertyAsString("PackingCode");//
					}
					if (so.hasProperty("PackingName")) {
						PackingName = so.getPropertyAsString("PackingName"); //
					}
					if (so.hasProperty("TruckNO")) {
						TruckNO = so.getPropertyAsString("TruckNO");//
					}

					_CurrentBill.ID = ID;
					_CurrentBill.CustomerID = CustomerID;
					_CurrentBill.PackingID = PackingID;
					_CurrentBill.SalesBillNO = SalesBillNO;
					_CurrentBill.Count = Count;
					_CurrentBill.MakeDate = MakeDate;
					_CurrentBill.MakeUser = MakeUser;
					_CurrentBill.Remark = Remark;
					_CurrentBill.CustomerCode = CustomerCode;
					_CurrentBill.CustomerName = CustomerName;
					_CurrentBill.PackingCode = PackingCode;
					_CurrentBill.PackingName = PackingName;
					_CurrentBill.TruckNO = TruckNO;
				} else if (Success.equals("false")) {
					_CurrentBill.ErrMsg = so.getPropertyAsString("ErrMsg");
				}

			} else if (so == null) {
				_CurrentBill.ErrMsg = "没有获取到信息，请重试!";
			}
		} catch (Exception ex) {
			_CurrentBill.ErrMsg = "异常:" + ex.getMessage();
			return _CurrentBill;
		}
		return _CurrentBill;
	}

	public static SaleBillDo GetSalesBill(String barcode) {

		SaleBillDo _CurrentBill = new SaleBillDo();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("barcode", barcode);
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					"GetSalesBill", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					String ID = "";
					String CustomerID = "";
					String ProductID = "";
					String SalesBillNO = "";
					String Count = "";
					String MakeDate = "";
					String MakeUser = "";
					String Remark = "";
					String CustomerCode = "";
					String CustomerName = "";
					String ProductCode = "";
					String ProductName = "";
					String ProductShortName = "";
					String Character = "";
					String TruckNO = "";
					String NumPerUnit = "";

					if (so.hasProperty("ID")) {
						ID = so.getPropertyAsString("ID");
					}
					if (so.hasProperty("CustomerID")) {
						CustomerID = so.getPropertyAsString("CustomerID");
					}
					if (so.hasProperty("ProductID")) {
						ProductID = so.getPropertyAsString("ProductID");
					}
					if (so.hasProperty("SalesBillNO")) {
						SalesBillNO = so.getPropertyAsString("SalesBillNO");
					}
					if (so.hasProperty("Count")) {
						Count = so.getPropertyAsString("Count");
					}
					if (so.hasProperty("MakeDate")) {
						MakeDate = so.getPropertyAsString("MakeDate");
					}
					if (so.hasProperty("MakeUser")) {
						MakeUser = so.getPropertyAsString("MakeUser");
					}
					if (so.hasProperty("Remark")) {
						Remark = so.getPropertyAsString("Remark");
					}
					if (so.hasProperty("CustomerCode")) {
						CustomerCode = so.getPropertyAsString("CustomerCode");
					}
					if (so.hasProperty("CustomerName")) {
						CustomerName = so.getPropertyAsString("CustomerName");
					}
					if (so.hasProperty("ProductCode")) {
						ProductCode = so.getPropertyAsString("ProductCode");
					}
					if (so.hasProperty("ProductName")) {
						ProductName = so.getPropertyAsString("ProductName");
					}
					if (so.hasProperty("ProductShortName")) {
						ProductShortName = so
								.getPropertyAsString("ProductShortName");
					}
					if (so.hasProperty("Character")) {
						Character = so.getPropertyAsString("Character");
					}
					if (so.hasProperty("TruckNO")) {
						TruckNO = so.getPropertyAsString("TruckNO");
					}
					if (so.hasProperty("NumPerUnit")) {
						NumPerUnit = so.getPropertyAsString("NumPerUnit");
					}

					_CurrentBill.ID = ID;
					_CurrentBill.CustomerID = CustomerID;
					_CurrentBill.ProductID = ProductID;
					_CurrentBill.SalesBillNO = SalesBillNO;
					_CurrentBill.Count = Count;
					_CurrentBill.MakeDate = MakeDate;
					_CurrentBill.MakeUser = MakeUser;
					_CurrentBill.Remark = Remark;
					_CurrentBill.CustomerCode = CustomerCode;
					_CurrentBill.CustomerName = CustomerName;
					_CurrentBill.ProductCode = ProductCode;
					_CurrentBill.ProductName = ProductName;
					_CurrentBill.ProductShortName = ProductShortName;
					_CurrentBill.Character = Character;
					_CurrentBill.TruckNO = TruckNO;
					_CurrentBill.NumPerUnit = NumPerUnit;
				} else if (Success.equals("false")) {
					_CurrentBill.ErrMsg = so.getPropertyAsString("ErrMsg");
				}

			}
		} catch (Exception ex) {
			_CurrentBill.ErrMsg = ex.getMessage();
			return _CurrentBill;
		}
		return _CurrentBill;
	}

	public static PackBillResult GetPackBill(String barcode) {

		PackBillResult _CurrentBill = new PackBillResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("barcode", barcode);
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					"GetPackBill", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					String ID = "";
					String CustomerID = "";
					String PackingID = "";
					String SalesBillNO = "";
					String Count = "";
					String MakeDate = "";
					String MakeUser = "";
					String Remark = "";
					String CustomerCode = "";
					String CustomerName = "";
					String PackingCode = "";
					String PackingName = "";
					String TruckNO = "";

					if (so.hasProperty("ID")) {
						ID = so.getPropertyAsString("ID");
					}
					if (so.hasProperty("CustomerID")) {
						CustomerID = so.getPropertyAsString("CustomerID");//
					}
					if (so.hasProperty("PackingID")) {
						PackingID = so.getPropertyAsString("PackingID");//
					}
					if (so.hasProperty("SalesBillNO")) {
						SalesBillNO = so.getPropertyAsString("SalesBillNO");//
					}
					if (so.hasProperty("Count")) {
						Count = so.getPropertyAsString("Count");//
					}
					if (so.hasProperty("MakeDate")) {
						MakeDate = so.getPropertyAsString("MakeDate");//
					}
					if (so.hasProperty("MakeUser")) {
						MakeUser = so.getPropertyAsString("MakeUser");//
					}
					if (so.hasProperty("Remark")) {
						Remark = so.getPropertyAsString("Remark");//
					}
					if (so.hasProperty("CustomerCode")) {
						CustomerCode = so.getPropertyAsString("CustomerCode");//
					}
					if (so.hasProperty("CustomerName")) {
						CustomerName = so.getPropertyAsString("CustomerName");//
					}
					if (so.hasProperty("PackingCode")) {
						PackingCode = so.getPropertyAsString("PackingCode");//
					}
					if (so.hasProperty("PackingName")) {
						PackingName = so.getPropertyAsString("PackingName"); //
					}
					if (so.hasProperty("TruckNO")) {
						TruckNO = so.getPropertyAsString("TruckNO");//
					}
					_CurrentBill.ID = ID;
					_CurrentBill.CustomerID = CustomerID;
					_CurrentBill.PackingID = PackingID;
					_CurrentBill.SalesBillNO = SalesBillNO;
					_CurrentBill.Count = Count;
					_CurrentBill.MakeDate = MakeDate;
					_CurrentBill.MakeUser = MakeUser;
					_CurrentBill.Remark = Remark;
					_CurrentBill.CustomerCode = CustomerCode;
					_CurrentBill.CustomerName = CustomerName;
					_CurrentBill.PackingCode = PackingCode;
					_CurrentBill.PackingName = PackingName;
					_CurrentBill.TruckNO = TruckNO;
				} else if (Success.equals("false")) {
					_CurrentBill.ErrMsg = so.getPropertyAsString("ErrMsg");
				}

			}
		} catch (Exception ex) {
			_CurrentBill.ErrMsg = ex.getMessage();
			return _CurrentBill;
		}
		return _CurrentBill;
	}

	public static String BlankoutTakingout(String barcode) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("barcode", barcode);
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapPrimitive sp = SoapControl.ExecuteWebMethod(
					"BlankoutTakingout", map, Global.SendDataTime);
			if (sp != null) {
				String Success = sp.toString();
				return Success;
			} else {
				return "";
			}
		} catch (Exception ex) {
			return "";
		}
	}

	public static List<CItem> GetAllCorporation() {
		List<CItem> corpList = new ArrayList<CItem>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					"GetAllCorporation", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					SoapObject soap_CorporationResults = (SoapObject) so
							.getProperty("CorporationResults");
					int ic1 = soap_CorporationResults.getPropertyCount();
					for (int i = 0; i < ic1; i++) {
						SoapObject soap_Corporation = (SoapObject) soap_CorporationResults
								.getProperty(i);
						String ID = soap_Corporation.getProperty("ID")
								.toString();
						String Code = soap_Corporation.getProperty("Code")
								.toString();
						String ShortName = soap_Corporation.getProperty(
								"ShortName").toString();
						String FullName = soap_Corporation.getProperty(
								"FullName").toString();
						String Remark = soap_Corporation.getProperty("Remark")
								.toString();

						// CorporationDO corp = new CorporationDO();
						// corp.ID = ID;
						// corp.Code = Code;
						// corp.ShortName = ShortName;
						// corp.FullName = FullName;
						// corp.Remark = Remark;

						CItem item = new CItem(ID, ShortName, Code);

						corpList.add(item);
					}
				}
			}
		} catch (Exception ex) {
			return corpList;
		}

		return corpList;
	}

	// 获取所有固定资产
	public static List<AssetDO> GetAllAssets(String corporationCode) {
		List<AssetDO> list = new ArrayList<AssetDO>();

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("corpCode", corporationCode);
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					"GetAllAssets", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					SoapObject soap_AssetResults = (SoapObject) so
							.getProperty("AssetResults");
					int ic1 = soap_AssetResults.getPropertyCount();
					for (int i = 0; i < ic1; i++) {
						SoapObject soap_Asset = (SoapObject) soap_AssetResults
								.getProperty(i);
						String ID = soap_Asset.getProperty("ID").toString();
						String Barcode = soap_Asset.getProperty("Barcode")
								.toString();
						String Code = soap_Asset.getProperty("Code").toString();
						String Name = soap_Asset.getProperty("Name").toString();
						String Department = soap_Asset
								.getProperty("Department").toString();
						String DevalueMethod = soap_Asset.getProperty(
								"DevalueMethod").toString();
						String HowGetting = soap_Asset
								.getProperty("HowGetting").toString();
						String Location = soap_Asset.getProperty("Location")
								.toString();
						String MonthUsed = soap_Asset.getProperty("MonthUsed")
								.toString();
						String Count = soap_Asset.getProperty("Count")
								.toString();
						String DevalueRate = soap_Asset.getProperty(
								"DevalueRate").toString();
						String OriginalMoney = soap_Asset.getProperty(
								"OriginalMoney").toString();
						String Standard = soap_Asset.getProperty("Standard")
								.toString();
						String Status = soap_Asset.getProperty("Status")
								.toString();
						String AssetType = soap_Asset.getProperty("AssetType")
								.toString();
						String Abstract = soap_Asset.getProperty("Abstract")
								.toString();
						String Supplier = soap_Asset.getProperty("Supplier")
								.toString();
						String ManufactureDate = soap_Asset.getProperty(
								"ManufactureDate").toString();
						String StartUsingDate = soap_Asset.getProperty(
								"StartUsingDate").toString();
						String PhotoFileSize = soap_Asset.getProperty(
								"PhotoFileSize").toString();

						AssetDO ado = new AssetDO();
						ado.ID = ID;
						ado.Barcode = Barcode;
						ado.Code = Code;
						ado.Name = Name;
						ado.Department = Department;
						ado.DevalueMethod = DevalueMethod;
						ado.HowGetting = HowGetting;
						ado.Location = Location;
						ado.MonthUsed = MonthUsed;
						ado.Count = Count;
						ado.DevalueRate = DevalueRate;
						ado.OriginalMoney = OriginalMoney;
						ado.Standard = Standard;
						ado.Status = Status;
						ado.AssetType = AssetType;
						ado.Abstract = Abstract;
						ado.Supplier = Supplier;
						ado.ManufactureDate = ManufactureDate;
						ado.StartUsingDate = StartUsingDate;
						ado.PhotoFileSize = PhotoFileSize;
						ado.Photo = null;

						list.add(ado);
					}

				}
			}
		} catch (Exception ex) {
			return list;
		}
		return list;
	}

	// 获取所有固定资产
	public static Hashtable GetAllAssetsHash(String corporationCode) {
		// List<AssetDO> list = new ArrayList<AssetDO>();
		Hashtable<String, AssetDO> list = new Hashtable<String, AssetDO>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("corpCode", corporationCode);
			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					"GetAllAssets", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					SoapObject soap_AssetResults = (SoapObject) so
							.getProperty("AssetResults");
					int ic1 = soap_AssetResults.getPropertyCount();
					for (int i = 0; i < ic1; i++) {
						SoapObject soap_Asset = (SoapObject) soap_AssetResults
								.getProperty(i);
						String ID = soap_Asset.getProperty("ID").toString();
						String Barcode = soap_Asset.getProperty("Barcode")
								.toString();
						String Code = soap_Asset.getProperty("Code").toString();
						String Name = soap_Asset.getProperty("Name").toString();
						String Department = soap_Asset
								.getProperty("Department").toString();
						String DevalueMethod = soap_Asset.getProperty(
								"DevalueMethod").toString();
						String HowGetting = soap_Asset
								.getProperty("HowGetting").toString();
						String Location = soap_Asset.getProperty("Location")
								.toString();
						String MonthUsed = soap_Asset.getProperty("MonthUsed")
								.toString();
						String Count = soap_Asset.getProperty("Count")
								.toString();
						String DevalueRate = soap_Asset.getProperty(
								"DevalueRate").toString();
						String OriginalMoney = soap_Asset.getProperty(
								"OriginalMoney").toString();
						String Standard = soap_Asset.getProperty("Standard")
								.toString();
						String Status = soap_Asset.getProperty("Status")
								.toString();
						String AssetType = soap_Asset.getProperty("AssetType")
								.toString();
						String Abstract = soap_Asset.getProperty("Abstract")
								.toString();
						String Supplier = soap_Asset.getProperty("Supplier")
								.toString();
						String ManufactureDate = soap_Asset.getProperty(
								"ManufactureDate").toString();
						String StartUsingDate = soap_Asset.getProperty(
								"StartUsingDate").toString();
						String PhotoFileSize = soap_Asset.getProperty(
								"PhotoFileSize").toString();

						AssetDO ado = new AssetDO();
						ado.ID = ID;
						ado.Barcode = Barcode;
						ado.Code = Code;
						ado.Name = Name;
						ado.Department = Department;
						ado.DevalueMethod = DevalueMethod;
						ado.HowGetting = HowGetting;
						ado.Location = Location;
						ado.MonthUsed = MonthUsed;
						ado.Count = Count;
						ado.DevalueRate = DevalueRate;
						ado.OriginalMoney = OriginalMoney;
						ado.Standard = Standard;
						ado.Status = Status;
						ado.AssetType = AssetType;
						ado.Abstract = Abstract;
						ado.Supplier = Supplier;
						ado.ManufactureDate = ManufactureDate;
						ado.StartUsingDate = StartUsingDate;
						ado.PhotoFileSize = PhotoFileSize;
						ado.Photo = null;

						list.put(ado.Barcode, ado);
					}

				}
			}
		} catch (Exception ex) {
			return list;
		}
		return list;
	}

	public static List<String> GetAllAssetStatus() {
		List<String> strArr = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", Global.UserCode);
		map.put("password", Global.Password);
		map.put("deviceId", Global.deviceId);
		map.put("clientVersion", Global.clientVersion);
		map.put("dbServer", Global.dbServer);

		SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
				"GetAllAssetStatus", map);
		if (so != null) {
			int ic1 = so.getPropertyCount();
			strArr = new ArrayList<String>();

			String value = so.getPropertyAsString(0);
			strArr.add(value);
			value = so.getPropertyAsString(1);
			strArr.add(value);
			value = so.getPropertyAsString(2);
			strArr.add(value);
			value = so.getPropertyAsString(3);
			strArr.add(value);
		}
		return strArr;
	}

	// 获取固定资产照片
	public static byte[] GetAssetPhoto(String barcode) {
		byte[] rt_photo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("barcode", barcode);
		map.put("userName", Global.UserCode);
		map.put("password", Global.Password);
		map.put("deviceId", Global.deviceId);
		map.put("clientVersion", Global.clientVersion);
		map.put("dbServer", Global.dbServer);

		SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
				"GetAssetPhoto1", map);
		if (so != null) {
			String Success = so.getPropertyAsString("Success");
			if (Success.equals("true")) {

				if (so.hasProperty("imgBase64")) {
					String base64Data = so.getPropertyAsString("imgBase64");
					byte[] bt64 = CommonMethord.base64ToByte(base64Data);

					InputStream is = CommonMethord.Byte2InputStream(bt64);
					Bitmap bmp = CommonMethord.InputStream2Bitmap(is);
					InputStream is1 = CommonMethord.Bitmap2InputStream(bmp);
					rt_photo = CommonMethord.InputStreamTOByte(is1);

					// Bitmap bit_SmPhoto = CommonMethord.getSmallBitmap(bt64);
					// rt_photo = CommonMethord.Bitmap2Bytes(bit_SmPhoto);

					// Bitmap bmp = CommonMethord.base64ToBitmap(base64Data);
					// byte[] bphoto = CommonMethord.Bitmap2Bytes(bmp);
					// Bitmap bit_SmPhoto =
					// CommonMethord.getSmallBitmap(bphoto);
					// rt_photo = CommonMethord.Bitmap2Bytes(bit_SmPhoto);
				}

				// if(so.hasProperty("sData"))
				// {
				// byte[] photo = null;
				// SoapObject soap_sData = (SoapObject) so.getProperty("sData");
				// int ic1 = soap_sData.getPropertyCount();
				// photo = new byte[ic1];
				// for(int i =0;i<ic1;i++)
				// {
				// String sbyte = soap_sData.getProperty(i).toString();
				// photo[i] = Byte.parseByte(sbyte);
				// //sbyte += soap_sData.getProperty("byte").toString();
				// }
				// //Bitmap bit_photo = CommonMethord.Bytes2Bimap(photo);
				// Bitmap bit_SmPhoto = CommonMethord.getSmallBitmap(photo);
				// rt_photo = CommonMethord.Bitmap2Bytes(bit_SmPhoto);
				// }
			}
		}
		return rt_photo;
	}

	// 提交盘点结果
	public static String SubmitInventory(String barcode, String inventoryUser,
			String inventoryDate, String inventoryRemark) {
		String Success = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("barcode", barcode);
		map.put("inventoryUser", inventoryUser);
		map.put("inventoryDate", inventoryDate);
		map.put("inventoryRemark", inventoryRemark);
		map.put("userName", Global.UserCode);
		map.put("password", Global.Password);
		map.put("deviceId", Global.deviceId);
		map.put("clientVersion", Global.clientVersion);
		map.put("dbServer", Global.dbServer);

		SoapPrimitive so = SoapControl.ExecuteWebMethod("SubmitInventory", map,
				Global.SendDataTime);
		if (so != null) {
			Success = so.toString();
		}

		return Success;
	}

	// 获取所有服务器
	public static List<DbServerDO> GetAllDbServer() {
		List<DbServerDO> Arr = new ArrayList<DbServerDO>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", Global.deviceId);
		map.put("clientVersion", Global.clientVersion);

		SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
				"GetAllDbServer", map);
		if (so != null) {
			String rlt = so.getPropertyAsString("Success");
			if (rlt.equals("true")) {
				SoapObject soap_AssetResults = (SoapObject) so
						.getProperty("DbServerResults");
				int ic1 = soap_AssetResults.getPropertyCount();
				for (int i = 0; i < ic1; i++) {
					SoapObject soap_Asset = (SoapObject) soap_AssetResults
							.getProperty(i);
					String Success = soap_Asset.getProperty("Success")
							.toString();
					String SalesServerName = soap_Asset.getProperty(
							"SalesServerName").toString();
					String DisplayName = soap_Asset.getProperty("DisplayName")
							.toString();
					String DbInfo = soap_Asset.getProperty("DbInfo").toString();
					DbServerDO dbserver = new DbServerDO();

					dbserver.SalesServerName = SalesServerName;
					dbserver.DisplayName = DisplayName;
					dbserver.DbInfo = DbInfo;

					Arr.add(dbserver);
				}
			}
		}
		return Arr;
	}

	// 获取所有服务器
	public static List<String> GetAllDbServer_Str() {
		List<String> Arr = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deviceId", Global.deviceId);
		map.put("clientVersion", Global.clientVersion);

		SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
				"GetAllDbServer", map);
		if (so != null) {
			String rlt = so.getPropertyAsString("Success");
			if (rlt.equals("true")) {
				SoapObject soap_AssetResults = (SoapObject) so
						.getProperty("DbServerResults");
				int ic1 = soap_AssetResults.getPropertyCount();
				for (int i = 0; i < ic1; i++) {
					SoapObject soap_Asset = (SoapObject) soap_AssetResults
							.getProperty(i);
					String Success = soap_Asset.getProperty("Success")
							.toString();
					String SalesServerName = soap_Asset.getProperty(
							"SalesServerName").toString();
					String DisplayName = soap_Asset.getProperty("DisplayName")
							.toString();
					String DbInfo = soap_Asset.getProperty("DbInfo").toString();
					DbServerDO dbserver = new DbServerDO();

					// dbserver.SalesServerName = SalesServerName;
					// dbserver.DisplayName = DisplayName;
					// dbserver.DbInfo = DbInfo;

					Arr.add(dbserver.DisplayName);
				}
			}
		}
		return Arr;
	}

	// 获取所有服务器
	public static String GetServerDate() {
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		String year = String.valueOf(t.year).substring(0, 2);
		return year;
	}

	// 获取默认的服务器列表
	public static DbServerDO GetDefaultDbServer() {
		DbServerDO dbser = new DbServerDO();
		dbser.SalesServerName = Global.dbServer;
		return dbser;
	}

	public static void AddBill(SaleBillDo bill) {
		if (bill != null) {
			boolean isExit = false;
			for (SaleBillDo item : Global._ModelList) {
				if (item.SalesBillNO == bill.SalesBillNO)
					isExit = true;
			}
			if (!isExit) {
				Global._ModelList.add(bill);
			}
		}
	}

	public static void RemoveBill(SaleBillDo bill) {
		if (bill != null) {
			boolean isExit = false;
			for (SaleBillDo item : Global._ModelList) {
				if (item.SalesBillNO == bill.SalesBillNO)
					isExit = true;
			}
			if (!isExit) {
				Global._ModelList.remove(bill);
			}
		}
	}

	// 获取固定资产照片测试
	public static byte[] GetAssetPhototest(String barcode) {
		byte[] photo = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("barcode", barcode);
		map.put("userName", Global.UserCode);
		map.put("password", Global.Password);
		map.put("deviceId", Global.deviceId);
		map.put("clientVersion", Global.clientVersion);
		map.put("dbServer", Global.dbServer);

		SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
				"GetAssetPhoto1", map);
		if (so != null) {
			String Success = so.getPropertyAsString("Success");
			if (Success.equals("true")) {
				if (so.hasProperty("sData")) {
					String Data = so.getPropertyAsString("sData");
					try {
						InputStream is = CommonMethord
								.StringTOInputStream(Data);
						photo = CommonMethord.InputStreamTOByte(is);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return photo;
	}

	/*
	 * 提交批号
	 */
	public static String SubmitBatchNO(String billNo, int num,
			List<String> lineNos, List<String> beginDates,
			List<String> beginNos, List<String> endDates, List<String> endNos) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("billNo", billNo);
			map.put("confirmer", "");
			map.put("num", num);
			// map.put("lineNos", (String[])lineNos.toArray(new
			// String[lineNos.size()]));
			// map.put("beginDates", (String[])beginDates.toArray(new
			// String[lineNos.size()]));
			// map.put("beginNos", (String[])beginNos.toArray(new
			// String[lineNos.size()]));
			// map.put("endDates", (String[])endDates.toArray(new
			// String[lineNos.size()]));
			// map.put("endNos", (String[])endNos.toArray(new
			// String[lineNos.size()]));

			map.put("userName", Global.UserCode);
			map.put("password", Global.Password);
			map.put("deviceId", Global.deviceId);
			map.put("clientVersion", Global.clientVersion);
			map.put("dbServer", Global.dbServer);

			SoapPrimitive sp = SoapControl.ExecuteWebMethodSubmitBatchNos(
					billNo, num, lineNos, beginDates, beginNos, endDates,
					endNos, "SubmitBatchNO_Ad", map, Global.SendDataTime);
			if (sp != null) {
				String Success = sp.toString();
				return Success;
			} else {
				return "";
			}
		} catch (Exception ex) {
			return "";
		}
	}

	// -----------------------------------------------------------------------------
	// -----------------------------------------------------------------------------
	// -----------------------------------------------------------------------------
	// -----------------------------------------------------------------------------
	// 包装物结算单
	// -----------------------------------------------------------------------------
	// -----------------------------------------------------------------------------
	// -----------------------------------------------------------------------------
	// -----------------------------------------------------------------------------

	//获取用户所属工厂
	public UserInCorporationListResult PackingLogin(CrashApplication userInfo,String userCode) {
		 
		UserInCorporationListResult rlt = new UserInCorporationListResult();
		List<UserInCorporationResult> userInCorps = new ArrayList<UserInCorporationResult>();
		
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userCode", userCode); 
			map.put("userName", userInfo.getUserName());
			map.put("password", userInfo.getPassword());
			map.put("deviceId", userInfo.getDeviceId());
			map.put("clientVersion", userInfo.getClientVersion());
			map.put("dbServer", userInfo.getDbServer());

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					userInfo.getWebServiceUrl(),"PackingLogin", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {
					SoapObject soap_AssetResults = (SoapObject) so
							.getProperty("UserInCorporationResults");
					int ic1 = soap_AssetResults.getPropertyCount();
					for (int i = 0; i < ic1; i++) {
						SoapObject soap_Asset = (SoapObject) soap_AssetResults
								.getProperty(i);
						UserInCorporationResult ado = new UserInCorporationResult();
					  
						if (soap_Asset.hasProperty("ID")) {
							String ID = soap_Asset.getPropertyAsString("ID");
							ado.ID = ID;
						}
						if (soap_Asset.hasProperty("UserID")) {  
							String UserID = null;
							UserID = soap_Asset.getPropertyAsString("UserID");
							ado.UserID = UserID;
						}
						if (soap_Asset.hasProperty("UserName")) {
							String UserName = null;
							UserName = soap_Asset.getPropertyAsString("UserName");
							ado.UserName = UserName;
						}
						if (soap_Asset.hasProperty("CorporationID")) {
							String CorporationID = null;
							CorporationID = soap_Asset.getPropertyAsString("CorporationID");
							ado.CorporationID = CorporationID;
						}
						if (soap_Asset.hasProperty("CorporationName")) { 
							String CorporationName = null;
							CorporationName = soap_Asset.getPropertyAsString("CorporationName");
							ado.CorporationName = CorporationName;
						}
						if (soap_Asset.hasProperty("Remark")) { 
							String Remark = null;
							Remark = soap_Asset.getPropertyAsString("Remark");
							ado.Remark = Remark;
						} 
						userInCorps.add(ado);
					}
				}  
			}
			
			rlt.Success = true;
			rlt.UserInCorporationResults = userInCorps;
			return rlt;
			
		} catch (Exception ex) {
			rlt.Success = false;
			rlt.ErrMsg = ex.getMessage();
			return rlt;
		} 
	}

	//根据身份识别卡获取客户信息
	public CustomerResult GetCustomer(CrashApplication userInfo,String CardSerialNumber) { 
		CustomerResult rlt = new CustomerResult();
		List<CustomerInCorporationResult> cusInCorp = new ArrayList<CustomerInCorporationResult>();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CardSerialNumber", CardSerialNumber); 
			map.put("userName", userInfo.getUserName());
			map.put("password", userInfo.getPassword());
			map.put("deviceId", userInfo.getDeviceId());
			map.put("clientVersion", userInfo.getClientVersion());
			map.put("dbServer", userInfo.getDbServer());

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					userInfo.getWebServiceUrl(),"GetCustomer", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {  
			        if (so.hasProperty("ID")) {
						String ID = so.getPropertyAsString("ID");
						rlt.ID = ID;
					}
					if (so.hasProperty("IDCardNO")) {
							String IDCardNO = so.getPropertyAsString("IDCardNO");
							rlt.IDCardNO = IDCardNO;
					}
			        if (so.hasProperty("CustomerCode")) {
						String CustomerCode = so.getPropertyAsString("CustomerCode");
						rlt.CustomerCode = CustomerCode;
					}
			        if (so.hasProperty("CustomerName")) {
						String CustomerName = so.getPropertyAsString("CustomerName");
						rlt.CustomerName = CustomerName;
					}
			        if (so.hasProperty("Remark")) {
						String Remark = so.getPropertyAsString("Remark");
						rlt.Remark = Remark;
					}
  
					SoapObject soap_AssetResults = (SoapObject) so
							.getProperty("CustomerInCorporationResults");
					int ic1 = soap_AssetResults.getPropertyCount();
					for (int i = 0; i < ic1; i++) {
						SoapObject soap_Asset = (SoapObject) soap_AssetResults
								.getProperty(i);
						CustomerInCorporationResult ado = new CustomerInCorporationResult();
						     
						if (soap_Asset.hasProperty("ID")) {
							String ID = soap_Asset.getPropertyAsString("ID");
							ado.ID = ID;
						}
						if (soap_Asset.hasProperty("CustomerID")) {  
							String CustomerID = soap_Asset.getPropertyAsString("CustomerID");
							ado.CustomerID = CustomerID;
						}
						if (soap_Asset.hasProperty("CustomerName")) {
							String CustomerName = soap_Asset.getPropertyAsString("CustomerName");
							ado.CustomerName = CustomerName;
						}
						if (soap_Asset.hasProperty("CorporationID")) {
							String CorporationID = soap_Asset.getPropertyAsString("CorporationID");
							ado.CorporationID = CorporationID;
						}
						if (soap_Asset.hasProperty("CorporationName")) { 
							String CorporationName = soap_Asset.getPropertyAsString("CorporationName");
							ado.CorporationName = CorporationName;
						}
						if (soap_Asset.hasProperty("Remark")) { 
							String Remark = soap_Asset.getPropertyAsString("Remark");
							ado.Remark = Remark;
						} 
						cusInCorp.add(ado);
					}
					rlt.Success = true;
					rlt.CustomerInCorporationResults = cusInCorp;
					return rlt;
				}
				else
				{
					String ErrMsg = so.getPropertyAsString("ErrMsg");
					rlt.Success = false;
					rlt.ErrMsg = ErrMsg;
					return rlt; 
				}
			}
			else
			{
				rlt.Success = false;
				rlt.ErrMsg = "没有获取到信息";
				return rlt; 
			}
			
			
			
		} catch (Exception ex) {
			rlt.Success = false;
			rlt.ErrMsg = ex.getMessage();
			return rlt;
		} 
	}

	//根据工厂获取所有的箱瓶现场
	public StorehouseListResult GetAllPackingStorehouse(CrashApplication userInfo,String corporationID) {
		 
		StorehouseListResult rlt = new StorehouseListResult();
		List<StorehouseResult> storeHouse = new ArrayList<StorehouseResult>();
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("corporationID", corporationID); 
			map.put("userName", userInfo.getUserName());
			map.put("password", userInfo.getPassword());
			map.put("deviceId", userInfo.getDeviceId());
			map.put("clientVersion", userInfo.getClientVersion());
			map.put("dbServer", userInfo.getDbServer());

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					userInfo.getWebServiceUrl(),"GetAllPackingStorehouse", map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {  
					  
						SoapObject soap_AssetResults = (SoapObject) so
								.getProperty("StorehouseResults");
						int ic1 = soap_AssetResults.getPropertyCount();
						for (int i = 0; i < ic1; i++) {
							SoapObject soap_Asset = (SoapObject) soap_AssetResults
									.getProperty(i);
							StorehouseResult ado = new StorehouseResult();
							        
							if (soap_Asset.hasProperty("ID")) {
								String ID = soap_Asset.getPropertyAsString("ID");
								ado.ID = ID;
							} 
							if (soap_Asset.hasProperty("StorehouseCode")) {
								String StorehouseCode = soap_Asset.getPropertyAsString("StorehouseCode");
								ado.StorehouseCode = StorehouseCode;
							}
							if (soap_Asset.hasProperty("StorehouseName")) { 
								String StorehouseName = soap_Asset.getPropertyAsString("StorehouseName");
								ado.StorehouseName = StorehouseName;
							}
							if (soap_Asset.hasProperty("Remark")) { 
								String Remark = soap_Asset.getPropertyAsString("Remark");
								ado.Remark = Remark;
							} 
							storeHouse.add(ado);
						}
					}  
				}
				
				rlt.Success = true;
				rlt.StorehouseResults = storeHouse;
				return rlt;
				
			} catch (Exception ex) {
				rlt.Success = false;
				rlt.ErrMsg = ex.getMessage();
				return rlt;
			} 
	}

	//获取客户初检单,已初检未复检
	/**
	 * 获取客户初检单已初检未复检 或 已复检未打印
	 * @param CustomerID 
	 * @param CorporationID 
	 * @param IsSecondNoPrint false=未复检，true=已复检未打印
	 * @return
	 */
	public FirstCheckBillListResult GetFirstCheckBill(CrashApplication userInfo,String CustomerID,String CorporationID, Boolean IsSecondCheck) 
	{
 
		FirstCheckBillListResult rlt = new FirstCheckBillListResult();
		List<FirstCheckBillResult> storeHouse = new ArrayList<FirstCheckBillResult>();
		
		String methordName = "";
		if(!IsSecondCheck)
		{
			methordName = "GetFirstCheckBill"; 
		}
		else  
		{ 
			methordName = "GetFirstCheckBill_SecondCheckedUnPrint"; 
		}
		
		try {
			
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("CustomerID",CustomerID);
			map.put("CorporationID",CorporationID);
			map.put("userName", userInfo.getUserName());
			map.put("password", userInfo.getPassword());
			map.put("deviceId", userInfo.getDeviceId());
			map.put("clientVersion", userInfo.getClientVersion());
			map.put("dbServer", userInfo.getDbServer());

			SoapObject so = SoapControl.ExecuteWebMethodReturnClass(
					userInfo.getWebServiceUrl(),methordName, map);
			if (so != null) {
				String Success = so.getPropertyAsString("Success");
				if (Success.equals("true")) {  
					  
						SoapObject soap_AssetResults = (SoapObject) so
								.getProperty("FirstCheckBillResults");
						int ic1 = soap_AssetResults.getPropertyCount();
						for (int i = 0; i < ic1; i++) {
							SoapObject soap_Asset = (SoapObject) soap_AssetResults
									.getProperty(i);
							FirstCheckBillResult ado = new FirstCheckBillResult();
							                               
							if (soap_Asset.hasProperty("BillID")) {
								String BillID = soap_Asset.getPropertyAsString("BillID");
								ado.BillID = BillID;
							} 
							if (soap_Asset.hasProperty("ReceivingBillNO")) {
								String ReceivingBillNO = soap_Asset.getPropertyAsString("ReceivingBillNO");
								ado.ReceivingBillNO = ReceivingBillNO;
							}
							if (soap_Asset.hasProperty("MakeDate")) { 
								String MakeDate = soap_Asset.getPropertyAsString("MakeDate");
								ado.MakeDate = MakeDate;
							}
							if (soap_Asset.hasProperty("PackingID")) { 
								String PackingID = soap_Asset.getPropertyAsString("PackingID");
								ado.PackingID = PackingID;
							} 
							if (soap_Asset.hasProperty("PackingCode")) { 
								String PackingCode = soap_Asset.getPropertyAsString("PackingCode");
								ado.PackingCode = PackingCode;
							} 
							if (soap_Asset.hasProperty("PackingName")) { 
								String PackingName = soap_Asset.getPropertyAsString("PackingName");
								ado.PackingName = PackingName;
							} 
							if (soap_Asset.hasProperty("StorehouseCode")) { 
								String StorehouseCode = soap_Asset.getPropertyAsString("StorehouseCode");
								ado.StorehouseCode = StorehouseCode;
							} 
							if (soap_Asset.hasProperty("StorehouseName")) { 
								String StorehouseName = soap_Asset.getPropertyAsString("StorehouseName");
								ado.StorehouseName = StorehouseName;
							} 
							if (soap_Asset.hasProperty("SecondCheckCount")) { 
								String SecondCheckCount = soap_Asset.getPropertyAsString("SecondCheckCount");
								ado.SecondCheckCount = SecondCheckCount;
							} 
							if (soap_Asset.hasProperty("LostBottleCount")) { 
								String LostBottleCount = soap_Asset.getPropertyAsString("LostBottleCount");
								ado.LostBottleCount = LostBottleCount;
							} 
							if (soap_Asset.hasProperty("BadBoxCount")) { 
								String BadBoxCount = soap_Asset.getPropertyAsString("BadBoxCount");
								ado.BadBoxCount = BadBoxCount;
							} 
							if (soap_Asset.hasProperty("BrokenBoxCount")) { 
								String BrokenBoxCount = soap_Asset.getPropertyAsString("BrokenBoxCount");
								ado.BrokenBoxCount = BrokenBoxCount;
							} 
							if (soap_Asset.hasProperty("SumClaimMoney")) { 
								String SumClaimMoney = soap_Asset.getPropertyAsString("SumClaimMoney");
								ado.SumClaimMoney = SumClaimMoney;
							} 
							if (soap_Asset.hasProperty("SumReturnForegiftMoney")) { 
								String SumReturnForegiftMoney = soap_Asset.getPropertyAsString("SumReturnForegiftMoney");
								ado.SumReturnForegiftMoney = SumReturnForegiftMoney;
							} 
							if (soap_Asset.hasProperty("TruckNO")) { 
								String TruckNO = soap_Asset.getPropertyAsString("TruckNO");
								ado.TruckNO = TruckNO;
							} 
							if (soap_Asset.hasProperty("ReturnMethod")) { 
								String ReturnMethod = soap_Asset.getPropertyAsString("ReturnMethod");
								ado.ReturnMethod = ReturnMethod.replace("anyType{}", "");
							} 
							if (soap_Asset.hasProperty("FirstCheckUser")) { 
								String FirstCheckUser = soap_Asset.getPropertyAsString("FirstCheckUser");
								ado.FirstCheckUser = FirstCheckUser;
							} 
							if (soap_Asset.hasProperty("SecondCheckUser")) { 
								String SecondCheckUser = soap_Asset.getPropertyAsString("SecondCheckUser");
								ado.SecondCheckUser = SecondCheckUser;
							} 
							if (soap_Asset.hasProperty("MakeUser")) { 
								String MakeUser = soap_Asset.getPropertyAsString("MakeUser");
								ado.MakeUser = MakeUser;
							} 
							if (soap_Asset.hasProperty("Remark")) { 
								String Remark = soap_Asset.getPropertyAsString("Remark");
								ado.Remark = Remark;
							} 
							if (soap_Asset.hasProperty("ReceivingBillTypeName")) { 
								String ReceivingBillTypeName = soap_Asset.getPropertyAsString("ReceivingBillTypeName");
								ado.ReceivingBillTypeName = ReceivingBillTypeName;
							} 
							if (soap_Asset.hasProperty("IsBoxReback")) { 
								String IsBoxReback = soap_Asset.getPropertyAsString("IsBoxReback");
								ado.IsBoxReback = IsBoxReback;
							} 
							if (soap_Asset.hasProperty("PackingTypeName")) { 
								String PackingTypeName = soap_Asset.getPropertyAsString("PackingTypeName");
								ado.PackingTypeName = PackingTypeName;
							} 
							if (soap_Asset.hasProperty("FirstCheckDate")) { 
								String FirstCheckDate = soap_Asset.getPropertyAsString("FirstCheckDate");
								ado.FirstCheckDate = FirstCheckDate;
							} 
							if (soap_Asset.hasProperty("FirstCheckRemark")) { 
								String FirstCheckRemark = soap_Asset.getPropertyAsString("FirstCheckRemark");
								ado.FirstCheckRemark = FirstCheckRemark;
							} 
							storeHouse.add(ado);
						}
					}  
				}
				
				rlt.Success = true;
				rlt.FirstCheckBillResults = storeHouse;
				return rlt;
				
			} catch (Exception ex) {
				rlt.Success = false;
				rlt.ErrMsg = ex.getMessage();
				return rlt;
			} 

	}
 
	public String ChangeSecondCheckBillPrintFlag(CrashApplication userInfo,String billID) {
		try { 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("billID", billID);
			map.put("userName", userInfo.getUserName());
			map.put("password", userInfo.getPassword());
			map.put("deviceId", userInfo.getDeviceId());
			map.put("clientVersion", userInfo.getClientVersion());
			map.put("dbServer", userInfo.getDbServer());

			SoapPrimitive sp = SoapControl.ExecuteWebMethod(
					userInfo.getWebServiceUrl(),"ChangeSecondCheckBillPrintFlag", map, Global.SendDataTime);
			if (sp != null) {
				String Success = sp.toString();
				return Success;
			} else {
				return "no";
			}
		} catch (Exception ex) {
			return "no" + ex.getMessage();
		}
	}

	public String SaveSecondCheckBill(String FirstCheckBillID,
			String StoreHouseID, String SecondCheckUserID,
			String SecondCheckQty, String remark, String badBottle,
			String canFixBox, String NoFixBox) {
		return NoFixBox;
	}

 

}
