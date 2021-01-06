package com.example.test.aac.data

data class UserProfile(
        val n:String = "없음",
        val a:String = "없음",
        val p:String = "000-0000-0000",
        val g:Boolean = true
) {
    var name: String = n
    var address:String = a
    var phone: String = p
    var gender: Boolean = g
}