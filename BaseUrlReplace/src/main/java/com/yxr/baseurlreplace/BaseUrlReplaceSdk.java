package com.yxr.baseurlreplace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.yxr.baseurlreplace.callback.OnBaseUrlReplaceCallback;
import com.yxr.baseurlreplace.config.BaseUrlReplaceConfig;
import com.yxr.baseurlreplace.util.HttpUtil;
import com.yxr.baseurlreplace.util.PackageUtil;
import com.yxr.baseurlreplace.util.SPUtil;
import com.yxr.baseurlreplace.widget.BaseUrlReplaceEditActivity;

public class BaseUrlReplaceSdk {
    private static final String SP_CURR_DEBUG_BASE_URL = "SP_CURR_DEBUG_BASE_URL";
    @SuppressLint("StaticFieldLeak")
    private static BaseUrlReplaceSdk instance;
    private Context context;
    private BaseUrlReplaceConfig replaceConfig;
    private String currDebugBaseUrl;

    private BaseUrlReplaceSdk() {

    }

    public static BaseUrlReplaceSdk getInstance() {
        if (instance == null) {
            synchronized (BaseUrlReplaceSdk.class) {
                if (instance == null) {
                    instance = new BaseUrlReplaceSdk();
                }
            }
        }
        return instance;
    }

    public void init(@NonNull Context context, @NonNull BaseUrlReplaceConfig replaceConfig) {
        this.context = context;
        this.replaceConfig = replaceConfig;
    }

    public void startBaseUrlReplaceEditActivity(@NonNull Context context) {
        Intent intent = new Intent(context, BaseUrlReplaceEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 更新自定义的Debug网络地址
     */
    public void updateDebugUrl(String url) {
        currDebugBaseUrl = url;
        SPUtil.putString(SP_CURR_DEBUG_BASE_URL, currDebugBaseUrl);
    }

    /**
     * 获取动态域名
     */
    public String getBaseUrl() {
        if (getReplaceConfig() == null) {
            return null;
        }

        if (PackageUtil.isDebug()) {
            if (currDebugBaseUrl == null) {
                currDebugBaseUrl = SPUtil.getString(SP_CURR_DEBUG_BASE_URL, "");
            }
            if (TextUtils.isEmpty(currDebugBaseUrl)) {
                return getReplaceConfig().getDebugBaseUrl();
            }
            return currDebugBaseUrl;
        }

        OnBaseUrlReplaceCallback callback = getReplaceConfig().getOnBaseUrlReplaceCallback();
        return callback != null && HttpUtil.isUrl(callback.getCustomFormalBaseUrl())
                ? callback.getCustomFormalBaseUrl()
                : getReplaceConfig().getFormalBaseUrl();
    }

    public BaseUrlReplaceConfig getReplaceConfig() {
        return replaceConfig;
    }

    public Context getContext() {
        return context;
    }
}
