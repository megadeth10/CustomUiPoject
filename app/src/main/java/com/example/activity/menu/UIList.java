package com.example.activity.menu;

import com.example.activity.CardScrollActivity;
import com.example.activity.FoldListActivity;
import com.example.infinitylist.PostInfinityListActivity;
import com.example.activity.MaterialButtonActivity;
import com.example.activity.CollapsingToolbarLayoutActivity;
import com.example.activity.NestedScrollViewActivity;
import com.example.activity.ScrollingViewBehaviorActivity;
import com.example.activity.PagerActivity;
import com.example.test.aac.AddUserProfileActivity;
import com.example.test.aac.FragmentBindingActivity;
import com.example.test.databinding.DataBindingActivity;
import com.example.test.material.BottomNavigationActivity;

import java.util.ArrayList;

public class UIList {
    private ArrayList<MenuItem> mList = new ArrayList<>();

    public UIList(){
        if(mList.size() == 0){
            mList.add(new MenuItem("Infinity Pager View", PagerActivity.class));
            mList.add(new MenuItem("Material Button List", MaterialButtonActivity.class));
            mList.add(new MenuItem("Card Horizontal ScrollView", CardScrollActivity.class));
            mList.add(new MenuItem("Bottom Navigation Activity", BottomNavigationActivity.class));
            mList.add(new MenuItem("Infinity List", PostInfinityListActivity.class));
            mList.add(new MenuItem("CollapsingToolbarLayout", CollapsingToolbarLayoutActivity.class));
            mList.add(new MenuItem("ScrollingViewBehavior", ScrollingViewBehaviorActivity.class));
            mList.add(new MenuItem("Fold List", FoldListActivity.class));
            mList.add(new MenuItem("NestedScrollView Activity", NestedScrollViewActivity.class));
            mList.add(new MenuItem("AAC Test Activity", AddUserProfileActivity.class));
            mList.add(new MenuItem("AAC Test Fragment", FragmentBindingActivity.class));
            mList.add(new MenuItem("DataBinding Activity", DataBindingActivity.class));
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
