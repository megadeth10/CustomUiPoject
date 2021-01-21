package com.example.store.user

import android.app.Application
import com.example.secure.secure
import com.example.storetest.viewmodel.StoreTestViewModel
import com.example.utils.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class User {
    private var application: Application
    private var _token: String? = null
    private var tokenObserverableArray: ArrayList<PublishSubject<Boolean>> = ArrayList(0)
    constructor(application: Application) {
        Log.e(TAG, "constructor()")
        this.application = application
    }

    fun addTokenSubscribe(observer: Observer<Boolean>): PublishSubject<Boolean>? {
        Log.e(TAG, "addTokenSubscribe() size: ${this.tokenObserverableArray.size}")
        var observerable = PublishSubject.create<Boolean>()
        observerable.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
        observerable.subscribe(observer)
        this.tokenObserverableArray.add(observerable)
        return observerable
    }

    fun removeTokenSubscribe(observerable: PublishSubject<Boolean>){
        this.tokenObserverableArray.remove(observerable)
    }

    fun notifyTokenSubscribe(state: Boolean){
        this.tokenObserverableArray.forEach{
            it.onNext(state)
        }
    }

    fun getToken():String {
        if(this._token == null){
            this._token = getSecure().get(this.application.applicationContext, TOKEN_KEY_NAME, DEFAULT_TOKEN)
        }
        return this._token!!
    }

    fun setToken(token: String) {
        Log.e(TAG, "setToken() $token")
        this._token = token
        getSecure().set(this.application.applicationContext, TOKEN_KEY_NAME, this._token)
        this.notifyTokenSubscribe(true)
    }

    fun deleteToken(){
        Log.e(TAG, "deleteToken()")
        getSecure().remove(this.application.applicationContext, TOKEN_KEY_NAME)
        this._token = null
        this.notifyTokenSubscribe(false)
    }

    fun isLogin():Boolean{
        val hasToken = getSecure().get(this.application.applicationContext, TOKEN_KEY_NAME, null) != null
        Log.e(TAG, "isLogin() $hasToken")
        return hasToken
    }

    private fun getSecure() = secure()

    companion object {
        val TAG = User::class.java.simpleName
        val TOKEN_KEY_NAME = "token"
        val DEFAULT_TOKEN = "기본토큰"
    }
}