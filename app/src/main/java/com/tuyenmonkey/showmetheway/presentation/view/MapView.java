package com.tuyenmonkey.showmetheway.presentation.view;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by tuyen on 1/4/2016.
 */
public interface MapView extends BaseView {

    void drawPath(PolylineOptions polylineOptions);
    void moveCameraToLocation(double latitude, double longitude);
    void addMarker(LatLng position, boolean isStartingPoint);
    void clearMap();
    void setOriginPosition(LatLng latLng);
    LatLng getOriginPosition();
    void setDestinationPosition(LatLng latLng);
    LatLng getDestinationPosition();
}
