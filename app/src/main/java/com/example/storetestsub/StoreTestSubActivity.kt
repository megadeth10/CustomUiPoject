package com.example.storetestsub

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.custom.activity.ToolbarActivity
import com.example.storetestsub.viewmodel.StoreTestSubViewModel
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutStoreTestSubBinding
import org.koin.java.KoinJavaComponent

class StoreTestSubActivity: ToolbarActivity(), View.OnClickListener {
    private lateinit var contentsBinding: LayoutStoreTestSubBinding
    private val subViewModel by KoinJavaComponent.inject(StoreTestSubViewModel::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setToolbar()
        this.setContents()
    }

    private fun setToolbar(){
        this.binding.toolbar.title = "Store Sub Activity"
    }

    private fun setContents(){
        this.contentsBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_store_test_sub,
                null,
                false)
        this.contentsBinding.lifecycleOwner = this
        this.contentsBinding.storeTestSubViewModel = subViewModel
        this.setContentsViewBinding(this.contentsBinding)

        this.contentsBinding.loginBtn.setOnClickListener(this)
        this.subViewModel.userToken.observe(this, Observer {
            this.contentsBinding.resultTv.text = it
        })
        this.subViewModel.isLogin.observe(this, Observer {
         var text = "로그인"
            if(it){
                text = "로그아웃"
            }
            this.contentsBinding.loginBtn.text = text
        })
    }

    override fun onClick(p0: View?) {
        var id = p0?.id
        when(id){
            R.id.login_btn -> {
                if(this.subViewModel.isLogin.value!!){
                    this.subViewModel.logout()
                } else {
                    this.subViewModel.login()
                }
            }
        }
    }
}