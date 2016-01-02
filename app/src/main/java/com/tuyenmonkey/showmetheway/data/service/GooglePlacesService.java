package com.tuyenmonkey.showmetheway.data.service;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by tuyen on 1/2/2016.
 */
public interface GooglePlacesService {

    String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    @GET("autocomplete/json?key=AIzaSyCPerJaKOABVI3Du2HQBRoxADjc9Nd61Gs&components=country:vn&types=address")
    Observable<String> getPlaceList(@Query("input") String input);
}
