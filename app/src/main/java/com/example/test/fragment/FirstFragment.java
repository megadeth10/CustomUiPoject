package com.example.test.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.fragment.adapter.NormalAdapter;
import com.example.test.fragment.data.NormalItem;
import com.example.test.myapplication.R;
import com.example.utils.Log;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FirstFragment extends Fragment implements View.OnClickListener{
    private String TAG = FirstFragment.class.getSimpleName();
    private Object mData;
    private ArrayList<NormalItem> mListData = new ArrayList();
    public FirstFragment(Object data){
        this.mData = data;
        mListData.add(new NormalItem("aaaa"));
        mListData.add(new NormalItem("bbbb"));
        mListData.add(new NormalItem("cccc"));
        mListData.add(new NormalItem("dddd"));
        mListData.add(new NormalItem("eeee"));
        mListData.add(new NormalItem("ffff"));
        mListData.add(new NormalItem("gggg"));
        mListData.add(new NormalItem("hhhh"));
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        NormalAdapter adapter = new NormalAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setItemArray(mListData);

        return view;
    }

    @Override
    public void onClick(View view) {
        TextView tv = view.findViewById(R.id.title);
        NormalItem obj = (NormalItem) tv.getTag();
        Log.e(TAG, String.format("onClick : %s", obj.getTitle()));
    }
}
