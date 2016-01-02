package com.tuyenmonkey.showmetheway.data.entity;

import java.util.List;

/**
 * Created by tuyen on 1/2/2016.
 */
public class PredictionEntity {

    private List<PlaceEntity> predictions;

    public PredictionEntity() {}

    public List<PlaceEntity> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PlaceEntity> predictions) {
        this.predictions = predictions;
    }
}
