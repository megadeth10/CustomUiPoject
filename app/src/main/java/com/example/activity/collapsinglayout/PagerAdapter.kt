package com.example.activity.collapsinglayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.utils.Log

class TabPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        Log.e("TabPagerAdapter",String.format("getItem() %d", position))
        return when(position){
            0->{
                LeftTabFragment()
            }
            else ->{
                RightTabFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

}