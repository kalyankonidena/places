package com.google.places.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.places.R;
import com.google.places.adapter.PlacesDataAdapter;
import com.google.places.model.PlacesResults;
import com.google.places.model.Results;
import com.google.places.network.PlacesListService;
import com.google.places.utils.PlacesUtils;
import com.google.places.utils.PlacesUIProgressDialog;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    private SharedPreferences prefs = null;
    private SharedPreferences.Editor editor = null;
    @BindView(R.id.places_list_recycler_view)
    RecyclerView placesListRecyclerView;

    @BindView((R.id.empty_view))
    TextView emptyView;

    private RecyclerView.LayoutManager layoutManager;
    private PlacesDataAdapter mAdapter;
    private Gson gson;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_places_list, container, false);
        ButterKnife.bind(this, view);
        placesListRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        placesListRecyclerView.setLayoutManager(layoutManager);
         Bundle arguments = getArguments();
        if(arguments!=null) {
            String term = arguments.getString("term");
            Long radius = arguments.getLong("radius");
            searchTerm = term != null ? term : "";
            searchRadius = radius != null ? radius : 0L;
            loadPlacesList();
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        gson = new Gson();
       restoreRecyclerViewStoreResults();
    }

    private void restoreRecyclerViewStoreResults(){
        String json = prefs.getString("placesJson", "");
        Type type = new TypeToken<List<Results>>() {}.getType();
        ArrayList<Results> placesResults = gson.fromJson(json, type);
        mAdapter = new PlacesDataAdapter(placesResults);
        placesListRecyclerView.setAdapter(mAdapter);
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
        PlacesUIProgressDialog.showProgressDialog(getActivity(),"Loading Places...");
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

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * This method will will handle the response by loading the urban dictionary list
     * in to the recycler view.
     * @param
     */
    private void handleResponse(PlacesResults placesResults) {

        if(placesResults!=null && placesResults.getResults()!=null) {
            if (placesResults.getResults().size() > 0) {
                mAdapter = new PlacesDataAdapter(placesResults.getResults());
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
        saveSearchResults();
        mCompositeDisposable.clear();
    }

    /*
      This method will  save the recyclerView Search Results in to Preferences.
     */
    private void saveSearchResults(){
        String json = gson.toJson(mAdapter.getPlacesResults());
        editor.putString("searchTerm",searchTerm);
        editor.putString("placesJson", json);
        editor.commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
