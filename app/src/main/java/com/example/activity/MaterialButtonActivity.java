package com.example.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.custom.activity.BaseActivity;
import com.example.myapplication.R;

public class MaterialButtonActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = MaterialButtonActivity.class.getSimpleName();

        setTextToolBar("Material Button");
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackBar("테스트 테스트");
            }
        });
        setContentsLayout(R.layout.layout_material_button);

        Button btn = findViewById(R.id.normal_btn);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.normal_btn){
            this.showSnackBar("테스트 테스트");
        }
    }
}