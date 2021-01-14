package com.example.kointest

class KoinPresenter(val repo: KoinRepository) {
    fun sayHello() = "hi! Hello ${repo.getMessage()} from $this"
}