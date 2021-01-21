package com.example.storetestsub.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.user.User

class StoreTestSubViewModel(user: User): ViewModel() {
    private val userStore = user
    var isLogin: MutableLiveData<Boolean> = MutableLiveData(userStore.isLogin())
        private set

    var userToken: MutableLiveData<String> = MutableLiveData(userStore.getToken())
        private set

    private fun setToken(){
        this.userToken.value = userStore.getToken()
        this.isLogin()
    }

    fun login(){
        userStore.setToken("sub Activity에서 login 했다")
        this.setToken()
    }

    fun logout(){
        userStore.deleteToken()
        this.setToken()
    }

    private fun isLogin(){
        this.isLogin.value = userStore.isLogin()
    }
}