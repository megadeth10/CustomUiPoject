package com.example.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.network.okhttp3.IOkHttp3ApiCallback;
import com.example.network.okhttp3.OkHttp3NetworkLayer;
import com.example.network.okhttp3.response.appInfoResponse;
import com.example.network.okhttp3.response.errorResponse;
import com.example.network.okhttp3.response.resultResponse;
import com.example.utils.Log;

public class FourActivity extends AppCompatActivity implements IOkHttp3ApiCallback {
    private apiResponseAdapter adapter;
    RecyclerView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        adapter = new apiResponseAdapter(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int index = (int) v.findViewById(R.id.index).getTag();
                Log.e(FourActivity.class.getSimpleName(), Integer.toString(index));
                adapter.removeData(index);
            }
        });
        listView = findViewById(R.id.list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listView.getContext(), mLinearLayoutManager.getOrientation());
        listView.addItemDecoration(dividerItemDecoration);
        listView.setAdapter(adapter);

        Button btn = findViewById(R.id.getData);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        OkHttp3NetworkLayer.getAppInfo(this, appInfoResponse.class);
    }

    @Override
    public void onFailure(errorResponse obj) {

    }

    @Override
    public void onSuccess(resultResponse obj) {
        final appInfoResponse res = (appInfoResponse) obj;
        if(res.getCode() == 200){
            listView.post(new Runnable() {
                @Override
                public void run() {
                    adapter.setData(res);
                    Log.e(FourActivity.class.getSimpleName(), Integer.toString(adapter.getItemCount()));
                }
            });
        }
    }
}
