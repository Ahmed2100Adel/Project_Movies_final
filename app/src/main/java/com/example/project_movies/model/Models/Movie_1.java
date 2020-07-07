package com.example.project_movies.model.Models;

import androidx.annotation.Nullable;
import com.example.project_movies.constants.constants;
public class Movie_1 {
    private  int id;

    private double vote_average;

    private String title;

    private String release_date;

    private String poster_path;

    public Movie_1(int id, double vote_average, String title, String release_date, String poster_path) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        String url="https://image.tmdb.org/t/p/w185/"+poster_path+"?api_key="+constants.THEMOVIEDB.API_KEY;
        return url;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Movie_1 movie=(Movie_1) obj;
        if (this.id==movie.getId()
            &&this.vote_average==movie.getVote_average()
                &&this.title.equals(movie.getTitle())
                &&this.release_date.equals(movie.getRelease_date())
                &&this.poster_path.equals(movie.getPoster_path())

        ){
            return true;
        }


        return false;
    }
}
