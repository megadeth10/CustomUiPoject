package com.example.scene.storetest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.custom.activity.ToolbarActivity
import com.example.scene.storetest.viewmodel.StoreTestViewModel
import com.example.scene.storetestsub.StoreTestSubActivity
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutStoreTestBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

/**
 * TODO: Refresh Token과 Token의 변경을 각각의 API에서 어떻게 대응할 것인가.
 * inProgress: retrofit에서 제공을 하는지 확인을 더 해봐야 한다.
 * TODO: 서로 다른 화면에서 Store의 update에 어떻게 반응을 하나
 * solve: 일단 Rxjava publishSubject로 구현 가능하다.
 * TODO: observer했을때, Activity에서 onResume으로 task를 수행 할수 있는지 추가 구현이 필요하다.
 * 그러니깐 즉시 수행하지 않고 Activity가 foreground 될때 수행 하는 방법을 찾아 봐야 할것 같다.
 * TODO: Note5에서 file에 write 할때 UI가 update 되지 않는다. MainThread 문제로 ui draw가 hold 되는데 원인을 찾아야한다.
 * solve: IO time이 필요한 secure set() 함수에 Single Observerable로 생성하여
 * 이후에 결과를 onNext로 전달 받아 UI업데이트 되도록 수정함.
 */
class StoreTestActivity : ToolbarActivity(), View.OnClickListener {
    private val storeViewModel: StoreTestViewModel by viewModel()
    private lateinit var contentBinding: LayoutStoreTestBinding
    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = StoreTestActivity::class.java.simpleName
    }

    override fun onDestroy() {
        this.disposable.dispose()
        super.onDestroy()
    }

    override fun setToolbar() {
        this.binding.toolbar.title = "Application Store Test"
    }

    override fun setContent() {
        this.contentBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_store_test,
                null,
                false)
        this.contentBinding.storeViewModel = this.storeViewModel
        this.contentBinding.lifecycleOwner = this
        this.setContentsViewBinding(this.contentBinding)

        storeViewModel.userToken.observe(this, Observer {
            this.contentBinding.tokenResultTv.text = it
            this.updateButton()
        })

        val clickObserverable = Observable.create<View> {
            this.contentBinding.loginBtn.setOnClickListener { view->
                if(!it.isDisposed){
                    it.onNext(view)
                }
            }
        }
//        val clickObserverable = Observable.create(ObservableOnSubscribe<View> {
//            this.contentBinding.loginBtn.setOnClickListener { view->
//                if(!it.isDisposed){
//                    it.onNext(view)
//                }
//            }
//        })
        clickObserverable.debounce(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: io.reactivex.rxjava3.core.Observer<View>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable?) {
                        disposable.add(d)
                    }

                    override fun onNext(t: View?) {
                        onClick(t)
                    }

                    override fun onError(e: Throwable?) {

                    }
                })

        this.contentBinding.nextBtn.setOnClickListener(this)
    }

    private fun updateButton() {
        val isLogin = this.storeViewModel.isLogin()
        var text = "로그인"
        if (isLogin) {
            text = "로그오프"
        }
        this.contentBinding.loginBtn.text = text
    }

    override fun onClick(p0: View?) {
        var id = p0?.id
        when (id) {
            R.id.login_btn -> {
                val isLogin = this.storeViewModel.isLogin()
                if (isLogin) {
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