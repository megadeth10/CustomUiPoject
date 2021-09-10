package com.example.test.widget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.test.myapplication.R
import com.example.utils.AnimationUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.observers.ConsumerSingleObserver
import io.reactivex.rxjava3.subjects.SingleSubject
import java.util.concurrent.TimeUnit

class TestActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val button = findViewById<Button>(R.id.d)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.d -> {
//                v.isSelected = !v.isSelected
                AnimationUtil.Expand.animateExpand(v.id, v)
                SingleSubject.just(true).delay(100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { t1, t2 ->
                        val button = findViewById<Button>(R.id.d)
                        button.isSelected = !button.isSelected
                    }
            }
        }
    }
}