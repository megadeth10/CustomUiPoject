package com.example.custom.widget.listview;

import android.content.Context;
import android.util.AttributeSet;

import com.example.test.myapplication.R;
import com.example.utils.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InfinityListView extends RecyclerView {
    private final String TAG = InfinityListView.class.getSimpleName();
    // get data
    private boolean loading;
    private OnLoadMoreListener loadMoreListener;
    private int VISIBLE_THRESHOLD = 1;
    private boolean isEndItem;
    // get data

    public InfinityListView(@NonNull Context context) {
        super(context);
        init();
    }

    public InfinityListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InfinityListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        addItemDecoration(dividerItemDecoration);
        this.loading = false;
        this.isEndItem = false;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (dx == 0 && dy == 0)
            return;
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        int totalItemCount = linearLayoutManager.getItemCount();
        int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        if (!this.loading && totalItemCount != 0 && !isEndItem
                && totalItemCount <= lastVisibleItem + this.VISIBLE_THRESHOLD) {
            if (this.loadMoreListener != null) {
                Log.e(this.TAG, String.format("onScrolled() totalItemCount: %d lastVisibleItem: %d",
                        totalItemCount, lastVisibleItem));
                this.loading = true;
                this.loadMoreListener.onLoadMore();
            }
        }
    }

    // get data
    public void setLoaded() {
        this.loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public boolean isEndItem() {
        return isEndItem;
    }

    public void setEndItem(boolean endItem) {
        isEndItem = endItem;
    }
    // get data
}
