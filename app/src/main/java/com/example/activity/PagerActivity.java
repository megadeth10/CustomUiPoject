package com.example.activity;

import android.os.Bundle;
import android.view.View;

import com.example.custom.activity.BaseActivity;
import com.example.custom.view.pager.CustomViewPager;
import com.example.custom.view.pager.adapter.ImagePagerAdapter;
import com.example.custom.view.pager.adapter.item.ImagePagerItem;
import com.example.myapplication.R;
import com.example.util.Log;

import java.util.ArrayList;

public class PagerActivity extends BaseActivity {
    CustomViewPager mPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = PagerActivity.class.getSimpleName();
        setTextToolBar("Infinity Pager View");

        setContentsLayout(R.layout.layout_pager);
        mPager = findViewById(R.id.custom_pager);
        ArrayList<ImagePagerItem> list = new ArrayList();
        list.add(new ImagePagerItem(R.drawable.pager_image_1, new Integer(1)));
        list.add(new ImagePagerItem(R.drawable.pager_image_2, new Integer(2)));
        list.add(new ImagePagerItem(R.drawable.pager_image_3, new Integer(3)));
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePagerItem data = (ImagePagerItem) view.getTag();
                Log.e(TAG,
                        String.format("data title = %d", Integer.valueOf((Integer) data.getData())));
            }
        };
        mPager.setAdapter(new ImagePagerAdapter(this, list, clickListener));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPager.startRolling();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPager.stopRolling();
    }
}