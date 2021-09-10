package com.example.custom.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.myapplication.R;
import com.example.utils.Log;
import com.google.android.material.snackbar.Snackbar;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends RxAppCompatActivity {
    protected String TAG = BaseActivity.class.getSimpleName();
    protected Snackbar mSnackBar;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
    }

    @Override
    protected void onDestroy() {
        this.TAG = null;
        this.mContext = null;
        if(this.mSnackBar != null) {
            this.mSnackBar.dismiss();
            this.mSnackBar = null;
        }
        super.onDestroy();
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
