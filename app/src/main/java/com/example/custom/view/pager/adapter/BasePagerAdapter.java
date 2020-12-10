package com.example.custom.view.pager.adapter;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class BasePagerAdapter extends PagerAdapter {
    protected Context mCtx;
    protected ArrayList<?> mDataList;
    protected View.OnClickListener mClickListener;
    public BasePagerAdapter(Context ctx, ArrayList<?> datas, View.OnClickListener listener){
            this.mCtx = ctx;
            this.mDataList = datas;
            this.mClickListener = listener;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)  {
        return view == ((View) object);
    }

    public int getDataSize() {
        if(this.mDataList != null){
            return this.mDataList.size();
        }
        return 0;
    }
}
