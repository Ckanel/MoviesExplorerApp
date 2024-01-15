package com.example.myapplication.Activities;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Activities.API.Api;
import com.example.myapplication.Activities.API.MyApi;
import com.example.myapplication.Adapters.MovieAdapter;
import com.example.myapplication.Models.MovieDeets;
import com.example.myapplication.Models.Movies;
import com.example.myapplication.R;
import com.example.myapplication.Network.NetworkCheck;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesActivity extends NetworkCheck {
    private RecyclerView rvFavourites;
    private ImageButton favourite;
    private ArrayList<Movies> movies = new ArrayList<>();

    private ImageButton home;
    private MovieAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize the layout things, like the toolbar and rec viewer
        setContentView(R.layout.favourites);
        rvFavourites = findViewById(R.id.rvFavourites);
        rvFavourites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        home = findViewById(R.id.tvHome);
        adapter = new MovieAdapter(movies, FavouritesActivity.this);
        rvFavourites.setAdapter(adapter);
        // in case home button is pressed destroy the activity
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
            getFav();

    }

    public void getFav()
    {
        // get the id of the movie from shared pref and check if they are liked or not based on the boolean given before on the value
        SharedPreferences pref = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Map<String, ?> entries = pref.getAll();
        for (Map.Entry<String, ?> entry : entries.entrySet()) {
            //check boolean value and check if its true after making a string
            if (entry.getValue().toString().equals("true")) {
                String movieID = entry.getKey();
                //call api
                MyApi api = Api.getAPI();
                Call<MovieDeets> call = api.getMovieDeets(Integer.parseInt(movieID));
                call.enqueue(new Callback<MovieDeets>() {


                    @Override
                    public void onResponse(@NonNull Call<MovieDeets> call, @NonNull Response<MovieDeets> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(FavouritesActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                            Log.e("API Error", "Code: " + response.code());
                        }else
                        {
                            // show the fav movies to the rec view
                            MovieDeets movie = response.body();
                            assert movie != null;
                            Movies favMovie = new Movies( movie.getId(),movie.getPopularity(),movie.getPoster_path(), movie.getRelease_date(),movie.getTitle(),movie.getVote_average(), movie.getVote_count());
                            movies.add(favMovie);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDeets> call, Throwable t) {
                        Toast.makeText(FavouritesActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("API Error", "Code: " + t.getMessage());
                    }
                });
            }
            //without this it wont update the recycler view upon removing the last favouite movie from the list
            // i dont really know why, but i tried everything i could think of and just threw a bandaid fix
            if (movies.isEmpty()) {
                adapter.notifyDataSetChanged();
            }
        }
    }
    // on restart to clear and rebuild the list in case someone removed something from favourites when checking the details
    @Override
    public void onRestart() {
        super.onRestart();
        movies.clear();
        getFav();
    }


}
