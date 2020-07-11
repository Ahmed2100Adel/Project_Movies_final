package com.example.project_movies.model.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private static Retrofit retrofit;

    private static String BASE_URL_MOVIE_DB="https://api.themoviedb.org/3/";
    public static final String BASE_URL_OMDB="https://www.omdbapi.com";


    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_MOVIE_DB)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    private RetrofitClient(String BASE_URL) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }



    public static synchronized RetrofitClient getInstance(){
        if (mInstance==null){
            mInstance=new RetrofitClient();
        }
        return mInstance;
    }

    public static synchronized RetrofitClient getInstance(String BASE_URL){
        if (mInstance==null){
            if (BASE_URL.equals(BASE_URL_OMDB)){
                mInstance=new RetrofitClient(BASE_URL);

            }
        }
        return mInstance;
    }

    public static synchronized RetrofitClient clearClient(){
        mInstance=null;
        return mInstance;
    }



    public static synchronized String getBaseUrlOmdb(){
        return BASE_URL_OMDB;
    }
    public JsonApiHolder getApi(){
        return retrofit.create(JsonApiHolder.class);
    }
}
