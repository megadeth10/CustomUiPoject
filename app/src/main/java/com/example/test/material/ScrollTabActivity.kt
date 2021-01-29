package com.example.test.material

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.custom.activity.ToolbarActivity
import com.example.test.material.adapter.TabPagerAdapter
import com.example.test.material.viewmodel.TabViewModel
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutScrollTabBinding
import com.example.utils.Log
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * TODO: constraint height 문제가 또 발생한다.... 이전에 FoldListActivity의 해결책으로도 해결되지 않는다.
 * solve: binding을 2개를 연속으로 사용하니 문제가 발생 하였다. ToolbarActivity가 아닌 AppCompatActivity로변경
 */
class ScrollTabActivity : AppCompatActivity() {
    private val TAG = ScrollTabActivity::class.simpleName
    private lateinit var contentsBinding: LayoutScrollTabBinding
    private lateinit var viewModel: TabViewModel
    private lateinit var tabPagerAdapter: TabPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContent()
        this.setTabs()

    }

    fun setContent() {
        this.contentsBinding = DataBindingUtil.setContentView(
                this,
                R.layout.layout_scroll_tab
        )
        this.viewModel = ViewModelProvider(this).get(TabViewModel::class.java)
        Log.e(TAG, "Activity ViewModel $viewModel")
    }

    private fun setTabs() {
        this.tabPagerAdapter = TabPagerAdapter(supportFragmentManager, this, this.viewModel)
        this.contentsBinding.pagerView.adapter = this.tabPagerAdapter

        TabLayoutMediator(this.contentsBinding.tabLayout, this.contentsBinding.pagerView) { tab, position ->
            tab.text = viewModel.tabData.value!!.get(position).title
        }.attach()
    }
}