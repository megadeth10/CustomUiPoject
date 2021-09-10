package com.example.scene.storetestsub

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.custom.activity.ToolbarActivity
import com.example.scene.storetestsub.viewmodel.StoreTestSubViewModel
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutStoreTestSubBinding
import org.koin.android.viewmodel.ext.android.viewModel

class StoreTestSubActivity : ToolbarActivity(), View.OnClickListener {
    private lateinit var contentsBinding: LayoutStoreTestSubBinding
    private val subViewModel: StoreTestSubViewModel by viewModel() // by KoinJavaComponent.inject(StoreTestSubViewModel::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setToolbar() {
        this.binding.toolbar.title = "Store Sub Activity"
    }

    override fun setContent() {
        this.contentsBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_store_test_sub,
                null,
                false)
        this.contentsBinding.lifecycleOwner = this
        this.contentsBinding.storeTestSubViewModel = subViewModel
        this.setContentsViewBinding(this.contentsBinding)
        this.subViewModel.setLifeCycle(this)

        this.contentsBinding.loginBtn.setOnClickListener(this)
        this.subViewModel.userToken.observe(this, Observer {
            this.contentsBinding.resultTv.text = it
            updateButton()
        })
    }

    private fun updateButton() {
        val isLogin = this.subViewModel.isLogin()
        var text = "로그인"
        if (isLogin) {
            text = "로그오프"
        }
        this.contentsBinding.loginBtn.text = text
    }

    override fun onClick(p0: View?) {
        var id = p0?.id
        when (id) {
            R.id.login_btn -> {
                val isLogin = this.subViewModel.isLogin()
                if (isLogin) {
                    this.subViewModel.logout()
                } else {
                    this.subViewModel.login()
                }
            }
        }
    }
}