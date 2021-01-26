package com.example.test.kointest

class KoinPresenter(val repo: KoinRepository) {
    fun sayHello() = "hi! Hello ${repo.getMessage()} from $this"
}