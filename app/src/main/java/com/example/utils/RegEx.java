package com.example.utils;

public class RegEx {
    public static final String CELL_PHONE_NUMBER= "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";
    public static final String IS_CELL_PHONE_NUMBER= "^\\s*(010|011|012|013|014|015|016|017|018|019)\\s*$";
    public static final String IS_PHONE_NUMBER= "^\\s*(010|011|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";
    public static final String HOME_PHONE_NUMBER = "^\\s*(02|031|032|033|041|042|043|044|051|052|053|054|055|061|062|063|064|070)?(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$";
}
