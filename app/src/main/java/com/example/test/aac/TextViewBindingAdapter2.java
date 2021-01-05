package com.example.test.aac;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class TextViewBindingAdapter2 {
    @BindingAdapter("app:showPhone")
    public static void showPhone(View view, String phone){
        if(view instanceof TextView){
            ((TextView) view).setText(phone.replace("-", ":"));
        }
    }
}
