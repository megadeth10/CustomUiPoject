package com.example.secure;

import android.content.Context;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

public class secure {
    private String TAG = secure.class.getSimpleName();
    public void set(Context ctx, String key, String value){
        SecurePreferences.setValue(key, value, ctx);
    }

    public String get(Context ctx, String key, String defaultValue){
        return SecurePreferences.getStringValue(key, ctx, defaultValue);
    }
}
