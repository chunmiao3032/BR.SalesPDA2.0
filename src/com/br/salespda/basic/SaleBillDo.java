package com.br.salespda.basic;

import java.util.ArrayList;
import java.util.List;

public class SaleBillDo 
{ 
    public String ProductCode;
    public String ProductName ;
    public String ProductShortName;
    public String Character;
    public String Count;
    public String CustomerID;
    public String CustomerCode;
    public String CustomerName;
    public String ID;
    public String MakeDate;
    public String MakeUser;
    public String ProductID;
    public String Remark;
    public String SalesBillNO;
    public String TruckNO;
    public String NumPerUnit;  
    public String ErrMsg;
    
    /*
     * 当前销货单对应的批号列表
     */
    public List<BatchNO> BatchNoList = new ArrayList<BatchNO>();
}

