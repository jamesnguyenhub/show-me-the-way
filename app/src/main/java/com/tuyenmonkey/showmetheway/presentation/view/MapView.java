package com.tuyenmonkey.showmetheway.presentation.view;

import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by tuyen on 1/4/2016.
 */
public interface MapView extends BaseView {

    void drawPath(PolylineOptions polylineOptions);
}
