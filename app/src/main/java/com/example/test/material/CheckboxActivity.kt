package com.example.test.material

import android.content.Intent
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
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * TODO: bindingAdapter로 activity를 이용하여 코드를 bindingAdapter로 Activity로 부터 코드를 이동시킴
 * 그러나 screen rotation하면 activity의 reference값이 변경된다.... 문제가 될까 그리고 왜 바뀌는지...
 * solve: activity가 새로 생성된다. 정상적인 상태다.
 */
class CheckboxActivity:ToolbarActivity() {
    private lateinit var contentBinding: LayoutCheckboxBinding
    private lateinit var viewModel: CheckViewModel  // by viewModel { parametersOf(map)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var name = intent?.getStringExtra("name") ?: ""
        TAG = CheckboxActivity::class.java.simpleName + name
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
        this.contentBinding.activity = this
        Log.e(TAG, "setContent() Activity $this")
        val map = HashMap<String, Boolean>().apply {
            set(contentBinding.firstCb.id.toString(), false)
            set(contentBinding.secondCb.id.toString(), false)
            set(contentBinding.thirdCb.id.toString(), false)
        }
        this.viewModel = getViewModel { parametersOf(map) }
//        this.viewModel = ViewModelProvider(this, CheckViewModelFactory(map)).get(CheckViewModel::class.java)
        this.contentBinding.checkViewModel = this.viewModel
        Log.e(TAG, "setContent() viewModel $viewModel")

        this.contentBinding.toggleButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            Log.e(TAG, "ButtonCheckedListener() checkedId: $checkedId isChecked: $isChecked")
        }
    }
}