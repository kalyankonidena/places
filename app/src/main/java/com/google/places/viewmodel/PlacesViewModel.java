package com.google.places.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.places.model.PlacesResults;
import com.google.places.repository.PlacesRepository;

public class PlacesViewModel extends ViewModel {

    private PlacesRepository placesRepository;

    public void init(){

        placesRepository = PlacesRepository.getRepositoryInstance();
        placesRepository.init();
    }

    public LiveData<PlacesResults> getPlacesResults(String searchTerm, String radius){
        return placesRepository.getPlacesList(searchTerm,radius);
    }


}
