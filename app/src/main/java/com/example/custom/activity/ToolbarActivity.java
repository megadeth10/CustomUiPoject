package com.example.custom.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;
import com.example.test.myapplication.R;
import com.example.utils.Log;

public class ToolbarActivity extends DialogActivity{
    protected Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_base);

        mToolBar = findViewById(R.id.toolbar);
        setActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick");
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        this.mToolBar = null;
        super.onDestroy();
    }

}
