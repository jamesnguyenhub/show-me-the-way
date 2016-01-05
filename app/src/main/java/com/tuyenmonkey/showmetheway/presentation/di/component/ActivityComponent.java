package com.tuyenmonkey.showmetheway.presentation.di.component;

import android.app.Activity;

import com.tuyenmonkey.showmetheway.presentation.di.PerActivity;
import com.tuyenmonkey.showmetheway.presentation.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by tuyen on 1/5/2016.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();
}
