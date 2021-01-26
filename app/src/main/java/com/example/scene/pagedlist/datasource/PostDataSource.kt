package com.example.scene.pagedlist.datasource

import androidx.paging.ItemKeyedDataSource
import com.example.scene.infinitylist.api.GetPosts
import com.example.scene.infinitylist.data.Post
import com.example.scene.infinitylist.data.Posts
import com.example.scene.infinitylist.viewmodel.PostViewModel
import com.example.utils.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDataSource(private val getPosts: GetPosts): ItemKeyedDataSource<Int, Post>() {
    private val TAG = PostViewModel::class.java.simpleName
    private var pageNumber = 0
    private var mCaller: Call<*>? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Post>) {
        Log.e(TAG, "loadInitial() params: ${params.requestedInitialKey}")
        this.getData(callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Post>) {
        Log.e(TAG, "loadAfter() key: ${params.key}")
        pageNumber++
        this.getData(callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Post>) {
        Log.e(TAG, "loadBefore() key: ${params.key}")
    }

    override fun getKey(item: Post): Int {
        return item.id
    }

    private fun cancelCaller() {
        if (mCaller != null) {
            mCaller!!.cancel()
            mCaller = null
        }
    }

    private fun setCaller(caller: Call<*>?) {
        mCaller = caller
    }

    private fun getData(callback: LoadCallback<Post>) {
        this.cancelCaller()
        val caller = getPosts.getPosts()
        setCaller(caller)
        caller.enqueue(
                object : Callback<Posts?> {
                    override fun onResponse(call: Call<Posts?>, response: Response<Posts?>) {
                        setCaller(null)
                        Log.e(TAG, "onResponse()")
                        if (response.isSuccessful) {
                            val item = response.body()
                            if (item != null) {
                                item.data?.map {
                                    it.title = String.format("%s-%d",it.title, pageNumber)
                                }
                                callback.onResult(item.data as List<Post>)
                                return
                            }
                        }
                    }

                    override fun onFailure(call: Call<Posts?>, t: Throwable) {
                        setCaller(null)
                        Log.e(TAG, String.format("onFailure() %s", t.message))
//                        callback.onResult(ArrayList<Post>(0))
                    }
                }
        )
    }

    fun refresh(){
        invalidate()
    }
}