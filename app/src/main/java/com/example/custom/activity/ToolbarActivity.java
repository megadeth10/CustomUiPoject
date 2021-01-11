package com.example.custom.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toolbar;
import com.example.test.myapplication.R;
import com.example.test.myapplication.databinding.ActivityBaseBinding;
import com.example.utils.Log;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class ToolbarActivity extends DialogActivity{
    protected ActivityBaseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base);

        setActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick");
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        this.binding = null;
        super.onDestroy();
    }

    protected Toolbar getToolBar(){
        Toolbar toolbar;
        try{
            toolbar = this.binding.toolbar;
        }catch (Exception e){
            return null;
        }
        return toolbar;
    }

    protected LinearLayout getContentView(){
        LinearLayout layout;
        try{
            layout = this.binding.contentView;
        }catch (Exception e){
            return null;
        }
        return layout;
    }

    protected View setContentsLayout(int resource){
        ViewGroup viewGroup = findViewById(R.id.content_view);
        return View.inflate(this, resource, viewGroup);
    }

    protected void setContentsLayout(View view){
        if(view != null) {
            binding.contentView.addView(view);
        }
    }

    protected void setContentsViewBinding(ViewDataBinding rootView){
        if(rootView != null) {
            binding.contentView.addView(rootView.getRoot());
        }
    }
}
