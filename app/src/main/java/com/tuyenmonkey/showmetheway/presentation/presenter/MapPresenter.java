package com.tuyenmonkey.showmetheway.presentation.presenter;

import android.graphics.Color;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
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
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;
import com.tuyenmonkey.showmetheway.presentation.view.MapView;

import java.util.ArrayList;
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

    public void drawDirection(LatLng origin, LatLng destination) {
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

                        List<List<LatLng>> routes = getRoutes(
                                directionEntity.getRoutes());

                        PolylineOptions lineOptions = getLineOptions(routes.get(0));

                        mapView.drawPath(lineOptions);
                    }
                });
    }

    public void findPlace(GoogleApiClient googleApiClient, PlaceModel placeModel,
                          final boolean isStartingPoint) {
        LogUtils.i(TAG, "findPlace");

        Places.GeoDataApi.getPlaceById(googleApiClient, placeModel.getPlaceId())
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess() && places.getCount() > 0) {
                            final Place place = places.get(0);
                            mapView.clearMap();

                            if (isStartingPoint) {
                                mapView.setOriginPosition(place.getLatLng());
                            } else {
                                mapView.setDestinationPosition(place.getLatLng());
                            }

                            moveCamera(place.getLatLng().latitude,
                                    place.getLatLng().longitude);

                            if (mapView.getOriginPosition() != null) {
                                mapView.addMarker(mapView.getOriginPosition(), true);
                            }

                            if (mapView.getDestinationPosition() != null) {
                                mapView.addMarker(mapView.getDestinationPosition(), false);
                            }

                            if (mapView.getOriginPosition() != null
                                    && mapView.getDestinationPosition() != null) {
                                drawDirection(mapView.getOriginPosition(),
                                        mapView.getDestinationPosition());
                            }

                            LogUtils.d(TAG, "Place found: " + place.getName());
                        } else {
                            LogUtils.e(TAG, "Place not found");
                        }

                        places.release();
                    }
                });
    }

    public void moveCamera(double latitude, double longitude) {
        this.mapView.moveCameraToLocation(latitude, longitude);
    }

    private List<List<LatLng>> getRoutes(List<RouteEntity> routeEntityList) {
        List<List<LatLng>> routes = new ArrayList<>();

        for (RouteEntity routeEntity : routeEntityList) {
            List<LatLng> path = new ArrayList<>();

            for (LegEntity legEntity : routeEntity.getLegs()) {
                for (StepEntity stepEntity : legEntity.getSteps()) {
                    PolylineEntity polyline = stepEntity.getPolyline();
                    List<LatLng> positionList = Utilities.decodePolyline(polyline.getPoints());
                    path.addAll(positionList);
                }

                routes.add(path);
            }
        }

        return routes;
    }

    private PolylineOptions getLineOptions(List<LatLng> route) {
        PolylineOptions lineOptions = new PolylineOptions();

        lineOptions.addAll(route);
        lineOptions.width(5);
        lineOptions.color(Color.BLUE);

        return lineOptions;
    }
}
