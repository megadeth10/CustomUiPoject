package com.example.scene.infinitylist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scene.infinitylist.api.GetPosts
import com.example.scene.infinitylist.data.Post
import com.example.scene.infinitylist.data.Posts
import com.example.utils.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel: ViewModel {
    private val TAG = PostViewModel::class.java.simpleName
    private var mCaller: Call<*>? = null

    private val _list:MutableLiveData<ArrayList<Post>> = MutableLiveData(ArrayList<Post>(0))
    val list: LiveData<ArrayList<Post>>
        get() = this._list

    private val _isEndItem: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEndItem: LiveData<Boolean>
        get() = this._isEndItem

    private val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = this._isRefreshing

    private var getApiPosts: GetPosts
    private var pageNumber: Int = 0

    constructor(getPosts: GetPosts): super(){
        this.getApiPosts = getPosts
        getDataListWithKoin()
    }

    fun setEndItem(state: Boolean){
        this._isEndItem.value = state
    }

    fun setIsRefreshing(state: Boolean){
        this._isRefreshing.value = state
    }

    fun setList(list: ArrayList<Post>){
        val currentList = this._list.value
        currentList?.addAll(list)
        this._list.value = currentList
    }

    fun reset(){
        val currentList = this._list.value
        currentList?.clear()
        this._list.value = currentList
        pageNumber = 0
        getDataListWithKoin()
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

    /**
     * Rest API 호출용 함수 이며 Koin을 이용한 객체 사용
     */
    fun getDataListWithKoin() {
        this.cancelCaller()
        val caller = getApiPosts.getPosts()
        setCaller(caller)
        pageNumber++
        caller.enqueue(
                object : Callback<Posts?> {
                    override fun onResponse(call: Call<Posts?>, response: Response<Posts?>) {
                        setCaller(null)
                        setIsRefreshing(false)
                        Log.e(TAG, "onResponse()")
                        if (pageNumber > 5) {
                            setEndItem(true)
                            setList(java.util.ArrayList<Post>(0))
                            return
                        }
                        if (response.isSuccessful) {
                            val item = response.body()
                            if (item != null) {
                                setEndItem(false)
                                setList(item.data as java.util.ArrayList<Post>)
                                return
                            }
                        }
                    }

                    override fun onFailure(call: Call<Posts?>, t: Throwable) {
                        setCaller(null)
                        setIsRefreshing(false)
                        setEndItem(false)
                        Log.e(TAG, String.format("onFailure() %s", t.message))
                        getSampleDataList(0)
                    }
                }
        )
    }

    private fun getSampleDataList(pageNumber: Int) {
        val list = java.util.ArrayList<Post>()
        for (i in 0..19) {
            val item = Post()
            item.id = i
            item.author = String.format("bbbb %d-%d ", pageNumber, i)
            item.title = String.format("bbbb %d-%d", pageNumber, i)
            list.add(item)
        }
        setList(list)
    }
}