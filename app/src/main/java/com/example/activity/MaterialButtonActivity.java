package com.example.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.custom.activity.BaseActivity;
import com.example.test.myapplication.R;

// Material Component 사용시에 Style를 @style/Theme.AppCompat.DayNight.NoActionBar 이게 아니고
// @style/Theme.MaterialComponents.Light.NoActionBar를 사용해야 inflate 된다.
// ref : https://material.io/develop/android/components/buttons
public class MaterialButtonActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = MaterialButtonActivity.class.getSimpleName();

        this.mToolBar.setTitle("Material Button");
//        setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showSnackBar("테스트 테스트");
//            }
//        });
        setContentsLayout(R.layout.layout_material_button);

        setClickListener(R.id.normal_btn);
        setClickListener(R.id.text_btn);
        setClickListener(R.id.text_icon_btn);
        setClickListener(R.id.text_outline_btn);
    }

    public void setClickListener(int id){
        Button btn = findViewById(id);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        try{
            TextView tv = findViewById(id);
            this.showSnackBar((String) tv.getText());
        }catch (Exception e){

        }
    }
}