package com.example.project_movies.model.Models;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.project_movies.constants.constants;
@Entity(tableName = "favorite_movies")
public class Movie_1 {


    @PrimaryKey(autoGenerate = true)
    private int idFav;

    private int id;

    private double vote_average;

    private String title;

    private String release_date;

    private String poster_path;

    private long timeOfInsertion=0;


    @Ignore
    public Movie_1(int id, double vote_average, String title, String release_date, String poster_path) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
    }

    public Movie_1( int id,double vote_average, String title, String release_date, String poster_path, long timeOfInsertion) {
        this.id=id;
        this.vote_average = vote_average;
        this.title = title;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.timeOfInsertion = timeOfInsertion;
    }

    public void setIdFav(int idFav) {
        this.idFav = idFav;
    }

    public int getIdFav() {
        return idFav;
    }

    public void setId(int id) {
        this.id = id;
    }
    public long getTimeOfInsertion() {
        return timeOfInsertion;
    }

    public void setTimeOfInsertion(long timeOfInsertion) {
        this.timeOfInsertion = timeOfInsertion;
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
        if (timeOfInsertion!=0.0){
            return poster_path;
        }
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
