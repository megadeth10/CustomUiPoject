package com.example.scene.infinitylist.module

import com.example.scene.infinitylist.viewmodel.PostViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postViewModelModule = module {
    viewModel { PostViewModel(get()) }
}