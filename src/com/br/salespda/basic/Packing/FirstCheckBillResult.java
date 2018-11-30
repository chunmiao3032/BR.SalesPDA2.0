package com.br.salespda.basic.Packing;

import java.io.Serializable;

public class FirstCheckBillResult implements Serializable{
	public boolean Success;
	public String ErrMsg;

	public String BillID;
 
	public String ReceivingBillNO;

	public String MakeDate;

	public String PackingID;
	public String PackingCode;
	public String PackingName;

	public String StorehouseID;
	public String StorehouseCode;
	public String StorehouseName;
	public String SecondCheckCount;
	public String LostBottleCount;
	public String BadBoxCount;
	public String BrokenBoxCount;
	public String SumClaimMoney;
	public String SumReturnForegiftMoney;
	public String TruckNO;
	public String ReturnMethod;
	public String FirstCheckUser;
	public String SecondCheckUser;
	public String MakeUser;
	public String Remark;
	public String ReceivingBillTypeName;
	public String IsBoxReback;
	public String PackingTypeName;

	public String BusinessType;

	public String FirstCheckDate;
	public String FirstCheckRemark;

	public String getBusinessType() {
		return ReceivingBillTypeName + (IsBoxReback.equals("1") ? "/∑µø’œ‰" : "");
	}
	
	public String CustomerCode;
	public String CustomerName;
}
