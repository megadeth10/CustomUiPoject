package com.example.infinitylistwithrxjava.module

import com.example.infinitylistwithrxjava.viewmodel.PostRxJavaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postRxJavaViewModelModule = module {
    viewModel { PostRxJavaViewModel(get()) }
}