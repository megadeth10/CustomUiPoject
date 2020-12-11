package com.example.global;

import android.app.Application;

import com.example.myapplication.BuildConfig;

public class AppApplication extends Application {
    public static boolean PrintLog = BuildConfig.BUILD_TYPE == "debug";
}
