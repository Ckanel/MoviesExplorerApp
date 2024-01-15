package com.example.myapplication.Activities;



import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.content.SharedPreferences;
import com.example.myapplication.Models.Movies;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.OnBackPressedCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.API.Api;
import com.example.myapplication.Activities.API.MyApi;
import com.example.myapplication.Adapters.CastAdapter;
import com.example.myapplication.Models.MovieDeets;
import com.example.myapplication.Models.Movies;
import com.example.myapplication.Network.NetworkCheck;
import com.example.myapplication.R;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SecondActivity extends NetworkCheck {

    private RecyclerView recView3;
    private TextView title,prodComp,lang,runtime,overview,genre,rating,popularity,director,writer,producer,releaseDate;
    private ImageView poster;
    private ImageButton share;
    private ToggleButton favourite;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize the layout things, like textview for title,imageview for poster etc
        setContentView(R.layout.moviedetails);
        progressBar = findViewById(R.id.progressBar);
        recView3 = findViewById(R.id.recView3);
        title = findViewById(R.id.title);
        prodComp = findViewById(R.id.prodComp);
        lang = findViewById(R.id.lang);
        runtime = findViewById(R.id.runtime);
        overview = findViewById(R.id.overview);
        genre = findViewById(R.id.genre);
        rating = findViewById(R.id.rating);
        popularity = findViewById(R.id.popularity);
        director = findViewById(R.id.director);
        writer = findViewById(R.id.writer);
        producer = findViewById(R.id.producer);
        releaseDate = findViewById(R.id.releaseDate);
        poster = findViewById(R.id.poster);
        share = findViewById(R.id.share);
        favourite = findViewById(R.id.favbtn);
        //pass movie id to get the movie details
        int id = getIntent().getIntExtra("id", 0);

        getMovieDeets(id);

    }
    public void getMovieDeets(int id) {
        //call api to fetch movie details
        MyApi api = Api.getAPI();
        Call<MovieDeets> call = api.getMovieDeets(id);
        //make progress bar visible until response from api arrives
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<MovieDeets>() {
            @Override
            public void onResponse(Call<MovieDeets> call, Response<MovieDeets> response) {
                // hide the progressbar since we got a response
                progressBar.setVisibility(View.GONE);
                if (!response.isSuccessful()) {
                    Toast.makeText(SecondActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Code: " + response.code());
                } else {
                    // get api response
                    MovieDeets movieDeets = response.body();
                    //set movie title
                    title.setText(movieDeets.getTitle());
                    //set movie cast on the recycler
                    recView3.setLayoutManager(new LinearLayoutManager(SecondActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    recView3.setAdapter(new CastAdapter(SecondActivity.this, movieDeets.getCast()));
                    // set the movie details for the second activity such a poster production overview etc
                    MovieDeets.ProductionCompanies[] production_companies = movieDeets.getProduction_companies();
                    String prodCompString = "Production Companies: ";
                    for (int i = 0; i < production_companies.length; i++) {
                        prodCompString += production_companies[i].getName();
                        if (i != production_companies.length - 1) {
                            prodCompString += ", ";
                        }
                    }
                    prodComp.setText(prodCompString);
                    MovieDeets.SpokenLanguages[] spoken_languages = movieDeets.getSpoken_languages();
                    String langString = "Spoken Languages: ";
                    for (int i = 0; i < spoken_languages.length; i++) {
                        langString += spoken_languages[i].getName();
                        if (i != spoken_languages.length - 1) {
                            langString += ", ";
                        }
                    }
                    lang.setText(langString);
                    int runtimeInt = movieDeets.getRuntime();
                    int hours = runtimeInt / 60;
                    int minutes = runtimeInt % 60;
                    String runtimeString = hours + "h " + minutes + "m";
                    runtime.setText("Duration: " + runtimeString);
                    overview.setText(movieDeets.getOverview());
                    MovieDeets.Genres[] genres = movieDeets.getGenres();
                    String genreString = "Genres: ";
                    for (int i = 0; i < genres.length; i++) {
                        genreString += genres[i].getName();
                        if (i != genres.length - 1) {
                            genreString += ", ";
                        }
                    }
                    genre.setText(genreString);
                    double ratingDouble = movieDeets.getVote_average();
                    String ratingString = String.format("%.1f", ratingDouble);
                    rating.setText("Rating: " + ratingString + "/10");
                    String releaseDateString = movieDeets.getRelease_date();
                    releaseDate.setText("Release date: " + releaseDateString);
                    double popularityDouble = movieDeets.getPopularity();
                    String popularityString = String.format("%.1f", popularityDouble);
                    popularity.setText("Popularity: " + popularityString);
                    MovieDeets.Crew[] crew = movieDeets.getCrew();
                    String directorString = "Director: ";
                    String writerString = "Writer: ";
                    String producerString = "Producer:";
                    boolean isFirstDirector = true;
                    boolean isFirstWriter = true;
                    boolean isFirstProducer = true;

                    for (int i = 0; i < crew.length; i++) {
                        if (crew[i].getJob().equals("Director")) {
                            if (!isFirstDirector) {
                                directorString += ", ";
                            }
                            directorString += crew[i].getName();
                            isFirstDirector = false;
                        }

                        if (crew[i].getJob().equals("Writer")) {
                            if (!isFirstWriter) {
                                writerString += ", ";
                            }
                            writerString += crew[i].getName();
                            isFirstWriter = false;
                        }

                        if (crew[i].getJob().equals("Producer")) {
                            if (!isFirstProducer) {
                                producerString += ", ";
                            }
                            producerString += crew[i].getName();
                            isFirstProducer = false;
                        }
                    }
                    if (directorString.equals("Director: ")) directorString += "N/A";
                    if (writerString.equals("Writer: ")) writerString += "N/A";
                    if (producerString.equals("Producer: ")) producerString += "N/A";
                    director.setText(directorString);
                    writer.setText(writerString);
                    producer.setText(producerString);
                    Picasso.get()
                            .load(movieDeets.getPoster_path())
                            .into(poster);
                    // share button listener
                    share.setOnClickListener(v -> shareMovie(movieDeets));
                    // favourite button set up
                    //save on shared pref the id of the movie and the boolean value of the favourite button
                    SharedPreferences pref = getSharedPreferences("shared preferences", MODE_PRIVATE);
                    boolean isFavourite = pref.getBoolean("" + movieDeets.getId(), false);
                    //set the favourite button to the boolean value
                    // so that it will be checked if the movie is liked or not
                    favourite.setChecked(isFavourite);
                    //listen for any changes on the favourite button and connect
                    // the changes to the shared pref
                    favourite.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("" + movieDeets.getId(), isChecked);
                        editor.apply();
                    });


                }
            }

            @Override
            public void onFailure(Call<MovieDeets> call, Throwable t) {
                Toast.makeText(SecondActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Code: " + t.getMessage());
            }
            // create the share intent for the share button
            // add id of the movie at the end of the link
            public void shareMovie(MovieDeets movieDeets) {
                int id = movieDeets.getId();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Check this movie out");
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/" + id);
                startActivity(Intent.createChooser(intent, "Share"));
            }


        });


    }

}
