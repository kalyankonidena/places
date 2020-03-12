package com.google.places;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.places.R;
import com.google.places.ui.PlacesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.widget.EditText;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.action.ViewActions.click;
/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UrbanDictionaryIntrumentationTest {

    @Rule
    public ActivityTestRule<PlacesActivity> mActivityRule = new ActivityTestRule<>(
            PlacesActivity.class);


    @Test
    @SuppressWarnings("unchecked")
    public void testAppCompatSearchViewFromActionBar() throws InterruptedException {

        EditText editText = (EditText) mActivityRule.getActivity().findViewById(R.id.search_src_text) ;
        onView(withId(R.id.mi_search))
                .perform(click());

        onView(withId(R.id.search_src_text))
                .perform(typeText("Hello World"));

        onView(withId(R.id.search_src_text))
                .perform(click());

        BaseInputConnection inputConnection = new BaseInputConnection(editText, true);
        inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
        Thread.sleep(3000);

    }
}
