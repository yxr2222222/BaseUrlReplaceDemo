package com.yxr.baseurlreplace.callback;

public interface OnBaseUrlReplaceCallback {
    /**
     * 获取自定义的正式环境的BaseUrl
     *
     * @return 返回空的或者不是网络地址将使用@{@link com.yxr.baseurlreplace.config.BaseUrlReplaceConfig}的formalBaseUrl
     */
    String getCustomFormalBaseUrl();

    /**
     * BaseUrl被替换
     *
     * @param newBaseUrl 新的BaseUrl地址
     */
    void onBaseUrlReplace(String newBaseUrl);

}
