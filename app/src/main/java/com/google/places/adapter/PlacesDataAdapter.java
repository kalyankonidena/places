package com.google.places.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.places.R;
import com.google.places.model.Results;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesDataAdapter extends RecyclerView.Adapter<PlacesDataAdapter.ViewHolder> {

    private ArrayList<Results> placesResults;

    private static Integer RECYCLER_ROW_LIMIT = 10;

    public PlacesDataAdapter(ArrayList<Results> placesItemsModels) {
        placesResults = placesItemsModels;
    }

    public PlacesDataAdapter() { }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_recycler_row, parent, false);
        return new ViewHolder(view);
    }

    public void setPlacesResults(ArrayList<Results> placesItemsModels){
        placesResults = placesItemsModels;
    }

    public ArrayList<Results> getPlacesResults(){
        return placesResults;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Results placesModel = placesResults.get(position);

        holder.placename.setText(placesModel.getName());

        Glide.with(holder.icon)
                .load(placesModel.getIcon())
                .centerCrop()
                .placeholder(R.drawable.thumbsdown)
                .into(holder.icon);

        holder.rating.setText(String.valueOf("Rating "+placesModel.getRating()));

    }

    @Override
    public int getItemCount() {
        if (placesResults != null) {
            if (placesResults.size() > RECYCLER_ROW_LIMIT)
                return RECYCLER_ROW_LIMIT;
            else
                return placesResults.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.placename)
        TextView placename;

        @BindView(R.id.icon)
        ImageView icon;

        @BindView(R.id.rating)
        TextView rating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
