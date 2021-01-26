package com.example.scene.pagedlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scene.infinitylist.data.Post
import com.example.scene.pagedlist.adapter.PostPagedListAdapter
import com.example.scene.pagedlist.viewmodel.PostPagedListViewModel
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityPagedListBinding

/**
 * TODO: refresh 동작하지 않는다. 분명히 dataSource의 invalidate()를 이용하면 된다는데... 좀 더 확인이 필요다.
 */
class PostPagedListActivity: AppCompatActivity(), View.OnClickListener{
    private lateinit var viewModel: PostPagedListViewModel
    private lateinit var binding: ActivityPagedListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_paged_list)
        binding!!.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(PostPagedListViewModel::class.java)
        binding!!.viewModel = viewModel
        val adapter = PostPagedListAdapter(this)
        binding!!.list.adapter = adapter
        binding!!.list.layoutManager = LinearLayoutManager(this)
        binding!!.list.addItemDecoration(
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        binding!!.refreshLayout.setOnRefreshListener {
            setRefresh()
            viewModel.refresh()
        }

        viewModel.pagedListData.observe(this, Observer<PagedList<Post>> { posts ->
            adapter.submitList(posts)
        })
    }

    private fun setRefresh(){
        binding!!.refreshLayout.isRefreshing = false
    }

    override fun onClick(p0: View?) {

    }
}