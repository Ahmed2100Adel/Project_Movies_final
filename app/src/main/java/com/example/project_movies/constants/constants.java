package com.example.project_movies.constants;

public class constants {

    public static class THEMOVIEDB{
        public static final String API_KEY="db93980ce8f3949194e9c02bf9645258";
        public static final String SORT_BY_POPULARITY_DESC="popularity.desc";
        public static final String LANGUAGE_EN="en-US";
        public static final String CERTIFICATION_COUNTRY_US="US";
        public static final String CERTIFICATION_LTE="G";

        public static final int GENRE_ACTION = 28;
        public static final int GENRE_FANTASY = 14;
        public static final int GENRE_DRAMA= 18 ;
        public static final int GENRE_WAR= 10752 ;
        public static final int GENRE_COMEDY =35 ;
        public static final int GENRE_ROMANCE =10749 ;
        public static final int GENRE_SCIENCE_FICTION =878 ;
        public static final int GENRE_SCIENCE_HORROR =27 ;
        public static final int GENRE_SCIENCE_SCIENCE =878 ;
        public static final int GENRE_ADVENTURE =12 ;
        public static final int GENRE_HISTORY =36 ;
        public static final int GENRE_ANIMATIONS =16 ;
        public static final int GENRE_MYSTERY =9648 ;
        public static final int GENRE_FAMILY =10751 ;
        public static final int GENRE_THRILLER =53 ;
        public static final int GENRE_CRIME =80 ;
    }

    public static class THEOMDB{
        public static final String API_KEY="f1ab1e93";
    }

    public static class THENYTIME{
        public static final String API_KEY="ql1z2jEAGyInBsK9ozx8Q9BoTmSdezBP";
    }

    public static class Movie_details{
        public static final String ID="id";
        public static final String TITLE="title";
        public static final String RELEASE_DATE="release-date";
        public static final String POSTER_URL="poster-url";
        public static final String VOTE_AVERAGE="vote-average";
    }

    public static class FILTER{
        public static final String TYPE_RELEASE="type_release";
        public static final String RELEASED_AT="released_at";
        public static final String RELEASED_AFTER="released_after";
        public static final String RELEASE_VALUE = "release_value";
        public static final String GREATER_THAN = "greater_than";
        public static final String LESS_THAN = "less_than";
        public static final String GENRES = "genres";
    }

    public static class CURRENT_STATE{
        public static  Integer CURRENT_STATE_TRENDING_FILTER=6;
        public static final Integer STATE_LOADING_INITIAL=12;
        public static final Integer STATE_LOADING_AFTER=13;
        public static final Integer STATE_LOADING_BEFORE=14;
    }
}
