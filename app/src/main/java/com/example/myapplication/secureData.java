package com.example.myapplication;

public class secureData {
    private String ACCESS_TOKEN;
    private String BASE_URL;
    private boolean SECURE;

    public boolean isSECURE() {
        return SECURE;
    }

    public void setSECURE(boolean SECURE) {
        this.SECURE = SECURE;
    }

    public String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public void setACCESS_TOKEN(String ACCESS_TOKEN) {
        this.ACCESS_TOKEN = ACCESS_TOKEN;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public void setBASE_URL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }
}
