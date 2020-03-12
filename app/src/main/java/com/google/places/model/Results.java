package com.google.places.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Results implements Parcelable {

    private Geometry geometry;
    private String icon;
    private String name;
    private String rating;

    protected Results(Parcel in) {
        icon = in.readString();
        name = in.readString();
        rating = in.readString();
        geometry = (Geometry)in.readValue(Results.class.getClassLoader());
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public Geometry getGeometry() {
        return geometry;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(geometry);
        parcel.writeString(icon);
        parcel.writeString(name);
        parcel.writeString(rating);
    }
}
