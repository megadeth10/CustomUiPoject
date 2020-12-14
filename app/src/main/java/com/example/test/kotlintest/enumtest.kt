package com.example.test.kotlintest

enum class Color(val r:Int, val g:Int, val b:Int){
    RED(255,0,0), GREEN(0,255,0), BLUE(0,0,255);

    fun rgb() = (r * 255 + g) * 255 + b
}

fun getColor() : ArrayList<Int> {
    Color.RED.rgb();
    val array = ArrayList<Int>()
    array.add(Color.RED.rgb())
    array.add(Color.GREEN.rgb())
    array.add(Color.BLUE.rgb())
    return array
}