package com.example.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.activity.infinitylist.Retrofit2APIInterface;
import com.example.activity.infinitylist.item.InfinityItem;
import com.example.activity.infinitylist.item.Post;
import com.example.custom.activity.ToolbarActivity;
import com.example.custom.widget.listview.InfinityListView;
import com.example.custom.widget.listview.adapter.InfinityAdapter;
import com.example.custom.widget.listview.callback.iInfinityListCallback;
import com.example.network.retrofit2.Retrofit2NetworkLayer;
import com.example.test.myapplication.R;
import com.example.test.myapplication.databinding.ViewToolbarButtonBinding;
import com.example.utils.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfinityListActivity extends ToolbarActivity implements iInfinityListCallback, View.OnClickListener {
    private int REQUEST_DATA = 0;
    private Call<?> mCaller = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = InfinityListActivity.class.getSimpleName();
        this.binding.toolbar.setTitle("Infinity List");
        ViewToolbarButtonBinding toolbarBinding = ViewToolbarButtonBinding.inflate(getLayoutInflater());
        this.binding.toolbar.addView(toolbarBinding.getRoot());
        toolbarBinding.toolbarBtn1.setOnClickListener(this);
        toolbarBinding.toolbarBtn2.setVisibility(View.GONE);
//        View view = View.inflate(this, R.layout.view_toolbar_button, this.binding.toolbar);
//        view.findViewById(R.id.toolbar_btn1).setOnClickListener(this);
//        view.findViewById(R.id.toolbar_btn2).setVisibility(View.GONE);

        setContentsLayout(R.layout.layout_infinity_list);
        InfinityListView recyclerView = findViewById(R.id.list);
        recyclerView.setOnLoadMoreListener(new InfinityListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData();
            }
        });
        InfinityAdapter adapter = new InfinityAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post item = (Post) view.getTag();
                showSnackBar(item.getTitle());
            }
        },
                this);
        recyclerView.setAdapter(adapter);
        this.getDataList(0);

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, String.format("onRefresh"));
                refreshList();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mLoadDataHandler.removeCallbacksAndMessages(null);
        this.mLoadDataHandler = null;
        this.cancelCaller();

    }

    private Handler mLoadDataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == REQUEST_DATA) {
                InfinityListView recyclerView = findViewById(R.id.list);
                int count = ((InfinityAdapter) recyclerView.getAdapter()).getPageCount();
                getDataList(count);
                return true;
            }
            return false;
        }
    });

    @Override
    public void onBind(@NonNull RecyclerView.ViewHolder holder, int position, List<?> data) {
        Post item = (Post) data.get(position);
        ((InfinityViewHolder) holder).tv.setText(item.getTitle());
        ((LinearLayout) ((InfinityViewHolder) holder).tv.getParent()).setTag(item);
    }

    @Override
    public RecyclerView.ViewHolder createView(@NonNull ViewGroup parent, int viewType, View.OnClickListener clickListener) {
        View view = LayoutInflater.from(this).inflate(R.layout.menu_item, parent, false);
        view.setOnClickListener(clickListener);
        InfinityViewHolder viewHolder = new InfinityViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.toolbar_btn1:
                this.refreshList();
                break;
        }
    }

    private class InfinityViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv;

        public InfinityViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.title);
        }
    }

    private void refreshList() {
        this.resetList();
        this.getDataList(0);
        this.setRefresh();
    }

    private void resetList() {
        InfinityListView recyclerView = findViewById(R.id.list);
        ((InfinityAdapter) recyclerView.getAdapter()).resetAdapter();
    }

    private void setList(@NonNull List<Post> list) {
        List<Post> data = list;
        if (list == null || (list != null && list.size() == 0)) {
            data = new ArrayList<>(0);
        }
        InfinityListView recyclerView = findViewById(R.id.list);
        ((InfinityAdapter) recyclerView.getAdapter()).setItemArray(data);
        recyclerView.setLoaded();
    }

    private void cancelCaller() {
        if (this.mCaller != null) {
            this.mCaller.cancel();
            this.mCaller = null;
        }
    }

    private void setCaller(Call<?> caller) {
        this.mCaller = caller;
    }

    private void setRefresh() {
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh_layout);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * ============================= 테스트를 위해 만든 로직 =======================
     */
    private void getData() {
        Message msg = mLoadDataHandler.obtainMessage(REQUEST_DATA);
        mLoadDataHandler.removeMessages(REQUEST_DATA);
        // TODO: 테스트용으로 Delay 1초줌
        mLoadDataHandler.sendMessageDelayed(msg, 1000);
    }

    private void getDataList(int pageNumber) {
        List list = new ArrayList<Post>();
        for (int i = 0; i < 20; i++) {
            Post item = new Post();
            item.setId(i);
            item.setAuthor(String.format("bbbb %d-%d ", pageNumber, i));
            item.setTitle(String.format("bbbb %d-%d", pageNumber, i));
            list.add(item);
        }
        setList(list);
    }

    /**
     * Rest API 호출용 함수
     */
    private void getDataList() {
        Call<InfinityItem> caller = Retrofit2NetworkLayer.INSTANCE.getInstance().create(Retrofit2APIInterface.class).getPosts();
        this.setCaller(caller);
        caller.enqueue(
                new Callback<InfinityItem>() {
                    @Override
                    public void onResponse(Call<InfinityItem> call, Response<InfinityItem> response) {
                        Log.e(TAG, "onResponse()");
                        if (response.isSuccessful()) {
                            InfinityItem item = response.body();
                            if (item != null) {
                                setList(item.getData());
                            }
                        }
                        setRefresh();
                    }

                    @Override
                    public void onFailure(Call<InfinityItem> call, Throwable t) {
                        Log.e(TAG, String.format("onFailure() %s", t.getMessage()));
                        setRefresh();
                    }
                }
        );
    }
}
