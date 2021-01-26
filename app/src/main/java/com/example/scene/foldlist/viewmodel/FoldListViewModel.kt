package com.example.scene.foldlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scene.uilist.menu.UIList
import com.example.custom.widget.listview.data.FoldItem

class FoldListViewModel: ViewModel {
    private val _list : MutableLiveData<ArrayList<FoldItem>> = MutableLiveData(ArrayList(0))
    val list: LiveData<ArrayList<FoldItem>>
        get() = this._list

    constructor():super(){
        if(this._list.value!!.size == 0){
            this.fetchData()
        }
    }

    fun setList(item: FoldItem){
        this._list.value?.add(item)
    }

    fun setList(list: ArrayList<FoldItem>){
        var currentList = this._list.value
        currentList?.addAll(list)
        this._list.value = currentList
    }

    fun reset(){
        this._list.value?.clear()
    }

    fun pagingData(){
        var list = this.getData()
        if(list?.size > 0){
            this.setList(list)
        }
    }

    private fun fetchData(){
        var list = this.getData()
        this._list.value!!.addAll(list)
    }

    private fun getData():ArrayList<FoldItem>{
        val list: ArrayList<FoldItem> = ArrayList()
        val childItem = ArrayList<UIList.MenuItem>()
        childItem.add(UIList.MenuItem("aaa-1111", null))
        childItem.add(UIList.MenuItem("aaa-2222", null))
        childItem.add(UIList.MenuItem("aaa-3333", null))
        childItem.add(UIList.MenuItem("aaa-4444", null))
        list.add(FoldItem("aaaa",childItem, true))
        list.add(FoldItem("bbbb",childItem, true))
        list.add(FoldItem("cccc",childItem, false))
        list.add(FoldItem("dddd",childItem, false))
        list.add(FoldItem("eeee",childItem, false))
        list.add(FoldItem("ffff",childItem, true))
        list.add(FoldItem("gggg",childItem, false))
        list.add(FoldItem("hhhh",childItem, true))
        list.add(FoldItem("iiii",null, true))
        list.add(FoldItem("jjjj",null, true))
        list.add(FoldItem("kkkk",null, true))
        list.add(FoldItem("llll",null, true))
        list.add(FoldItem("mmmm",null, true))
        list.add(FoldItem("nnnn",null, true))
        list.add(FoldItem("oooo",null, true))
        list.add(FoldItem("pppp",null, true))
        list.add(FoldItem("qqqq",null, true))
        list.add(FoldItem("rrrr",null, true))
        list.add(FoldItem("ssss",null, true))
        list.add(FoldItem("tttt",null, true))
        list.add(FoldItem("uuuu",null, true))
        list.add(FoldItem("vvvv",null, true))
        list.add(FoldItem("aaaa",null, true))
        list.add(FoldItem("bbbb",null, true))
        list.add(FoldItem("cccc",null, true))
        list.add(FoldItem("dddd",null, true))
        list.add(FoldItem("eeee",null, true))
        list.add(FoldItem("ffff",null, true))
        list.add(FoldItem("gggg",null, true))
        list.add(FoldItem("hhhh",null, true))
        list.add(FoldItem("iiii",null, true))
        list.add(FoldItem("jjjj",null, true))
        list.add(FoldItem("kkkk",null, true))
        list.add(FoldItem("llll",null, true))
        list.add(FoldItem("mmmm",null, true))
        list.add(FoldItem("nnnn",null, true))
        list.add(FoldItem("oooo",null, true))
        list.add(FoldItem("pppp",null, true))
        list.add(FoldItem("qqqq",null, true))
        list.add(FoldItem("rrrr",null, true))
        list.add(FoldItem("ssss",null, true))
        list.add(FoldItem("tttt",null, true))
        list.add(FoldItem("uuuu",null, true))
        list.add(FoldItem("vvvv",null, true))
        return list
    }
}