package com.example.scene.activity.collapsinglayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scene.activity.menu.MenuAdapter
import com.example.scene.activity.menu.UIList
import com.example.test.myapplication.R
import com.example.utils.Log

class RightTabFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_right, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = view.findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(context)
        val adapter = MenuAdapter(View.OnClickListener {
            val item: UIList.MenuItem = it.tag as UIList.MenuItem
        })
        list.adapter = adapter;
        this.initData()
    }

    private fun initData() {
        Log.e("ListFragment", "initData")
        val data = ArrayList<UIList.MenuItem>();
        for (i in 0..1) {
            data.add(UIList.MenuItem(String.format("aaaa %d", i), null))
        }
        val list = view?.findViewById<RecyclerView>(R.id.list)
        val adapter: MenuAdapter = list?.adapter as MenuAdapter
        adapter?.itemArray = data;
        list.adapter?.notifyDataSetChanged()
    }
}