package com.poepoemyintswe.weeklyenglish.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import com.poepoemyintswe.weeklyenglish.BuildConfig;
import com.poepoemyintswe.weeklyenglish.Config;
import com.squareup.okhttp.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static com.poepoemyintswe.weeklyenglish.Config.PASSWORD;
import static com.poepoemyintswe.weeklyenglish.Config.USER_NAME;

/**
 * Created by poepoe on 12/12/14.
 */
public class CustomRestAdapter {
  private static CustomRestAdapter customRestAdapter;

  private Context mContext;
  private OkHttpClient okHttpClient = new OkHttpClient();

  public CustomRestAdapter(Context mContext) {
    this.mContext = mContext;
  }

  public static synchronized CustomRestAdapter getInstance(Context mContext) {
    if (customRestAdapter == null) {
      customRestAdapter = new CustomRestAdapter(mContext);
    }
    return customRestAdapter;
  }

  public RestAdapter normalRestAdapter() {
    final String credentials = USER_NAME + ":" + PASSWORD;
    RequestInterceptor requestInterceptor = new RequestInterceptor() {
      @Override public void intercept(RequestFacade request) {
        String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        request.addHeader("Accept", "application/json");
        request.addHeader("Authorization", string);
      }
    };

    RestAdapter restAdapter;
    if (BuildConfig.DEBUG)

    {
      restAdapter = new RestAdapter.Builder().setEndpoint(Config.BASE_URL)
          .setLogLevel(RestAdapter.LogLevel.BASIC)
          .setClient(new OkClient(okHttpClient))
          .setErrorHandler(new RetrofitErrorHandler((Activity) mContext))
          .setRequestInterceptor(requestInterceptor)
          .build();
    } else

    {
      restAdapter = new RestAdapter.Builder().setEndpoint(Config.BASE_URL)
          .setClient(new OkClient(okHttpClient))
          .setErrorHandler(new RetrofitErrorHandler((Activity) mContext))
          .setRequestInterceptor(requestInterceptor)
          .build();
    }

    return restAdapter;
  }
}