package com.example.project_movies.model.Models;

import java.util.List;

public class Movie_2  {


    private boolean adult;
    private List<String> genres;
    private String homepage;
    private String overview;



    public Movie_2(boolean adult, List<String> genres, String homepage, String overview) {
        this.adult = adult;
        this.genres = genres;
        this.homepage = homepage;
        this.overview = overview;
    }

    public Movie_2() {
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getGenres() {
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append("genres: ");
        for (int i=0;i<genres.size();i++){
            if (i==0){
                stringBuilder.append(genres.get(i));
            }
            else if (i<genres.size()-2){

                stringBuilder.append(", "+genres.get(i));
            }else{
                stringBuilder.append(", and "+genres.get(i));
            }
        }
        return stringBuilder.toString();
    }

    public String getHomepage() {
        return homepage;
    }

    public String getOverview() {
        return overview;
    }
}
