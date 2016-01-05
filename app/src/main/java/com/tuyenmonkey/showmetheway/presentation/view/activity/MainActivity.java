package com.tuyenmonkey.showmetheway.presentation.view.activity;

import android.os.Bundle;

import com.tuyenmonkey.showmetheway.R;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.presentation.di.HasComponent;
import com.tuyenmonkey.showmetheway.presentation.di.component.DaggerNetComponent;
import com.tuyenmonkey.showmetheway.presentation.di.component.NetComponent;
import com.tuyenmonkey.showmetheway.presentation.di.module.NetModule;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;
import com.tuyenmonkey.showmetheway.presentation.view.fragment.MapFragment;
import com.tuyenmonkey.showmetheway.presentation.view.fragment.SearchFragment;

public class MainActivity extends BaseActivity implements
        SearchFragment.OnPlaceChosenListener,
        MapFragment.OnAddressChangedListener,
        HasComponent<NetComponent> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SearchFragment searchFragment;
    private MapFragment mapFragment;
    private NetComponent netComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    public void onAddressChanged(String address, boolean isStartingPoint) {
        if (isStartingPoint) {
            searchFragment.setStartingText(address);
        } else {
            searchFragment.setDestinationText(address);
        }
    }

    @Override
    public NetComponent getComponent() {
        return netComponent;
    }

    @Override
    public void onPlaceChosen(PlaceModel placeModel, boolean isStartingPoint) {
        LogUtils.i(TAG, "onPlaceChosen");

        mapFragment.findPlaceOnMap(placeModel, isStartingPoint);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            searchFragment = SearchFragment.newInstance();
            mapFragment = MapFragment.newInstance();

            addFragment(R.id.fl_search_portion, searchFragment);
            addFragment(R.id.fl_map_portion, mapFragment);
        } else {
            searchFragment = (SearchFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.fl_search_portion);
            mapFragment = (MapFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.fl_map_portion);
        }
    }

    private void initializeInjector() {
        this.netComponent = DaggerNetComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .netModule(new NetModule())
                .build();
    }
}
