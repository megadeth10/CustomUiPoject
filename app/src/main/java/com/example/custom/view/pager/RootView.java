package com.example.custom.view.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.test.myapplication.R;
import com.example.utils.Log;

import androidx.annotation.Nullable;

public class RootView extends LinearLayout {
    public RootView(Context context) {
        super(context);
        init(context);
    }

    public RootView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RootView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RootView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        Log.e(RootView.class.getSimpleName(), "init");
        View view = View.inflate(context, R.layout.view_root, null);
        this.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
