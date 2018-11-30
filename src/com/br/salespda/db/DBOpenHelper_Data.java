package com.br.salespda.db;

import java.util.Date;

import com.br.salespda.basic.AssetDO;
import com.br.salespda.common.Global;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
  
public class DBOpenHelper_Data extends SQLiteOpenHelper {

	public DBOpenHelper_Data(Context context) {
		super(context, Global._ConnString4Data, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String sql_SaleBill = "CREATE TABLE IF NOT EXISTS SaleBill (" +
				         //"TID integer autoincrement," +
						 "ProductCode TEXT," +
					     "ProductName TEXT, " +
					     "ProductShortName TEXT," +
					     "Character TEXT," +
					     "Count TEXT," +
					     "CustomerID TEXT," +
					     "CustomerCode TEXT," +
					     "CustomerName TEXT," +
					     "ID TEXT PRIMARY KEY NOT NULL," +
					     "MakeDate TEXT," +
					     "MakeUser TEXT," +
					     "ProductID TEXT," +
					     "Remark TEXT," +
					     "SalesBillNO TEXT," +
					     "TruckNO TEXT," +
					     "NumPerUnit TEXT," +
					     "ErrMsg TEXT);";

		String sql_BatchNO = "CREATE TABLE IF NOT EXISTS BatchNO (" +
				             //"TID integer PRIMARY KEY autoincrement," +
							 "SalesBillNO TEXT," +
						     "LineNo TEXT, " +
						     "StartDate TEXT," +
						     "EndDate TEXT," +
						     "StartIdx TEXT," +
						     "EndIdx TEXT);";
		db.execSQL("CREATE TABLE IF NOT EXISTS Asset (Barcode TEXT PRIMARY KEY NOT NULL, ID INTEGER, Code TEXT, Name TEXT, AssetType TEXT, Abstract TEXT, Standard TEXT, Department TEXT, HowGetting TEXT, Location TEXT, Status TEXT, MonthUsed TEXT, DevalueRate TEXT, DevalueMethod TEXT, OriginalMoney TEXT, Count TEXT, StartUsingDate TEXT, ManufactureDate TEXT, Supplier TEXT, InventoryUser TEXT, InventoryDate DATETIME, InventoryRemark TEXT, Photo BLOB);");	
		db.execSQL(sql_SaleBill);	
		db.execSQL(sql_BatchNO);	
	
	
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS SaleBill");
		db.execSQL("DROP TABLE IF EXISTS BatchNO");
		db.execSQL("DROP TABLE IF EXISTS Asset");
		onCreate(db);
	}


} 