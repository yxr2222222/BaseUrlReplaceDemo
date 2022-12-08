package com.yxr.baseurlreplace.interceptor;

import android.text.TextUtils;

import com.yxr.baseurlreplace.BaseUrlReplaceSdk;
import com.yxr.baseurlreplace.config.BaseUrlReplaceConfig;
import com.yxr.baseurlreplace.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseUrlReplaceInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl oldHttpUrl = request.url();

        String ignoreReplaceHost = oldHttpUrl.queryParameter(BaseUrlReplaceConfig.IGNORE_REPLACE_BASE_URL);
        if (TextUtils.isEmpty(ignoreReplaceHost)) {
            String baseUrl = BaseUrlReplaceSdk.getInstance().getBaseUrl();
            if (HttpUtil.isUrl(baseUrl)) {
                HttpUrl newBaseUrl = HttpUrl.parse(baseUrl);
                if (newBaseUrl != null && !TextUtils.equals(oldHttpUrl.scheme() + oldHttpUrl.host() + oldHttpUrl.port()
                        , newBaseUrl.scheme() + newBaseUrl.host() + newBaseUrl.port())) {
                    String scheme = newBaseUrl.scheme();
                    String host = newBaseUrl.host();
                    int port = newBaseUrl.port();

                    BaseUrlReplaceConfig replaceConfig = BaseUrlReplaceSdk.getInstance().getReplaceConfig();
                    if (replaceConfig != null && replaceConfig.getOnBaseUrlReplaceCallback() != null) {
                        replaceConfig.getOnBaseUrlReplaceCallback().onBaseUrlReplace(host);
                    }

                    Request.Builder builder = request.newBuilder();
                    return chain.proceed(builder.url(
                                    oldHttpUrl.newBuilder()
                                            .scheme(scheme)
                                            .host(host)
                                            .port(port)
                                            .build())
                            .build());
                }
            }
        }

        return chain.proceed(request);
    }
}
