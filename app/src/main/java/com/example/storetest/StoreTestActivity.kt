package com.example.storetest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.custom.activity.ToolbarActivity
import com.example.storetest.viewmodel.StoreTestViewModel
import com.example.storetestsub.StoreTestSubActivity
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutStoreTestBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.java.KoinJavaComponent

/**
 * TODO: Refresh Token과 Token의 변경을 각각의 API에서 어떻게 대응할 것인가.
 * inProgress: retrofit에서 제공을 하는지 확인을 더 해봐야 한다.
 * TODO: 서로 다른 화면에서 Store의 update에 어떻게 반응을 하나
 * solve: 일단 Rxjava publishSubject로 구현 가능하다.
 */
class StoreTestActivity:ToolbarActivity(), View.OnClickListener {
    private lateinit var storeViewModel: StoreTestViewModel
    private lateinit var contentBinding: LayoutStoreTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = StoreTestActivity::class.java.simpleName
        this.setToolbar()
        this.setContents()
    }

    private fun setToolbar(){
        this.binding.toolbar.title = "Application Store Test"
    }

    private fun setContents(){
        this.contentBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_store_test,
                null,
                false)
        this.storeViewModel = getViewModel()
        this.contentBinding.storeViewModel = this.storeViewModel
        this.contentBinding.lifecycleOwner = this
        this.setContentsViewBinding(this.contentBinding)

        storeViewModel.userToken.observe(this, Observer {
            this.contentBinding.tokenResultTv.text = it
        })

        this.contentBinding.loginBtn.setOnClickListener(this)
        this.contentBinding.nextBtn.setOnClickListener(this)
        storeViewModel.isLogin.observe(this, Observer {
            var text = "로그인"
            if(it){
                text = "로그오프"
            }
            this.contentBinding.loginBtn.text = text
        })
    }

    override fun onClick(p0: View?) {
        var id = p0?.id
        when(id){
            R.id.login_btn -> {
                if(storeViewModel.isLogin.value!!){
                    storeViewModel.logout()
                } else {
                    storeViewModel.login()
                }
            }
            R.id.next_btn -> {
                val intent = Intent(this, StoreTestSubActivity::class.java)
                startActivity(intent)
            }
        }
    }
}