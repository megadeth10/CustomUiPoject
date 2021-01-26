package com.example.scene.storetestsub.module

import com.example.scene.storetestsub.viewmodel.StoreTestSubViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val storeSubViewModelModule = module {
    viewModel { StoreTestSubViewModel(get()) }
}