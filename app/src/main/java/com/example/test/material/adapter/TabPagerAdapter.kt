package com.example.test.material.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.test.fragment.FourFragment
import com.example.test.fragment.SecondFragment
import com.example.test.fragment.ThreeFragment
import com.example.test.material.fragment.ListFragment
import com.example.test.material.viewmodel.TabViewModel
import com.example.utils.Log

class TabPagerAdapter
    : FragmentStateAdapter {
    private val TAG = TabPagerAdapter::class.simpleName
    private lateinit var viewModel: TabViewModel

    constructor(fragmentManager: FragmentManager, activity:AppCompatActivity, viewModel: TabViewModel)
        :super(fragmentManager, activity.lifecycle){
        this.viewModel = viewModel
    }

    override fun getItemCount(): Int {
        return this.viewModel.tabData.value?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        Log.e(TAG, "createFragment() position: $position")
        when(position % 4){
            1 -> {
                return SecondFragment.newInstance(null, null)
            }
            2 -> {
                return ThreeFragment.newInstance(null, null)
            }
            3 -> {
                return FourFragment.newInstance(null, null)
            }
        }
        return ListFragment.newInstance(Object())
    }
}