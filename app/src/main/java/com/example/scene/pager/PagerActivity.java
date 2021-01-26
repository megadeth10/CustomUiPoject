package com.example.scene.pager;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.custom.activity.ToolbarActivity;
import com.example.custom.view.pager.CustomViewPager;
import com.example.custom.view.pager.adapter.ImagePagerAdapter;
import com.example.custom.view.pager.adapter.item.ImagePagerItem;
import com.example.test.myapplication.R;
import com.example.utils.util;

import java.util.ArrayList;

public class PagerActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener{
    CustomViewPager mPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = PagerActivity.class.getSimpleName();
        this.getToolBar().setTitle("Infinity Pager View");
        this.getToolBar().inflateMenu(R.menu.menu_pager);
        this.getToolBar().setOnMenuItemClickListener(this);
        View view = View.inflate(this, R.layout.view_toolbar_button, getToolBar());
        view.findViewById(R.id.toolbar_btn1).setOnClickListener(this);
        view.findViewById(R.id.toolbar_btn2).setOnClickListener(this);

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
                logE(String.format("data title = %d", Integer.valueOf((Integer) data.getData())));
            }
        };
        mPager.setAdapter(new ImagePagerAdapter(this, list, clickListener));
        ViewGroup.LayoutParams layoutParams = mPager.getLayoutParams();
        Point scaledPoint = util.ratioSize(this, 375.0f, 200.0f, null);
        layoutParams.height = scaledPoint.y;
        logE(String.format("onCreate scaledPoint.y : %d", scaledPoint.y));
        mPager.setLayoutParams(layoutParams);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPager = null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.menu_add){
            this.showSnackBar("추가");
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String text;
        switch (id){
            case R.id.toolbar_btn1:
                text = "버튼 1";
                break;
            case R.id.toolbar_btn2:
                text = "버튼 2";
                break;
            default:
                text = "오류";
                break;
        }
        this.showSnackBar(text);
    }
}