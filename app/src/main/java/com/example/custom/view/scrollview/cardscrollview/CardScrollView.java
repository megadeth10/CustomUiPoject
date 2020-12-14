package com.example.custom.view.scrollview.cardscrollview;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.test.myapplication.R;
import com.example.utils.Log;

import java.util.ArrayList;

/**
 * Created by 이상현 on 2017-02-27.
 */

public class CardScrollView extends HorizontalScrollView {
    private String TAG = CardScrollView.class.getSimpleName();

    private ArrayList<ImagePosition> mImageWidth = null;
    private LinearLayout mRootView = null;
    private boolean isDraw = true;
    private int mCurrentScroll = 0;
    private int mTouchDownScroll = 0;
    private int mCurrentIndex = 0;
    private OnClickListener mOnClickListener = null;
    private iChangeScroll mIChangeScroll = null;
    public interface iChangeScroll{
        void changeScroll(int index, int size, Object item);
    }
    // 이미지 비율 계산용
    private final int IMAGE_WIDTH = 324;
    private final int IMAGE_HEIGHT = 165;
    // 이미지 비율 계산용
    /**
     * 전체 이미지 길이 계산 용
     */
    private class ImagePosition {
        private int viewWidth = 0;
        private int totalLength = 0;
        private int startPosition = 0;

        public ImagePosition() {
        }

        public int getStartPosition() {
            return startPosition;
        }

        public void setStartPosition(int startPosition) {
            this.startPosition = startPosition;
        }

        public int getViewWidth() {
            return viewWidth;
        }

        public void setViewWidth(int viewWidth) {
            this.viewWidth = viewWidth;
        }

        public int getTotalLength() {
            return totalLength;
        }

        public void setTotalLength(int totalLength) {
            this.totalLength = totalLength;
        }
    }

    public CardScrollView(Context context) {
        super(context);
        init(context);
    }

    public CardScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * view initialize
     * @param ctx
     */
    private void init(Context ctx) {
        this.mImageWidth = new ArrayList<ImagePosition>();
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.mCurrentScroll = 0;
        this.isDraw = true;
        this.mRootView = new LinearLayout(ctx);
        this.mRootView.setOrientation(LinearLayout.HORIZONTAL);
        this.setSmoothScrollingEnabled(true);
        addView(this.mRootView);
    }

    /**
     * scroll 변화 notify listener
     * @param changeScroll
     */
    public void setChangeScroll(iChangeScroll changeScroll){
        this.mIChangeScroll = changeScroll;
    }

    /**
     * imageSetting
     * @param list
     * @param changeScroll
     * @param clickListener
     */
    public void setImage(ArrayList<CardItem> list, iChangeScroll changeScroll, OnClickListener clickListener) {
        int size = list.size();
        int margin18 = (int) getContext().getResources().getDimension(R.dimen.dp18);
        int margin9  = (int) getContext().getResources().getDimension(R.dimen.dp9);

        setChangeScroll(changeScroll);
        setOnClickListener(clickListener);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int window_width = wm.getDefaultDisplay().getWidth();

        for (int i = 0; i < size; i++) {
            View layout = View.inflate(getContext(), R.layout.view_card_image, null);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));


            ImageView imageView  = (ImageView) layout.findViewById(R.id.Card_Iv);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();

            if (i == 0) { //시작 이미지
                if(size == 1) {
                    params.setMargins(margin18, 0, margin18, 0);
                } else {
                    params.setMargins(margin18, 0, 0, 0);
                }
                imageView.setLayoutParams(params);
            } else if (i == (size - 1)) {//마지막 이미지
                params.setMargins(margin9, 0, margin18, 0);
                imageView.setLayoutParams(params);
            } else {
                params.setMargins(margin9, 0, 0, 0);
                imageView.setLayoutParams(params);
            }

            params.width = window_width - margin18 - margin18;
            params.height = (int)(Float.valueOf(IMAGE_HEIGHT) * (Float.valueOf(params.width) / Float.valueOf(IMAGE_WIDTH)));
            CardItem item = list.get(i);
            String imageUrl = item.getUrl();
            if(TextUtils.isEmpty(imageUrl)){
                Glide.with(this)
                        .load(item.getDrawable())
                        .override(params.width, params.height)
                        .into(imageView);
            } else {
                Glide.with(this)
                        .load(imageUrl)
                        .override(params.width, params.height)
                        .into(imageView);
            }

            imageView.setTag(i);
            layout.setTag(item);
            mRootView.addView(layout);

            Log.i(TAG, "init() id : " + layout.getId());
        }
    }

    /**
     * 각 이미지 Layout의 관련 위치 값 계산
     */
    private void checkImageWidth() {
        mImageWidth.clear();
        int count = mRootView.getChildCount();
        int totalLength = 0;
        int dp9 = (int) getContext().getResources().getDimension(R.dimen.dp9);
        boolean isOperate = false;
        for (int i = 0; i < count; i++) {
            ImagePosition item = new ImagePosition();
            item.setViewWidth(mRootView.getChildAt(i).getWidth());
            totalLength += item.getViewWidth();
            if((i != 0) && !isOperate){
                isOperate = true;
                totalLength -= dp9;
            }
            item.setTotalLength(totalLength);
            item.setStartPosition(item.getTotalLength() - item.getViewWidth());
            mImageWidth.add(item);
        }
    }

    /**
     * horizontal Scroll 동작
     * @param increment
     */
    private void sendMessage(int increment){
        Log.i(TAG, "sendMessage() increment : " + increment);
        int size = mImageWidth.size();

        if(increment > 0){
            mCurrentIndex +=1;
        } else if(increment < 0){
            mCurrentIndex -=1;
        }

        if (mCurrentIndex < 0) {
            mCurrentIndex = 0;
        } else if (mCurrentIndex > (size - 1)) {
            mCurrentIndex = size - 1;
        }

        Log.i(TAG, "sendMessage() index : " + mCurrentIndex);
        final int startPosition = mImageWidth.get(mCurrentIndex).getStartPosition();

        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "setScroll() setScroll : " + startPosition);
                if(getContext() != null) {
                    smoothScrollTo(startPosition, 0);
                    if(mIChangeScroll != null){
                        mIChangeScroll.changeScroll(mCurrentIndex, mRootView.getChildCount(), mRootView.getChildAt(mCurrentIndex).getTag());
                    }
                }
            }
        }, 100);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mOnClickListener = l;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isDraw) {
            isDraw = false;
            checkImageWidth();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.w(TAG, "onScrollChanged() l : " + l);
        mCurrentScroll = l;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = mGestureDetector.onTouchEvent(ev);

        if(!handled){
            Log.w(TAG, "dispatchTouchEvent() action : " + ev.getAction() + " mCurrentScroll : " + mCurrentScroll);
            //long press 발생시에 onFling이 호출되지 않아서 예외처리함.
            if((ev.getAction() == MotionEvent.ACTION_UP)
                    || (ev.getAction() == MotionEvent.ACTION_CANCEL)){

                int size = mImageWidth.size();
                int index = 0;
                for (int i = 0; i < size; i++) {
                    int startPosition = mImageWidth.get(i).getStartPosition();
                    int halfImage = mImageWidth.get(i).getViewWidth() / 2;
                    int endLine = mImageWidth.get(i).getTotalLength();
                    if ((mCurrentScroll >= startPosition) && (mCurrentScroll < (startPosition + halfImage))) {
                        index = i;
                        Log.w(TAG, "onDown() action down  index : " + mCurrentIndex);
                        break;
                    }else if((mCurrentScroll >= (startPosition + halfImage)) && mCurrentScroll < endLine){
                        index = i + 1;
                        break;
                    }
                }

                if(mCurrentIndex == index){
                    sendMessage(0);
                } else if(mCurrentIndex > index){
                    sendMessage(-1);
                } else {
                    sendMessage(1);
                }

                return true;
            }
            handled = super.dispatchTouchEvent(ev);
        }
        return handled;
    }

    private GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
        private final float Y_VELOVITY = 2000.f;

        @Override
        public boolean onDown(MotionEvent e) {
            if(e.getAction() == MotionEvent.ACTION_DOWN) {
                mTouchDownScroll = mCurrentScroll;
                int size = mImageWidth.size();

                for (int i = 0; i < size; i++) {
                    int startPosition = mImageWidth.get(i).getStartPosition();
                    int halfImage = mImageWidth.get(i).getViewWidth() / 2;
                    if ((mTouchDownScroll >= startPosition) && (mTouchDownScroll < (startPosition + halfImage))) {
                        mCurrentIndex = i;
                        Log.w(TAG, "onDown() action down  index : " + mCurrentIndex);
                        break;
                    }
                }
            }
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {// 클릭
            Log.i(TAG, "onSingleTapUp() index : " + mCurrentIndex);
            if(mOnClickListener != null){
                mOnClickListener.onClick(mRootView.getChildAt(mCurrentIndex));
                return true;
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.w(TAG, "onScroll() distanceX : " + distanceX);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.w(TAG, "onLongPress() ");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.w(TAG, "onFling()  velocityX : " + velocityX);
            if(Y_VELOVITY <= Math.abs(velocityX)) {// fliking
                if (velocityX > 0) {//오른쪽
                    sendMessage(-1);
                } else if (velocityX < 0) {//왼쪽
                    sendMessage(1);
                }
                return true;
            } else {
                return false;
            }
        }
    });
}
