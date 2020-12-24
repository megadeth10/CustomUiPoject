package com.example.custom.widget.listview.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.custom.widget.listview.callback.iRecycleViewAdapterCallback
import com.example.utils.Log
import java.lang.Exception

open class BaseAdapter<T : RecyclerView.ViewHolder, V> : RecyclerView.Adapter<T>() {
    var TAG = BaseAdapter::class.simpleName
    private val itemArray: ArrayList<V> = ArrayList()
    lateinit var clickListener: View.OnClickListener
    lateinit var recycleViewAdapterCallback: iRecycleViewAdapterCallback<T>

    fun getItemArray(): ArrayList<V> = this.itemArray

    open fun setItemArray(list: ArrayList<V>) {
        this.itemArray.clear()
        this.itemArray.addAll(list)
        notifyDataSetChanged()
    }

    open fun addItem(list: ArrayList<V>) {
        this.itemArray.addAll(list)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): V = this.itemArray[position]

    fun setItem(position: Int, item: V) {
        try {
            this.itemArray[position] = item
            notifyItemChanged(position)
        } catch (e: Exception) {
            Log.e(TAG, String.format("setItem() %s", e.message))
        }
    }

    fun removeItem(position: Int): T? {
        try {
            val item = this.itemArray.removeAt(position) as T
            notifyItemChanged(position)
            return item
        } catch (e: Exception) {
            Log.e(TAG, String.format("setItem() %s", e.message))
        }
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return this.recycleViewAdapterCallback?.onCreateViewHolder(
                parent,
                viewType,
                this.clickListener
        )
    }

    override fun getItemCount(): Int {
        if (this.itemArray == null) {
            return 0
        }
        return this.itemArray?.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        this.recycleViewAdapterCallback?.onBindViewHolder(holder, position, this.itemArray)
    }
}