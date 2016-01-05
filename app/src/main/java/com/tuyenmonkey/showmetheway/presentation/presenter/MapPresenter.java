package com.tuyenmonkey.showmetheway.presentation.presenter;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tuyenmonkey.showmetheway.data.entity.DirectionEntity;
import com.tuyenmonkey.showmetheway.data.entity.LegEntity;
import com.tuyenmonkey.showmetheway.data.entity.PolylineEntity;
import com.tuyenmonkey.showmetheway.data.entity.RouteEntity;
import com.tuyenmonkey.showmetheway.data.entity.StepEntity;
import com.tuyenmonkey.showmetheway.data.service.GoogleApiService;
import com.tuyenmonkey.showmetheway.data.service.ServiceFactory;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.helper.Utilities;
import com.tuyenmonkey.showmetheway.presentation.view.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tuyen on 1/4/2016.
 */
public class MapPresenter implements Presenter {

    private static final String TAG = MapPresenter.class.getSimpleName();

    private MapView mapView;
    private GoogleApiService googleApiService;

    public MapPresenter() {
        googleApiService = ServiceFactory.createRetrofitService(
                GoogleApiService.class, GoogleApiService.BASE_URL);
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    public void showRoute(LatLng origin, LatLng destination) {
        googleApiService.getDirection(
                String.format("%f,%f", origin.latitude, origin.longitude),
                String.format("%f,%f", destination.latitude, destination.longitude))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DirectionEntity>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(DirectionEntity directionEntity) {
                        LogUtils.i(TAG, "onNext");
                        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();

                        if (directionEntity != null && directionEntity.getRoutes() != null) {
                            for (RouteEntity routeEntity : directionEntity.getRoutes()) {
                                List path = new ArrayList<HashMap<String, String>>();

                                for (LegEntity legEntity : routeEntity.getLegs()) {
                                    for (StepEntity stepEntity : legEntity.getSteps()) {
                                        PolylineEntity polyline = stepEntity.getPolyline();

                                        List<LatLng> positionList = Utilities.decodePolyline(polyline.getPoints());

                                        for (int l = 0; l < positionList.size(); l++) {
                                            HashMap<String, String> hm = new HashMap<String, String>();
                                            hm.put("lat", Double.toString(((LatLng) positionList.get(l)).latitude));
                                            hm.put("lng", Double.toString(((LatLng) positionList.get(l)).longitude));
                                            path.add(hm);
                                        }
                                    }

                                    routes.add(path);
                                }
                            }
                        }

                        ArrayList<LatLng> points = null;
                        PolylineOptions lineOptions = null;

                        for (int i = 0; i < routes.size(); i++) {
                            points = new ArrayList<LatLng>();
                            lineOptions = new PolylineOptions();

                            List<HashMap<String, String>> path = routes.get(i);

                            for (int j = 0; j < path.size(); j++) {
                                HashMap<String, String> point = path.get(j);

                                double lat = Double.parseDouble(point.get("lat"));
                                double lng = Double.parseDouble(point.get("lng"));
                                LatLng position = new LatLng(lat, lng);

                                points.add(position);
                            }

                            lineOptions.addAll(points);
                            lineOptions.width(5);
                            lineOptions.color(Color.BLUE);
                        }

                        mapView.drawPath(lineOptions);
                    }
                });
    }
}
