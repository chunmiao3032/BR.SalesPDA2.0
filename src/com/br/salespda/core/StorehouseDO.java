package com.br.salespda.core;

public class StorehouseDO {
	 // Methods
    public String ToString()
    {
        return this.StorehouseCode + "|" + this.StorehouseName ;
    }

    // Properties
    public long ID ;

    public String Remark ;

    public String StorehouseCode ;

    public String StorehouseName;
}
