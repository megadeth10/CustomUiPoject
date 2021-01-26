package com.example.scene.infinitylistwithrxjava

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.custom.activity.ToolbarActivity
import com.example.scene.infinitylist.adapter.PostInfinityAdapter
import com.example.scene.infinitylist.data.Post
import com.example.scene.infinitylistwithrxjava.viewmodel.PostRxJavaViewModel
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutListWithRxjavaBinding
import com.example.test.myapplication.databinding.ViewToolbarButtonBinding
import com.example.utils.Log
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit

/**
 * Infinity RecycleView
 * TODO: 화면 rotate시에 마지막 item이 없어져서 api data call을 하지 못는 오류가 있다.
 * Log 상으로는 rotate시에 item이 추가된다.
 */
class PostListWithRxjavaActivity : ToolbarActivity(), View.OnClickListener {
    private var contentBinding: LayoutListWithRxjavaBinding? = null
    private val postViewModel: PostRxJavaViewModel by viewModel()
    private val rxjavaGetPost: Retrofit by inject(Retrofit::class.java, named("RetrofitRxJava"))
    private val getPost: Retrofit by inject(Retrofit::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = PostListWithRxjavaActivity::class.java.simpleName
        setInitView()
        Log.e(TAG, "rxJavaGetPost $rxjavaGetPost")
        Log.e(TAG, "GetPost $getPost")
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.toolbar_btn1 -> refreshList()
        }
    }

    override fun setToolbar() {
        val toolbarBinding = ViewToolbarButtonBinding.inflate(layoutInflater)
        toolbarBinding.toolbarBtn1.setOnClickListener(this)
        toolbarBinding.toolbarBtn2.visibility = View.GONE
        toolbarBinding.lifecycleOwner = this
        this.toolBar.title = "Infinity List with RxJava"
        this.toolBar.addView(toolbarBinding.root)
    }

    override fun setContent() {
        contentBinding = LayoutListWithRxjavaBinding.inflate(layoutInflater)
        contentBinding!!.lifecycleOwner = this
        contentBinding!!.viewModel = postViewModel
        setContentsViewBinding(contentBinding)
    }

    /**
     * 이걸 BindingAdapater로 바꾸어야 하나.
     */
    private fun setInitView() {
        contentBinding!!.list.setOnLoadMoreListener { postViewModel.getDataListWithKoin() }
        val adapter = PostInfinityAdapter(View.OnClickListener { view: View ->
            val item = view.tag as Post
            showSnackBar(item.title)
        })
        contentBinding!!.list.adapter = adapter
        contentBinding!!.refreshLayout.setOnRefreshListener {
            Log.e(TAG, String.format("onRefresh"))
            refreshList()
        }
        postViewModel.list.observe(this, Observer {
            adapter.setItemArray(it)
            contentBinding!!.list.setLoaded()
        })
        postViewModel.isRefreshing.observe(this, Observer {
            this.setRefresh(it)
        })
        postViewModel.isEndItem.observe(this, Observer {
            contentBinding!!.list.isEndItem = it
            setProgressState(!it)
        })
    }

    private fun refreshList() {
        postViewModel!!.reset()
    }

    private fun setRefresh(state: Boolean) {
        if (contentBinding!!.refreshLayout.isRefreshing != state) {
            contentBinding!!.refreshLayout.isRefreshing = state
        }
    }

    private fun setProgressState(state: Boolean){
        var adapter = contentBinding!!.list.adapter as PostInfinityAdapter
        adapter.isShowProgress = state
    }
}