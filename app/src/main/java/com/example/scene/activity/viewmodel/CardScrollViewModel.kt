package com.example.scene.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.custom.view.scrollview.cardscrollview.CardItem
import com.example.test.myapplication.R

class CardScrollViewModel : ViewModel {
    private val _itemList: MutableLiveData<ArrayList<CardItem>> = MutableLiveData(ArrayList(0))
    val itemList : LiveData<ArrayList<CardItem>>
        get() = this._itemList

    constructor(): super(){
        this._itemList.value?.add(CardItem(R.drawable.pager_image_1, Integer(1)))
        this._itemList.value?.add(CardItem(R.drawable.pager_image_2, Integer(2)))
        this._itemList.value?.add(CardItem(R.drawable.pager_image_3, Integer(3)))
    }

    fun setItem(item: CardItem){
        this._itemList.value?.add(item)
    }

    fun setItem(list: List<CardItem>){
        this._itemList.value?.addAll(list)
    }

    fun reset(){
        this._itemList.value?.clear()
    }
}