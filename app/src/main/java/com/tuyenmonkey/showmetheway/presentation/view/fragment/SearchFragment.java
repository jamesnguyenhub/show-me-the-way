package com.tuyenmonkey.showmetheway.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tuyenmonkey.showmetheway.R;
import com.tuyenmonkey.showmetheway.data.service.GooglePlacesService;
import com.tuyenmonkey.showmetheway.data.service.ServiceFactory;
import com.tuyenmonkey.showmetheway.helper.LogUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tuyen on 1/2/2016.
 */
public class SearchFragment extends BaseFragment {

    private static final String TAG = SearchFragment.class.getSimpleName();

    @Bind(R.id.ll_search_panel)
    LinearLayout llSearchPanel;

    @Bind(R.id.et_search)
    EditText etSearch;

    @Bind(R.id.rv_places)
    RecyclerView rvPlaces;

    @Inject
    //SearchPresenter searchPresenter;

    //private PlacesAdapter placesAdapter;

    public static SearchFragment newInstance() {
        SearchFragment searchFragment = new SearchFragment();

        return searchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, fragmentView);
        //setupUI();

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //this.initialize();
        //this.loadPlaceList("Cong hoa");
    }

    @OnClick(R.id.tv_starting_point)
    void onStartingPointTextViewClicked() {
        LogUtils.v(TAG, "onStartingPointTextViewClicked");
        //showSearchPanel(true);
    }

    @OnClick(R.id.tv_destination)
    void onDestinationTextViewClicked() {
        LogUtils.v(TAG, "onDestinationTextViewClicked");
        //showSearchPanel(true);

        GooglePlacesService googlePlacesService = ServiceFactory.createRetrofitService(
                GooglePlacesService.class, GooglePlacesService.BASE_URL);

        googlePlacesService.getPlaceList("cong hoa")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(String predictionEntity) {
                        LogUtils.i(TAG, "onNext" + predictionEntity);
                        /*if (predictionEntity != null) {
                            LogUtils.i(TAG, "!=null");
                            if (predictionEntity.getPredictions() != null) {
                                for (PlaceEntity placeEntity : predictionEntity.getPredictions()) {
                                    LogUtils.d(TAG, placeEntity.getDescription());
                                }
                            }
                        }*/
                    }
                });
    }

    @OnTextChanged(R.id.et_search)
    void onSearchEditTextChanged() {
        LogUtils.v(TAG, "onSearchEditTextChanged");
        if (etSearch.getText().toString().length() > 3) {
            // Todo: query places
        }
    }

    @OnClick(R.id.bt_temp)
    void onButtonTempClicked() {
        //showSearchPanel(false);
        GooglePlacesService googlePlacesService = ServiceFactory.createRetrofitService(
                GooglePlacesService.class, GooglePlacesService.BASE_URL);

        googlePlacesService.getPlaceList("cong hoa")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(String predictionEntity) {
                        LogUtils.i(TAG, "onNext" + predictionEntity);
                        /*if (predictionEntity != null) {
                            LogUtils.i(TAG, "!=null");
                            if (predictionEntity.getPredictions() != null) {
                                for (PlaceEntity placeEntity : predictionEntity.getPredictions()) {
                                    LogUtils.d(TAG, placeEntity.getDescription());
                                }
                            }
                        }*/
                    }
                });
    }
}