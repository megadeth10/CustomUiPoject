package com.example.test.material

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.custom.activity.ToolbarActivity
import com.example.test.material.viewmodel.CheckViewModel
import com.example.test.material.viewmodelfactory.CheckViewModelFactory
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.LayoutCheckboxBinding
import com.example.utils.Log

class CheckboxActivity:ToolbarActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private lateinit var contentBinding: LayoutCheckboxBinding
    private lateinit var viewModel: CheckViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = CheckboxActivity::class.java.simpleName
    }

    override fun setToolbar() {
        this.toolBar.title = "Material Checkbox"
    }

    override fun setContent() {
        this.contentBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_checkbox,
                null,
                false
        )
        setContentsViewBinding(this.contentBinding)
        this.contentBinding.lifecycleOwner = this
        val map = HashMap<String, Boolean>().apply {
            set(contentBinding.firstCb.id.toString(), false)
            set(contentBinding.secondCb.id.toString(), false)
            set(contentBinding.thirdCb.id.toString(), false)
        }
        this.viewModel = ViewModelProvider(this, CheckViewModelFactory(map)).get(CheckViewModel::class.java)
        this.contentBinding.checkViewModel = this.viewModel

        this.viewModel.checkAll.observe(this, Observer {
            Log.e(TAG, "checkAll observer $it")
            this.contentBinding.allCb.isChecked = it
            var enableBtn = false
            if(it){
                enableBtn = true
            }
            if(this.contentBinding.confirmBtn.isEnabled != enableBtn){
                this.contentBinding.confirmBtn.isEnabled = enableBtn
            }
        })

        this.viewModel.checkGroup.observe(this, Observer {
            Log.e(TAG, "checkGroup observer")
            this.contentBinding.firstCb.isChecked = it[this.contentBinding.firstCb.id.toString()]!!
            this.contentBinding.secondCb.isChecked = it[this.contentBinding.secondCb.id.toString()]!!
            this.contentBinding.thirdCb.isChecked = it[this.contentBinding.thirdCb.id.toString()]!!
        })

        this.contentBinding.allCb.setOnCheckedChangeListener(this)
        this.contentBinding.firstCb.setOnCheckedChangeListener(this)
        this.contentBinding.secondCb.setOnCheckedChangeListener(this)
        this.contentBinding.thirdCb.setOnCheckedChangeListener(this)
        this.contentBinding.toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            Log.e(TAG, "ButtonCheckedListener() checkedId: $checkedId isChecked: $isChecked")
        }

        this.contentBinding.confirmBtn.setOnClickListener(this)
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when(p0?.id){
            R.id.first_cb,R.id.second_cb,R.id.third_cb -> {
                this.viewModel.setCheck(p0.id.toString(), p1)
            }
            R.id.all_cb -> {
                this.viewModel.setCheckAll(p1)
            }
        }
    }

    override fun onClick(p0: View?) {
        if(p0?.id == this.contentBinding.confirmBtn.id){
            this.viewModel.checkGroup.value?.entries!!.forEach {
                Log.e(TAG, "confirm onClick() ${it.key}-${it.value}")
            }
        }
    }
}