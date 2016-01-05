package com.tuyenmonkey.showmetheway.presentation.di.module;

import com.tuyenmonkey.showmetheway.data.service.GoogleApiService;
import com.tuyenmonkey.showmetheway.data.service.ServiceFactory;
import com.tuyenmonkey.showmetheway.presentation.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tuyen on 1/5/2016.
 */
@Module
public class NetModule {

    public NetModule() {}

    @Provides
    @PerActivity
    GoogleApiService provideGoogleApiService() {
        return ServiceFactory.createRetrofitService(
                GoogleApiService.class, GoogleApiService.BASE_URL);
    }
}
