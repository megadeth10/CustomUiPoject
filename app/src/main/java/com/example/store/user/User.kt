package com.example.store.user

import android.app.Application
import androidx.lifecycle.Lifecycle
import com.example.secure.secure
import com.example.utils.Log
import com.trello.rxlifecycle4.android.ActivityEvent
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import com.trello.rxlifecycle4.kotlin.bindUntilEvent
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class User {
    private var application: Application
    private var _token: String? = null
    private var tokenObserverableArray: ArrayList<PublishSubject<Boolean>> = ArrayList(0)
    private var secure: secure
    constructor(application: Application) {
        Log.e(TAG, "constructor()")
        this.application = application
        this.secure = secure()
    }

    fun addTokenSubscribe(observer: Observer<Boolean>, activity: RxAppCompatActivity): PublishSubject<Boolean>? {
        Log.e(TAG, "addTokenSubscribe() size: ${this.tokenObserverableArray.size}")
        var observerable = PublishSubject.create<Boolean>()
        observerable.observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .bindUntilEvent(activity, ActivityEvent.PAUSE)
        observerable.subscribe(observer)
        this.tokenObserverableArray.add(observerable)
        return observerable
    }

    fun removeTokenSubscribe(observerable: PublishSubject<Boolean>){
        this.tokenObserverableArray.remove(observerable)
    }

    fun notifyTokenSubscribe(state: Boolean){
        val endIndex = this.tokenObserverableArray.size - 1
        for(index in endIndex downTo 0 step 1){
            val observerable = this.tokenObserverableArray[index]
            observerable?.onNext(state)
        }
//        this.tokenObserverableArray.forEach{
//            it.onNext(state)
//        }
    }

    fun getToken():String {
        if(this._token == null){
            this._token = this.secure.get(this.application.applicationContext, TOKEN_KEY_NAME, DEFAULT_TOKEN)
        }
        return this._token!!
    }

    fun setToken(token: String) {
        Log.e(TAG, "setToken() $token")
        this._token = token
        val callback: (Boolean) -> Unit = {
            this.notifyTokenSubscribe(it)
        }
        this.secure.set(this.application.applicationContext, TOKEN_KEY_NAME, this._token, callback)
    }

    fun deleteToken(){
        Log.e(TAG, "deleteToken()")
        this.secure.remove(this.application.applicationContext, TOKEN_KEY_NAME)
        this._token = null
        this.notifyTokenSubscribe(false)
    }

    fun isLogin():Boolean{
        val hasToken = this.secure.get(this.application.applicationContext, TOKEN_KEY_NAME, null) != null
        Log.e(TAG, "isLogin() $hasToken")
        return hasToken
    }

    companion object {
        val TAG = User::class.java.simpleName
        val TOKEN_KEY_NAME = "token"
        val DEFAULT_TOKEN = "default_token"
    }
}