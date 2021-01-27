package com.example.test.material.bindingadapter

import android.text.TextUtils
import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.util.StringUtil
import com.example.test.material.CheckboxActivity
import com.example.test.material.viewmodel.CheckViewModel

@BindingAdapter(value=["setCheck"], requireAll = true)
fun setCheck(checkbox:CheckBox, viewModel:CheckViewModel){
    checkbox.let {view ->
        view.setOnCheckedChangeListener{ compoundButton, state ->
            viewModel.let {
                it.setCheck(compoundButton.id.toString(), state)
            }
        }
    }
}

@BindingAdapter(value=["setCheckObserver", "activity"], requireAll = true)
fun setCheckObserver(checkbox: CheckBox, liveData: LiveData<HashMap<String, Boolean>>, activity: CheckboxActivity){
    liveData.let {
        val id = checkbox.id?.toString()
        if(!TextUtils.isEmpty(id)){
            val value = liveData.value!![id]
            if(checkbox.isChecked != value){
                checkbox.isChecked = value!!
            }
        }
    }
}

@BindingAdapter(value=["setAllCheck"], requireAll = true)
fun setAllCheck(checkbox: CheckBox, viewModel:CheckViewModel){
    checkbox.let { view ->
        view.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.let {
                it.setCheckAll(b)
            }
        }
    }
}

@BindingAdapter(value=["setAllCheckObserver", "activity"], requireAll = true)
fun setAllCheckObserver(checkbox: CheckBox, liveData: LiveData<Boolean>, activity: CheckboxActivity){
    liveData.let {
        liveData.observe(activity, Observer {
            if(checkbox.isChecked != it){
                checkbox.isChecked = it
            }
        })
    }
}