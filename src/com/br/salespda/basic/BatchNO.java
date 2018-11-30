package com.br.salespda.basic;

import java.text.DateFormat;
import java.util.Date;
 
	 public class BatchNO
     {
         public String LineNo;
         public Date StartDate;
         public Date EndDate;
         public String StartIdx;
         public String EndIdx;
          
         public String getDate(Date date)
         { 
        	 String strDate = "";
        	 try
        	 {
	        	 DateFormat format1 = new java.text.SimpleDateFormat("yyMMdd");
	        	 strDate = format1.format(date);
        	 }
        	 catch(Exception ex)
        	 {
        		 return strDate; 
        	 }
			return strDate; 
         }
         
         public String ToString(Date StartDate,Date EndDate)
         {
             return LineNo + "_" + getDate(StartDate) + StartIdx+ "_" + getDate(EndDate) + EndIdx;
         }
     } 
