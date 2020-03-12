package com.google.places.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.places.R;
import com.google.places.model.Results;

public class PlacesDataAdapter extends RecyclerView.Adapter<PlacesDataAdapter.ViewHolder> {

    private Results[] placesResults;


    public PlacesDataAdapter(Results[] placesItemsModels) {
        placesResults = placesItemsModels;

    }

    public PlacesDataAdapter() {

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.urban_dictionary_recycler_row, parent, false);
        return new ViewHolder(view);
    }


    public void setPlacesResults(Results[] placesItemsModels){
        placesResults = placesItemsModels;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Results gitRepositoryItemsModel = placesResults[position];


        holder.defnition.setText(gitRepositoryItemsModel.getName());

        Glide.with(holder.thumbsUp)  //2
                .load(gitRepositoryItemsModel.getIcon()) //3
                .centerCrop() //4
                .placeholder(R.drawable.thumbsdown) //5
                .into(holder.thumbsUp);

        holder.rating.setText(String.valueOf(gitRepositoryItemsModel.getRating()));

    }

    @Override
    public int getItemCount() {
        if(placesResults.length>10)
       return 10;
       else
        return placesResults.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView defnition;
        private ImageView thumbsUp;
        private TextView rating;

        public ViewHolder(View view) {
            super(view);

            defnition = (TextView) view.findViewById(R.id.defnition);
            thumbsUp = (ImageView) view.findViewById(R.id.icon);
            rating = (TextView) view.findViewById(R.id.rating);

        }

    }

}
