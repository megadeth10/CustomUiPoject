package com.example.test.material.module

import com.example.test.material.viewmodel.CheckViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val checkViewModelModule = module {
    viewModel {(checkboxMap: HashMap<String, Boolean>) -> CheckViewModel(checkboxMap) }
}