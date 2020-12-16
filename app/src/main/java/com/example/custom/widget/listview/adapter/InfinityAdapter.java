package com.example.custom.widget.listview.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.custom.widget.listview.callback.iInfinityListCallback;
import com.example.test.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
 * @param <T>
 */
public class InfinityAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String TAG = InfinityAdapter.class.getSimpleName();
    private enum VIEW_TYPE {
        VIEW_ITEM(0),
        VIEW_PROGRESS(1);
        private final int value;

        VIEW_TYPE(int value) {
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    }

    private List<T> mItemArray = new ArrayList<T>();
    private View.OnClickListener mOnClickListener;
    private iInfinityListCallback iInfinityListCallback;
    // page의 끝을 체크
    private boolean isLastItem = false;
    // page 번호
    private int pageCount = 0;
    // page 당 item 갯수
    private int pagePerCount = 10;

    public InfinityAdapter(View.OnClickListener listener, iInfinityListCallback callback) {
        this.init(listener, callback, 10);
    }

    public InfinityAdapter(View.OnClickListener listener, iInfinityListCallback callback, int pagePerCount) {
        this.init(listener, callback, pagePerCount);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.mItemArray = null;
        this.mOnClickListener = null;
        this.iInfinityListCallback = null;
        this.TAG = null;
    }

    /**
     * 클래스 맴버변수 초기화 함수
     * @param listener
     * @param callback
     * @param pagePerCount
     */
    private void init(View.OnClickListener listener, iInfinityListCallback callback, int pagePerCount){
        this.mOnClickListener = listener;
        this.iInfinityListCallback = callback;
        this.mItemArray = new ArrayList<>();
        this.mItemArray.add(null);
        this.pagePerCount = pagePerCount;
        this.pageCount = 0;
        this.isLastItem = false;
    }

    /**
     * 리스트 초기화용
     */
    public void resetAdapter(){
        this.init(this.mOnClickListener, this.iInfinityListCallback, this.pagePerCount);
        notifyDataSetChanged();
    }

    /**
     * Getter & Setter
     */
    public List<T> getItemArray() {
        return mItemArray;
    }

    public void setItemArray(List<T> itemArray) {
        int itemSize = itemArray.size();
        int currentSize = this.mItemArray.size();
        this.mItemArray.remove(currentSize - 1);
        if(itemSize > 0) {
            pageCount++;
            this.mItemArray.addAll(itemArray);
            this.mItemArray.add(null);
            this.isLastItem = itemSize < pagePerCount;
        } else {
            this.isLastItem = true;
        }
        Log.e(TAG,String.format("setItemArray() isLastItem: %s", this.isLastItem));
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

    /**
     * Loading Footer Progress View Holder
     */
    private class ProgressViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE.VIEW_PROGRESS.getValue()){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_list_footer_progress, parent, false);
            ProgressViewHolder viewHolder = new ProgressViewHolder(view);
            return viewHolder;
        }
        return this.iInfinityListCallback.createView(parent, viewType, this.mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(!this.isLastItem && (getItemCount() - 1 == position)){
            return;
        }
        this.iInfinityListCallback.onBind(holder, position, this.mItemArray);
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
        VIEW_TYPE type = VIEW_TYPE.VIEW_ITEM;
        if (this.isLastItem){
            return type.getValue();
        }
        int sizeToIndex = this.getItemCount() - 1;
        type = sizeToIndex == position ? VIEW_TYPE.VIEW_PROGRESS : VIEW_TYPE.VIEW_ITEM;
        return type.getValue();
    }
}
