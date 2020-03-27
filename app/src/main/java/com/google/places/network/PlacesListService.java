package com.google.places.network;

import com.google.places.model.PlacesResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesListService {


    @GET("maps/api/place/textsearch/json?")
    Call<PlacesResults> getPlacesResults(@Query("query") String type, @Query("radius") String radius, @Query("key") String key);

}
