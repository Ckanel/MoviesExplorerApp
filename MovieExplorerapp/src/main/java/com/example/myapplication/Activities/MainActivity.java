package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.myapplication.Activities.API.Api;
import com.example.myapplication.Activities.API.MyApi;
import com.example.myapplication.Adapters.MovieAdapter;
import com.example.myapplication.Models.ApiResponse;
import com.example.myapplication.Models.Movies;
import com.example.myapplication.Network.NetworkCheck;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends NetworkCheck {

    private RecyclerView recView1;
    private ImageButton favourite;
    private RecyclerView recView2;
    private Spinner spinner;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set night mode for the whole app, i tried to make the toolbar black for normal white bg, i didnt like it as much
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //init the rec viewers and the spinner
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        recView1 = findViewById(R.id.recView1);
        recView2 = findViewById(R.id.recView2);
        recView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        favourite = findViewById(R.id.tvFavourite);
        // in case a movie is pressed go to the second activity
        favourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
        });
        getMovies();

    }


    private List<Movies> getMovies() {
        //create list of movies
        List<Movies> movies = new ArrayList<>();
        //call api to fetch movies
        MyApi api = Api.getAPI();
        Call<ApiResponse> call = api.getMovies();
        // show progress bar until we get api response
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call,@NonNull Response<ApiResponse> response) {
                // hide progress bar
                progressBar.setVisibility(View.GONE);
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Code: " + response.code());
                    return;
                }
                // populate movies list with response and set the adapter
                assert response.body() != null;
                List<Movies> movies = response.body().getResults();
                recView1.setAdapter(new MovieAdapter((ArrayList<Movies>) movies, MainActivity.this));

                // create second list for the spinner and init as sort by rating since it was the first choice
                ArrayList<Movies> moviesCompare = new ArrayList<>(movies);
                moviesCompare.sort(new Comparator<Movies>() {
                    @Override
                    public int compare(Movies o1, Movies o2) {
                        return Double.compare(o2.getVote_average(), o1.getVote_average());
                    }
                });
                // init the adapter for the second rec view and set it to the sorted list by rating
                recView2.setAdapter(new MovieAdapter(moviesCompare, MainActivity.this));
                // use a listener to see what the user wants to sort by
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    //based on the choice of the user sort the list accordingly
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        String selectedSortOption = parent.getItemAtPosition(pos).toString();

                        if (selectedSortOption.equals("Top Rated")) {
                            moviesCompare.sort(new Comparator<Movies>() {
                                @Override
                                public int compare(Movies o1, Movies o2) {
                                    return Double.compare(o2.getVote_average(), o1.getVote_average());
                                }
                            });

                        } else if (selectedSortOption.equals("Most Recent")) {
                            moviesCompare.sort(new Comparator<Movies>() {
                                @Override
                                public int compare(Movies o1, Movies o2) {
                                    return o2.getRelease_date().compareTo(o1.getRelease_date());
                                }
                            });
                        } else if (selectedSortOption.equals("Most Popular")) {

                            moviesCompare.sort(new Comparator<Movies>() {
                                @Override
                                public int compare(Movies o1, Movies o2) {

                                    return Double.compare(o2.getPopularity(), o1.getPopularity());

                                }
                            });
                        }
                        //finally sort the adapter based on the choice given by the user
                        recView2.setAdapter(new MovieAdapter(moviesCompare, MainActivity.this));
                        // the Objects.requireNonNull() is an ide suggestion that i don't think does anything since it cant be null
                        // but i didn't want to see it yellow, maybe im wrong as well and i could get a null pointer
                        //exception
                        Objects.requireNonNull(recView2.getAdapter()).notifyItemChanged(pos);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


                }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return movies;
            }



}
