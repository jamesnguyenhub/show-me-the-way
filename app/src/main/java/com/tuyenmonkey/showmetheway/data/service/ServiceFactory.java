package com.tuyenmonkey.showmetheway.data.service;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by tuyen on 1/2/2016.
 */
public class ServiceFactory {

    public static <T> T createRetrofitService(final Class<T> serviceClass, final String baseUrl) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        T service = retrofit.create(serviceClass);

        return service;
    }
}
