package com.junaya.gank.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aya on 2016/11/25.
 */

public class GankRetrofit {

    private static final String BASE_URL = "http://gank.io/api/";

    static GankApi mGankApi;

    static Retrofit mRetrofit;

    public GankRetrofit() {
    }


    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    public static GankApi getGankApi() {
        return mGankApi == null ? iniRetrofit().create(GankApi.class) : mGankApi;
    }

    private static Retrofit iniRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(Client.configClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return mRetrofit;
    }


}
