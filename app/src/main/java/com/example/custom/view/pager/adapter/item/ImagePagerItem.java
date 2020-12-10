package com.example.custom.view.pager.adapter.item;

public class ImagePagerItem {
    private String mUrl;
    private Integer mDrawable;
    private Object mData;
    public ImagePagerItem(String url, Object obj){
        this.mUrl = url;
        this.mData = obj;
    }
    public ImagePagerItem(Integer resource, Object obj){
        this.mDrawable = resource;
        this.mData = obj;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public Integer getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Integer drawable) {
        this.mDrawable = drawable;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }
}
