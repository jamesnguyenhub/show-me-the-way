package com.tuyenmonkey.showmetheway.presentation.presenter;

import android.content.Context;

import com.tuyenmonkey.showmetheway.data.entity.PredictionEntity;
import com.tuyenmonkey.showmetheway.data.service.GoogleApiService;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.helper.Utilities;
import com.tuyenmonkey.showmetheway.presentation.di.PerActivity;
import com.tuyenmonkey.showmetheway.presentation.mapper.PlaceModelDataMapper;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;
import com.tuyenmonkey.showmetheway.presentation.view.SearchView;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tuyen on 1/2/2016.
 */
@PerActivity
public class SearchPresenter implements Presenter {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private SearchView searchView;
    private Context context;
    private GoogleApiService googleApiService;
    private PlaceModelDataMapper placeModelDataMapper;

    @Inject
    public SearchPresenter(GoogleApiService googleApiService,
                           PlaceModelDataMapper placeModelDataMapper) {
        this.googleApiService = googleApiService;
        this.placeModelDataMapper = placeModelDataMapper;
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void toggleSearchPanel(boolean show) {
        LogUtils.i(TAG, "toggleSearchPanel");

        this.searchView.toggleSearchPanel(show);

        if (show) {
            this.searchView.setFocusableSearchEditText(true);
            this.searchView.toggleSoftKey(true);
        }
    }

    public void searchPlace(String address) {
        LogUtils.i(TAG, "searchPlace");

        if (Utilities.isThereInternetConnection(context)) {
            searchView.hideError();
        } else {
            searchView.showError();
            return;
        }

        if (address.length() > 0) {
            this.searchView.toggleRemoveSearchTextImageView(true);

            // Only request to search if text size > 3
            if (address.length() > 3) {
                this.loadPlaceList(address);
            }
        } else {
            this.searchView.toggleRemoveSearchTextImageView(false);
        }
    }

    public void selectPlace(PlaceModel placeModel, boolean isStartingPointSearched) {
        LogUtils.i(TAG, "selectPlace");

        if (isStartingPointSearched) {
            this.searchView.setStartingText(placeModel.getDescription());
        } else {
            this.searchView.setDestinationText(placeModel.getDescription());
        }

        this.backToMapView();
    }

    public void loadPlaceList(String address) {
        LogUtils.i(TAG, "loadPlaceList");

        googleApiService.getPlaceList(address)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PredictionEntity>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(PredictionEntity predictionEntity) {
                        LogUtils.i(TAG, "onNext");
                        if (predictionEntity != null) {
                            searchView.renderPlaceList(
                                    placeModelDataMapper.transform(
                                            predictionEntity.getPlaces()));
                        }
                    }
                });
    }

    public void backToMapView() {
        LogUtils.i(TAG, "backToMapView");

        this.searchView.toggleSearchPanel(false);
        this.searchView.setFocusableSearchEditText(false);
        this.searchView.toggleSoftKey(false);
        this.resetSearch();
    }

    public void resetSearch() {
        LogUtils.i(TAG, "backToMapView");

        this.searchView.resetSearch();
    }
}
