package com.example.project_movies.model.Models;

import java.util.ArrayList;

public class Movie_filter extends Movie_1 {


    ArrayList<Integer> genres;

    public Movie_filter(int id, double vote_average, String title, String release_date, String poster_path,ArrayList<Integer> genres) {
        super(id, vote_average, title, release_date, poster_path);
        this.genres=genres;
    }

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Integer> genres) {
        this.genres = genres;
    }
}
