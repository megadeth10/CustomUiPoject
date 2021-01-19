package com.example.global;

import android.app.Application;

import com.example.infinitylist.module.PostApiNetworkModuleKt;
import com.example.infinitylist.module.PostViewModelModuleKt;
import com.example.infinitylistwithrxjava.module.PostRxJavaApiNetworkModuleKt;
import com.example.infinitylistwithrxjava.module.PostRxJavaViewModelModuleKt;
import com.example.infinitylistwithrxjava.viewmodel.PostRxJavaViewModel;
import com.example.kointest.KoinModuleKt;
import com.example.network.retrofit2.koin.NetworkModuleKt;
import com.example.pagedlist.module.PostDataSourceModuleKt;
import com.example.test.myapplication.BuildConfig;

import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;
import org.koin.core.context.GlobalContext;
import org.koin.core.module.Module;

import java.util.ArrayList;
import java.util.List;

import static org.koin.core.context.GlobalContextExtKt.startKoin;

public class AppApplication extends Application {
    public static boolean PrintLog = BuildConfig.BUILD_TYPE == "debug";

    @Override
    public void onCreate() {
        super.onCreate();
        List<Module> modules = new ArrayList<>(0);
        modules.add(KoinModuleKt.getAppModule());
        modules.add(PostApiNetworkModuleKt.getPostApiModule());
        modules.add(NetworkModuleKt.getNetworkModule());
        modules.add(PostViewModelModuleKt.getPostViewModelModule());
        modules.add(PostDataSourceModuleKt.getPostDataSourceModule());
        modules.add(PostRxJavaApiNetworkModuleKt.getPostRxJavaApiModule());
        modules.add(PostRxJavaViewModelModuleKt.getPostRxJavaViewModelModule());
        KoinApplication koin = KoinAndroidApplication.create(this)
                .modules(modules);
        startKoin(GlobalContext.INSTANCE, koin);
    }
}
