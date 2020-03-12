package com.google.places.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
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
    private Long search_limit;
    private String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urban_dictionary_list);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ButterKnife.bind(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void showDictionaryFragment(String term, Long radius) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle termBundle = new Bundle();
        termBundle.putString("term",term);
        termBundle.putLong("radius",radius);

        placesListFragment = PlacesListFragment.newInstance();
        placesListFragment.setArguments(termBundle);
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
        ten_mile_radius.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ten_mile_radius.setChecked(true);
                twentymile_radius.setChecked(false);
                search_limit = Long.valueOf(10 * 1609);
                if(searchTerm != null)
                    showDictionaryFragment(searchTerm, search_limit);
                return false;
            }
        });

        twentymile_radius.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ten_mile_radius.setChecked(false);
                twentymile_radius.setChecked(true);
                search_limit = Long.valueOf(20* 1609);
                if(searchTerm!=null)
                showDictionaryFragment(searchTerm, search_limit);
                return false;
            }
        });

        SearchView searchView = (SearchView) search_item.getActionView();
        if (searchView != null) {
            searchView.setFocusable(false);
            searchView.setQueryHint("Search Places");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String term) {

                    searchTerm = term;
                    ab.setDisplayShowTitleEnabled(false);
                    showDictionaryFragment(term, Long.valueOf(10 * 1609));  
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
