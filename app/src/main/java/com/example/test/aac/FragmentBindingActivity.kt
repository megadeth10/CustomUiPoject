package com.example.test.aac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.aac.fragment.StringViewModelFragment
import com.example.test.myapplication.R

class FragmentBindingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binding_fragment)

        var fragment = StringViewModelFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_body, fragment, null)
                .commit()
    }
}