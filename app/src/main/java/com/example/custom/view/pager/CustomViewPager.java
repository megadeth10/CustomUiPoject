package com.example.custom.view.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.custom.timer.CustomTimer;
import com.example.custom.view.pager.adapter.BasePagerAdapter;
import com.example.myapplication.R;
import com.example.utils.ExtraName;
import com.example.utils.Log;

import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends RelativeLayout {
    private RelativeLayout mRootView = null;
    private ViewPager mPager = null;
    private ViewPager.OnPageChangeListener mPageChangeLs = null;
    private int mIntListCount = 1;

    private ImageView[] mIndicators;
    private CustomTimer mTimer;
    private final int ROLLING_TIME = 5 * 1000;

    private VIEW_TYPE mViewType = VIEW_TYPE.INDICATOR_TYPE;

    public enum VIEW_TYPE {
        INDICATOR_TYPE, NUMBER_TYPE, BTN_NUMBER_TYPE, NOT_INDICATOR
    }

    private indicatorListener mIndicatorListener = null;

    public interface indicatorListener {
        void onClickIndicator();
    }

    public CustomViewPager(Context context, VIEW_TYPE type) {
        super(context);
        mViewType = type;
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        boolean userIndicator = attrs.getAttributeBooleanValue(ExtraName.ATTRIBUTE_NAME, "enable_indicator", false);
        boolean isNumberIndicator = attrs.getAttributeBooleanValue(ExtraName.ATTRIBUTE_NAME, "number_indicator", false);
        boolean isBtnNumberIndicator = attrs.getAttributeBooleanValue(ExtraName.ATTRIBUTE_NAME, "btn_number_indicator", false);
        boolean isNotIndicator = attrs.getAttributeBooleanValue(ExtraName.ATTRIBUTE_NAME, "not_indicator", false);

        if (isNumberIndicator) {
            mViewType = VIEW_TYPE.NUMBER_TYPE;
        } else if (isBtnNumberIndicator) {
            mViewType = VIEW_TYPE.BTN_NUMBER_TYPE;
        } else if (userIndicator) {
            mViewType = VIEW_TYPE.INDICATOR_TYPE;
        } else {
            mViewType = VIEW_TYPE.NOT_INDICATOR;
        }

        init(userIndicator);

        if (isInEditMode()) {
            return;
        }
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                changeViewState(arg0);
                if (mPageChangeLs != null) {
                    mPageChangeLs.onPageSelected(arg0 % mIntListCount);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

                if (mPageChangeLs != null) {
                    mPageChangeLs.onPageScrolled(arg0 / 100, arg1, arg2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mPager.getAdapter().getCount() > 1) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        stopRolling();
                    } else {
                        startRolling();
                    }
                }

                if (mPageChangeLs != null) {
                    mPageChangeLs.onPageScrollStateChanged(state);
                }
            }
        });

        mTimer = new CustomTimer("Rolling Timer", new CustomTimer.TickListener() {

            @Override
            public void onFinished(String name) {
                if (mPager != null) {
                    int index = mPager.getCurrentItem();
                    mPager.setCurrentItem(index + 1, true);
                }
                if (mTimer != null) {
                    mTimer.start();
                }
            }

            @Override
            public void UpdateTime(String name, long millis) {

            }
        }, ROLLING_TIME, ROLLING_TIME);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (mTimer != null) {
            mTimer.stop();
        }
        mTimer = null;
    }

    public boolean stopRolling() {
        boolean ret = false;
        if (mTimer != null) {
            ret = true;
            mTimer.stop();
        }

        return ret;
    }

    public void startRolling() {
        if (mTimer != null && !mTimer.getState() && this.mPager != null) {
            int realDataSize = ((BasePagerAdapter)this.mPager.getAdapter()).getDataSize();
            if(realDataSize > 1) {
                mTimer.start();
            }
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener changeLs) {
        this.mPageChangeLs = changeLs;
    }

    public void setIndicatorClickListener(indicatorListener indicatorClickListener) {
        this.mIndicatorListener = indicatorClickListener;
    }

    public void setAdapter(BasePagerAdapter adapter) {
        mPager.setAdapter(adapter);
        int realDataSize = adapter.getDataSize();
        int initialIndex = (Integer.MAX_VALUE / realDataSize) / 2;
        mPager.setCurrentItem(initialIndex);
        mIntListCount = realDataSize;
        setIndicater(realDataSize);

        if (mIntListCount == 1) {
            mPager.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        } else {
            mPager.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }
    }

    private void init(boolean userIndicator) {
        if (isInEditMode()) {
            return;
        }

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = (RelativeLayout) inflater.inflate(R.layout.viewpager, this, true);

        mPager = (ViewPager) mRootView.findViewById(R.id.pager);
        if (this.mViewType == VIEW_TYPE.INDICATOR_TYPE) {
            mRootView.findViewById(R.id.Number_Layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.Btn_Number_Layout).setVisibility(View.GONE);

            mIndicators = new ImageView[]{
                    (ImageView) mRootView.findViewById(R.id.First_IV),
                    (ImageView) mRootView.findViewById(R.id.Second_IV),
                    (ImageView) mRootView.findViewById(R.id.Third_IV),
                    (ImageView) mRootView.findViewById(R.id.Four_IV),
                    (ImageView) mRootView.findViewById(R.id.Five_IV),
                    (ImageView) mRootView.findViewById(R.id.Six_IV),
                    (ImageView) mRootView.findViewById(R.id.Seven_IV),
                    (ImageView) mRootView.findViewById(R.id.Eight_IV),
                    (ImageView) mRootView.findViewById(R.id.Nine_IV),
                    (ImageView) mRootView.findViewById(R.id.Ten_IV)
            };

            mRootView.findViewById(R.id.Indicator_Layout).setVisibility(userIndicator ? View.VISIBLE: View.GONE);
        } else if (this.mViewType == VIEW_TYPE.NUMBER_TYPE) {
            mRootView.findViewById(R.id.Indicator_Layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.Btn_Number_Layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.Number_Layout).setVisibility(View.VISIBLE);
        } else if (this.mViewType == VIEW_TYPE.BTN_NUMBER_TYPE) {
            mRootView.findViewById(R.id.Indicator_Layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.Number_Layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.Btn_Number_Layout).setVisibility(View.VISIBLE);

            mRootView.findViewById(R.id.Btn_Number_Layout).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIndicatorListener != null) {
                        mIndicatorListener.onClickIndicator();
                    }

                    return;
                }
            });
        } else {
            mRootView.findViewById(R.id.Indicator_Layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.Number_Layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.Btn_Number_Layout).setVisibility(View.GONE);
        }

        //프레쉬 마트 UI 고도화
        changeViewState(0);
        //프레쉬 마트 UI 고도화
    }

    private void setIndicater(int size) {
        if(size < 1){
            return;
        }

        if (this.mViewType == VIEW_TYPE.INDICATOR_TYPE) {
            mIndicators[0].setVisibility(View.VISIBLE);
            for (int i = 1; i < size; i++) {
                ImageView imageView = mIndicators[i];

                int isVisible = View.VISIBLE;
                imageView.setVisibility(isVisible);
            }
        } else if (this.mViewType == VIEW_TYPE.NUMBER_TYPE) {
            if (size <= 1) {
                mRootView.findViewById(R.id.Number_Layout).setVisibility(View.GONE);
            } else {
                TextView tv = (TextView) mRootView.findViewById(R.id.Number_Layout);

                tv.setText("1 / " + mPager.getAdapter().getCount());
            }
        } else {
            if (size == 0) {
                mRootView.findViewById(R.id.Number_Layout).setVisibility(View.GONE);
            } else {
                TextView select = (TextView) mRootView.findViewById(R.id.pager_count);
                TextView total = (TextView) mRootView.findViewById(R.id.pager_total_count);

                select.setText("1");
                total.setText("/" + mPager.getAdapter().getCount());
            }
        }
    }

    private void changeViewState(int index) {
        int realIndex = index % mIntListCount;
        Log.d(CustomViewPager.class.getSimpleName(),
                String.format("mIntListCount = %d index = %d", mIntListCount, index));
        if (this.mViewType == VIEW_TYPE.INDICATOR_TYPE) {
            for (int i = 0; i < mIndicators.length; i++) {
                ImageView imageView = mIndicators[i];

                if (realIndex == i) {
                    imageView.setSelected(true);
                } else {
                    imageView.setSelected(false);
                }
            }
        } else if (this.mViewType == VIEW_TYPE.NUMBER_TYPE) {
            TextView tv = (TextView) mRootView.findViewById(R.id.Number_Layout);
            int realCount = 1;

            try {
                realCount = mPager.getAdapter().getCount();
            } catch (Exception e) {
                realCount = 1;
            }

            tv.setText(String.valueOf((index % realCount) + 1) + " / " + String.valueOf(realCount));
        } else {
            TextView select = (TextView) mRootView.findViewById(R.id.pager_count);
            TextView total = (TextView) mRootView.findViewById(R.id.pager_total_count);
            int realCount = 1;

            try {
                realCount = mPager.getAdapter().getCount();
            } catch (Exception e) {
                realCount = 1;
            }

            select.setText(String.valueOf((index % realCount) + 1));
            total.setText("/" + String.valueOf(realCount));
        }
    }
}