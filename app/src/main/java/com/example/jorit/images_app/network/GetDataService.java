package com.example.jorit.images_app.network;

import com.example.jorit.images_app.domain.Flickr.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

//// https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=a1f79d9845c842d2151c1a14a3f5fb01&tags=horse&format=json&nojsoncallback=1
    @GET("./")
    Call<BaseResponse> getFlickrImages(@Query("method") String method, @Query("api_key") String apiKey, @Query("tags") String tags, @Query("format") String format, @Query("nojsoncallback") String jsonCallback);

}
