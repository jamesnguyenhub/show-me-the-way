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

                        List<List<HashMap<String, Double>>> routes = getRoutes(
                                directionEntity.getRoutes());

                        PolylineOptions lineOptions = getLineOptions(routes);

                        mapView.drawPath(lineOptions);
                    }
                });
    }

    private List<List<HashMap<String, Double>>> getRoutes(List<RouteEntity> routeEntityList) {
        List<List<HashMap<String, Double>>> routes = new ArrayList<>();

        for (RouteEntity routeEntity : routeEntityList) {
            List<HashMap<String, Double>> path = new ArrayList<>();

            for (LegEntity legEntity : routeEntity.getLegs()) {
                for (StepEntity stepEntity : legEntity.getSteps()) {
                    PolylineEntity polyline = stepEntity.getPolyline();
                    List<LatLng> positionList = Utilities.decodePolyline(polyline.getPoints());

                    for (int i = 0; i < positionList.size(); i++) {
                        HashMap<String, Double> temp = new HashMap<>();
                        temp.put("lat", positionList.get(i).latitude);
                        temp.put("lng", positionList.get(i).longitude);
                        path.add(temp);
                    }
                }

                routes.add(path);
            }
        }

        return routes;
    }

    private PolylineOptions getLineOptions(List<List<HashMap<String, Double>>> routes) {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;

        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();

            List<HashMap<String, Double>> path = routes.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap<String, Double> point = path.get(j);

                double lat = point.get("lat");
                double lng = point.get("lng");
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            lineOptions.addAll(points);
            lineOptions.width(5);
            lineOptions.color(Color.BLUE);
        }

        return lineOptions;
    }
}
