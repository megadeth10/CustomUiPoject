package com.example.javatest;

import android.util.Log;

public class ClassTest {
    int mInt;
    String mStr;

    public ClassTest(String str, int i){
        this.initail(str, i);
    }

    public ClassTest(int value){
        this.initail("", value);
    }

    public ClassTest(String value){
        this.initail(value, 0);
    }

    private void initail(String str, int i) {
        this.mInt = i;
        this.mStr = str;
    }

    public void displayValue() {
        Log.e(ClassTest.class.getSimpleName(), String.format("%d %s", this.mInt, this.mStr));
    }
}
