package com.example.scene.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scene.activity.menu.UIList


class HorizontalViewModel : ViewModel {
    private val _list: MutableLiveData<ArrayList<UIList.MenuItem>> = MutableLiveData(ArrayList(0))
    val list: LiveData<ArrayList<UIList.MenuItem>>
        get() = this._list

    constructor() : super() {
        this.fetchData()
    }

    fun setList(item: UIList.MenuItem) {
        this._list.value?.add(item)
    }

    fun setList(list: List<UIList.MenuItem>) {
        this._list.value?.addAll(list)
    }

    fun reset() {
        this._list.value?.clear()
    }

    private fun fetchData() {
        if (this._list.value?.size == 0) {
            val list: ArrayList<UIList.MenuItem> = ArrayList()
            list.add(UIList.MenuItem(String.format("aaaa"), null))
            list.add(UIList.MenuItem(String.format("bbbb"), null))
            list.add(UIList.MenuItem(String.format("cccc"), null))
            list.add(UIList.MenuItem(String.format("dddd"), null))
            list.add(UIList.MenuItem(String.format("eeee"), null))
            list.add(UIList.MenuItem(String.format("ffff"), null))
            list.add(UIList.MenuItem(String.format("gggg"), null))
            list.add(UIList.MenuItem(String.format("hhhh"), null))
            list.add(UIList.MenuItem(String.format("iiii"), null))
            list.add(UIList.MenuItem(String.format("jjjj"), null))
            list.add(UIList.MenuItem(String.format("kkkk"), null))
            list.add(UIList.MenuItem(String.format("llll"), null))
            list.add(UIList.MenuItem(String.format("mmmm"), null))
            list.add(UIList.MenuItem(String.format("nnnn"), null))
            this._list.value?.addAll(list)
        }
    }
}