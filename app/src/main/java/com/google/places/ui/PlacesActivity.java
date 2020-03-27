package com.google.places.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.places.R;
import com.google.places.ui.fragments.PlacesListFragment;
import com.google.places.utils.PreferenceUtils;

import butterknife.ButterKnife;

public class PlacesActivity extends AppCompatActivity {

    ActionBar ab;
    PlacesListFragment placesListFragment;
    private static final String TEN_MILE_RADIUS = "16093.9";
    private static final String TWENTY_MILE_RADIUS = "32186.9";
    private static final String RADIUS = "radius";
    private String search_limit = TEN_MILE_RADIUS;
    private String searchTerm;
    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_places_list);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPlacesFragmentWithNoSearch();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void showPlacesFragment(String term, String radius) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle termBundle = new Bundle();
        termBundle.putString("term",term);
        termBundle.putString(RADIUS,radius);

        placesListFragment = PlacesListFragment.newInstance();
        placesListFragment.setArguments(termBundle);
        ft.replace(R.id.listcontainer, placesListFragment);
        ft.commit();
    }

    public void showPlacesFragmentWithNoSearch(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        placesListFragment = PlacesListFragment.newInstance();
        ft.replace(R.id.listcontainer, placesListFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search_item = menu.findItem(R.id.mi_search);
        MenuItem ten_mile_radius = menu.findItem(R.id.ten_miles);
        ten_mile_radius.setCheckable(true);
        MenuItem twenty_mile_radius = menu.findItem(R.id.twenty_miles);
        twenty_mile_radius.setCheckable(true);
        String radius = PreferenceUtils.getStringPreference(getApplicationContext(),RADIUS);

        // set the saved preference value for radius
        if(radius!=null){
            if (radius.equals("10")) {
                ten_mile_radius.setChecked(true);
                twenty_mile_radius.setChecked(false);

            } else {
                twenty_mile_radius.setChecked(true);
                ten_mile_radius.setChecked(false);

            }
        }
        ten_mile_radius.setOnMenuItemClickListener(item -> {
            ten_mile_radius.setChecked(true);
            twenty_mile_radius.setChecked(false);
            PreferenceUtils.setStringPreference(getApplicationContext(),RADIUS,"10");

            if(searchTerm != null)
                showPlacesFragment(searchTerm, TEN_MILE_RADIUS);
            return false;
        });

        twenty_mile_radius.setOnMenuItemClickListener(item -> {
            ten_mile_radius.setChecked(false);
            twenty_mile_radius.setChecked(true);
            if(searchTerm!=null)
                PreferenceUtils.setStringPreference(getApplicationContext(),RADIUS,"20");

            showPlacesFragment(searchTerm, TWENTY_MILE_RADIUS);
            return false;
        });

         searchView = (SearchView) search_item.getActionView();
        if (searchView != null) {
            searchView.setFocusable(false);
            searchView.setQueryHint(PreferenceUtils.getStringPreference(getApplicationContext(),"searchTerm"));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String term) {
                    searchTerm = term;
                    ab.setDisplayShowTitleEnabled(false);
                    showPlacesFragment(term, search_limit);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
