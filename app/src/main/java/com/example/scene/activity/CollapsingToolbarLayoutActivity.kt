package com.example.scene.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.scene.activity.collapsinglayout.TabPagerAdapter
import com.example.test.myapplication.R
import com.example.utils.Log
import com.example.utils.util
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*

class CollapsingToolbarLayoutActivity : AppCompatActivity() {
    val TAG: String = CollapsingToolbarLayoutActivity::class.simpleName.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            Log.e(TAG, "setNavigationOnClickListener")
            finish()
        })

        this.loadImage()

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = TabPagerAdapter(supportFragmentManager)
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                changePager(position)
            }
        })
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.addOnTabSelectedListener( object: OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.e(TAG,"onTabSelected")
                var position = tab?.position ?: 0
                changeTab(position)
            }
        })
    }

    private fun changePager(position: Int){
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val tab = tabLayout.getTabAt(position)
        tab?.select()
    }

    private fun changeTab(position: Int){
        Log.e(TAG,String.format("changeTab() %d", position))
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.setCurrentItem(position,false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pager, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        val id: Int = item.itemId;
        if(id === R.id.menu_add){
            showSnackbar("추가")
            return true
        }
        return false
    }

    private fun showSnackbar(message: String){
        Snackbar.make(root, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("확인", View.OnClickListener {  })
                .show()
    }

    private fun loadImage(){
        val imageView = findViewById<ImageView>(R.id.image);
        var width = windowManager.defaultDisplay.width
        var scalePoint = util.ratioSize(this,375f, 300f, null)
        Glide.with(this)
                .load("https://tlj.co.kr:7008/data/product/2018-3-14_event(46).jpg")
                .override(width, scalePoint.y)
                .centerInside()
                .into(imageView)
    }
}
