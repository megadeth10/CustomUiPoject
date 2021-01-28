package com.example.test.material.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.material.viewmodel.BottomNavigationViewModel
import java.lang.IllegalStateException

class BottomNavigationViewModelFactory(private val initMenuId: Int):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BottomNavigationViewModel::class.java)){
            return BottomNavigationViewModel(initMenuId) as T
        } else {
            throw IllegalStateException()
        }
    }
}