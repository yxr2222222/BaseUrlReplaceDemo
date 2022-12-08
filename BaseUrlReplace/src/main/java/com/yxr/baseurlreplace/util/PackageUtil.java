package com.yxr.baseurlreplace.util;

import android.content.pm.ApplicationInfo;

import com.yxr.baseurlreplace.BaseUrlReplaceSdk;

public class PackageUtil {
    private static Boolean isDebug = null;

    /**
     * 是否是Debug包
     */
    public static boolean isDebug() {
        if (isDebug == null) {
            try {
                ApplicationInfo info = BaseUrlReplaceSdk.getInstance().getContext().getApplicationInfo();
                isDebug = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (isDebug == null) {
            isDebug = false;
        }
        return isDebug;
    }
}
