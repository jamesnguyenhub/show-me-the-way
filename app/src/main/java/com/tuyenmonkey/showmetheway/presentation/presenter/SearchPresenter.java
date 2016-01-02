package com.tuyenmonkey.showmetheway.presentation.presenter;

import com.tuyenmonkey.showmetheway.data.entity.PredictionEntity;
import com.tuyenmonkey.showmetheway.data.service.GooglePlacesService;
import com.tuyenmonkey.showmetheway.data.service.ServiceFactory;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.presentation.mapper.PlaceModelDataMapper;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;
import com.tuyenmonkey.showmetheway.presentation.view.SearchView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tuyen on 1/2/2016.
 */
public class SearchPresenter implements Presenter {

    private static final String TAG = SearchPresenter.class.getSimpleName();

    private SearchView searchView;
    private GooglePlacesService googlePlacesService;
    private PlaceModelDataMapper placeModelDataMapper;

    public SearchPresenter() {
        googlePlacesService = ServiceFactory.createRetrofitService(
                GooglePlacesService.class, GooglePlacesService.BASE_URL);
        placeModelDataMapper = new PlaceModelDataMapper();
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    public void onPlaceItemClicked(PlaceModel placeModel, boolean isStartingPointSearched) {
        LogUtils.i(TAG, "onPlaceItemClicked");

        if (isStartingPointSearched) {
            this.searchView.setStartingText(placeModel.getDescription());
        } else {
            this.searchView.setDestinationText(placeModel.getDescription());
        }

        this.searchView.toggleSearchPanel(false);
    }

    public void loadPlaceList(String address) {
        googlePlacesService.getPlaceList(address)
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
                                            predictionEntity.getPlaceEntityList()));
                        }
                    }
                });
    }
}
