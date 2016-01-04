package com.tuyenmonkey.showmetheway.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tuyen on 1/4/2016.
 */
public class StepEntity {

    @SerializedName("polyline")
    private PolylineEntity polyline;

    public StepEntity() {}

    public PolylineEntity getPolyline() {
        return polyline;
    }

    public void setPolyline(PolylineEntity polyline) {
        this.polyline = polyline;
    }
}
