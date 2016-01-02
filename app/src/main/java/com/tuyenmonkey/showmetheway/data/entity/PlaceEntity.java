package com.tuyenmonkey.showmetheway.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tuyen on 1/2/2016.
 */
public class PlaceEntity {

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("description")
    private String description;

    public PlaceEntity() {}

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
