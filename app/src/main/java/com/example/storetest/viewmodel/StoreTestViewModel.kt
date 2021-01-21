package com.example.storetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.custom.activity.ToolbarActivity
import com.example.store.user.User
import com.example.storetestsub.viewmodel.StoreTestSubViewModel
import com.example.utils.Log
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * TODO: activity 종료 되어서 onCleared() 호출이 되지 않는다.
 * koin viewModel로 생성하면 lifecycle이 설정 되지 않은 듯하다. 일단. ViewModelProvider로 변경해봐야 겠다.
 * ref: https://gogorchg.tistory.com/entry/AndroidDI-ViewModel-onCleared-%ED%95%A8%EC%88%98%EA%B0%80-%ED%98%B8%EC%B6%9C-%EB%90%98%EC%A7%80-%EC%95%8A%EC%9D%84-%EB%95%8C
 */
class StoreTestViewModel(userStore:User): ViewModel() {
    private val userStore= userStore
    private val _userToken: MutableLiveData<String> = MutableLiveData(userStore.getToken())
    val userToken: LiveData<String>
        get() = this._userToken
    var isLogin: MutableLiveData<Boolean> = MutableLiveData(userStore.isLogin())
        private set
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
        this.disposable.clear()
        super.onCleared()
    }

    private fun setToken(){
        _userToken.value = userStore.getToken()
        this.isLogin()
    }

    fun login(){
        userStore.setToken("Activity에서 login 했다.")
        this.setToken()
    }

    fun logout(){
        userStore.deleteToken()
        this.setToken()
    }

    private fun isLogin() {
        isLogin.value = userStore.isLogin()
    }
}