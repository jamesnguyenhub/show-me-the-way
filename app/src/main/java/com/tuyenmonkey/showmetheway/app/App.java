package com.tuyenmonkey.showmetheway.app;

import android.app.Application;

import com.tuyenmonkey.showmetheway.presentation.di.component.AppComponent;
import com.tuyenmonkey.showmetheway.presentation.di.component.DaggerAppComponent;
import com.tuyenmonkey.showmetheway.presentation.di.module.AppModule;

/**
 * Created by tuyen on 1/5/2016.
 */
public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
