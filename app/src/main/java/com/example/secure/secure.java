package com.example.secure;

import android.content.Context;

import com.example.utils.Log;

import java.util.Calendar;

import de.adorsys.android.securestoragelibrary.SecurePreferences;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class secure {
    private String TAG = secure.class.getSimpleName();

    public void set(Context ctx, String key, String value, Function1<? super Boolean, Unit> callback) {
        long time = Calendar.getInstance().getTimeInMillis();
//        SecurePreferences.setValue(key, value, ctx);
        PreferencesObject object = new PreferencesObject(ctx, key, value, callback);
        Single.just(object)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .subscribe(t -> {
                    SecurePreferences.setValue(t.key, t.value, t.context);
                    Log.e(TAG, String.format("SecurePreferences.setValue() finish"));
                    if(t.function != null) {
                        t.function.invoke(true);
                    }
                });
        Log.e(TAG, String.format("set() consumed time: %d",
                Calendar.getInstance().getTimeInMillis() - time));
    }

    public String get(Context ctx, String key, String defaultValue) {
        return SecurePreferences.getStringValue(key, ctx, defaultValue);
    }

    public void remove(Context ctx, String key) {
        long time = Calendar.getInstance().getTimeInMillis();
        SecurePreferences.removeValue(key, ctx);
        Log.e(TAG, String.format("remove() consumed time: %d",
                Calendar.getInstance().getTimeInMillis() - time));
    }

    private class PreferencesObject {
        private Context context;
        private String key;
        private String value;
        private Function1<? super Boolean, Unit> function;

        public PreferencesObject(Context ctx, String key, String value, Function1<? super Boolean, Unit> fun) {
            this.context = ctx;
            this.key = key;
            this.value = value;
            this.function = fun;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Function1<? super Boolean, Unit> getFunction() {
            return function;
        }

        public void setFunction(Function1<? super Boolean, Unit> function) {
            this.function = function;
        }
    }
}
