package com.br.salespda.basic;

public class CItem {
	private String ID = "";  
    private String Value = "";  
    private String Code = "";
  
    public CItem() {  
        ID = "";  
        Value = "";  
        Code = "";
    }  
  
    public CItem(String _ID, String _Value,String _Code) {  
        ID = _ID;  
        Value = _Value;  
        Code = _Code;
    }  
  
    @Override  
    public String toString() {  
        // 为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()  
        // TODO Auto-generated method stub  
        return Value;  
    }  
  
    public String GetID() {  
        return ID;  
    }  
    
    public String GetCode() {  
        return Code;  
    }  
  
    public String GetValue() {  
        return Value;  
    }  
}
