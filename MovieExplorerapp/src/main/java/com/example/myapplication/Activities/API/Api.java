package com.example.myapplication.Activities.API;

import com.example.myapplication.Activities.API.MyApi;
import com.example.myapplication.Models.Movies;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//int the api for the calls
public class Api {
    private static final String BASE_URL = "https://app-vpigadas.herokuapp.com/";
    private static MyApi API;
        // use retrofit to make the calls guide found here https://www.vogella.com/tutorials/Retrofit/article.html
    public static MyApi getAPI() {
        if (API == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            API = retrofit.create(MyApi.class);
        }

        return API;
    }


}

