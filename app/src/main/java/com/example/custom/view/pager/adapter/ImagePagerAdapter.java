package com.example.custom.view.pager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.custom.view.pager.adapter.item.ImagePagerItem;
import com.example.test.myapplication.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class ImagePagerAdapter extends BasePagerAdapter {
    public ImagePagerAdapter(Context ctx, ArrayList<?> datas, View.OnClickListener listener){
        super(ctx, datas, listener);

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realIndex = position % this.mDataList.size();

        ImagePagerItem item = (ImagePagerItem) this.mDataList.get(realIndex);
        ImageView view = (ImageView) View.inflate(this.mCtx, R.layout.pager_image_view, null);
        view.setBackgroundResource(item.getDrawable());
        view.setOnClickListener(this.mClickListener);
        container.addView(view);
        view.setTag(item);
        return view;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
