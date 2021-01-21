package com.example.storetestsub.module

import com.example.storetestsub.viewmodel.StoreTestSubViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val storeSubViewModelModule = module {
    viewModel { StoreTestSubViewModel(get()) }
}