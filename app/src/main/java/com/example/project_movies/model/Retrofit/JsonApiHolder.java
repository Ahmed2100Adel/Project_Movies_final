package com.example.project_movies.model.Retrofit;

import androidx.lifecycle.LiveData;

import com.example.project_movies.model.Models.Movie_2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApiHolder {


    @GET("trending/movie/day")
    Call<ResponseBody> getTrending(@Query("api_key") String api_key,
                                   @Query("sort_by") String sort_by ,
                                   @Query("page") int page);


    @GET("movie/{id}")
    Call<ResponseBody> getMovieAtId(@Path("id") String id,
                               @Query("api_key") String api_key ,
                               @Query("language") String language);
}
