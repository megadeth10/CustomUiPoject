package com.example.custom.widget.listview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.custom.widget.listview.callback.iFoldRecycleViewAdapterCallback
import com.example.custom.widget.listview.data.FoldItem
import com.example.custom.widget.listview.viewholder.FoldViewHolder
import com.example.test.myapplication.R

class FoldItemAdapter<T:FoldViewHolder, V: FoldItem>: BaseAdapter<T, V>(), View.OnClickListener{
    var layoutId = R.layout.view_fold_list_item
    var foldAdapterCallback: iFoldRecycleViewAdapterCallback<T,V>?
    init {
        this.TAG = FoldItemAdapter::class.simpleName
        this.foldAdapterCallback = null
    }

    override fun setItemArray(list: ArrayList<V>) {
        val size = list.size
        super.setItemArray(list)
    }

    override fun addItem(list: ArrayList<V>) {
        this.addItem(list)
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val item = this.getItem(position) as V
        holder.headerLayout.tag = position
        holder.headerLayout.setOnClickListener(this)
        holder.headerTitle.text = item.title
        holder.foldCheckbox.isChecked = item.isFold
        holder.childLayout.removeAllViews()
        var visible = View.GONE
        if(item.isFold && (item.childItem != null) && (this.foldAdapterCallback != null)){
            visible = View.VISIBLE
            this.foldAdapterCallback?.onBindChildView(holder,position, holder.childLayout, item)
        }
        holder.childLayout.visibility = visible
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return FoldViewHolder(view) as T
    }

    override fun onClick(p0: View?) {
        val index = p0?.tag as Int
        val item = getItem(index)
        item.isFold = !item.isFold
        setItem(index, item)
    }
}