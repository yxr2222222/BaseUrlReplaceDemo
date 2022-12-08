package com.yxr.baseurlreplace.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yxr.baseurlreplace.BaseUrlReplaceSdk;

public class SPUtil {

    public static SharedPreferences getSP() {
        return BaseUrlReplaceSdk.getInstance().getContext().getSharedPreferences("base_url_replace", Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        getSP().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return getSP().getString(key, defaultValue);
    }
}