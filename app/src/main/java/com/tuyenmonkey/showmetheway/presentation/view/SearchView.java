package com.tuyenmonkey.showmetheway.presentation.view;

import android.widget.EditText;

import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;

import java.util.List;

/**
 * Created by tuyen on 1/2/2016.
 */
public interface SearchView extends BaseView {

    void renderPlaceList(List<PlaceModel> placeModelList);
    void toggleSearchPanel(boolean show);
    void toggleRemoveSearchTextImageView(boolean show);
    void setStartingText(String text);
    void setDestinationText(String text);
    void hideSoftKey(EditText editText);
}
