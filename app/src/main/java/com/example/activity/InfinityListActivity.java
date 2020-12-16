package com.example.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.activity.item.InfinityItem;
import com.example.custom.activity.BaseActivity;
import com.example.custom.widget.listview.InfinityListView;
import com.example.custom.widget.listview.adapter.InfinityAdapter;
import com.example.custom.widget.listview.callback.iInfinityListCallback;
import com.example.test.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InfinityListActivity extends BaseActivity implements iInfinityListCallback, View.OnClickListener {
    private ArrayList<InfinityItem> mListData = new ArrayList();
    private int REQUEST_DATA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = InfinityListActivity.class.getSimpleName();
        this.mToolBar.setTitle("Infinity List");
        View view = View.inflate(this, R.layout.view_toolbar_button, this.mToolBar);
        view.findViewById(R.id.toolbar_btn1).setOnClickListener(this);
        view.findViewById(R.id.toolbar_btn2).setVisibility(View.GONE);

        mListData = (ArrayList<InfinityItem>) this.getDataList(0);

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
                InfinityItem item = (InfinityItem) view.getTag();
                showSnackBar(item.getTitle());
            }
        },
                this);
        recyclerView.setAdapter(adapter);
        adapter.setItemArray(this.mListData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mListData.clear();
        this.mListData = null;
        this.mLoadDataHandler.removeCallbacksAndMessages(null);
        this.mLoadDataHandler = null;
    }

    private Handler mLoadDataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == REQUEST_DATA){
                InfinityListView recyclerView = findViewById(R.id.list);
                int count = ((InfinityAdapter) recyclerView.getAdapter()).getPageCount();
                if(count < 3) {// TODO: 테스트용
                    ArrayList list = (ArrayList) getDataList(count);
                    ((InfinityAdapter) recyclerView.getAdapter()).setItemArray(list);
                } else {
                    ((InfinityAdapter) recyclerView.getAdapter()).setItemArray(new ArrayList());
                }
                recyclerView.setEndItem(((InfinityAdapter) recyclerView.getAdapter()).isLastItem());
                recyclerView.setLoaded();
                return true;
            }
            return false;
        }
    });

    @Override
    public void onBind(@NonNull RecyclerView.ViewHolder holder, int position, List<?> data) {
        InfinityItem item = (InfinityItem) data.get(position);
        ((InfinityViewHolder) holder).tv.setText(item.getTitle());
        ((LinearLayout)((InfinityViewHolder) holder).tv.getParent()).setTag(item);
    }

    @Override
    public RecyclerView.ViewHolder createView(@NonNull ViewGroup parent, int viewType,  View.OnClickListener clickListener) {
        View view = LayoutInflater.from(this).inflate(R.layout.menu_item, parent, false);
        view.setOnClickListener(clickListener);
        InfinityViewHolder viewHolder = new InfinityViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.toolbar_btn1:
                InfinityListView recyclerView = findViewById(R.id.list);
                ((InfinityAdapter) recyclerView.getAdapter()).resetAdapter();
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

    /**
     * ============================= 테스트를 위해 만든 로직 =======================
     */
    private void getData() {
        Message msg = mLoadDataHandler.obtainMessage(REQUEST_DATA);
        mLoadDataHandler.removeMessages(REQUEST_DATA);
        // TODO: 테스트용으로 Delay 1초줌
        mLoadDataHandler.sendMessageDelayed(msg, 1000);
    }

    private List getDataList(int pageNumber) {
        List list = new ArrayList();
        list.add(new InfinityItem(String.format("aaaa %d", pageNumber)));
        list.add(new InfinityItem(String.format("bbbb %d", pageNumber)));
        list.add(new InfinityItem(String.format("cccc %d", pageNumber)));
        list.add(new InfinityItem(String.format("dddd %d", pageNumber)));
        list.add(new InfinityItem(String.format("eeee %d", pageNumber)));
        list.add(new InfinityItem(String.format("ffff %d", pageNumber)));
        list.add(new InfinityItem(String.format("gggg %d", pageNumber)));
        list.add(new InfinityItem(String.format("hhhh %d", pageNumber)));
        list.add(new InfinityItem(String.format("iiii %d", pageNumber)));
        list.add(new InfinityItem(String.format("jjjj %d", pageNumber)));
        list.add(new InfinityItem(String.format("kkkk %d", pageNumber)));
        list.add(new InfinityItem(String.format("llll %d", pageNumber)));
        list.add(new InfinityItem(String.format("mmmm %d", pageNumber)));
        list.add(new InfinityItem(String.format("nnnn %d", pageNumber)));
        list.add(new InfinityItem(String.format("oooo %d", pageNumber)));
        list.add(new InfinityItem(String.format("pppp %d", pageNumber)));
        list.add(new InfinityItem(String.format("qqqq %d", pageNumber)));
        list.add(new InfinityItem(String.format("rrrr %d", pageNumber)));
        list.add(new InfinityItem(String.format("ssss %d", pageNumber)));
        list.add(new InfinityItem(String.format("tttt %d", pageNumber)));
        list.add(new InfinityItem(String.format("uuuu %d", pageNumber)));
        list.add(new InfinityItem(String.format("vvvv %d", pageNumber)));
        return list;
    }
}
