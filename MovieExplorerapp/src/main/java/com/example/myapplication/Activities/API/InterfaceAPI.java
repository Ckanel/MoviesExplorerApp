package com.example.myapplication.Activities.API;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface InterfaceAPI {

    @Headers("Content-Type: application/json")

    @GET("movie/popular?language=en-US&page=1")
    Call<API> getMovies();


}
