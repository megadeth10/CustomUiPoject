package com.example.test.aac.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.aac.data.UserProfile

class UserViewModel: ViewModel() {
    // Mutable & None Mutable 수정가능 & 불가능
    private val _userProfile : MutableLiveData<UserProfile> = MutableLiveData(UserProfile())
    var userProfile: LiveData<UserProfile> = _userProfile
        get() = _userProfile
        private set

    fun setUserProfile(user:UserProfile){
        this._userProfile.value = user
    }
}