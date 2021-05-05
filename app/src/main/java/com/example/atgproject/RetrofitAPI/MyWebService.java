package com.example.atgproject.RetrofitAPI;

import com.example.atgproject.ModelClass.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public interface MyWebService {

    String BASE_URL = "https://api.flickr.com/services/rest/";

    // instance variables
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("?method=flickr.photos.getRecent&per_page=30&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<JSONObject> getFlickrData();
}
