package com.example.test.kointest

import org.koin.dsl.module

val appModule = module {
    single<KoinRepository> { KoinRepositoryImpl() }
    factory { KoinPresenter(get()) }
}