package com.example.activity.menu;

import com.example.activity.CardScrollActivity;
import com.example.activity.FoldListActivity;
import com.example.activity.InfinityListActivity;
import com.example.activity.MaterialButtonActivity;
import com.example.activity.CollapsingToolbarLayoutActivity;
import com.example.activity.NestedScrollViewActivity;
import com.example.activity.PagerActivity;
import com.example.test.material.BottomNavigationActivity;

import java.util.ArrayList;

public class UIList {
    private ArrayList<MenuItem> mList = new ArrayList<>();

    public UIList(){
        if(mList.size() == 0){
            mList.add(new MenuItem("PagerView", PagerActivity.class));
            mList.add(new MenuItem("Button List", MaterialButtonActivity.class));
            mList.add(new MenuItem("Card Horizontal ScrollView", CardScrollActivity.class));
            mList.add(new MenuItem("Bottom Navigation Activity", BottomNavigationActivity.class));
            mList.add(new MenuItem("Infinity List", InfinityListActivity.class));
            mList.add(new MenuItem("CollapsingToolbarLayout", CollapsingToolbarLayoutActivity.class));
            mList.add(new MenuItem("Scrolling View Behavior", NestedScrollViewActivity.class));
            mList.add(new MenuItem("Fold List", FoldListActivity.class));
        }
    }

    public ArrayList<?> getList(){
        return mList;
    }

    public static class MenuItem{
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
