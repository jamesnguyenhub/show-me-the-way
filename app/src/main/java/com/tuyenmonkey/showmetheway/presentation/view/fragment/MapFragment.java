package com.tuyenmonkey.showmetheway.presentation.view.fragment;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;
import com.tuyenmonkey.showmetheway.presentation.presenter.MapPresenter;
import com.tuyenmonkey.showmetheway.presentation.view.MapView;

/**
 * Created by tuyen on 1/2/2016.
 */
public class MapFragment extends SupportMapFragment implements
        MapView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MapFragment.class.getSimpleName();

    private MapPresenter mapPresenter;
    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap;
    private Place originPlace, destinationPlace;

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

        initialize();
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

    private void initialize() {
        mapPresenter = new MapPresenter();
        mapPresenter.setMapView(this);

        googleMap = getMap();

        this.moveCameraToLocation(10.75, 106.6667);
    }

    public void findPlace(PlaceModel placeModel, final boolean isStartingPoint) {
        LogUtils.i(TAG, "findPlace");

        if (googleApiClient != null && googleApiClient.isConnected()) {
            Places.GeoDataApi.getPlaceById(googleApiClient, placeModel.getPlaceId())
                    .setResultCallback(new ResultCallback<PlaceBuffer>() {
                        @Override
                        public void onResult(PlaceBuffer places) {
                            if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                final Place place = places.get(0);

                                if (isStartingPoint) {
                                    originPlace = place;
                                } else {
                                    destinationPlace = place;
                                }

                                moveCameraToLocation(place.getLatLng().latitude,
                                        place.getLatLng().longitude);

                                googleMap.clear();

                                if (originPlace != null) {
                                    addMarker(originPlace.getLatLng(), 0);
                                }

                                if (destinationPlace != null) {
                                    addMarker(destinationPlace.getLatLng(), 1);
                                }

                                if (originPlace != null && destinationPlace != null) {
                                    drawDirection(originPlace.getLatLng(),
                                            destinationPlace.getLatLng());
                                }

                                LogUtils.d(TAG, "Place found: " + place.getName());
                            } else {
                                LogUtils.e(TAG, "Place not found");
                            }
                        }
                    });
        } else {
            LogUtils.e(TAG, "googleApiClient is disconnected");
        }
    }

    private void drawDirection(LatLng origin, LatLng destination) {
        LogUtils.i(TAG, "drawDirection");
        this.mapPresenter.testGetDirection(origin, destination);
    }

    private void addMarker(LatLng position, int markerIcon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        //markerOptions.title(address);
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
        //        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());

        googleMap.addMarker(markerOptions);
    }

    private void moveCameraToLocation(double latitude, double longitude) {
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(new LatLng(latitude, longitude))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), null);
    }

    @Override
    public void drawPath(PolylineOptions polylineOptions) {
        googleMap.addPolyline(polylineOptions);
    }
}
