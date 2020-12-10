package com.example.secure;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

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
