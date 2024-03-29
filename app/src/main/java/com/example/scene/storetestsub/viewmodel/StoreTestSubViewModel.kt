package com.example.scene.storetestsub.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scene.storetest.viewmodel.StoreTestViewModel
import com.example.store.user.User
import com.example.utils.Log
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

class StoreTestSubViewModel(user: User): ViewModel() {
    private val userStore = user
    var userToken: MutableLiveData<String> = MutableLiveData(userStore.getToken())
        private set

    private var tokenObserverable: PublishSubject<Boolean>? = null
    private val disposable = CompositeDisposable()
    init {
    }

    fun setLifeCycle(activity: RxAppCompatActivity) {
        tokenObserverable = this.userStore?.addTokenSubscribe(object : Observer<Boolean> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {
                disposable.add(d)
            }

            override fun onNext(t: Boolean?) {
                setToken()
            }

            override fun onError(e: Throwable?) {

            }
        },
                activity
        )
    }

    override fun onCleared() {
        if(this.tokenObserverable != null){
            userStore.removeTokenSubscribe(this.tokenObserverable!!)
        }
        this.disposable.dispose()
        super.onCleared()
    }
    private fun setToken(){
        this.userToken.postValue(userStore.getToken())
    }

    fun login(){
        userStore.setToken("sub Activity에서 login 했다")
    }

    fun logout(){
        userStore.deleteToken()
    }

    fun isLogin():Boolean = userStore.isLogin()
}