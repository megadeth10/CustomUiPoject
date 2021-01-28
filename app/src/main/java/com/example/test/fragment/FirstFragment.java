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
    private ArrayList<NormalItem> mListData = new ArrayList();
    private static String LIST_BUNDLE_NAME = "list";
    /**
     * TODO : Fragment 사용시 빈생성자 정의 해줘야 한다. 화면 rotate시에  could not find fragment constructor exception 발생
     * ref : https://black-jin0427.tistory.com/250
     */
    public static FirstFragment newInstance(Object obj){
        FirstFragment fragment = new FirstFragment();
        Bundle bundle = new Bundle();

        Object d = obj;
        ArrayList<NormalItem> list = new ArrayList(0);
        list.add(new NormalItem("aaaa"));
        list.add(new NormalItem("bbbb"));
        list.add(new NormalItem("cccc"));
        list.add(new NormalItem("dddd"));
        list.add(new NormalItem("eeee"));
        list.add(new NormalItem("ffff"));
        list.add(new NormalItem("gggg"));
        list.add(new NormalItem("hhhh"));
        bundle.putSerializable(LIST_BUNDLE_NAME, list);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate()");
        this.mListData = (ArrayList<NormalItem>) getArguments().getSerializable(LIST_BUNDLE_NAME);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        NormalAdapter adapter = new NormalAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setItemArray(mListData);
    }

    @Override
    public void onClick(View view) {
        TextView tv = view.findViewById(R.id.title);
        NormalItem obj = (NormalItem) tv.getTag();
        Log.e(TAG, String.format("onClick : %s", obj.getTitle()));
    }
}
