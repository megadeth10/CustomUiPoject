package com.example.test.material.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.fragment.adapter.NormalAdapter
import com.example.test.fragment.data.NormalItem
import com.example.test.material.viewmodel.TabViewModel
import com.example.test.myapplication.R
import com.example.utils.Log
import java.util.*

class ListFragment : Fragment(), View.OnClickListener {
    companion object {
        fun newInstance(obj: Any?): ListFragment {
            return ListFragment()
        }
    }

    private val TAG = ListFragment::class.java.simpleName
    private var viewModel: TabViewModel? = null

    private val addButtonClickListener = View.OnClickListener {
        this.viewModel?.addList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate()")
        viewModel = ViewModelProvider(requireActivity()).get(TabViewModel::class.java)
        Log.e(TAG, "Fragment ViewModel $viewModel")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewmodel_list, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.list)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.list_divider, null))
        recyclerView.addItemDecoration(dividerItemDecoration)
        val adapter = NormalAdapter(this)
        recyclerView.adapter = adapter
        adapter.itemArray = viewModel!!.listData.value!!
        view.findViewById<Button>(R.id.add_btn).setOnClickListener(addButtonClickListener)

        this.viewModel?.listData?.observe(this, androidx.lifecycle.Observer {
            adapter.itemArray = it
        })
    }

    override fun onClick(view: View) {
        val tv = view.findViewById<TextView>(R.id.title)
        val obj = tv.tag as NormalItem
        Log.e(TAG, String.format("onClick : %s", obj.title))
    }
}
