package com.google.places.network;

import com.google.places.model.PlacesResults;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesListService {


    @GET("maps/api/place/textsearch/json?")
    Observable<PlacesResults> getPlacesResults(@Query("query") String type, @Query("radius") String radius, @Query("key") String key);

}
