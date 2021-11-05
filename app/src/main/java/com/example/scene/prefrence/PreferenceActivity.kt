package com.example.scene.prefrence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityPrefernceBinding

class PreferenceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var contentBinding: ActivityPrefernceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.activity_prefernce,
            null,
            false
        )
        setContentView(contentBinding.root)

        this.contentBinding.btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
    }
}