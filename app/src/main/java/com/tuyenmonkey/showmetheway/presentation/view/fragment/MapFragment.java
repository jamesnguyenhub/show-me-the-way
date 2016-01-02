package com.tuyenmonkey.showmetheway.presentation.view.fragment;

import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;

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

    private GoogleApiClient googleApiClient;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Places.GEO_DATA_API)
                    .build();
        }

        this.initializeListeners();
        this.moveCamera(10.75, 106.6667);
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
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

    public void findPlace(PlaceModel placeModel) {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            Places.GeoDataApi.getPlaceById(googleApiClient, placeModel.getPlaceId())
                    .setResultCallback(new ResultCallback<PlaceBuffer>() {
                        @Override
                        public void onResult(PlaceBuffer places) {
                            if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                final Place place = places.get(0);
                                addMarker(place.getLatLng());
                                moveCamera(place.getLatLng().latitude, place.getLatLng().longitude);
                                LogUtils.d(TAG, "Place found: " + place.getName());
                            } else {
                                LogUtils.e(TAG, "Place not found");
                            }

                            places.release();
                        }
                    });
        } else {
            LogUtils.e(TAG, "googleApiClient is disconnected");
        }
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        markerOptions.title(getAddressFromLatLng(latLng));
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
        //        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());

        getMap().addMarker(markerOptions);
    }

    private void initializeListeners() {
        getMap().setOnMarkerClickListener(this);
        getMap().setOnMapLongClickListener(this);
        getMap().setOnInfoWindowClickListener( this );
        getMap().setOnMapClickListener(this);
    }

    private void moveCamera(double latitude, double longitude) {
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
