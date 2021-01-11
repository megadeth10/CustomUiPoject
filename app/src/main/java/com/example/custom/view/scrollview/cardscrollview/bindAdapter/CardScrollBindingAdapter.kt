package com.example.custom.view.scrollview.cardscrollview.bindAdapter

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.example.custom.view.scrollview.cardscrollview.CardItem
import com.example.custom.view.scrollview.cardscrollview.CardScrollView
import com.example.utils.Log

@BindingAdapter(value = ["setItem", "aaa"], requireAll = true)
fun setItem(view: CardScrollView, list: LiveData<ArrayList<CardItem>>, str: String){
    Log.e("CardScrollBindingAdapter", String.format("setItem() %s", str))
    view.setImage(list.value)
}