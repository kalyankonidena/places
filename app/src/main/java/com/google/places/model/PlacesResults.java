package com.google.places.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PlacesResults implements Parcelable {

     private String[] html_attributions;
     private String next_page_token;
     private ArrayList<Results> results;


    public ArrayList<Results> getResults() {
        return results;
    }

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public String getNext_page_token() {
        return next_page_token;

    }

    protected PlacesResults(Parcel in) {
        html_attributions = (String[])in.readArray(PlacesResults.class.getClassLoader());
        next_page_token = in.readString();
        results = (ArrayList<Results>)in.readArrayList(PlacesResults.class.getClassLoader());

    }

    public static final Creator<PlacesResults> CREATOR = new Creator<PlacesResults>() {
        @Override
        public PlacesResults createFromParcel(Parcel in) {
            return new PlacesResults(in);
        }

        @Override
        public PlacesResults[] newArray(int size) {
            return new PlacesResults[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(html_attributions);
        parcel.writeString(next_page_token);
        parcel.writeList(results);
    }
}
