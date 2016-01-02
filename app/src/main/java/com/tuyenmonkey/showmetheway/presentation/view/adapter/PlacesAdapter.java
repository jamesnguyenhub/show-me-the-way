package com.tuyenmonkey.showmetheway.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuyenmonkey.showmetheway.R;
import com.tuyenmonkey.showmetheway.presentation.model.PlaceModel;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tuyen on 1/2/2016.
 */
public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private static final String TAG = PlacesAdapter.class.getSimpleName();

    private List<PlaceModel> placeModelList;
    private LayoutInflater layoutInflater;
    private OnPlaceItemClickedListener onPlaceItemClickedListener;

    public interface OnPlaceItemClickedListener {
        void onPlaceItemClicked(PlaceModel placeModel);
    }

    public PlacesAdapter(Context context, Collection<PlaceModel> placeModelCollection) {
        placeModelList = (List<PlaceModel>)placeModelCollection;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PlaceModel placeModel = placeModelList.get(position);

        holder.tvDescription.setText(placeModel.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlacesAdapter.this.onPlaceItemClickedListener != null) {
                    PlacesAdapter.this.onPlaceItemClickedListener.onPlaceItemClicked(placeModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (placeModelList != null) ? placeModelList.size() : 0;
    }

    public void setPlaceModelList(Collection<PlaceModel> placeModelCollection) {
        this.placeModelList = (List<PlaceModel>)placeModelCollection;
        this.notifyDataSetChanged();
    }

    public void setOnPlaceItemClickedListener(
            OnPlaceItemClickedListener onPlaceItemClickedListener) {
        this.onPlaceItemClickedListener = onPlaceItemClickedListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_description)
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

