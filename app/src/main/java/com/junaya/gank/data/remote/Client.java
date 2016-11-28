package com.junaya.gank.data.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by aya on 2016/11/28.
 */

public class Client {
    private static final int DEFAULT_TIMEOUT = 5;


    public static OkHttpClient configClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
