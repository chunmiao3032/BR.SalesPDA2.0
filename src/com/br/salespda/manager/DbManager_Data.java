package com.br.salespda.manager;

import com.br.salespda.basic.AssetDO;
import com.br.salespda.basic.BatchNO;
import com.br.salespda.basic.SaleBillDo;
import com.br.salespda.common.CommonMethord;
import com.br.salespda.db.DBOpenHelper_Data; 

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DbManager_Data  {
	// Fields
    private String _ConnString4Config;
    private String _ConnString4Data;
    private static DbManager_Data _Instance;
    
    private DBOpenHelper_Data helper;
	private SQLiteDatabase database;
	private Cursor cursor; 
	private Cursor cursorBatchNo; 
	
	public DbManager_Data(Context context) {
		
		helper = new DBOpenHelper_Data(context);
		database=helper.getWritableDatabase();
	}
	 
	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	
	public Cursor getcursorBatchNo() {
		return cursor;
	}

	public void setcursorBatchNo(Cursor cursor) {
		this.cursorBatchNo = cursor;
	}
	
	public DBOpenHelper_Data getHelper() {
		return helper;
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
	
    public Cursor GetAllAssetBarcodes() {

			if (database == null) {
				// database = helper.getWritableDatabase();
			}
			if (!database.isOpen()) {
				// database = helper.getWritableDatabase();
			}
			if (cursor != null || (cursor != null && cursor.moveToFirst() != false)) {
				cursor.close();
			}
			cursor = database
					.rawQuery(
							"select Barcode from Asset where InventoryDate != ''",
							null);
			if (cursor.moveToFirst()) {
				return cursor;
			} else {
				cursor.close(); 
				return null;
			}
   }
    
    public Cursor GetAllDepartments() {
    	if (database == null) {
			// database = helper.getWritableDatabase();
		}
		if (!database.isOpen()) {
			// database = helper.getWritableDatabase();
		}
		if (cursor != null || (cursor != null && cursor.moveToFirst() != false)) {
			cursor.close();
		}
		cursor = database
				.rawQuery(
						"select distinct Department from Asset where Department is not null",
						null);
		if (cursor.moveToFirst()) {
			return cursor;
		} else {
			cursor.close(); 
			return null;
		}
    	
    }
    
    public Cursor GetAssets(String deptName)
    {
    	if (database == null) {
			// database = helper.getWritableDatabase();
		}
		if (!database.isOpen()) {
			// database = helper.getWritableDatabase();
		}
		if (cursor != null || (cursor != null && cursor.moveToFirst() != false)) {
			cursor.close();
		}
		cursor = database
				.rawQuery(
						"select Barcode,Code,Name,Department,Location,Status,InventoryUser,InventoryDate "
						+ "from Asset where Department like '%" + deptName + "%' "
					    + " order by InventoryDate desc",
						null);
		if (cursor.moveToFirst()) {
			return cursor;
		} else {
			cursor.close(); 
			return null;
		}
    }
    
    public Cursor  GetLoginUsers()
    {
    	if (database == null) {
			// database = helper.getWritableDatabase();
		}
		if (!database.isOpen()) {
			// database = helper.getWritableDatabase();
		}
		if (cursor != null || (cursor != null && cursor.moveToFirst() != false)) {
			cursor.close();
		}
		cursor = database
				.rawQuery(
						"select UserCode, UserName, Password, Remark from LoginUser",
						null);
		if (cursor.moveToFirst()) {
			return cursor;
		} else {
			cursor.close(); 
			return null;
		}
    }
    
	 public void InsertAsset(AssetDO asset)
	 {
		 database.execSQL(
			"insert into Asset "
				+ "(ID, Barcode, Code, Name, AssetType, Abstract, Standard, Department, "
				+ "HowGetting, Location, Status, MonthUsed, DevalueRate, "
				+ "DevalueMethod, OriginalMoney, Count, StartUsingDate, ManufactureDate, Supplier, Photo)"
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			new Object[] {
				asset.ID,asset.Barcode,asset.Code,asset.Name,asset.AssetType,asset.Abstract,asset.Standard,asset.Department,
				asset.HowGetting,asset.Location,asset.Status,asset.MonthUsed,asset.DevalueRate,
				asset.DevalueMethod,asset.OriginalMoney,asset.Count,asset.StartUsingDate,asset.ManufactureDate,asset.Supplier,asset.Photo});
			
	 }

	public void UpdateAsset(AssetDO asset) {
		 database.execSQL(
		 "update Asset "
		 + "set ID=?, Code=?, "
		 + "Name=?, AssetType=?, Abstract=?,"
		 + "Standard=?, Department=?, "
		 + "HowGetting=?, "
		 + "Location=?, Status=?,MonthUsed=?, "
		 + "DevalueRate=?, DevalueMethod=?, "
		 + "OriginalMoney=?,Count=?, "
		 + "StartUsingDate=?, "
		 + "ManufactureDate=?, "
		 + "Supplier=?, Photo=? "
		 + "where Barcode=?",
			new Object[] {
				asset.ID,asset.Code,
				asset.Name,asset.AssetType,asset.Abstract,
				asset.Standard,asset.Department,
				asset.HowGetting,
				asset.Location,asset.Status,asset.MonthUsed,
				asset.DevalueRate,asset.DevalueMethod,
				asset.OriginalMoney,asset.Count,
				asset.StartUsingDate,
				asset.ManufactureDate,
				asset.Supplier,
				(((asset.Photo != null) && (asset.Photo.length > 0)) ? asset.Photo : ""),asset.Barcode});
	}

	public void DeleteAsset(String barcode) {
		database.execSQL("delete from Asset where Barcode=?",new Object[]{barcode});
	}
    
	 public AssetDO GetAsset(String barcode)
     {
		 AssetDO asset = null;
			if (cursor != null || (cursor != null && cursor.moveToFirst() != false)) {
				cursor.close();
			}
			cursor = database.rawQuery("select * from Asset where Barcode='" + barcode + "'", null);
			if (cursor.moveToFirst()) 
			{
				String ID = cursor.getString(cursor.getColumnIndex("ID"));
				String Barcode = cursor.getString(cursor.getColumnIndex("Barcode"));
				String Code = cursor.getString(cursor.getColumnIndex("Code"));
				String Name = cursor.getString(cursor.getColumnIndex("Name"));
				String AssetType = cursor.getString(cursor.getColumnIndex("AssetType"));
				String Abstract = cursor.getString(cursor.getColumnIndex("Abstract"));
				String Standard = cursor.getString(cursor.getColumnIndex("Standard"));
				String Department = cursor.getString(cursor.getColumnIndex("Department"));
				String HowGetting = cursor.getString(cursor.getColumnIndex("HowGetting"));
				String DevalueMethod = cursor.getString(cursor.getColumnIndex("DevalueMethod"));
				String DevalueRate = cursor.getString(cursor.getColumnIndex("DevalueRate"));
				String ManufactureDate = cursor.getString(cursor.getColumnIndex("ManufactureDate"));
				String MonthUsed = cursor.getString(cursor.getColumnIndex("MonthUsed"));
				String OriginalMoney = cursor.getString(cursor.getColumnIndex("OriginalMoney"));
				String StartUsingDate = cursor.getString(cursor.getColumnIndex("StartUsingDate"));
				String Supplier = cursor.getString(cursor.getColumnIndex("Supplier"));
				String Location = cursor.getString(cursor.getColumnIndex("Location"));
				String Status = cursor.getString(cursor.getColumnIndex("Status"));
				String Count = cursor.getString(cursor.getColumnIndex("Count"));
				byte[] Photo = cursor.getBlob(cursor.getColumnIndex("Photo"));
				String PhotoFileSize = Photo == null ? "0" : Photo.length + "";
				String InventoryUser = cursor.getString(cursor.getColumnIndex("InventoryUser"));
				String InventoryDate = cursor.getString(cursor.getColumnIndex("InventoryDate"));
				String InventoryRemark  = cursor.getString(cursor.getColumnIndex("InventoryRemark"));
				
				asset = new AssetDO();
				asset.ID = ID;
				asset.Barcode = Barcode;
				asset.Code = Code;
				asset.Name = Name;
				asset.AssetType = AssetType;
				asset.Abstract = Abstract;
				asset.Standard = Standard;
				asset.Department = Department;
				asset.HowGetting = HowGetting;
				asset.DevalueMethod = DevalueMethod;
				asset.DevalueRate = DevalueRate;
				asset.ManufactureDate = ManufactureDate;
				asset.MonthUsed = MonthUsed;
				asset.OriginalMoney = OriginalMoney;
				asset.StartUsingDate = StartUsingDate;
				asset.Supplier = Supplier;
				asset.Location = Location;
				asset.Status = Status;
				asset.Count = Count;
				asset.Photo = Photo;
				asset.PhotoFileSize = PhotoFileSize;
				asset.InventoryUser = InventoryUser;
				asset.InventoryDate = InventoryDate;
				asset.InventoryRemark = InventoryRemark;
				 
				cursor.close();
				return asset;
			} else {
				cursor.close(); 
				return asset;
			}
		  
     }
	 
	 public Cursor GetAsset_cur(String barcode)
     { 
			if (cursor != null || (cursor != null && cursor.moveToFirst() != false)) {
				cursor.close();
			}
			cursor = database.rawQuery("select * from Asset where Barcode='" + barcode + "'", null);
			if (cursor.moveToFirst()) 
			{ 
				return cursor;
			} else {
				cursor.close(); 
				return null;
			}
		  
     }

	
	 public void UpdateAssetInventory(String barcode, String inventoryUser, String dateTime, String inventoryRemark) {
		 
		 database.execSQL("update Asset set InventoryUser=?, InventoryRemark=?, InventoryDate=? where Barcode=?",
				 	new Object[]{inventoryUser,inventoryRemark,dateTime,barcode});
	}
    
//***********************************************************************************************
//**************************成品酒发货	 
//***********************************************************************************************
	 public void InsertSaleBill(SaleBillDo saleBill)
	 {
		 database.execSQL(
			"insert into SaleBill (" +
					 "ProductCode," +"ProductName, " + "ProductShortName," +"Character," +
				     "Count," +"CustomerID," + "CustomerCode," + "CustomerName," +
				     "ID," + "MakeDate," + "MakeUser," + "ProductID," +
				     "Remark," + "SalesBillNO," + "TruckNO," + "NumPerUnit)"
				+ "values ("
				+ "?, ?, ?, ?, "
				+ "?, ?, ?, ?, "
				+ "?, ?, ?, ?, "
				+ "?, ?, ?, ?)",
			new Object[] {
					saleBill.ProductCode,saleBill.ProductName,saleBill.ProductShortName,saleBill.Character,
					saleBill.Count,saleBill.CustomerID,saleBill.CustomerCode,saleBill.CustomerName,
					saleBill.ID,saleBill.MakeDate,saleBill.MakeUser,saleBill.ProductID,
					saleBill.Remark,saleBill.SalesBillNO,saleBill.TruckNO,saleBill.NumPerUnit
			});
			
	 }
	 
	 public void DeleteSaleBill(String SalesBillNO) {
			database.execSQL("delete from SaleBill where SalesBillNO=?",new Object[]{SalesBillNO});
			database.execSQL("delete from BatchNO where SalesBillNO=?",new Object[]{SalesBillNO});
		}
	 
	 public void InsertBatchNo(SaleBillDo saleBill,BatchNO batchNo)
	 {
		 database.execSQL(
			"insert into BatchNO (" +
					"SalesBillNO," +
				     "LineNo, " +
				     "StartDate," +
				     "EndDate," +
				     "StartIdx," +
				     "EndIdx)"
				+ "values ("
				+ "?, ?, "
				+ "?, ?, ?, ?)",
			new Object[] {
					saleBill.SalesBillNO,
					batchNo.LineNo,CommonMethord.DateToStr(batchNo.StartDate),CommonMethord.DateToStr(batchNo.EndDate),batchNo.StartIdx,batchNo.EndIdx 	
		    });
			
	 }
	 
	 public void DeleteBatchNo(String SalesBillNO,BatchNO batchNo) { 
			database.execSQL(
					"delete from BatchNO where SalesBillNO=? and LineNo = ? and StartDate = ? and EndDate = ? and StartIdx = ? and EndIdx = ?",
					new Object[]{SalesBillNO,batchNo.LineNo,batchNo.StartDate,batchNo.EndDate,batchNo.StartIdx,batchNo.EndIdx});
		}
	 
	  public Cursor GetSaleBill(String SalesBillNO)
	    { 
		  if (database == null) {
				// database = helper.getWritableDatabase();
			}
			if (!database.isOpen()) {
				// database = helper.getWritableDatabase();
			}
			if (cursor != null || (cursor != null && cursor.moveToFirst() != false)) {
				cursor.close();
			}
			cursor = database
					.rawQuery(
							"select  " +
									 "ProductCode," +"ProductName, " + "ProductShortName," +"Character," +
								     "Count," +"CustomerID," + "CustomerCode," + "CustomerName," +
								     "ID," + "MakeDate," + "MakeUser," + "ProductID," +
								     "Remark," + "SalesBillNO," + "TruckNO," + "NumPerUnit"
									+ " from saleBill where SalesBillNO like '%" + SalesBillNO + "%'",
							null);
			if (cursor.moveToFirst()) {
				return cursor;
			} else {
				cursor.close(); 
				return null;
			}
	    }
	  
	  public Cursor GetSaleBills()
	    { 
		  if (database == null) {
				// database = helper.getWritableDatabase();
			}
			if (!database.isOpen()) {
				// database = helper.getWritableDatabase();
			}
			if (cursor != null || (cursor != null && cursor.moveToFirst() != false)) {
				cursor.close();
			}
			cursor = database
					.rawQuery(
							"select  " +
									 "ProductCode,ProductName,ProductShortName,Character," +
								     "Count,CustomerID,CustomerCode,CustomerName," +
								     "ID,MakeDate,MakeUser,ProductID," +
								     "Remark,SalesBillNO,TruckNO,NumPerUnit"
									+ " from SaleBill",
							null);
			if (cursor.moveToFirst()) {
				return cursor;
			} else {
				cursor.close(); 
				return null;
			}
	    }
	 
	  public Cursor GetBatchNo(String SalesBillNO)
	    { 
			if (cursorBatchNo != null || (cursorBatchNo != null && cursorBatchNo.moveToFirst() != false)) {
				cursorBatchNo.close();
			}
			cursorBatchNo = database
					.rawQuery(
							"select  " +
									"SalesBillNO," +
								     "LineNo, " +
								     "StartDate," +
								     "EndDate," +
								     "StartIdx," +
								     "EndIdx"
									+ " from BatchNo where SalesBillNO like '%" + SalesBillNO + "%'",
							null);
			if (cursorBatchNo.moveToFirst()) {
				return cursorBatchNo;
			} else {
				cursorBatchNo.close(); 
				return null;
			}
	    }
	 
    
	}
