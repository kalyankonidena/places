package com.google.places;

import android.support.test.filters.LargeTest;
import android.view.View;
import android.widget.Toast;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.google.places.adapter.PlacesDataAdapter;
import com.google.places.model.PlacesResults;
import com.google.places.network.PlacesListService;
import com.google.places.ui.PlacesActivity;
import com.google.places.utils.PlacesUIProgressDialog;
import com.google.places.utils.PlacesUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class PlacesApiTest  {
   PlacesListService placesListService;

   CompositeDisposable mCompositeDisposable;
   @Test
    public void tesApi(){
       mCompositeDisposable = new CompositeDisposable();

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

       mCompositeDisposable.add(requestInterface.getPlacesResults("restaurants","1500",PlacesUtils.GOOGLE_MAPS_API_KEY)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
               .subscribe(this::handleResponse, this::handleError));

       try {
           Thread.sleep(2000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }

   }

    private void handleResponse(PlacesResults placesResults) {

        Assert.assertNotNull(placesResults);
       // Assert.assertEquals(PlacesResults.class,placesResults);

    }

    private void handleError(Throwable error) {
      Assert.assertNotNull(error);
    }


}
