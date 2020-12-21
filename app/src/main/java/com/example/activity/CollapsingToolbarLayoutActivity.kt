package com.example.activity

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.activity.menu.MenuAdapter
import com.example.activity.menu.UIList.MenuItem
import com.example.test.myapplication.R
import com.example.utils.Log
import com.example.utils.util
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*

// TODO: CollapsingToolbarLayout은 behavior가 없나 있다면 toolbar background color 적용 해보기
// TODO: FloatingActionButton TestBehavior를 custom 해보기...
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
        list.layoutManager = LinearLayoutManager(this)
        val adapter = MenuAdapter(View.OnClickListener {
            val item: MenuItem = it.tag as MenuItem
            showSnackbar(item.title)
        })
        list.adapter = adapter;
        this.initData()
        this.loadImage()
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

    private fun initData() {
        val data = ArrayList<MenuItem>();
        for (i in 0..30) {
            data.add(MenuItem(String.format("aaaa %d", i), null))
        }
        val adapter: MenuAdapter = list.adapter as MenuAdapter
        adapter?.itemArray = data;
        list.adapter?.notifyDataSetChanged()
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
