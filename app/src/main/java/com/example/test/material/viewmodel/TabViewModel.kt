package com.example.test.material.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.fragment.data.NormalItem
import com.example.test.material.data.TabData

class TabViewModel: ViewModel {
    private val _tabData: MutableLiveData<ArrayList<TabData>> = MutableLiveData(ArrayList(0))
    val tabData: LiveData<ArrayList<TabData>>
        get() = this._tabData

    private val _listData: MutableLiveData<ArrayList<NormalItem>> = MutableLiveData(ArrayList(0))
    val listData: LiveData<ArrayList<NormalItem>>
        get() = this._listData
    var pageNumber = -1
    constructor(): super(){
        this._tabData.value = this.getData()
        this._listData.value = this.getListData()
    }

    private fun getData():ArrayList<TabData>{
        val list = ArrayList<TabData>()
        for(i in 0..10){
            list.add(TabData("Tab Index_$i", i.toString()))
        }
        return list
    }

    private fun getListData():ArrayList<NormalItem>{
        val list = ArrayList<NormalItem>()
        for(i in 0..10){
            list.add(NormalItem("item ${this.pageNumber}-$i"))
        }
        this.pageNumber++
        return list
    }

    fun addList(){
        this._listData.value?.addAll(this.getListData())
        this._listData.value = this._listData.value
    }
}