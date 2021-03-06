package com.plego.wagerocity.android.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by haris on 17/03/15.
 */
public class RestClient {

//    private static final String BASE_URL = "http://plego.info/wagerocity_api_v1/api_v1";
    private static final String BASE_URL = "http://api.wagerocity.com";
    private ServiceModel apiService;

    public RestClient()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(okHttpClient))
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(ServiceModel.class);
    }

    public ServiceModel getApiService()
    {
        return apiService;
    }
}