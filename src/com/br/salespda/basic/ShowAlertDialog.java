package com.br.salespda.basic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.br.salespda.main.FrmLadProdActivity;

public class ShowAlertDialog {
	
	public static void ShowDialog(Context context,String msg)
	{
		 new AlertDialog.Builder(context).setTitle("ϵͳ��ʾ")//���öԻ������  				  
	     .setMessage(msg)//������ʾ������  		  
	     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť  
    	  public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
	             
	         }  	  
	     }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���  
		
	}

}
