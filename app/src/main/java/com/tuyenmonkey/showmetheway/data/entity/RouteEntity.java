package com.tuyenmonkey.showmetheway.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuyen on 1/4/2016.
 */
public class RouteEntity {

    @SerializedName("legs")
    private List<LegEntity> legs;

    public RouteEntity() {}

    public List<LegEntity> getLegs() {
        return legs;
    }

    public void setLegs(List<LegEntity> legs) {
        this.legs = legs;
    }
}
