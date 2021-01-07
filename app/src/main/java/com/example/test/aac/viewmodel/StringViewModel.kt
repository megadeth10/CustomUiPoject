package com.example.test.aac.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StringViewModel : ViewModel() {
    var data = MutableLiveData<String>()
        private set
//    val data
//        get() {
//        return _data
//    }
}