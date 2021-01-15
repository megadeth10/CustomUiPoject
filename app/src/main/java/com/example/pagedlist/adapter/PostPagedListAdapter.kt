package com.example.pagedlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.custom.widget.listview.viewholder.InfinityViewHolder
import com.example.infinitylist.data.Post
import com.example.test.myapplication.databinding.MenuItemBinding
import java.lang.Exception

class PostPagedListAdapter(val listener: View.OnClickListener) : PagedListAdapter<Post, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = MenuItemBinding.inflate(LayoutInflater.from(parent.context))
        view.root.setOnClickListener(listener)
        return InfinityViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            val item = getItem(position) as Post
            val viewHolder = holder as InfinityViewHolder
            viewHolder.tv.text = item.title
            val layout = viewHolder.tv.parent as LinearLayout
            layout.tag = item
        }catch (e: Exception){}
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                    oldItem.id == newItem.id
        }
    }
}