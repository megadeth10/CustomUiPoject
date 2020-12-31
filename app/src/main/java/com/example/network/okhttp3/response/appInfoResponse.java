package com.example.network.okhttp3.response;

public class appInfoResponse extends resultResponse {
    private String appType;
    private version version;

    public appInfoResponse(int code, String message){
        super(code, message);
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public com.example.network.okhttp3.response.version getVersion() {
        return version;
    }

    public void setVersion(com.example.network.okhttp3.response.version version) {
        this.version = version;
    }
}
