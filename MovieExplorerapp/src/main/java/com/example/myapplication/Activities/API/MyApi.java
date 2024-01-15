package com.example.myapplication.Activities.API;
import com.example.myapplication.Models.ApiResponse;
import com.example.myapplication.Models.MovieDeets;
import com.example.myapplication.Models.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface MyApi {

    @Headers("Content-Type: application/json")
    @GET("api/movies/")
    Call<ApiResponse> getMovies();

    @Headers("Content-Type: application/json")
    @GET("api/movies/{id}")
    Call<MovieDeets> getMovieDeets(@Path("id") int id);



}
