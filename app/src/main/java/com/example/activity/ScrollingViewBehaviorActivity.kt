package com.example.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.activity.menu.MenuAdapter
import com.example.activity.menu.UIList.MenuItem
import com.example.activity.data.ScrollingViewBehaviorData
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityScrollBehaviorBinding

class ScrollingViewBehaviorActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityScrollBehaviorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scroll_behavior)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
        val adapter: MenuAdapter = MenuAdapter(this)
        binding.recycleList.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        getDrawable(R.drawable.list_divider)?.let { dividerItemDecoration.setDrawable(it) }
        binding.recycleList.addItemDecoration(dividerItemDecoration)
        binding.recycleList.adapter = adapter
        adapter.itemArray = ScrollingViewBehaviorData().listMenuItem as java.util.ArrayList<*>
    }

    override fun onClick(p0: View?) {
        val item: MenuItem = p0?.tag as MenuItem

        Toast.makeText(this,
                String.format("선택 %s", item.title),
                Toast.LENGTH_SHORT)
                .show()
    }
}