package com.example.global;

import android.app.Application;

import com.example.scene.infinitylist.module.PostApiNetworkModuleKt;
import com.example.scene.infinitylist.module.PostViewModelModuleKt;
import com.example.scene.infinitylistwithrxjava.module.PostRxJavaApiNetworkModuleKt;
import com.example.scene.infinitylistwithrxjava.module.PostRxJavaViewModelModuleKt;
import com.example.store.app.AppDataModuleKt;
import com.example.test.kointest.KoinModuleKt;
import com.example.network.retrofit2.koin.NetworkModuleKt;
import com.example.scene.pagedlist.module.PostDataSourceModuleKt;
import com.example.store.user.UserModuleKt;
import com.example.scene.storetest.module.StoreTestViewModelModuleKt;
import com.example.scene.storetestsub.module.StoreTestSubViewModelModuleKt;
import com.example.test.material.module.CheckViewModelModuleKt;
import com.example.test.myapplication.BuildConfig;
import com.example.utils.Log;

import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;
import org.koin.core.context.GlobalContext;
import org.koin.core.module.Module;

import java.util.ArrayList;
import java.util.List;

import static org.koin.core.context.GlobalContextExtKt.startKoin;

public class AppApplication extends Application {
    private static String TAG = AppApplication.class.getSimpleName();
    public static boolean PrintLog = BuildConfig.BUILD_TYPE == "debug";

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        super.onCreate();
        List<Module> modules = new ArrayList<>(0);
        modules.add(KoinModuleKt.getAppModule());
        modules.add(PostApiNetworkModuleKt.getPostApiModule());
        modules.add(NetworkModuleKt.getNetworkModule());
        modules.add(PostViewModelModuleKt.getPostViewModelModule());
        modules.add(PostDataSourceModuleKt.getPostDataSourceModule());
        modules.add(PostRxJavaApiNetworkModuleKt.getPostRxJavaApiModule());
        modules.add(PostRxJavaViewModelModuleKt.getPostRxJavaViewModelModule());
        modules.add(UserModuleKt.getUserModule());
        modules.add(StoreTestViewModelModuleKt.getStoreViewModelModule());
        modules.add(StoreTestSubViewModelModuleKt.getStoreSubViewModelModule());
        modules.add(CheckViewModelModuleKt.getCheckViewModelModule());
//        modules.add(AppDataModuleKt.getAppDataModule());
        KoinApplication koin = KoinAndroidApplication.create(this).modules(modules);
        startKoin(GlobalContext.INSTANCE, koin);
    }

    @Override
    public void onTerminate() {
        Log.e(TAG,"onTerminate");
        super.onTerminate();
    }
}
