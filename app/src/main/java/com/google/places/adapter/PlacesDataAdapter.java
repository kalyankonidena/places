package com.google.places.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.places.R;
import com.google.places.databinding.PlacesRecyclerRowBinding;
import com.google.places.model.Results;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesDataAdapter extends RecyclerView.Adapter<PlacesDataAdapter.ViewHolder> {

    private ArrayList<Results> placesResults;
    
    public PlacesDataAdapter(ArrayList<Results> placesItemsModels) {
        placesResults = placesItemsModels;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       PlacesRecyclerRowBinding placesRecyclerRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.places_recycler_row, parent, false);
        return new ViewHolder(placesRecyclerRowBinding);
    }

    public ArrayList<Results> getPlacesResults(){
        return placesResults;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Results placesModel = placesResults.get(position);
        holder.bind(placesModel);
    }

    @Override
    public int getItemCount() {
        if(placesResults==null || placesResults.size()==0)
            return 0;
       return Math.min(placesResults.size(), 10);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        PlacesRecyclerRowBinding placesRecyclerRowBinding;
        @BindView(R.id.icon)
        ImageView icon;

        public ViewHolder(PlacesRecyclerRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.placesRecyclerRowBinding = itemRowBinding;
        }
        public void bind(Results results) {
            placesRecyclerRowBinding.setVariable(BR.placeResults, results);
            placesRecyclerRowBinding.executePendingBindings();
        }
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
