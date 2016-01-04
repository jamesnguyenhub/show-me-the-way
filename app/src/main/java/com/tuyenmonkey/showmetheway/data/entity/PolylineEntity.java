package com.tuyenmonkey.showmetheway.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tuyen on 1/4/2016.
 */
public class PolylineEntity {

    @SerializedName("points")
    private String points;

    public PolylineEntity() {}

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
