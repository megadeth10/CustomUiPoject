package com.example.scene.infinitylistwithrxjava.module

import com.example.scene.infinitylistwithrxjava.viewmodel.PostRxJavaViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postRxJavaViewModelModule = module {
    viewModel { PostRxJavaViewModel(get()) }
}