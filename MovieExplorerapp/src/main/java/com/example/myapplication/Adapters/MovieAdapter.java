package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.FavouritesActivity;
import com.example.myapplication.Activities.SecondActivity;
import com.example.myapplication.Models.Movies;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    // init arraylist
    ArrayList<Movies> moviesArraylist = new ArrayList<Movies>();
    //init context
    private Context context;
    // init adapter
    public MovieAdapter(ArrayList<Movies> moviesArraylist, Context context) {
        this.context = context;
        this.moviesArraylist = moviesArraylist;
    }


    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie, parent, false);
        return new MovieViewHolder(view);
    }
    //init bindviewholder and int the movie title, year and poster
    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movies movie = moviesArraylist.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieYear.setText(movie.getRelease_date());
        Picasso.get()
                .load( movie.getPoster_path())
                .into(holder.posterImage);
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            //start the second activity in case of movie being pressed and send the id of the movie
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                intent.putExtra("id", movie.getId());
                context.startActivity(intent);

    }
        });
    }
    //get the size of the arraylist
    @Override
    public int getItemCount() {
        return moviesArraylist.size();
    }

    // init the viewholder and the textviews and imageview
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView movieYear;
        ImageView posterImage;
        public MovieViewHolder(@NonNull View parent) {
            super(parent);
            movieTitle = parent.findViewById(R.id.movieTitle);
            movieYear = parent.findViewById(R.id.movieYear);
            posterImage = parent.findViewById(R.id.posterImage);

        }

    }
}
