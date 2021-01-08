package com.example.test.aac

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.test.aac.room.UserProfile
import com.example.test.aac.room.UserProfileDatabase
import com.example.test.aac.viewmodel.UserDaoViewModel
import com.example.test.aac.viewmodel.UserDaoViewModelFactory
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityAddUserBinding
import com.example.utils.Log
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder

class AddUserProfileActivity : AppCompatActivity() {
    private val TAG = AddUserProfileActivity::class.simpleName
    lateinit var binding: ActivityAddUserBinding
    private lateinit var daoViewModel: UserDaoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_user)
        dbViewModel()
    }

    /**
     * Room + ViewModel + LiveData
     */
    fun dbViewModel(){
        // androidx.lifecycle:lifecycle-extensions 지원중단이 되어 ViewModelProvider.factory를 사용한다.
        //
        daoViewModel = ViewModelProvider(this, UserDaoViewModelFactory(application)).get(UserDaoViewModel::class.java)
        daoViewModel.userProfileList.observe(this, Observer {
            var str = StringBuilder("사용자목록\n")
            it.forEach {
                str.append(String.format("%d %s %s %s\n", it.id, it.name, it.phone, it.address))
            }
            binding.display.text = str.toString()
        })
    }

    /**
     * Room + LiveData
     */
//    lateinit var db: UserProfileDatabase
    fun dbCreate() {
//        db = Room.databaseBuilder(this, UserProfileDatabase::class.java, "userprofile").build()
//        db.userProfileDao.all.observe(this, Observer {
//            var str = StringBuilder("사용자목록\n")
//            it.forEach {
//                str.append(String.format("%d %s %s %s\n", it.id, it.name, it.phone, it.address))
//            }
//            binding.display.text = str.toString()
//        })
    }

    fun addUser(view: View) {
        Log.e(TAG, "addUser()")
        Snackbar.make(binding.root, "addUser", Snackbar.LENGTH_SHORT).show()
        var user = UserProfile()
        user.name = binding.name.text.toString()
        user.address = binding.address.text.toString()
        user.phone = binding.phone.text.toString()
        daoViewModel.insert(user)
    }
}