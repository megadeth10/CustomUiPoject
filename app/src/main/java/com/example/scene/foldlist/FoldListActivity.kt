package com.example.scene.foldlist

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scene.uilist.menu.MenuAdapter
import com.example.scene.uilist.menu.UIList
import com.example.scene.foldlist.viewmodel.FoldListViewModel
import com.example.scene.foldlist.viewmodel.HorizontalViewModel
import com.example.custom.widget.listview.callback.iRecycleViewAdapterCallback
import com.example.custom.activity.ToolbarActivity
import com.example.custom.widget.listview.adapter.BaseAdapter
import com.example.custom.widget.listview.adapter.FoldItemAdapter
import com.example.custom.widget.listview.callback.iFoldRecycleViewAdapterCallback
import com.example.custom.widget.listview.data.FoldItem
import com.example.custom.widget.listview.viewholder.FoldViewHolder
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutHorizontalListviewBinding
import com.example.test.myapplication.databinding.ViewToolbarButtonBinding

/**
 * TODO: List contents의 높이가 0dp로 높이값을 구하지 못한다. 어떻게 해야하나?
 * solution : RecyclerView height 문제는 0dp보다 constraint top, bottom 정의하고 layout_constrainedHeight를 설정하면 해결된다.
 */
class FoldListActivity : ToolbarActivity(), View.OnClickListener, iFoldRecycleViewAdapterCallback<FoldViewHolder, FoldItem> {
    lateinit var viewBinding: LayoutHorizontalListviewBinding
    lateinit var foldItemViewModel: FoldListViewModel
    lateinit var horizontalItemViewModel: HorizontalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = FoldListActivity::class.simpleName
        this.setToolBar()
        this.setContentBinding()
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

    private fun onClickAddList(){
        this.foldItemViewModel?.pagingData()
    }

    private fun setToolBar() {
        this.toolBar?.title = "Fold ListView"
        this.toolBar?.setNavigationOnClickListener { finish() }
        var toolbarButton = ViewToolbarButtonBinding.inflate(layoutInflater)
        toolbarButton.toolbarBtn2.visibility = View.GONE
        toolbarButton.toolbarBtn1.setOnClickListener { this.onClickAddList() }
        toolbarButton.toolbarBtn1.text = "Folding List data 추가"
        this.toolBar.addView(toolbarButton.root)
    }

    private fun setContentBinding(){
        viewBinding = LayoutHorizontalListviewBinding.inflate(layoutInflater)
        setContentsViewBinding(viewBinding)

        horizontalItemViewModel = ViewModelProvider(this).get(HorizontalViewModel::class.java)
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
        viewBinding.horizontalList.layoutManager = horizontalLayout
        viewBinding.horizontalList.adapter = horizontalListAdapter
        viewBinding.horizontalList.addItemDecoration(horizontalDivider)
        horizontalItemViewModel.list.observe(this, Observer {
            horizontalListAdapter.setItemArray(it)
        })

        this.foldItemViewModel = ViewModelProvider(this).get(FoldListViewModel::class.java)
        val itemAdapter = FoldItemAdapter<FoldViewHolder, FoldItem>();
        val itemDivider = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        val itemLayout = LinearLayoutManager(this)
        getDrawable(R.drawable.list_divider)?.let { itemDivider.setDrawable(it) }
        itemAdapter.foldAdapterCallback = this
        itemAdapter.clickListener = this
        viewBinding.itemList.layoutManager = itemLayout
        viewBinding.itemList.addItemDecoration(itemDivider)
        viewBinding.itemList.adapter = itemAdapter
        this.foldItemViewModel.list.observe(this, Observer {
            itemAdapter.setItemArray(it)
        })
    }
}
