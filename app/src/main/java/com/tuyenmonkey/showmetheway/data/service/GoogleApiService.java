package com.tuyenmonkey.showmetheway.data.service;

import com.tuyenmonkey.showmetheway.data.entity.DirectionEntity;
import com.tuyenmonkey.showmetheway.data.entity.PredictionEntity;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by tuyen on 1/2/2016.
 */
public interface GoogleApiService {

    String BASE_URL = "https://maps.googleapis.com/maps/api/";

    @GET("place/autocomplete/json?key=AIzaSyCPerJaKOABVI3Du2HQBRoxADjc9Nd61Gs&components=country:vn&types=address")
    Observable<PredictionEntity> getPlaceList(@Query("input") String input);

    /**
     *
     * @param origin format "latitude,longitude"
     * @param destination format "latitude,longitude"
     * @return
     */
    @GET("directions/json?")
    Observable<DirectionEntity> getDirection(@Query("origin") String origin, @Query("destination") String destination);
}
