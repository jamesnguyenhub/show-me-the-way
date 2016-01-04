package com.tuyenmonkey.showmetheway.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuyen on 1/4/2016.
 */
public class DirectionEntity {

    @SerializedName("routes")
    private List<RouteEntity> routes;

    public DirectionEntity() {}

    public List<RouteEntity> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteEntity> routes) {
        this.routes = routes;
    }
}
