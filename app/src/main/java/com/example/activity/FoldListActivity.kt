package com.example.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.activity.menu.MenuAdapter
import com.example.activity.menu.UIList
import com.example.custom.widget.listview.callback.iRecycleViewAdapterCallback
import com.example.custom.activity.ToolbarActivity
import com.example.custom.widget.listview.adapter.BaseAdapter
import com.example.custom.widget.listview.adapter.FoldItemAdapter
import com.example.custom.widget.listview.callback.iFoldRecycleViewAdapterCallback
import com.example.custom.widget.listview.data.FoldItem
import com.example.custom.widget.listview.viewholder.FoldViewHolder
import com.example.test.myapplication.R

class FoldListActivity : ToolbarActivity(), View.OnClickListener, iFoldRecycleViewAdapterCallback<FoldViewHolder, FoldItem> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.TAG = FoldListActivity::class.simpleName
        this.mToolBar.title = "Fold ListView"
        this.mToolBar.setNavigationOnClickListener { finish() }
        setContentsLayout(R.layout.layout_horizontal_listview)

        val horizontalListView = findViewById<RecyclerView>(R.id.horizontal_list)
        val horizontalListAdapter = BaseAdapter<MenuAdapter.MenuViewHolder, UIList.MenuItem>()
        val horizontalDivider = DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL)
        val horizontalLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalListAdapter.recycleViewAdapterCallback = (object: iRecycleViewAdapterCallback<MenuAdapter.MenuViewHolder> {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, clickListener: View.OnClickListener?): MenuAdapter.MenuViewHolder {
                val view = layoutInflater.inflate(R.layout.view_horizontal_list_item, parent, false)
                val viewHolder = MenuAdapter.MenuViewHolder(view)
                view.setOnClickListener(clickListener)
                return viewHolder
            }

            override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int, list: java.util.ArrayList<*>?) {
                val item = list?.get(position) as UIList.MenuItem
                holder.tv.text = item.title
                val layout = holder.tv.parent as android.widget.LinearLayout
                layout.tag = item
            }

        })
        horizontalListAdapter.clickListener = this
        horizontalListView.layoutManager = horizontalLayout
        horizontalListView.adapter = horizontalListAdapter
        horizontalListView.addItemDecoration(horizontalDivider)
        horizontalListAdapter.setItemArray(this.getHorizontalItem())

        val itemListView = findViewById<RecyclerView>(R.id.item_list)
        val itemAdapter = FoldItemAdapter<FoldViewHolder, FoldItem>();
        val itemDivider = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        val itemLayout = LinearLayoutManager(this)
        getDrawable(R.drawable.list_divider)?.let { itemDivider.setDrawable(it) }
        itemAdapter.foldAdapterCallback = this
        itemAdapter.clickListener = this
        itemListView.layoutManager = itemLayout
        itemListView.addItemDecoration(itemDivider)
        itemListView.adapter = itemAdapter
        this.getItemList()?.let { itemAdapter.setItemArray(it) }
    }

    override fun onBindChildView(holder: FoldViewHolder?, position: Int, parent: ViewGroup, item: FoldItem) {
        item.childItem.forEach {
            val childItem = it as UIList.MenuItem
            val view = View.inflate(parent.context, R.layout.menu_item, null)
            val tv = view.findViewById<TextView>(R.id.title)
            tv.text = childItem.title
            view.tag = it
            view.setOnClickListener(this)
            parent.addView(view)
        }
    }

    override fun onClick(p0: View?) {
        val item = p0?.tag
        if(item is UIList.MenuItem) {
            this.showSnackBar(item?.title)
        }
    }

    /**
     * ==================== 테스트를 위한 code
     */

    private fun getHorizontalItem(): ArrayList<UIList.MenuItem> {
        val list: ArrayList<UIList.MenuItem> = ArrayList()
        list.add(UIList.MenuItem(String.format("aaaa"), null))
        list.add(UIList.MenuItem(String.format("bbbb"), null))
        list.add(UIList.MenuItem(String.format("cccc"), null))
        list.add(UIList.MenuItem(String.format("dddd"), null))
        list.add(UIList.MenuItem(String.format("eeee"), null))
        list.add(UIList.MenuItem(String.format("ffff"), null))
        list.add(UIList.MenuItem(String.format("gggg"), null))
        list.add(UIList.MenuItem(String.format("hhhh"), null))
        list.add(UIList.MenuItem(String.format("iiii"), null))
        list.add(UIList.MenuItem(String.format("jjjj"), null))
        list.add(UIList.MenuItem(String.format("kkkk"), null))
        list.add(UIList.MenuItem(String.format("llll"), null))
        list.add(UIList.MenuItem(String.format("mmmm"), null))
        list.add(UIList.MenuItem(String.format("nnnn"), null))
        list.add(UIList.MenuItem(String.format("oooo"), null))
        return list;
    }

    private fun getItemList(): java.util.ArrayList<FoldItem>? {
        val list: ArrayList<FoldItem> = ArrayList()
        val childItem = ArrayList<UIList.MenuItem>()
        childItem.add(UIList.MenuItem("aaa-1111", null))
        childItem.add(UIList.MenuItem("aaa-2222", null))
        childItem.add(UIList.MenuItem("aaa-3333", null))
        childItem.add(UIList.MenuItem("aaa-4444", null))
        list.add(FoldItem("aaaa",childItem, true))
        list.add(FoldItem("bbbb",null, true))
        list.add(FoldItem("cccc",null, true))
        list.add(FoldItem("dddd",null, true))
        list.add(FoldItem("eeee",null, true))
        list.add(FoldItem("ffff",null, true))
        list.add(FoldItem("gggg",null, true))
        list.add(FoldItem("hhhh",null, true))
        list.add(FoldItem("iiii",null, true))
        list.add(FoldItem("jjjj",null, true))
        list.add(FoldItem("kkkk",null, true))
        list.add(FoldItem("llll",null, true))
        list.add(FoldItem("mmmm",null, true))
        list.add(FoldItem("nnnn",null, true))
        list.add(FoldItem("oooo",null, true))
        list.add(FoldItem("pppp",null, true))
        list.add(FoldItem("qqqq",null, true))
        list.add(FoldItem("rrrr",null, true))
        list.add(FoldItem("ssss",null, true))
        list.add(FoldItem("tttt",null, true))
        list.add(FoldItem("uuuu",null, true))
        list.add(FoldItem("vvvv",null, true))
        list.add(FoldItem("aaaa",null, true))
        list.add(FoldItem("bbbb",null, true))
        list.add(FoldItem("cccc",null, true))
        list.add(FoldItem("dddd",null, true))
        list.add(FoldItem("eeee",null, true))
        list.add(FoldItem("ffff",null, true))
        list.add(FoldItem("gggg",null, true))
        list.add(FoldItem("hhhh",null, true))
        list.add(FoldItem("iiii",null, true))
        list.add(FoldItem("jjjj",null, true))
        list.add(FoldItem("kkkk",null, true))
        list.add(FoldItem("llll",null, true))
        list.add(FoldItem("mmmm",null, true))
        list.add(FoldItem("nnnn",null, true))
        list.add(FoldItem("oooo",null, true))
        list.add(FoldItem("pppp",null, true))
        list.add(FoldItem("qqqq",null, true))
        list.add(FoldItem("rrrr",null, true))
        list.add(FoldItem("ssss",null, true))
        list.add(FoldItem("tttt",null, true))
        list.add(FoldItem("uuuu",null, true))
        list.add(FoldItem("vvvv",null, true))
        return list
    }
}
