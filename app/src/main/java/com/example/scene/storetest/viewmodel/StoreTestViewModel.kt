package com.example.scene.storetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.user.User
import com.example.utils.Log
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * TODO : rxjava subscribe를 activity lifecycle에 맞추어야 한다. activity backstack에 있어도 onNext가 호출된다.
 */
class StoreTestViewModel(userStore:User): ViewModel() {
    private val userStore= userStore
    private val _userToken: MutableLiveData<String> = MutableLiveData(userStore.getToken())
    val userToken: LiveData<String>
        get() = this._userToken
    private var tokenObserverable:PublishSubject<Boolean>? = null
    private val disposable = CompositeDisposable()
    init {
        tokenObserverable = this.userStore.addTokenSubscribe(object : Observer<Boolean> {
            override fun onComplete() {
                Log.e(TAG, "tokenObserverable onComplete()")
            }

            override fun onSubscribe(d: Disposable?) {
                disposable.add(d)
                Log.e(TAG, "tokenObserverable onSubscribe()")
            }

            override fun onNext(t: Boolean?) {
                Log.e(TAG, "tokenObserverable onNext()$t")
                setToken()
            }

            override fun onError(e: Throwable?) {
                Log.e(TAG, "tokenObserverable onError()")
            }
        })
    }

    companion object{
        private val TAG = StoreTestViewModel::class.java.simpleName
    }

    override fun onCleared() {
        Log.e(TAG, "onCleared()")
        if(tokenObserverable != null) {
            userStore.removeTokenSubscribe(tokenObserverable!!)
        }
        this.disposable.dispose()
        super.onCleared()
    }

    private fun setToken(){
//        _userToken.value = userStore.getToken()
        // TODO: postValue는 background에서 수행되기 때문에 바로 다음 line에서 변경된 값을 받지 못한다.
        // 다만 변경된 값에서 서로 다른 liveData를 변경하는 것이기 때문에 문제가 없을것 같다.
        _userToken.postValue(userStore.getToken())
    }

    fun login(){
        userStore.setToken("Activity에서 login 했다.")
    }

    fun logout() = userStore.deleteToken()

    fun isLogin():Boolean = userStore.isLogin()
}