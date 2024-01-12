package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Movies;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    ArrayList<Movies> moviesArraylist = new ArrayList<Movies>(); // ArrayList of Movies

    public MovieAdapter(ArrayList<Movies> moviesArraylist) {
        this.moviesArraylist = moviesArraylist;
    }


    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movies movie = moviesArraylist.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieYear.setText(movie.getReleaseDate());
        Picasso.get().load(movie.getPosterPath()).into(holder.posterImage);

    }

    @Override
    public int getItemCount() {
        return moviesArraylist.size();
    }

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
