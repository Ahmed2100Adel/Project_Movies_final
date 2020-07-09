package com.example.project_movies.model.Retrofit;


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



    @GET("discover/movie")
    Call<ResponseBody> getKidsMovies(@Query("certification_country") String certification_country
                                     ,@Query("certification.lte") String certification_lte
                                     ,@Query("sort_by") String sort_by
                                     ,@Query("api_key") String api_key
                                     ,@Query("page") int page
    );
}
