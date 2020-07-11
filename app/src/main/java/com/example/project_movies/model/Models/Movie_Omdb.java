package com.example.project_movies.model.Models;

public class Movie_Omdb {

     private String Rated;
     private String Runtime;
     private String Director;
     private String Writer;
     private String Actors;
     private String Plot;
     private String Language;
     private String Country;
     private String Awards;
     private String Poster;
     private String rottenTomatosRate;
     private String MetacriticRate;
     private String imdbRate;
     private String BoxOffice;
     private String Production;

    public Movie_Omdb(String rated, String runtime, String director, String writer, String actors, String plot,
                      String language, String country, String awards, String poster, String rottenTomatosRate, String metacriticRate,
                      String imdbRate, String boxOffice, String production) {
        Rated = rated;
        Runtime = runtime;
        Director = director;
        Writer = writer;
        Actors = actors;
        Plot = plot;
        Language = language;
        Country = country;
        Awards = awards;
        Poster = poster;
        this.rottenTomatosRate = rottenTomatosRate;
        MetacriticRate = metacriticRate;
        this.imdbRate = imdbRate;
        BoxOffice = boxOffice;
        Production = production;
    }

    public String getRated() {
        return Rated;
    }

    public String getRuntime() {
        return Runtime;
    }

    public String getDirector() {
        return Director;
    }

    public String getWriter() {
        return Writer;
    }

    public String getActors() {
        return Actors;
    }

    public String getPlot() {
        return Plot;
    }

    public String getLanguage() {
        return Language;
    }

    public String getCountry() {
        return Country;
    }

    public String getAwards() {
        return Awards;
    }

    public String getPoster() {
        return Poster;
    }

    public String getRottenTomatosRate() {
        return rottenTomatosRate;
    }

    public String getMetacriticRate() {
        return MetacriticRate;
    }

    public String getImdbRate() {
        return imdbRate;
    }

    public String getBoxOffice() {
        return BoxOffice;
    }

    public String getProduction() {
        return Production;
    }


    public String getFullActors(){
            return "Actors: " +getActors()+"  ";
    }

    public String getFullDirector(){
            return "Directors: "+getDirector()+"  ";
    }

    public String getFullWriter(){
            return "Writers: "+getWriter()+"  ";
    }

    public String getFullCountryAndLang(){
        return "Country: "+ getCountry()+"\n"+"Language: "+getLanguage();
    }

    public String getFullBoxOfficeAndProduction(){
        return "Box office: "+ getBoxOffice()+"\n"+"Production: "+getProduction();
    }
}
