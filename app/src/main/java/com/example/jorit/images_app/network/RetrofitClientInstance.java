package com.example.jorit.images_app.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    //https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=a1f79d9845c842d2151c1a14a3f5fb01&tags=horse&format=json&nojsoncallback=1
    private static final String BASE_URL = "https://api.flickr.com/services/rest/?";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
