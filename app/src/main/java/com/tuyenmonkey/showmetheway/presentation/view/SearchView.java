package com.tuyenmonkey.showmetheway.presentation.view;

import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;

import java.util.List;

/**
 * Created by tuyen on 1/2/2016.
 */
public interface SearchView extends BaseView {

    /**
     * Show place list to view
     *
     * @param placeModelList
     */
    void renderPlaceList(List<PlaceModel> placeModelList);

    /**
     * Show/hide the search panel
     *
     * @param show
     */
    void toggleSearchPanel(boolean show);

    /**
     * Show/hide remove text button in search panel
     *
     * @param show
     */
    void toggleRemoveSearchTextImageView(boolean show);

    /**
     * Set the origin place text
     *
     * @param text
     */
    void setStartingText(String text);

    /**
     * Set the destination text
     *
     * @param text
     */
    void setDestinationText(String text);

    /**
     * Toggle soft keyboard when search panel closed
     */
    void toggleSoftKey(boolean show);

    /**
     * Set focusable to search place edittext
     *
     * @param focusable
     */
    void setFocusableSearchEditText(boolean focusable);

    /**
     *
     */
    void resetSearch();

    void showError();

    void hideError();
}
