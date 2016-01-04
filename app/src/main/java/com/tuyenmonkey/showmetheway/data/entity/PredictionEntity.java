package com.tuyenmonkey.showmetheway.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuyen on 1/2/2016.
 */
public class PredictionEntity {

    @SerializedName("predictions")
    private List<PlaceEntity> places;

    public PredictionEntity() {}

    public List<PlaceEntity> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceEntity> places) {
        this.places = places;
    }
}
