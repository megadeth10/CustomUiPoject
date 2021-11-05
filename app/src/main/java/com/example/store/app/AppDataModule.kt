package com.example.store.app

import com.deleo.c2cmarketplace.store.AppDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appDataModule = module {
    single { AppDataStore(androidApplication()) }
}