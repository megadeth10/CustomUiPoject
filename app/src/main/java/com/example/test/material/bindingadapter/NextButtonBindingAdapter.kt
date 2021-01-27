package com.example.test.material.bindingadapter

import android.content.Intent
import androidx.databinding.BindingAdapter
import com.example.test.material.CheckboxActivity
import com.example.utils.Log
import com.google.android.material.button.MaterialButton

@BindingAdapter(value=["setOnClick"], requireAll = true)
fun setOnClick(button:MaterialButton, activity:CheckboxActivity){
    Log.e("NextButtonBindingAdapter", "setOnClick activity: $activity")
    button.setOnClickListener {
        activity.let {
            val intent = Intent(activity, CheckboxActivity::class.java)
            intent.putExtra("name", "child")
            activity.startActivity(intent)
        }
    }
}