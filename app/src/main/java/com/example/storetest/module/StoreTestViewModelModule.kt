package com.example.storetest.module

import com.example.storetest.viewmodel.StoreTestViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

var storeViewModelModule = module {
    viewModel { StoreTestViewModel(get()) }
}