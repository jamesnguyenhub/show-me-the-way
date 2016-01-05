package com.tuyenmonkey.showmetheway.presentation.di.module;

import com.tuyenmonkey.showmetheway.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tuyen on 1/5/2016.
 */
@Module
public class AppModule {

    App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplication() {
        return this.application;
    }
}
