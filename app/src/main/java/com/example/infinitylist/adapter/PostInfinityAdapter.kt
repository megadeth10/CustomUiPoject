package com.example.infinitylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.infinitylist.data.Post
import com.example.custom.widget.listview.adapter.InfinityAdapter
import com.example.custom.widget.listview.viewholder.InfinityViewHolder
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.MenuItemBinding
import com.example.utils.Log
import java.lang.Exception

/**
 * TODO: 기억하기
 * recycleView에 TextView 사용하면 view의 재사용으로 textView width를 match_parent로 잡아도
 * 가끔 text linefeed가 되는 현상이 있다.
 * 그래서 width를 warp_content로 하면 문제가 해결되는 것을 확인하였다.
 */
class PostInfinityAdapter(listener: View.OnClickListener): InfinityAdapter<Post>(listener) {
    init {
        this.TAG = PostInfinityAdapter::class.java.simpleName
    }
    override fun createView(
            parent: ViewGroup,
            viewType: Int,
            clickListener: View.OnClickListener?
    ): RecyclerView.ViewHolder {
        val view = MenuItemBinding.inflate(LayoutInflater.from(parent.context))
        view.root.setOnClickListener(this.onClickListener)
        return InfinityViewHolder(view.root)
    }

    override fun onBind(
            holder: RecyclerView.ViewHolder,
            position: Int,
            data: MutableList<*>?
    ) {
        try {
            val item = data?.get(position) as Post
            val viewHolder = holder as InfinityViewHolder
            viewHolder.tv.text = item.title
            val layout = viewHolder.tv.parent as LinearLayout
            layout.tag = item
        }catch (e: Exception){}
    }
}