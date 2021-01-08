package com.example.test.databinding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.test.databinding.databindObject.DataBindingObject
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityDataBindingBinding

/**
 * View에 Data를 Binding 하는 샘플
 */
class DataBindingActivity: AppCompatActivity() {
    lateinit var binding: ActivityDataBindingBinding
    lateinit var dataBinding: DataBindingObject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding)
        dataBinding = DataBindingObject()
        binding.dataBinding = dataBinding
    }

    fun onClickChangeDate(view: View){
        var newData = DataBindingObject()
        newData.age = 12
        newData.title = "데이터 변경"
        newData.name = "이름이다."
        binding.dataBinding = newData
    }
}