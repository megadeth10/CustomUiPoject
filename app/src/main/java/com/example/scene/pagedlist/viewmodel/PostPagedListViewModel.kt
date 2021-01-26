package com.example.scene.pagedlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.scene.infinitylist.data.Post
import com.example.scene.pagedlist.datasource.PostDataFactory

class PostPagedListViewModel : ViewModel{
    private var pagedListBuilder: LivePagedListBuilder<Int, Post>
    var pagedListData: LiveData<PagedList<Post>>
    private val sourceData = PostDataFactory()
    constructor():super(){
        val pagedListConfig = PagedList.Config.Builder()
                .setPageSize(16)
                .setInitialLoadSizeHint(16)
                .setPrefetchDistance(16)
                .setEnablePlaceholders(false)
                .build()
        this.pagedListBuilder = LivePagedListBuilder(sourceData, pagedListConfig)
        pagedListData = this.pagedListBuilder.setInitialLoadKey(0).build()
    }

    fun refresh(){
        pagedListData.value!!.dataSource.invalidate()
    }
}