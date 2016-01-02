package com.tuyenmonkey.showmetheway.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuyen on 1/2/2016.
 */
public class PredictionEntity {

    @SerializedName("predictions")
    private List<PlaceEntity> placeEntityList;

    public PredictionEntity() {}

    public List<PlaceEntity> getPlaceEntityList() {
        return placeEntityList;
    }

    public void setPlaceEntityList(List<PlaceEntity> placeEntityList) {
        this.placeEntityList = placeEntityList;
    }
}
