package com.example.store.user

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var userModule = module {
    single { User(androidApplication()) }
}