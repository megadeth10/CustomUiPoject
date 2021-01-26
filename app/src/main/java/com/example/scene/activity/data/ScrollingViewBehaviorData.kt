package com.example.scene.activity.data

import com.example.scene.activity.menu.UIList

class ScrollingViewBehaviorData{
    private val _listMenuItem: ArrayList<UIList.MenuItem> = ArrayList(0)
    val listMenuItem: List<UIList.MenuItem>
        get() = _listMenuItem

    constructor():super(){
        this._listMenuItem.addAll(getDataList())
    }

    private fun getDataList(): ArrayList<UIList.MenuItem> {
        val list: ArrayList<UIList.MenuItem> = ArrayList()
        list.add(UIList.MenuItem(String.format(
                "CoordinateLayout으로 Toolbar의 layout_ScrollFlags와 layout_behavior overriding하여 Snackbar를 움직임을 반전 시켰다."),
                null))
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
        list.add(UIList.MenuItem(String.format("pppp"), null))
        list.add(UIList.MenuItem(String.format("qqqq"), null))
        list.add(UIList.MenuItem(String.format("rrrr"), null))
        list.add(UIList.MenuItem(String.format("ssss"), null))
        list.add(UIList.MenuItem(String.format("tttt"), null))
        list.add(UIList.MenuItem(String.format("uuuu"), null))
        list.add(UIList.MenuItem(String.format("vvvv"), null))
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
        list.add(UIList.MenuItem(String.format("pppp"), null))
        list.add(UIList.MenuItem(String.format("qqqq"), null))
        list.add(UIList.MenuItem(String.format("rrrr"), null))
        list.add(UIList.MenuItem(String.format("ssss"), null))
        list.add(UIList.MenuItem(String.format("tttt"), null))
        list.add(UIList.MenuItem(String.format("uuuu"), null))
        list.add(UIList.MenuItem(String.format("vvvv"), null))
        return list
    }
}