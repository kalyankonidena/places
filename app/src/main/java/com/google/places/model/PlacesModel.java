package com.google.places.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PlacesModel implements Parcelable{


    protected PlacesModel(Parcel in) {
        candidates = in.createTypedArrayList(PlacesResults.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(candidates);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlacesModel> CREATOR = new Creator<PlacesModel>() {
        @Override
        public PlacesModel createFromParcel(Parcel in) {
            return new PlacesModel(in);
        }

        @Override
        public PlacesModel[] newArray(int size) {
            return new PlacesModel[size];
        }
    };

    public ArrayList<PlacesResults> getItems() {
        return candidates;
    }

    private ArrayList<PlacesResults> candidates;




}
