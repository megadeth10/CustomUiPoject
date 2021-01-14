package com.example.infinitylist.module

import com.example.infinitylist.viewmodel.PostViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postViewModelModule = module {
    viewModel { PostViewModel(get()) }
}