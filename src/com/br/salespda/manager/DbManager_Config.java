package com.br.salespda.manager;

import com.br.salespda.db.DBOpenHelper_Config;
import com.br.salespda.db.DBOpenHelper_Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DbManager_Config  {
	// Fields
    private String _ConnString4Config;
    private String _ConnString4Data;
    private static DbManager_Config _Instance;
    
    private DBOpenHelper_Config helper;
	private SQLiteDatabase database;
	private Cursor cursor;

	public DbManager_Config(Context context) {
		
		helper = new DBOpenHelper_Config(context);
		database=helper.getWritableDatabase();
	}
	 
	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}
	
	public DBOpenHelper_Config getHelper() {
		return helper;
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
	  
    public void SetParam(String paramName, String paramValue)
    { 
    	try
    	{
	    	Cursor cursor = database
					.rawQuery(
							"select * from SystemParam where ParamKey='" + paramName + "'",
							null );
	    	 
	            int num = cursor.getCount();
	            if (num == 0)
	            {
	            	 database.execSQL("insert into SystemParam (ParamKey, ParamValue) values (?,?)",
	            			 new Object[] {paramName,paramValue}); 
	            }
	            else
	            {
	            	 database.execSQL("update SystemParam set ParamValue=? where ParamKey=?",
	            			 new Object[]{paramValue,paramName}); 
	            }
	            
	            if(cursor != null && !cursor.isClosed())
	    		{
	    			cursor.close();
	    		}
	            
	    } 
    	catch(Exception ex)
    	{ 
    	}
		finally
		{
			 
		}    
    
    }
    
    @SuppressWarnings("finally")
	public String GetParam(String paramName)
    { 
    	String str = "";
    	try
    	{ 
	    	Cursor cursor = database
					.rawQuery(
							"select ParamValue from SystemParam where ParamKey = '" + paramName + "'",
							null );
	    	 
	    	if (cursor.moveToFirst()) 
	    	{
	    		if(cursor != null && cursor.getCount() > 0)
	    		{
		    		int idx = cursor.getColumnIndex("ParamValue");
		    		if(idx != -1)
		    		{
		    			str = cursor.getString(idx);
		    		}
	    		}
	    		if(cursor != null && !cursor.isClosed())
	    		{
	    			cursor.close();
	    		}
	    	}  
	    	else {
				cursor.close();  
			}
    	} 
    	catch(Exception ex)
    	{ 
    	}
    	finally
    	{
    		
    		return str;
    	}
        
    }
    
    
	
}
