package com.example.myapplication.Activities.API;

import com.example.myapplication.Models.Movies;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static InterfaceAPI API ;
    private ArrayList<Movies> results;
    public static InterfaceAPI getAPI() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ODIzMzFlMDUyNDJjMzJkMGE5NzZkMGM4ZjczODAxMCIsInN1YiI6IjY1NjYxOTYwM2Q3NDU0MDBlYTI2ZGIxNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Fih1Lp7jCklUM2xlYdsE5fgZag2pvher72FoDkyTN0k")
                .build();

        Response response;

        {
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return API;
    }

    public ArrayList<Movies> getResults() {
        return results;
    }




}
