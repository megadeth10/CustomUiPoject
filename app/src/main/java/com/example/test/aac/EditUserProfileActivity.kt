package com.example.test.aac

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityEditProfileBinding
import com.example.utils.Log
import java.util.regex.Pattern

class EditUserProfileActivity: AppCompatActivity() {
    lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var name = intent.getStringExtra("name")
        var address = intent.getStringExtra("address")
        var phone = intent.getStringExtra("phone")

        Log.e("EditUserProfileActivity", String.format("name: %s address: %s", name, address))
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_edit_profile, null, false)
        setContentView(binding.root)
        binding.name.setText(name)
        binding.address.setText(address)
        binding.phone.setText(phone)
        binding.applyBtn.setOnClickListener { this.onClickApply() }
    }

    fun onClickApply(){
        Log.e("EditUserProfileActivity", "onClickApply()")
        var resultIntent = Intent()
        resultIntent.putExtra("name",binding.name.text.toString())
        resultIntent.putExtra("address",binding.address.text.toString())
        resultIntent.putExtra("phone", binding.phone.text.toString())
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}