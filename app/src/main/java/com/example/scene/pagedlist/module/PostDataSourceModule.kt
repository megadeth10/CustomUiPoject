package com.example.scene.pagedlist.module

import com.example.scene.pagedlist.datasource.PostDataSource
import org.koin.dsl.module

val postDataSourceModule = module {
    factory { PostDataSource(get()) }
}