package com.example.test.aac.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.aac.data.UserProfile

class UserViewModel: ViewModel() {
    var userProfile : MutableLiveData<UserProfile> = MutableLiveData()
}