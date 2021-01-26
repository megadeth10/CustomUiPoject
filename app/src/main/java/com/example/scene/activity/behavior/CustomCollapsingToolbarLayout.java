package com.example.scene.activity.behavior;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import com.example.utils.Log;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomCollapsingToolbarLayout extends CollapsingToolbarLayout {
    private String TAG = CustomCollapsingToolbarLayout.class.getSimpleName();

    public CustomCollapsingToolbarLayout(@NonNull Context context) {
        super(context);
    }

    public CustomCollapsingToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCollapsingToolbarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).addOnOffsetChangedListener(mToolbarOffsetListener);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        final ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(mToolbarOffsetListener);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.checkHeight();
    }

    private void checkHeight() {
        if (this.pinModeLayoutHeight == -1 || this.parallaxModeLayoutHeight == -1) {
            for (int i = 0, z = getChildCount(); i < z; i++) {
                final View child = getChildAt(i);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                switch (lp.getCollapseMode()) {
                    case LayoutParams.COLLAPSE_MODE_PIN:
                        if (this.pinModeLayoutHeight == -1) {
                            pinModeLayoutHeight = child.getHeight();
                            Log.e(TAG, String.format("checkHeight() pinModeLayoutHeight: %d", pinModeLayoutHeight));
                        }
                        break;
                    case LayoutParams.COLLAPSE_MODE_PARALLAX:
                        if (this.parallaxModeLayoutHeight == -1) {
                            parallaxModeLayoutHeight = child.getHeight();
                            Log.e(TAG, String.format("checkHeight() parallaxModeLayoutHeight: %d", parallaxModeLayoutHeight));
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private int pinModeLayoutHeight = -1;
    private int parallaxModeLayoutHeight = -1;
    private ToolbarOffsetListener mToolbarOffsetListener = new ToolbarOffsetListener();

    private void setChildViewHeight(int verticalOffset) {
        for (int i = 0, z = getChildCount(); i < z; i++) {
            final View child = getChildAt(i);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();

            switch (lp.getCollapseMode()) {
                case LayoutParams.COLLAPSE_MODE_PIN:
                    if (pinModeLayoutHeight != -1 && parallaxModeLayoutHeight != -1) {
                        float alpha = 255f * ((float) Math.abs(verticalOffset) / (float) (parallaxModeLayoutHeight - pinModeLayoutHeight));
                        //API21에서 verticalOffset값에 오차가 있어서 alpha값 필터를 적용함.
                        int adjustAlpha = (int) Math.min(255f, Math.max(0f, alpha));
                        ColorDrawable colorDrawable = (ColorDrawable) child.getBackground();
                        colorDrawable.setAlpha(adjustAlpha);
                        child.setBackground(colorDrawable);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private class ToolbarOffsetListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            setChildViewHeight(verticalOffset);
        }
    }
}
