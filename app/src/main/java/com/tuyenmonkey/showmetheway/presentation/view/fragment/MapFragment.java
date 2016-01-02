package com.tuyenmonkey.showmetheway.presentation.view.fragment;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tuyenmonkey.showmetheway.helper.LogUtils;

import java.io.IOException;

/**
 * Created by tuyen on 1/2/2016.
 */
public class MapFragment extends SupportMapFragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = MapFragment.class.getSimpleName();

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initializeListeners();
        this.initializeCamera(10.75, 106.6667);
    }

    @Override
    public void onConnected(Bundle bundle) {
        LogUtils.i(TAG, "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        LogUtils.i(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        LogUtils.i(TAG, "onConnectionFailed");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LogUtils.i(TAG, "onInfoWindowClick");
    }

    @Override
    public void onMapClick(LatLng latLng) {
        LogUtils.i(TAG, "onMapClick");

        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        markerOptions.title(getAddressFromLatLng(latLng));
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
        //        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());

        getMap().addMarker(markerOptions);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        LogUtils.i(TAG, "onMapLongClick");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LogUtils.i(TAG, "onMarkerClick");
        return false;
    }

    private void initializeListeners() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener( this );
        getMap().setOnMapClickListener(this);
    }

    private void initializeCamera(double latitude, double longitude) {
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(latitude, longitude))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        getMap().animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), null);
        getMap().getUiSettings().setZoomControlsEnabled(true);
    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getActivity());

        String address = "";
        try {
            address = geocoder
                    .getFromLocation(latLng.latitude, latLng.longitude, 1)
                    .get(0)
                    .getAddressLine(0);

            LogUtils.d(TAG, String.format("Address: %s", address));
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return address;
    }
}
