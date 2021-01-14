package com.example.custom.widget.listview.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.custom.widget.listview.callback.iInfinityListCallback;
import com.example.test.myapplication.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * InfinityAdapter Class
 * 무한 RecyclerView 어뎁터용
 *
 * @param <T> list Item Class
 *            TODO: ViewModel에 observe가 update로 전달 하는 data가 list 전체이기 때문에 이 부분에 오류가 생김
 */
abstract public class InfinityAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements iInfinityListCallback {
    protected String TAG = InfinityAdapter.class.getSimpleName();

    private enum VIEW_TYPE {
        VIEW_ITEM(0),
        VIEW_PROGRESS(1);
        private final int value;

        VIEW_TYPE(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    private List<T> mItemArray = new ArrayList<T>();
    private View.OnClickListener mOnClickListener;
    // page의 끝을 체크
    private boolean isLastItem = false;
    // page 번호
    private int pageCount = 0;
    // page 당 item 갯수
    private int pagePerCount = 10;

    public InfinityAdapter(View.OnClickListener listener) {
        this.init(listener, 10);
    }

    public InfinityAdapter(View.OnClickListener listener, int pagePerCount) {
        this.init(listener, pagePerCount);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.mItemArray = null;
        this.mOnClickListener = null;
        this.TAG = null;
    }

    /**
     * 클래스 맴버변수 초기화 함수
     *
     * @param listener
     * @param pagePerCount
     */
    private void init(View.OnClickListener listener, int pagePerCount) {
        this.mOnClickListener = listener;
        this.mItemArray = new ArrayList<>(0);
        this.mItemArray.add(null);
        this.pagePerCount = pagePerCount;
        this.pageCount = 0;
        this.isLastItem = false;
    }

    /**
     * 리스트 초기화용
     */
    public void resetAdapter() {
        this.init(this.mOnClickListener, this.pagePerCount);
        notifyDataSetChanged();
    }

    /**
     * Getter & Setter
     */
    public List<T> getItemArray() {
        return mItemArray;
    }

    public void setItemArray(ArrayList<T> itemArray) {
        int itemSize = itemArray.size();
        if (itemSize > 0) {
            pageCount++;
            ArrayList<T> cloneObject = (ArrayList<T>) itemArray.clone();
            cloneObject.add(null);
            this.mItemArray = cloneObject;
            this.isLastItem = false;
        } else {
            int currentItemSize = this.getItemCount();
            if (currentItemSize > 0) {
                T item = this.mItemArray.get(currentItemSize - 1);
                if (item == null) {
                    this.mItemArray.remove(currentItemSize - 1);
                }
            }
            this.isLastItem = true;
        }
        Log.e(TAG, String.format("setItemArray() isLastItem: %s", this.isLastItem));
        Log.e(TAG, String.format("setItemArray() size: %s", this.mItemArray.size()));
        notifyDataSetChanged();
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isLastItem() {
        return isLastItem;
    }

    public void setLastItem(boolean lastItem) {
        isLastItem = lastItem;
    }

    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    /**
     * Loading Footer Progress View Holder
     */
    private class ProgressViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE.VIEW_PROGRESS.getValue()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_list_footer_progress, parent, false);
            ProgressViewHolder viewHolder = new ProgressViewHolder(view);
            return viewHolder;
        }
        return this.createView(parent, viewType, this.mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE.VIEW_PROGRESS.getValue()) {
            return;
        }
        this.onBind(holder, position, this.mItemArray);
    }

    @Override
    public int getItemCount() {
        if (mItemArray == null) {
            return 0;
        }
        return this.mItemArray.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (this.isLastItem){
//            return VIEW_TYPE.VIEW_ITEM.getValue();
//        }
        T item = this.mItemArray.get(position);
        if (item == null) {
            return VIEW_TYPE.VIEW_PROGRESS.getValue();
        }
        return VIEW_TYPE.VIEW_ITEM.getValue();
    }
}
