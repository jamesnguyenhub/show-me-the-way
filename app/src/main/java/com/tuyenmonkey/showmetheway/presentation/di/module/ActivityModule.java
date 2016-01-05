package com.tuyenmonkey.showmetheway.presentation.di.module;

import android.app.Activity;

import com.tuyenmonkey.showmetheway.presentation.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tuyen on 1/5/2016.
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
