package com.example.scene.infinitylistwithrxjava.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scene.infinitylist.data.Post
import com.example.scene.infinitylist.data.Posts
import com.example.scene.infinitylistwithrxjava.api.RxJavaGetPosts
import com.example.utils.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class PostRxJavaViewModel : ViewModel {
    private val TAG = PostRxJavaViewModel::class.java.simpleName
    private var mCaller: Single<*>? = null
    private val disposable = CompositeDisposable()

    private val _list: MutableLiveData<ArrayList<Post>> = MutableLiveData(ArrayList<Post>(0))
    val list: LiveData<ArrayList<Post>>
        get() = this._list

    private val _isEndItem: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEndItem: LiveData<Boolean>
        get() = this._isEndItem

    private val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = this._isRefreshing

    private var getApiPosts: RxJavaGetPosts
    private var pageNumber: Int = 0

    constructor(getPosts: RxJavaGetPosts) : super() {
        this.getApiPosts = getPosts
        getDataListWithKoin()
    }

    fun setEndItem(state: Boolean) {
        this._isEndItem.value = state
    }

    fun setIsRefreshing(state: Boolean) {
        this._isRefreshing.value = state
    }

    fun setList(list: ArrayList<Post>) {
        val currentList = this._list.value
        currentList?.addAll(list)
        this._list.value = currentList
    }

    fun reset() {
        val currentList = this._list.value
        currentList?.clear()
        this._list.value = currentList
        this.resetPageNumber()
        getDataListWithKoin()
    }

    private fun cancelCaller() {
        if (mCaller != null) {
            //TODO cancel을 해야하는데...
//            mCaller!!.cancel()
            mCaller = null
        }
    }

    private fun setCaller(caller: Single<*>?) {
        mCaller = caller
    }

    private fun addDisposabel(disposable: Disposable) {
        val size = this.disposable.size()
        Log.e(TAG, "addDisposabel() size: $size")
        this.disposable.add(disposable)
    }

    private fun clearDisposable() {
        this.disposable.dispose()
    }

    private fun nextPageNumber() = this.pageNumber++

    private fun prevPageNumber() = this.pageNumber--

    private fun resetPageNumber(){
        this.pageNumber = 0
    }

    /**
     * Rest API 호출용 함수 이며 Koin을 이용한 객체 사용
     */
    fun getDataListWithKoin() {
        getApiPosts.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Posts>() {
                    override fun onSuccess(t: Posts?) {
                        Log.e(TAG, "onSuccess()")
                        nextPageNumber()
                        setCaller(null)
                        setIsRefreshing(false)
//                        if (pageNumber > 5) {
//                            setEndItem(true)
//                            setList(java.util.ArrayList<Post>(0))
//                            return
//                        }
                        if (t != null) {
                            setEndItem(false)
                            t.data?.map {
                                it.title = "${it.title}-${pageNumber}"
                            }
                            setList(t.data as java.util.ArrayList<Post>)
                            return
                        }
                    }

                    override fun onError(e: Throwable?) {
                        setCaller(null)
                        setIsRefreshing(false)
                        Log.e(TAG, "onError() ${e?.message}")
                    }
                })
        this.addDisposabel(disposable)
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