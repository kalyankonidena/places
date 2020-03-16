package com.google.places;


import com.google.places.model.PlacesResults;
import com.google.places.network.PlacesListService;

import com.google.places.utils.PlacesUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import androidx.test.platform.app.InstrumentationRegistry;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



public class ExampleUnitTest {

    public void testSomething() throws Throwable {
        Assert.assertTrue(1 + 1 == 2);
    }

    public void testSomethingElse() throws Throwable {
        Assert.assertTrue(1 + 1 == 3);
    }

}