package com.yxr.baseurlreplace.config;

import com.yxr.baseurlreplace.callback.OnBaseUrlReplaceCallback;
import com.yxr.baseurlreplace.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseUrlReplaceConfig {
    public static final String IGNORE_REPLACE_BASE_URL = "ignoreReplaceBaseUrl";
    /**
     * 正式环境的BaseUrl
     */
    private String formalBaseUrl;
    /**
     * 测试环境的BaseUrl
     */
    private String debugBaseUrl;
    /**
     * BaseUrl回调
     */
    private OnBaseUrlReplaceCallback onBaseUrlReplaceCallback;
    private final List<String> baseUrlList = new ArrayList<>();

    private BaseUrlReplaceConfig() {

    }

    public void setFormalBaseUrl(String formalBaseUrl) {
        this.formalBaseUrl = formalBaseUrl;
    }

    public String getFormalBaseUrl() {
        return formalBaseUrl;
    }

    public String getDebugBaseUrl() {
        return debugBaseUrl;
    }

    public OnBaseUrlReplaceCallback getOnBaseUrlReplaceCallback() {
        return onBaseUrlReplaceCallback;
    }

    public List<String> getBaseUrlList() {
        return baseUrlList;
    }


    public static class Builder {
        private BaseUrlReplaceConfig replaceConfig = new BaseUrlReplaceConfig();

        public Builder formalBaseUrl(String formalBaseUrl) {
            replaceConfig.formalBaseUrl = formalBaseUrl;
            addToBaseUrlList(formalBaseUrl);
            return this;
        }

        public Builder debugBaseUrl(String debugBaseUrl) {
            replaceConfig.debugBaseUrl = debugBaseUrl;
            addToBaseUrlList(debugBaseUrl);
            return this;
        }

        public Builder otherBaseUrl(String otherBaseUrl) {
            addToBaseUrlList(otherBaseUrl);
            return this;
        }

        public Builder onBaseUrlReplaceCallback(OnBaseUrlReplaceCallback onBaseUrlReplaceCallback) {
            replaceConfig.onBaseUrlReplaceCallback = onBaseUrlReplaceCallback;
            return this;
        }

        public BaseUrlReplaceConfig build() {
            return replaceConfig;
        }

        private void addToBaseUrlList(String url) {
            if (HttpUtil.isUrl(url) && !replaceConfig.baseUrlList.contains(url)) {
                replaceConfig.baseUrlList.add(url);
            }
        }

    }
}
