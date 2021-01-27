package com.example.test.material.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.material.viewmodel.CheckViewModel
import java.lang.IllegalStateException

class CheckViewModelFactory(private val checkMap: HashMap<String, Boolean>):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CheckViewModel::class.java)){
            return CheckViewModel(checkMap) as T
        } else{
            throw  IllegalStateException()
        }
    }
}