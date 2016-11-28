package com.junaya.gank.data.remote;

import com.junaya.gank.data.Gank;
import com.junaya.gank.data.Results;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by aya on 2016/11/25.
 */

public interface GankApi {

    /**
     *
     * @param type Android | iOS | 前端 |
     * @param page current page
     * @return
     */
    @GET("data/{type}/10/{page}")
    Observable<Results<List<Gank>>> getGank(@Path("type") String type, @Path("page") int page);


    /**
     * @param query query key works
     * @param type  Android | iOS | 前端 |
     * @param page  current page
     * @return
     */
    @GET("search/query/{queryKey}/category/{type}/count/20/page/{page}")
    Observable<Results<List<Gank>>> getSearch(
            @Path("queryKey") String query,
            @Path("type") String type
            , @Path("page") int page);

}
