package com.google.places.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.places.network.PlacesListService;
import com.google.places.model.PlacesResults;
import com.google.places.utils.PlacesUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesRepository {

    private PlacesListService placesListService;
    private static PlacesRepository placesRepository;

    private PlacesRepository() {

    }

    synchronized public static PlacesRepository getRepositoryInstance() {
        if (placesRepository == null)
            placesRepository = new PlacesRepository();

        return placesRepository;
    }

    public void init() {
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        placesListService = new Retrofit.Builder()
                .baseUrl(PlacesUtils.MAPS_API_HOST)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PlacesListService.class);
    }

    public LiveData<PlacesResults> getPlacesList(String searchTerm, String radius) {
        final MutableLiveData<PlacesResults> data = new MutableLiveData<>();

        placesListService.getPlacesResults(searchTerm, radius,PlacesUtils.GOOGLE_MAPS_API_KEY).enqueue(new Callback<PlacesResults>() {
            @Override
            public void onResponse(Call call, Response response) {
                data.setValue((PlacesResults) response.body());
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
        return data;
    }

}
