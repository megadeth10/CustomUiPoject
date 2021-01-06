package com.example.test.aac

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.test.aac.data.UserProfile
import com.example.test.aac.viewmodel.UserViewModel
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityUserProfileBinding
import com.example.utils.Log

class UserProfileActivity: AppCompatActivity(){
    lateinit var binding: ActivityUserProfileBinding
    val REQUEST_CODE = 200;

    lateinit var model: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_user_profile, null, false)
//        model = ViewModelProvider(this).get(UserViewModel::class.java)
//        setContentView(binding.root)
//
//        model.userProfile.observe(this, Observer {
//            binding.profile = it
//        })
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        model = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.profile = model
        binding.lifecycleOwner = this

        if(model.userProfile.value == null){
            fetchData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE -> {
                if(resultCode == Activity.RESULT_OK){
                    var name = data?.extras?.getString("name")
                    var address = data?.extras?.getString("address")
                    var phone = data?.extras?.getString("phone")
                    Log.e("onActivityResult", String.format("name: %s address: %s", name, address))
                    var profile = model.userProfile.value
                    if (name != null) {
                        profile?.name = name
                    }
                    if (address != null) {
                        profile?.address = address
                    }
                    if (phone != null) {
                        profile?.phone = phone
                    }
                    model.userProfile.value = profile
                }
            }
        }
    }

//    override fun onClick(p0: View?) {
//        this.editProfile()
//    }


    fun fetchData(){
        var userData = UserProfile()
        userData.address = "서울시 광진구 구의동"
        userData.gender = true
        userData.name = "가가가가"
        userData.phone = "010-1111-2222"

        model.userProfile.value = userData
    }

    fun editProfile(view: View){
        var startIntent = Intent(this, EditUserProfileActivity::class.java)
        startIntent.putExtra("name", model.userProfile.value?.name)
        startIntent.putExtra("address", model.userProfile.value?.address)
        startIntent.putExtra("phone", model.userProfile.value?.phone)

        startActivityForResult(startIntent, REQUEST_CODE)
    }
}