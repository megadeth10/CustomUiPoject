package com.example.infinitylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.example.infinitylist.api.GetPosts
import com.example.infinitylist.data.Post
import org.koin.java.KoinJavaComponent.inject

class PostViewModel: ViewModel() {
    private val getPost = inject(GetPosts::class.java)
    private val _list:MutableLiveData<ArrayList<Post>> = MutableLiveData(ArrayList<Post>(0))
    val list: LiveData<ArrayList<Post>>
        get() = this._list

    fun setList(list: ArrayList<Post>){
        val currentList = this._list.value
        currentList?.addAll(list)
        this._list.value = currentList
    }

    fun reset(){
        val currentList = this._list.value
        currentList?.clear()
        this._list.value = currentList
    }
}