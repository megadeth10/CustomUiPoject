package com.example.test.aac

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["app:showPhone"])
fun showPhone(view: View, phone: String) {
    if (view is TextView) {
        view.text = phone.replace("-", ":")
    }
}
