package com.example.custom.widget.listview.data;

import com.example.custom.widget.listview.adapter.FoldItemAdapter;

import java.util.ArrayList;

public class FoldItem {
    private String title;
    private ArrayList<?> childItem;
    private boolean isFold;

    public FoldItem(){
        this.init("none title", null, true);
    }

    public FoldItem(String title, ArrayList<?> list, boolean fold) {
        this.init(title, list, fold);
    }

    void init(String title, ArrayList<?> list, boolean fold){
        this.title = title;
        this.childItem = list;
        this.isFold = fold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<?> getChildItem() {
        return childItem;
    }

    public void setChildItem(ArrayList<?> childItem) {
        this.childItem = childItem;
    }

    public boolean isFold() {
        return isFold;
    }

    public void setFold(boolean fold) {
        isFold = fold;
    }
}
