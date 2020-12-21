package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.activity.menu.MenuAdapter;
import com.example.activity.menu.UIList;
import com.example.test.myapplication.R;

public class UIListActivity extends AppCompatActivity {
    private UIList mListData = new UIList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_u_i);

        RecyclerView recyclerView = findViewById(R.id.ui_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.list_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        MenuAdapter adapter = new MenuAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIList.MenuItem item = (UIList.MenuItem) view.getTag();
                if(item != null){
                    callActivity(item);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setItemArray(mListData.getList());
    }

    private void callActivity(UIList.MenuItem item){
        Intent intent = new Intent(UIListActivity.this, item.getActivity());
        startActivity(intent);
    }
}