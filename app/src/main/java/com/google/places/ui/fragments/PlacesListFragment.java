package com.google.places.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.places.R;
import com.google.places.adapter.PlacesDataAdapter;
import com.google.places.model.Results;
import com.google.places.ui.PlacesActivity;
import com.google.places.utils.PlacesUIProgressDialog;
import com.google.places.utils.PreferenceUtils;
import com.google.places.viewmodel.PlacesViewModel;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesListFragment extends Fragment {


    @BindView(R.id.places_list_recycler_view)
    RecyclerView placesListRecyclerView;

    @BindView((R.id.empty_view))
    TextView emptyView;

    private PlacesDataAdapter mAdapter;
    private Gson gson;

    private String searchTerm;
    private String searchRadius;

    private PlacesViewModel placesViewModel;


    public static PlacesListFragment newInstance() {
        return new PlacesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_places_list, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
        placesViewModel.init();
        Bundle arguments = getArguments();
        if (arguments != null) {
            searchTerm = arguments.getString("term");
            searchRadius = arguments.getString("radius");
            loadPlacesList();
        }
        return view;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        placesListRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PlacesDataAdapter(new ArrayList<>());
        placesListRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        gson = new Gson();
        restoreRecyclerViewStoreResults();
    }

    private void restoreRecyclerViewStoreResults() {
        String json = PreferenceUtils.getStringPreference(getActivity(), "searchResults");
        Type type = new TypeToken<List<Results>>() {
        }.getType();
        ArrayList<Results> placesResults = gson.fromJson(json, type);
        mAdapter = new PlacesDataAdapter(placesResults);
        placesListRecyclerView.setAdapter(mAdapter);
    }

    /**
     * This method will make the api call for urban dictionary service.
     */
    private void loadPlacesList() {

        PlacesUIProgressDialog.showProgressDialog(getActivity(), "Loading Places...");
        placesViewModel.getPlacesResults(searchTerm, searchTerm).observe(this, placesResults -> {
            if (placesResults != null && placesResults.getResults() != null) {
                if (placesResults.getResults().size() > 0) {
                    mAdapter = new PlacesDataAdapter(placesResults.getResults());
                    mAdapter.notifyDataSetChanged();
                    placesListRecyclerView.setAdapter(mAdapter);
                    PlacesUIProgressDialog.dismissDialog();
                    emptyView.setVisibility(View.GONE);
                    placesListRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    PlacesUIProgressDialog.dismissDialog();
                    placesListRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStop() {
        super.onStop();
        saveSearchResults();
    }

    /*
      This method will  save the recyclerView Search Results in to Preferences.
     */
    private void saveSearchResults() {
        String json = gson.toJson(mAdapter.getPlacesResults());
        PlacesActivity placesActivity = (PlacesActivity) getActivity();
        PreferenceUtils.setStringPreference(placesActivity, "searchResults", json);
        PreferenceUtils.setStringPreference(placesActivity, "searchTerm", searchTerm);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
