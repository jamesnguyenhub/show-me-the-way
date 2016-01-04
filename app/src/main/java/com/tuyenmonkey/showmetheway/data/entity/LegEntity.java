package com.tuyenmonkey.showmetheway.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tuyen on 1/4/2016.
 */
public class LegEntity {

    @SerializedName("steps")
    private List<StepEntity> steps;

    public LegEntity() {}

    public List<StepEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<StepEntity> steps) {
        this.steps = steps;
    }
}
