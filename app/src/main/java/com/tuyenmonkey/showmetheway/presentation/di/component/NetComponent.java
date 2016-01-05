package com.tuyenmonkey.showmetheway.presentation.di.component;

import com.tuyenmonkey.showmetheway.presentation.di.PerActivity;
import com.tuyenmonkey.showmetheway.presentation.di.module.ActivityModule;
import com.tuyenmonkey.showmetheway.presentation.di.module.NetModule;
import com.tuyenmonkey.showmetheway.presentation.view.fragment.MapFragment;
import com.tuyenmonkey.showmetheway.presentation.view.fragment.SearchFragment;

import dagger.Component;

/**
 * Created by tuyen on 1/5/2016.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, NetModule.class})
public interface NetComponent extends ActivityComponent {

    void inject(SearchFragment searchFragment);
    void inject(MapFragment mapFragment);
}
