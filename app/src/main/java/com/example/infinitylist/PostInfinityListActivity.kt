package com.example.infinitylist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.custom.activity.ToolbarActivity
import com.example.custom.widget.listview.adapter.InfinityAdapter
import com.example.infinitylist.adapter.PostInfinityAdapter
import com.example.infinitylist.api.GetPosts
import com.example.infinitylist.data.Post
import com.example.infinitylist.data.Posts
import com.example.infinitylist.viewmodel.PostViewModel
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutInfinityListBinding
import com.example.test.myapplication.databinding.ViewToolbarButtonBinding
import com.example.utils.Log
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

/**
 * Infinity RecycleView
 * TODO: koin ViewModel 작업을 해야한다.
 * TODO: paging 되는 마지막 item이 잘못된 View로 bind된다.
 */
class PostInfinityListActivity : ToolbarActivity(), View.OnClickListener {
//    private val getPosts = KoinJavaComponent.inject(GetPosts::class.java)
    private var contentBinding: LayoutInfinityListBinding? = null
//    private val retrofit = KoinJavaComponent.inject(Retrofit::class.java)
//    private lateinit var postViewModel: PostViewModel
    private val postViewModel: PostViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.TAG = PostInfinityListActivity::class.java.simpleName
        setToolbar()
        setContentBind()
        setInitView()
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.toolbar_btn1 -> refreshList()
        }
    }

    private fun setToolbar() {
        val toolbarBinding = ViewToolbarButtonBinding.inflate(layoutInflater)
        toolbarBinding.toolbarBtn1.setOnClickListener(this)
        toolbarBinding.toolbarBtn2.visibility = View.GONE
        toolbarBinding.lifecycleOwner = this
        this.toolBar.title = "Infinity List"
        this.toolBar.addView(toolbarBinding.root)
    }

    private fun setContentBind() {
        contentBinding = LayoutInfinityListBinding.inflate(layoutInflater)
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

    /**
     * ============================= 테스트를 위해 만든 로직 =======================
     */
    //    private Handler mLoadDataHandler = new Handler(message -> {
    //        if (message.what == REQUEST_DATA) {
    //            getDataListWithKoin();
    //            return true;
    //        }
    //        return false;
    //    });
    //    private void getData() {
    //        Message msg = mLoadDataHandler.obtainMessage(REQUEST_DATA);
    //        mLoadDataHandler.removeMessages(REQUEST_DATA);
    //        // TODO: 테스트용으로 Delay 1초줌
    //        mLoadDataHandler.sendMessageDelayed(msg, 1000);
    //    }

    /**
     * Rest API 호출용 함수
     */
    //    private void getDataList() {
    //        Call<Posts> caller = Retrofit2NetworkLayer.INSTANCE.getInstance().create(Retrofit2APIInterface.class).getPosts();
    //        this.setCaller(caller);
    //        caller.enqueue(
    //                new Callback<Posts>() {
    //                    @Override
    //                    public void onResponse(Call<Posts> call, Response<Posts> response) {
    //                        Log.e(TAG, "onResponse()");
    //                        if (response.isSuccessful()) {
    //                            Posts item = response.body();
    //                            if (item != null) {
    //                                setList(item.getData());
    //                            }
    //                        }
    //                        setRefresh();
    //                    }
    //
    //                    @Override
    //                    public void onFailure(Call<Posts> call, Throwable t) {
    //                        Log.e(TAG, String.format("onFailure() %s", t.getMessage()));
    //                        setRefresh();
    //                    }
    //                }
    //        );
    //    }
}