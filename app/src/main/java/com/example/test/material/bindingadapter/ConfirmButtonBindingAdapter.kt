package com.example.test.material.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.custom.activity.ToolbarActivity
import com.example.test.material.CheckboxActivity
import com.example.test.material.viewmodel.CheckViewModel
import com.example.utils.Log
import com.google.android.material.button.MaterialButton

@BindingAdapter(value=["setDataObserver", "activity"], requireAll = true)
fun setDataObserver(button: MaterialButton, data: LiveData<Boolean>, activity:CheckboxActivity){
    Log.e("ConfirmButtonBindingAdapter", "setDataObserver activity: $activity")
    data.observe(activity, Observer {
        var enableBtn = false
        if(it){
            enableBtn = true
        }
        if(button.isEnabled != enableBtn){
            button.isEnabled = enableBtn
        }
    })
}

@BindingAdapter(value=["setOnClick"], requireAll = true)
fun setOnClick(button:MaterialButton, viewModel:CheckViewModel){
    button.setOnClickListener {
        viewModel.let {
            it.checkGroup.value?.entries!!.forEach { item ->
                Log.e("ConfirmButtonBindingAdapter", "confirm onClick() ${item.key}-${item.value}")
            }
        }
    }
}