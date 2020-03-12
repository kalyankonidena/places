package com.google.places;

import com.google.places.adapter.PlacesDataAdapter;
import com.google.places.model.PlacesResults;
import com.google.places.ui.fragments.PlacesListFragment;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        ArrayList<PlacesResults> placesResults = new ArrayList<PlacesResults>();
        PlacesDataAdapter urbanDictionaryDataAdapter = new PlacesDataAdapter();
        PlacesListFragment urbanDictionaryListFragment = new PlacesListFragment();
    //    urbanDictionaryDataAdapter.setPlacesResults(placesResults);
       // assertEquals(   urbanDictionaryDataAdapter.getUrbanDictonaryModel(),     urbanDictionaryListFragment.sortByThumbsCount(true));

    }
}