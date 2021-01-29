package com.example.scene.uilist.menu;

import com.example.scene.cardscroll.CardScrollActivity;
import com.example.scene.foldlist.FoldListActivity;
import com.example.scene.infinitylist.PostInfinityListActivity;
import com.example.scene.materialbutton.MaterialButtonActivity;
import com.example.scene.caollapsingtollbar.CollapsingToolbarLayoutActivity;
import com.example.scene.nestedscrollview.NestedScrollViewActivity;
import com.example.scene.scrollbehavior.ScrollingViewBehaviorActivity;
import com.example.scene.pager.PagerActivity;
import com.example.scene.infinitylistwithrxjava.PostListWithRxjavaActivity;
import com.example.scene.pagedlist.PostPagedListActivity;
import com.example.scene.rxjava.RxjavaSampleActivity;
import com.example.scene.storetest.StoreTestActivity;
import com.example.test.aac.AddUserProfileActivity;
import com.example.test.aac.FragmentBindingActivity;
import com.example.test.databinding.DataBindingActivity;
import com.example.test.material.BottomNavigationActivity;
import com.example.test.material.CheckboxActivity;
import com.example.test.material.ScrollTabActivity;

import java.util.ArrayList;

public class UIList {
    private ArrayList<MenuItem> mList = new ArrayList<>();

    public UIList(){
        if(mList.size() == 0){
            mList.add(new MenuItem("user Store Test", StoreTestActivity.class));
            mList.add(new MenuItem("Infinity List with Rxjava", PostListWithRxjavaActivity.class));
            mList.add(new MenuItem("Rxjava test Activity", RxjavaSampleActivity.class));
            mList.add(new MenuItem("Paged List Activity", PostPagedListActivity.class));
            mList.add(new MenuItem("Material Checkbox", CheckboxActivity.class));
            mList.add(new MenuItem("Material Scroll Tab", ScrollTabActivity.class));
            mList.add(new MenuItem("AAC Test Activity", AddUserProfileActivity.class));
            mList.add(new MenuItem("AAC Test Fragment", FragmentBindingActivity.class));
            mList.add(new MenuItem("DataBinding Activity", DataBindingActivity.class));
            mList.add(new MenuItem("Infinity Pager View", PagerActivity.class));
            mList.add(new MenuItem("Material Button List", MaterialButtonActivity.class));
            mList.add(new MenuItem("Card Horizontal ScrollView", CardScrollActivity.class));
            mList.add(new MenuItem("Bottom Navigation Activity", BottomNavigationActivity.class));
            mList.add(new MenuItem("Infinity List", PostInfinityListActivity.class));
            mList.add(new MenuItem("CollapsingToolbarLayout", CollapsingToolbarLayoutActivity.class));
            mList.add(new MenuItem("ScrollingViewBehavior", ScrollingViewBehaviorActivity.class));
            mList.add(new MenuItem("Fold List", FoldListActivity.class));
            mList.add(new MenuItem("NestedScrollView Activity", NestedScrollViewActivity.class));
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
