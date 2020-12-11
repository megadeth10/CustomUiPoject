package com.example.custom.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.R;
import com.example.util.Log;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity{
    protected String TAG = BaseActivity.class.getSimpleName();
    protected Snackbar mSnackBar;
    protected Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if(this.mSnackBar != null) {
            this.mSnackBar.dismiss();
            this.mSnackBar = null;
        }

        if(this.mToolBar != null){
            this.mToolBar = null;
        }
    }

    protected View setContentsLayout(int resource){
        ViewGroup viewGroup = findViewById(R.id.content_view);
        return View.inflate(this, resource, viewGroup);
    }

    protected void setTextToolBar(String text){
        this.mToolBar.setTitle(text);
    }

    protected void setNavigationOnClickListener(View.OnClickListener onClickListener){
        this.mToolBar.setNavigationOnClickListener(onClickListener);
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
}
