package com.example.myapplication.Models;

public class Movies {
    // initialize the movie details based on how the json is formatted
    private int id;
    private double popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private double vote_average;

    private int vote_count;

    public Movies(int id, double popularity, String poster_path, String release_date, String title, double voteAverage, int voteCount) {
        this.id = id;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
        this.vote_average = voteAverage;
        this.vote_count = voteCount;
    }

    public int getId() {
        return id;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }


}
