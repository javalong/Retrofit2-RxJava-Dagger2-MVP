package com.javalong.rrdm.retrofit;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by javalong on 16-11-11.
 */

public interface ServerApi {

    /**
     * 这里只是一个Demo
     * 运行会崩溃,请先替换成具体的类型,参数,url
     */
    @POST("url")
    @FormUrlEncoded
    Observable<String> requestUrl(@Field("param") String param);

}
