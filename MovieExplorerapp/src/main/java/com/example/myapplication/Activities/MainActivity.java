package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.example.myapplication.Activities.API.API;
import com.example.myapplication.Activities.API.InterfaceAPI;
import com.example.myapplication.Adapters.MovieAdapter;
import com.example.myapplication.Models.Movies;
import com.example.myapplication.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recView1;
    private RecyclerView recView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recView1 = findViewById(R.id.recView1);
        recView2 = findViewById(R.id.recView2);
        recView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        InterfaceAPI api = API.getAPI();
        Call<API> getMovies = api.getMovies();
        getMovies.enqueue(new Callback<API>() {
            @Override
            public void onResponse(Call<API> call, Response<API> response) {

                ArrayList<Movies> movies = new ArrayList<Movies>();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        API api = response.body();
                        movies = api.getResults();
                        MovieAdapter adapter = new MovieAdapter(movies);
                        recView1.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No movies found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<API> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
