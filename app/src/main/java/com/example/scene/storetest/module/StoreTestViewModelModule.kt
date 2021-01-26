package com.example.scene.storetest.module

import com.example.scene.storetest.viewmodel.StoreTestViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

var storeViewModelModule = module {
    viewModel { StoreTestViewModel(get()) }
}