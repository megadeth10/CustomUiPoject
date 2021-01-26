package com.example.scene.pagedlist.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.scene.infinitylist.data.Post
import org.koin.java.KoinJavaComponent.inject

class PostDataFactory: DataSource.Factory<Int, Post>() {
    private val postDataSource = inject(PostDataSource::class.java)
    private val postLiveData: MutableLiveData<Lazy<PostDataSource>> = MutableLiveData(postDataSource)
    override fun create(): DataSource<Int, Post> {
        postLiveData.postValue(postDataSource)
        return postDataSource.value
    }
}