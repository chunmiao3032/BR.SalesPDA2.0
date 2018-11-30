package com.br.salespda.db;

import com.br.salespda.common.Global;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
  
public class DBOpenHelper_Config extends SQLiteOpenHelper {

	public DBOpenHelper_Config(Context context) {
		super(context, Global._ConnString4Config, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS LoginUser (UserCode TEXT PRIMARY KEY NOT NULL, UserName TEXT, Password TEXT, Remark TEXT, LastLoginTime DATETIME);");
		db.execSQL("CREATE TABLE IF NOT EXISTS SystemParam (ParamKey TEXT PRIMARY KEY NOT NULL,ParamValue TEXT);");	 
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS LoginUser");
		db.execSQL("DROP TABLE IF EXISTS SystemParam"); 
		onCreate(db);
	}

} 