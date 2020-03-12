package com.google.places.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.places.R;
import com.google.places.adapter.PlacesDataAdapter;
import com.google.places.model.PlacesResults;
import com.google.places.network.PlacesListService;
import com.google.places.utils.PlacesUtils;
import com.google.places.utils.PlacesUIProgressDialog;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesListFragment extends Fragment {


    private CompositeDisposable mCompositeDisposable;

    @BindView(R.id.places_list_recycler_view)
    RecyclerView placesListRecyclerView;

    private PlacesDataAdapter mAdapter;

    private String searchTerm;
    private Long searchRadius;


    public static PlacesListFragment newInstance() {
        return new PlacesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_urban_dictionary_list, container, false);
        ButterKnife.bind(this, view);
        placesListRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        placesListRecyclerView.setLayoutManager(layoutManager);
        searchTerm = getArguments().getString("term");
        searchRadius = getArguments().getLong("radius");

        loadPlacesList();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * This method will make the api call for urban dictionary service.
     */
    private void loadPlacesList() {


        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();


        PlacesListService requestInterface = new Retrofit.Builder()
                .baseUrl(PlacesUtils.URBAN_DICTONARY_HOST)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PlacesListService.class);

        mCompositeDisposable.add(requestInterface.getPlacesResults(searchTerm,String.valueOf(searchRadius), PlacesUtils.GOOGLE_MAPS_API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));

    }


    /**
     * This method will will handle the response by loading the urban dictionary list
     * in to the recycler view.
     * @param
     */
    private void handleResponse(PlacesResults placesResults) {

        mAdapter = new PlacesDataAdapter(placesResults.getResults());
        placesListRecyclerView.setAdapter(mAdapter);
        PlacesUIProgressDialog.dismissDialog();
    }

    private void handleError(Throwable error) {
        Toast.makeText(getActivity(), "Error " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeDisposable.clear();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
