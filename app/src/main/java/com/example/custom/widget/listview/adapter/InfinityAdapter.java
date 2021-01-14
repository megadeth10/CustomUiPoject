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
    private boolean showProgress = true;

    public InfinityAdapter(View.OnClickListener listener) {
        this.init(listener);
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
     */
    private void init(View.OnClickListener listener) {
        this.mOnClickListener = listener;
        this.mItemArray = new ArrayList<>(0);
        this.mItemArray.add(null);
        this.showProgress = false;
    }

    public void resetAdapter() {
        this.init(this.mOnClickListener);
        notifyDataSetChanged();
    }

    /**
     * getter & setter
     */

    public List<T> getItemArray() {
        return mItemArray;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public void setItemArray(ArrayList<T> itemArray) {
        ArrayList<T> cloneObject = (ArrayList<T>) itemArray.clone();
        if (this.showProgress) {
            cloneObject.add(null);
        }
        this.mItemArray = cloneObject;
        Log.e(TAG, String.format("setItemArray() size: %s", this.mItemArray.size()));
        notifyDataSetChanged();
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
