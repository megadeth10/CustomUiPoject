package com.example.custom.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.test.myapplication.R;
import com.example.utils.Log;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity{
    protected String TAG = BaseActivity.class.getSimpleName();
    protected Snackbar mSnackBar;
    protected Toolbar mToolBar;
    protected Context mContext;

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
        super.onDestroy();
        this.TAG = null;
        this.mToolBar = null;
        this.mContext = null;
        if(this.mSnackBar != null) {
            this.mSnackBar.dismiss();
            this.mSnackBar = null;
        }
    }

    protected View setContentsLayout(int resource){
        ViewGroup viewGroup = findViewById(R.id.content_view);
        return View.inflate(this, resource, viewGroup);
    }

    protected void showSnackBar(String message){
        View view = findViewById(R.id.layout_root);
        mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("확인", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getBaseContext(), "snackbar 닫기",Toast.LENGTH_SHORT).show();
                    }
                })
                .setBackgroundTint(getResources().getColor(R.color.color_FFFF80))
                .setTextColor(getResources().getColor(R.color.blue))
                .setActionTextColor(getResources().getColor(R.color.red));
        mSnackBar.show();
    }

    protected void logE(String msg){
        Log.e(this.TAG, msg);
    }

    protected void logI(String msg){
        Log.i(this.TAG, msg);
    }

    protected void logW(String msg){
        Log.w(this.TAG, msg);
    }

    protected void logD(String msg){
        Log.d(this.TAG, msg);
    }
}
