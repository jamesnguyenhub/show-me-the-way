package com.tuyenmonkey.showmetheway.presentation.view.activity;

import android.os.Bundle;

import com.tuyenmonkey.showmetheway.R;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;
import com.tuyenmonkey.showmetheway.presentation.view.fragment.MapFragment;
import com.tuyenmonkey.showmetheway.presentation.view.fragment.SearchFragment;

public class MainActivity extends BaseActivity implements
        SearchFragment.OnPlaceChosenListener,
        MapFragment.OnAddressChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SearchFragment searchFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeActivity(savedInstanceState);
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
    public void onPlaceChosen(PlaceModel placeModel, boolean isStartingPoint) {
        LogUtils.i(TAG, "onPlaceChosen");

        mapFragment.findPlace(placeModel, isStartingPoint);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            searchFragment = SearchFragment.newInstance();
            mapFragment = MapFragment.newInstance();

            addFragment(R.id.fl_search_portion, searchFragment);
            addFragment(R.id.fl_map_portion, mapFragment);
        }
    }
}
