package com.tuyenmonkey.showmetheway.presentation.di.component;

import com.tuyenmonkey.showmetheway.presentation.di.module.AppModule;
import com.tuyenmonkey.showmetheway.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tuyen on 1/5/2016.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(BaseActivity baseActivity);
}
