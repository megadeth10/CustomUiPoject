package com.example.network;

import com.example.myapplication.BuildConfig;

public class networkConstans {
    static public String APP_TOKEN = BuildConfig.ACCESS_TOKEN;
    static private String BASE_URL = BuildConfig.BASE_URL;
    static public int DEFAULT_TIMEOUT = 1000;
    static private String BRANCH_URL_PREFIX = "/api-bs";
    static private String ORDER_URL_PREFIX = "/api-os";
    static private String USER_URL_PREFIX = "/api-us";
    static private String AUTH_URL_PREFIX = "/api-as";
    static private String COMMON_URL_PREFIX = "/api-cs";
    static private String PAY_URL_PREFIX = "/api-ps/v1/nicepay/payment?method=";
    static private String EVENT_URL_PREFIX = "/api-es";
    static private String PAYMENT_URL_PREFIX = "/api-pns";
    static public String BRANCH_URL = networkConstans.BASE_URL +  networkConstans.BRANCH_URL_PREFIX;
    static public String ORDER_URL = networkConstans.BASE_URL +  networkConstans.ORDER_URL_PREFIX;
    static public String USER_URL =  networkConstans.BASE_URL +  networkConstans.USER_URL_PREFIX;
    static public String AUTH_URL =  networkConstans.BASE_URL +  networkConstans.AUTH_URL_PREFIX;
    static public String COMMON_URL =  networkConstans.BASE_URL +  networkConstans.COMMON_URL_PREFIX;
    static public String PAY_URL =  networkConstans.BASE_URL +  networkConstans.PAY_URL_PREFIX;
    static public String EVENT_URL =  networkConstans.BASE_URL +  networkConstans.EVENT_URL_PREFIX;
    static public String PAYMENT_URL =  networkConstans.BASE_URL +  networkConstans.PAYMENT_URL_PREFIX;
}
