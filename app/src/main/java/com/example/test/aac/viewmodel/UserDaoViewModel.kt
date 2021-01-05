package com.example.test.aac.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.test.aac.room.UserProfile
import com.example.test.aac.room.UserProfileDao
import com.example.test.aac.room.UserProfileDatabase

class UserDaoViewModel: AndroidViewModel {
    var userProfileList: LiveData<List<UserProfile>>
    var userProfileDao: UserProfileDao
        private set
    constructor(application: Application):super(application) {
        var db = Room.databaseBuilder(application, UserProfileDatabase::class.java, "userprofile").build()
        userProfileDao = db.userProfileDao
        userProfileList = db.userProfileDao.all
    }

    fun insert(user: UserProfile){
        var thread = Thread(Runnable {
            userProfileDao.insert(user)
        })
        thread.start()
    }
}