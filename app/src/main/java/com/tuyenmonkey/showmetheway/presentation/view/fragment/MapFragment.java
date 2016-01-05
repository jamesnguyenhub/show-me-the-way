package com.tuyenmonkey.showmetheway.presentation.view.fragment;

import android.content.Context;
import android.location.Address;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;
import com.tuyenmonkey.showmetheway.presentation.presenter.MapPresenter;
import com.tuyenmonkey.showmetheway.presentation.view.MapView;

import java.util.List;
import java.util.Locale;

/**
 * Created by tuyen on 1/2/2016.
 */
public class MapFragment extends SupportMapFragment implements
        MapView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener {

    private static final String TAG = MapFragment.class.getSimpleName();

    private MapPresenter mapPresenter;
    private GoogleApiClient googleApiClient;
    private GoogleMap googleMap;
    private LatLng originLatLng, destinationLatLng;
    private OnAddressChangedListener onAddressChangedListener;

    public interface OnAddressChangedListener {
        void onAddressChanged(String address, boolean isStartingPoint);
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnAddressChangedListener) {
            onAddressChangedListener = (OnAddressChangedListener)context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LogUtils.i(TAG, "onMarkerDrag");

        if (onAddressChangedListener != null) {
            String address = getAddress(marker.getPosition().latitude, marker.getPosition().longitude);

            if (marker.getTitle().equalsIgnoreCase("start")) {
                originLatLng = marker.getPosition();
                onAddressChangedListener.onAddressChanged(address, true);
            } else {
                destinationLatLng = marker.getPosition();
                onAddressChangedListener.onAddressChanged(address, false);
            }
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (originLatLng != null && destinationLatLng != null) {
            googleMap.clear();
            drawDirection(originLatLng, destinationLatLng);
            addMarker(originLatLng, true);
            addMarker(destinationLatLng, false);
        }
    }

    @Override
    public void drawPath(PolylineOptions polylineOptions) {
        googleMap.addPolyline(polylineOptions);
    }

    private void initialize() {
        mapPresenter = new MapPresenter();
        mapPresenter.setMapView(this);

        googleMap = getMap();
        googleMap.setOnMarkerDragListener(this);

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
                                googleMap.clear();

                                if (isStartingPoint) {
                                    originLatLng = place.getLatLng();
                                } else {
                                    destinationLatLng = place.getLatLng();
                                }

                                moveCameraToLocation(place.getLatLng().latitude,
                                        place.getLatLng().longitude);

                                if (originLatLng != null) {
                                    addMarker(originLatLng, true);
                                }

                                if (destinationLatLng != null) {
                                    addMarker(destinationLatLng, false);
                                }

                                if (originLatLng != null && destinationLatLng != null) {
                                    drawDirection(originLatLng, destinationLatLng);
                                }

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

    private void drawDirection(LatLng origin, LatLng destination) {
        LogUtils.i(TAG, "drawDirection");
        this.mapPresenter.testGetDirection(origin, destination);
    }

    private void addMarker(LatLng position, boolean isStartingPoint) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);

        if (isStartingPoint) {
            markerOptions.title("start");
            markerOptions.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else {
            markerOptions.title("destination");
            markerOptions.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }

        markerOptions.draggable(true);

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

    private String getAddress(double latitude, double longitude) {
        LogUtils.i(TAG, String.format("getAddress (%f, %f)", latitude, longitude));

        String addressString = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                int maxAddressLine = returnedAddress.getMaxAddressLineIndex();
                for (int i = 0; i < maxAddressLine; i++) {
                    strReturnedAddress
                            .append(returnedAddress.getAddressLine(i))
                            .append((i < maxAddressLine - 1) ? ", " : "");
                }

                addressString = strReturnedAddress.toString();
                LogUtils.d(TAG, "" + strReturnedAddress.toString());
            } else {
                LogUtils.d(TAG, "No Address returned");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, e.getMessage());
        }

        return addressString;
    }
}
