package com.example.scene.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scene.activity.menu.MenuAdapter
import com.example.scene.activity.menu.UIList
import com.example.custom.activity.ToolbarActivity
import com.example.test.myapplication.R
import com.example.utils.Log

/**
 * NestedScrollView는 RecycleView 단일 스크롤 처럼 사용하기 위한 View
 * 단점은 Adapter의 View를 재사용하지 않고 한번에 data를 다 만든다.
 */
class NestedScrollViewActivity: ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = NestedScrollViewActivity::class.simpleName
        this.toolBar.title = "NestedScrollView Activity"

        setContentsLayout(R.layout.layout_nested_scroll_view)
        val imageView = findViewById<ImageView>(R.id.image)
        Glide.with(this)
                .load("https://tlj.co.kr:7008/data/product/2018-3-14_event(46).jpg")
                .centerCrop()
                .into(imageView)

        val list = findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(this)
        val adapter = MenuAdapter()
        list.adapter = adapter;
        adapter.itemArray = getList();
        list.adapter?.notifyDataSetChanged()

//        val list = findViewById<ListView>(R.id.list)
//        val adapter = ListAdapter()
//        list.adapter = adapter
//        adapter.setItem(getList())
    }

    fun getList(): ArrayList<UIList.MenuItem>{
        val array = ArrayList<UIList.MenuItem>()
        for(i in 0..20){
            array.add(UIList.MenuItem(String.format("aaaa %d", i), null))
        }

        return array
    }

    inner class ListAdapter : BaseAdapter() {
        val data = ArrayList<UIList.MenuItem>()
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val view = View.inflate(p2?.context, R.layout.menu_item, null)
            val item = getItem(p0)
            view.findViewById<TextView>(R.id.title).text = item.title
            Log.e(TAG, String.format("item %d",p0))
            return view
        }

        override fun getItem(p0: Int): UIList.MenuItem {
            return this.data[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return this.data.size
        }

        fun setItem(array: ArrayList<UIList.MenuItem>){
            this.data.addAll(array)
            notifyDataSetChanged()
        }
    }
}