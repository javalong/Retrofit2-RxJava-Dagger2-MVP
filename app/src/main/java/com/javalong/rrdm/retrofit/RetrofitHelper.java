package com.javalong.rrdm.retrofit;


import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by javalong on 2016/5/29.
 * retrofit2
 */
public class RetrofitHelper {
    private static ServerApi api;
    private static OkHttpClient mOkHttpClient;
    private static Context mContext;
    private static boolean showError = true;

    /**
     * 启动后初始化
     */
    public static void init(Context context) {
        mContext = context;
        initOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://{你的url}").
                addConverterFactory(TWGsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                client(mOkHttpClient).
                build();
        api = retrofit.create(ServerApi.class);
    }

    /**
     * 重置baseUrl
     */
    public static void resetBaseUrl() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://{你的url}").
                addConverterFactory(TWGsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                client(mOkHttpClient).
                build();
        api = retrofit.create(ServerApi.class);
    }


    public static ServerApi getApi() {
        return api;
    }

    /**
     * 统一处理原始数据
     *
     * @param origion          是否需要原生的 不转化的数据
     * @param originalResponse
     */
    private static Response dealResponseData(Boolean origion, Response originalResponse) {
        String jsonString = null;
        try {
            BufferedSource bufferedSource = originalResponse.body().source();
            jsonString = bufferedSource.readString(Charset.forName("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (origion != null && origion) {
            return onSuccess(originalResponse, jsonString);
        }
        ResponseMessageBean msgBean = ResponseMessageBean.analyseReponse(jsonString);
        if (msgBean == null) return onSuccess(originalResponse, msgBean.data.toString());
        if (msgBean != null && (msgBean.errorCode == 200)) {
            showError = true;
            if (msgBean.data != null) {
                return onSuccess(originalResponse, msgBean.data.toString());
            } else {
                return originalResponse.newBuilder().body(null).build();
            }
        } else {
            onFailed(msgBean);
            throw new RuntimeException(msgBean.moreInfo.toString());
        }
    }

    /**
     * 初始化okHttp
     */
    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        /**
                         * 统一设置请求头
                         */
                        Request newRequest = createRequestHeader(request.newBuilder()).build();
                        Response originalResponse = chain.proceed(newRequest);
                        //如果是重定向，那么就执行重定向后返回数据。
                        if (originalResponse.isRedirect()) {
                            Request redirectRequest = request.newBuilder().url(originalResponse.header("location")).build();
                            originalResponse = chain.proceed(redirectRequest);
                        }
                        String origion = newRequest.header("origion");
                        originalResponse = dealResponseData(Boolean.parseBoolean(origion),originalResponse);
                        return originalResponse;
                    }
                };

                if (mOkHttpClient == null) {
                    try {
                        X509TrustManager xtm = new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                X509Certificate[] x509Certificates = new X509Certificate[0];
                                return x509Certificates;
                            }
                        };

                        SSLContext sslContext = null;
                        try {
                            sslContext = SSLContext.getInstance("SSL");

                            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (KeyManagementException e) {
                            e.printStackTrace();
                        }
                        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        };

                        // 指定缓存路径,缓存大小100Mb
                        Cache cache = new Cache(new File(mContext.getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                        mOkHttpClient = new OkHttpClient.Builder().
                                addInterceptor(mRewriteCacheControlInterceptor).
                                retryOnConnectionFailure(false).
                                connectTimeout(30, TimeUnit.SECONDS).
                                sslSocketFactory(sslContext.getSocketFactory()).
                                hostnameVerifier(DO_NOT_VERIFY).
                                cache(cache).
                                build();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private static Response onSuccess(Response originalResponse, String content) {
        return originalResponse.newBuilder().
                body(ResponseBody.create(null, content)).
                build();
    }


    /**
     * errorCode 不为200
     *
     * @param msgBean
     */
    private static void onFailed(ResponseMessageBean msgBean) {
        String alert = "";
        if (msgBean == null) {
            return;
        }
        if (msgBean.errorCode != 200) {
            //TODO 自定义错误处理
        }
    }

    /**
     * 统一处理请求头部数据
     *
     * @return
     */

    private static Request.Builder createRequestHeader(Request.Builder builder) {
        builder.header("Content-Type",
                "application/x-www-form-urlencoded");
        builder.header("User-Agent", getUserAgent());
        return builder;
    }


    public static String getUserAgent() {
        return "{自定义header头}";
    }

}