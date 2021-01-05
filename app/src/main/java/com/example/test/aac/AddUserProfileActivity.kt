package com.example.test.aac

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.test.aac.room.UserProfile
import com.example.test.aac.room.UserProfileDatabase
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityAddUserBinding
import com.example.utils.Log
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder

class AddUserProfileActivity : AppCompatActivity() {
    private val TAG = AddUserProfileActivity::class.simpleName
    lateinit var binding: ActivityAddUserBinding
    lateinit var db: UserProfileDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_user)
        db = Room.databaseBuilder(this, UserProfileDatabase::class.java, "userprofile").build()
        fetchUserProfile()
    }

    fun addUser(view: View) {
        Log.e(TAG, "addUser()")
        Snackbar.make(binding.root, "addUser", Snackbar.LENGTH_SHORT).show()
        var user = UserProfile()
        user.name = binding.name.text.toString()
        user.address = binding.address.text.toString()
        user.phone = binding.phone.text.toString()
        var thread = Thread(Runnable {
            db.userProfileDao.insert(user)
            fetchUserProfile()
        })
        thread.start()
    }

    fun fetchUserProfile(){
        var thread = Thread(Runnable {
            var list = db.userProfileDao.all
            var str = StringBuilder("사용자목록\n")
            list.forEach {
                str.append(String.format("%d %s %s %s\n", it.id, it.name, it.phone, it.address))
            }
            binding.display.text = str.toString()
        })
        thread.start()
    }
}