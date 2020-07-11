package com.example.project_movies.model.Models;

import java.util.List;

public class Movie_2  {


    private boolean adult;
    private List<String> genres;
    private String homepage;
    private String overview;
    private String imdb_id;



    public Movie_2(boolean adult, List<String> genres, String homepage, String overview,String imdb_id) {
        this.adult = adult;
        this.genres = genres;
        this.homepage = homepage;
        this.overview = overview;
        this.imdb_id= imdb_id;
    }







    public void setHomepage(String homepage) {
        this.homepage = homepage;
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
            else if (i<genres.size()-1){

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

    public String getImdbId() {
        return imdb_id;
    }
}
