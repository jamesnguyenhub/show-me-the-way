package com.tuyenmonkey.showmetheway.presentation.mapper;

import com.tuyenmonkey.showmetheway.data.entity.PlaceEntity;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tuyen on 1/2/2016.
 */
public class PlaceModelDataMapper {

    public PlaceModelDataMapper() {}

    public PlaceModel transform(PlaceEntity placeEntity) {
        if (placeEntity == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        PlaceModel placeModel = new PlaceModel();
        placeModel.setPlaceId(placeEntity.getPlaceId());
        placeModel.setDescription(placeEntity.getDescription());

        return placeModel;
    }

    public List<PlaceModel> transform(List<PlaceEntity> placeEntityList) {
        List<PlaceModel> placeModelList;

        if (placeEntityList != null && !placeEntityList.isEmpty()) {
            placeModelList = new ArrayList<>();

            for (PlaceEntity placeEntity : placeEntityList) {
                placeModelList.add(transform(placeEntity));
            }
        } else {
            placeModelList = Collections.emptyList();
        }

        return placeModelList;
    }
}
