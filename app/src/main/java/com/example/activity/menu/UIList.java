package com.example.activity.menu;


import com.example.activity.CardScrollActivity;
import com.example.activity.MaterialButtonActivity;
import com.example.activity.PagerActivity;
import com.example.custom.activity.BaseActivity;

import java.util.ArrayList;

public class UIList {
    private ArrayList<MenuItem> mList = new ArrayList<>();

    public UIList(){
        if(mList.size() == 0){
            mList.add(new MenuItem("PagerView", PagerActivity.class));
            mList.add(new MenuItem("Button List", MaterialButtonActivity.class));
            mList.add(new MenuItem("Card Horizontal ScrollView", CardScrollActivity.class));
        }
    }

    public ArrayList<?> getList(){
        return mList;
    }

    public class MenuItem{
        private String mTitle;
        private Class<?> mActivity;

        public MenuItem(String title, Class<?> cls){
            this.mTitle = title;
            this.mActivity = cls;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public Class<?> getActivity() {
            return mActivity;
        }

        public void setActivity(Class<?> activity) {
            this.mActivity = activity;
        }
    }
}
