package com.example.pagedlist.module

import com.example.pagedlist.datasource.PostDataSource
import org.koin.dsl.module

val postDataSourceModule = module {
    factory { PostDataSource(get()) }
}