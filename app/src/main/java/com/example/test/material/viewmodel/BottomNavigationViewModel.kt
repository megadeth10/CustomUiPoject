package com.example.test.material.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomNavigationViewModel(initMenuId:Int): ViewModel() {
    private val _currentMenuId: MutableLiveData<Int> = MutableLiveData(initMenuId)
    val currentMenuId: LiveData<Int>
        get() = this._currentMenuId

    fun setCurrentMenuId(menuId:Int){
        this._currentMenuId.value = menuId
    }
}