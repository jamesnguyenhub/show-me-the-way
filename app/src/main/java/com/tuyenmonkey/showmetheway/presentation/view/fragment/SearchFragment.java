package com.tuyenmonkey.showmetheway.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuyenmonkey.showmetheway.R;
import com.tuyenmonkey.showmetheway.helper.LogUtils;
import com.tuyenmonkey.showmetheway.helper.Utilities;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;
import com.tuyenmonkey.showmetheway.presentation.presenter.SearchPresenter;
import com.tuyenmonkey.showmetheway.presentation.view.SearchView;
import com.tuyenmonkey.showmetheway.presentation.view.adapter.PlacesAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by tuyen on 1/2/2016.
 */
public class SearchFragment extends BaseFragment implements
        SearchView,
        PlacesAdapter.OnPlaceItemClickedListener {

    private static final String TAG = SearchFragment.class.getSimpleName();

    @Bind(R.id.tv_starting_point)
    TextView tvStartingPoint;

    @Bind(R.id.tv_destination)
    TextView tvDestination;

    @Bind(R.id.ll_search_panel)
    LinearLayout llSearchPanel;

    @Bind(R.id.et_search)
    EditText etSearch;

    @Bind(R.id.rv_places)
    RecyclerView rvPlaces;

    private SearchPresenter searchPresenter;
    private PlacesAdapter placesAdapter;
    private boolean isStartingPointSearched;
    private OnPlaceChosenListener onPlaceChosenListener;

    public interface OnPlaceChosenListener {
        void onPlaceChosen(PlaceModel placeModel, boolean isStartingPoint);
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnPlaceChosenListener) {
            onPlaceChosenListener = (OnPlaceChosenListener)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG, "onCreateView");

        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, fragmentView);
        setupUI();

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        this.initialize();
    }

    @Override
    public void renderPlaceList(List<PlaceModel> placeModelList) {
        LogUtils.i(TAG, "renderPlaceList");

        this.placesAdapter.setPlaceModelList(placeModelList);
    }

    @Override
    public void toggleSearchPanel(boolean show) {
        LogUtils.i(TAG, "toggleSearchPanel");

        if (show) {
            llSearchPanel.animate()
                    .translationY(0)
                    .withLayer();
            etSearch.setFocusable(true);
            etSearch.setFocusableInTouchMode(true);
        } else {
            llSearchPanel.animate()
                    .translationY(Utilities.getSystemWindowSize(getActivity()).y)
                    .withLayer();
            etSearch.setFocusable(false);
            etSearch.setFocusableInTouchMode(false);
            etSearch.setText("");
        }
    }

    @Override
    public void setStartingText(String text) {
        tvStartingPoint.setText(text);
    }

    @Override
    public void setDestinationText(String text) {
        tvDestination.setText(text);
    }

    @Override
    public void onPlaceItemClicked(PlaceModel placeModel) {
        LogUtils.v(TAG, "onPlaceItemClicked");

        this.searchPresenter.onPlaceItemClicked(placeModel, isStartingPointSearched);
        if (onPlaceChosenListener != null) {
            onPlaceChosenListener.onPlaceChosen(placeModel, isStartingPointSearched);
        }
    }

    @OnClick(R.id.tv_starting_point)
    void onStartingPointTextViewClicked() {
        LogUtils.v(TAG, "onStartingPointTextViewClicked");
        isStartingPointSearched = true;
        toggleSearchPanel(true);
    }

    @OnClick(R.id.tv_destination)
    void onDestinationTextViewClicked() {
        LogUtils.v(TAG, "onDestinationTextViewClicked");
        isStartingPointSearched = false;
        toggleSearchPanel(true);
    }

    @OnTextChanged(R.id.et_search)
    void onSearchEditTextChanged() {
        LogUtils.v(TAG, "onSearchEditTextChanged");
        if (etSearch.getText().toString().length() > 3) {
            this.searchPresenter.loadPlaceList(etSearch.getText().toString());
        }
    }

    private void initialize() {
        LogUtils.i(TAG, "initialize");

        this.searchPresenter = new SearchPresenter();
        this.searchPresenter.setSearchView(this);
    }

    private void setupUI() {
        LogUtils.i(TAG, "setupUI");
        // Hide search panel for the first time
        llSearchPanel.setTranslationY(Utilities.getSystemWindowSize(getActivity()).y);

        this.rvPlaces.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.placesAdapter = new PlacesAdapter(getActivity(), null);
        this.placesAdapter.setOnPlaceItemClickedListener(this);
        this.rvPlaces.setAdapter(this.placesAdapter);
    }
}