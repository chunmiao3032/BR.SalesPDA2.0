package com.br.salespda.main;

import java.io.IOException;
import java.lang.reflect.Field;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Parcelable;

public class control_MF0 {
	static public byte[]getUID(Intent intent)
	{
		if(intent!=null)
		return intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
		else
			return null;
	}
	static public String getType(Intent intent)
	{
		String strret="<UID> :";
		if(intent!=null)
		{
			strret+=bytesToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
			strret+="\ntype:\n";
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG); 			
			String []strlist= tag.getTechList();
			for(String x:strlist)
			{
				strret+=x+"\n";
			}
			return strret;
		}
		else
			return null;
	}
	static public byte[] readPages(Intent intent,int pageOffset)
	{
		if(intent!=null)
		{
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG); 
		   if(tag!=null)
		   {
			   MifareUltralight nfc = MifareUltralight.get(tag); 
			   if(nfc!=null)
			   { 
					try {						
						nfc.connect();
						byte []bufread=nfc.readPages(pageOffset);
						nfc.close();
						return bufread;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							nfc.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();  
						}
					} 
				   System.out.print(""+pageOffset);

			   }
		   }
		}
		return null;
	}
	static public boolean writePage(Intent intent,int pageOffset,byte[]data)
	{
		if(intent!=null)
		{
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG); 
		   if(tag!=null)
		   {
			   MifareUltralight nfc = MifareUltralight.get(tag); 
			   if(nfc!=null)
			   { 
					try {						
						nfc.connect();
						nfc.writePage(pageOffset, data);
						nfc.close();
						return true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						try {
							nfc.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();  
						}
					} 
				   System.out.print(""+pageOffset);

			   }
		   }
		}
		return false;
	}
    static public String bytesToHexString(byte[] src) {   
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

}
