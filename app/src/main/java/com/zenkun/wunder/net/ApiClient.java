package com.zenkun.wunder.net;

import android.content.Context;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zen zenyagami@gmail.com on 07/04/2017.
 */

public class ApiClient {
    private static ApiClient SINGLETON = new ApiClient();

    public static ApiClient get() {
        return SINGLETON;
    }

    private static final String BASE_URL="https://s3-us-west-2.amazonaws.com/";
    private WunderApi wunderApi;

    public WunderApi getWunderApi() {
        return wunderApi;
    }

    public ApiClient init(Gson gson)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        wunderApi=retrofit.create(WunderApi.class);
        return this;
    }
}
