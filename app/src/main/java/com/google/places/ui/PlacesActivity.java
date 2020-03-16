package com.google.places.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.places.R;
import com.google.places.ui.fragments.PlacesListFragment;

import butterknife.ButterKnife;

public class PlacesActivity extends AppCompatActivity {

    private static final String BACK_STACK_ROOT_TAG = "PlacesActivity";
    ActionBar ab;
    PlacesListFragment placesListFragment;
    private String TEN_MILE_RADIUS = "16093.9";
    private String TWENTY_MILE_RADIUS = "32186.9";

    private String search_limit = TEN_MILE_RADIUS;
    private String searchTerm;
    public SearchView searchView;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_places_list);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
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
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle termBundle = new Bundle();
        termBundle.putString("term",term);
        termBundle.putString("radius",radius);

        placesListFragment = PlacesListFragment.newInstance();
        placesListFragment.setArguments(termBundle);
        ft.replace(R.id.listcontainer, placesListFragment);
        ft.commit();
    }

    public void showPlacesFragmentWithNoSearch(){
        FragmentManager fm = getFragmentManager();
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
        MenuItem twentymile_radius = menu.findItem(R.id.twenty_miles);
        twentymile_radius.setCheckable(true);

        ten_mile_radius.setOnMenuItemClickListener(item -> {
            ten_mile_radius.setChecked(true);
            twentymile_radius.setChecked(false);
            prefs.edit().putInt("radius",10);

            if(searchTerm != null)
                showPlacesFragment(searchTerm, TEN_MILE_RADIUS);
            return false;
        });

        twentymile_radius.setOnMenuItemClickListener(item -> {
            ten_mile_radius.setChecked(false);
            twentymile_radius.setChecked(true);
            if(searchTerm!=null)
                prefs.edit().putInt("radius",20);

            showPlacesFragment(searchTerm, TWENTY_MILE_RADIUS);
            return false;
        });

        prefs.edit().commit();
         searchView = (SearchView) search_item.getActionView();
        if (searchView != null) {
            searchView.setFocusable(false);
            searchView.setQueryHint(prefs.getString("searchTerm",null));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String term) {
                    searchTerm = term;
                    ab.setDisplayShowTitleEnabled(false);
                    boolean checkValue = ten_mile_radius.isChecked();
                    boolean checkValue_20 = twentymile_radius.isChecked();
                    if(checkValue){
                        search_limit = TEN_MILE_RADIUS;
                    }
                    else if(checkValue_20) {
                        search_limit = TWENTY_MILE_RADIUS;
                    }

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
