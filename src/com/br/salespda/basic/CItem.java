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
        // ΪʲôҪ��дtoString()�أ���Ϊ����������ʾ���ݵ�ʱ����������������Ķ������ַ���������£�ֱ�Ӿ�ʹ�ö���.toString()  
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
