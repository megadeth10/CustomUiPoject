package com.example.test.material.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.HashMap

class CheckViewModel: ViewModel {
    private var _checkAll: MutableLiveData<Boolean> = MutableLiveData(false)
    val checkAll: LiveData<Boolean>
        get() = this._checkAll
    private val _checkGroup: MutableLiveData<HashMap<String,Boolean>>
    val checkGroup: LiveData<HashMap<String, Boolean>>
        get() = this._checkGroup

    constructor(checkboxMap:HashMap<String, Boolean>): super(){
        this._checkGroup = MutableLiveData(checkboxMap)
        this.checkAllState()
    }

    /**
     * 전체 값 변경시에 하위 체크 박스 상태 변경 함수
     */
    fun setCheckAll(state: Boolean){
        if(this._checkAll.value != state){
            this._checkAll.value = state
            val checkMap = HashMap<String,Boolean>()

            this._checkGroup.value.let{
                it!!.entries.forEach { item ->
                    checkMap[item.key] = state
                }
            }
            this._checkGroup.value = checkMap
        }
    }

    /**
     * 개별 상태 변경시 전체 상태 체크 확인 함수
     */
    private fun checkAllState(){
        this._checkGroup.value.let {
            var stateAll = true
            this._checkGroup.value!!.forEach{
                stateAll = stateAll && it.value
            }
            if(this._checkAll.value != stateAll) {
                this._checkAll.value = stateAll
            }
        }
    }

    /**
     * 개별 상태 변경 함수
     */
    fun setCheck(key:String, value:Boolean){
        this._checkGroup.value.let {
            it!![key] = value
        }
        this.checkAllState()
    }
}