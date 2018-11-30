package com.br.salespda.basic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.br.salespda.main.FrmLadProdActivity;

public class ShowAlertDialog {
	
	public static void ShowDialog(Context context,String msg)
	{
		 new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题  				  
	     .setMessage(msg)//设置显示的内容  		  
	     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
    	  public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
	             
	         }  	  
	     }).show();//在按键响应事件中显示此对话框  
		
	}

}
