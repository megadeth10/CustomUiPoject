package com.example.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.activity.menu.MenuAdapter
import com.example.activity.menu.UIList.MenuItem
import com.example.test.myapplication.R
import kotlinx.android.synthetic.main.menu_item.view.*
import kotlin.collections.ArrayList

/**
 * NestedScrollView를 사용고 RecycleView를 사용하더라도 List의 Item이 한번에 다 생성 된다.
 * 메모리 효율에 문제가 생길듯 하다.
 */
class NestedScrollViewActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scroll)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
        val listview: RecyclerView = findViewById(R.id.recycle_list)
        val adapter: MenuAdapter = MenuAdapter(this)
        listview.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this,
                (listview.layoutManager as LinearLayoutManager).layoutDirection
        )
        getDrawable(R.drawable.list_divider)?.let { dividerItemDecoration.setDrawable(it) }
        listview.addItemDecoration(dividerItemDecoration)
        listview.adapter = adapter
        adapter.itemArray = getDataList()
    }

    override fun onClick(p0: View?) {
        val item: MenuItem = p0?.tag as MenuItem

        Toast.makeText(this, String.format("선택 %s", item.title), Toast.LENGTH_SHORT).show()
    }

    /**
     * ==================== 테스트를 위한 code
     */

    private fun getDataList(): java.util.ArrayList<*>? {
        val list: ArrayList<MenuItem> = ArrayList()
        list.add(MenuItem(String.format("aaaa"), null))
        list.add(MenuItem(String.format("bbbb"), null))
        list.add(MenuItem(String.format("cccc"), null))
        list.add(MenuItem(String.format("dddd"), null))
        list.add(MenuItem(String.format("eeee"), null))
        list.add(MenuItem(String.format("ffff"), null))
        list.add(MenuItem(String.format("gggg"), null))
        list.add(MenuItem(String.format("hhhh"), null))
        list.add(MenuItem(String.format("iiii"), null))
        list.add(MenuItem(String.format("jjjj"), null))
        list.add(MenuItem(String.format("kkkk"), null))
        list.add(MenuItem(String.format("llll"), null))
        list.add(MenuItem(String.format("mmmm"), null))
        list.add(MenuItem(String.format("nnnn"), null))
        list.add(MenuItem(String.format("oooo"), null))
        list.add(MenuItem(String.format("pppp"), null))
        list.add(MenuItem(String.format("qqqq"), null))
        list.add(MenuItem(String.format("rrrr"), null))
        list.add(MenuItem(String.format("ssss"), null))
        list.add(MenuItem(String.format("tttt"), null))
        list.add(MenuItem(String.format("uuuu"), null))
        list.add(MenuItem(String.format("vvvv"), null))
        list.add(MenuItem(String.format("aaaa"), null))
        list.add(MenuItem(String.format("bbbb"), null))
        list.add(MenuItem(String.format("cccc"), null))
        list.add(MenuItem(String.format("dddd"), null))
        list.add(MenuItem(String.format("eeee"), null))
        list.add(MenuItem(String.format("ffff"), null))
        list.add(MenuItem(String.format("gggg"), null))
        list.add(MenuItem(String.format("hhhh"), null))
        list.add(MenuItem(String.format("iiii"), null))
        list.add(MenuItem(String.format("jjjj"), null))
        list.add(MenuItem(String.format("kkkk"), null))
        list.add(MenuItem(String.format("llll"), null))
        list.add(MenuItem(String.format("mmmm"), null))
        list.add(MenuItem(String.format("nnnn"), null))
        list.add(MenuItem(String.format("oooo"), null))
        list.add(MenuItem(String.format("pppp"), null))
        list.add(MenuItem(String.format("qqqq"), null))
        list.add(MenuItem(String.format("rrrr"), null))
        list.add(MenuItem(String.format("ssss"), null))
        list.add(MenuItem(String.format("tttt"), null))
        list.add(MenuItem(String.format("uuuu"), null))
        list.add(MenuItem(String.format("vvvv"), null))
        return list
    }
}