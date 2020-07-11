package com.example.project_movies.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.project_movies.model.Models.Movie_2;
import com.example.project_movies.model.Retrofit.RetrofitClient;
import com.example.project_movies.constants.constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.project_movies.model.Models.Movie_Omdb ;

public class repo_Movie2{


    private MutableLiveData<Movie_2> mutableLiveData= new MutableLiveData<>();
    private MutableLiveData<Movie_Omdb> mutableLiveDataOmdb= new MutableLiveData<>();



    public MutableLiveData<Movie_2> getMutableLiveData(String id){
        RetrofitClient.clearClient().getInstance().getApi().getMovieAtId(id,constants.THEMOVIEDB.API_KEY,constants.THEMOVIEDB.LANGUAGE_EN)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.body()!=null){
                            String body= null;
                            try {
                               body =response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                JSONObject root= new JSONObject(body);
                                boolean adult= root.optBoolean("adult");
                                String homepage= root.optString("homepage");
                                String overview= root.optString("overview");
                                String imdb_id= root.optString("imdb_id");
                                JSONArray genres=root.getJSONArray("genres");
                                List<String> names= new ArrayList<String>();
                                for (int i=0;i<genres.length();i++){
                                    JSONObject jsonObject= genres.getJSONObject(i);
                                    names.add(jsonObject.optString("name"));

                                }
                                Movie_2 movie_2= new Movie_2(adult,names,homepage,overview,imdb_id);
                                mutableLiveData.setValue(movie_2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

        return mutableLiveData;
    }

    public MutableLiveData<Movie_Omdb> getMutableLiveDataOmdb(String imdbId){
        RetrofitClient.clearClient().getInstance(RetrofitClient.getBaseUrlOmdb()).getApi().getDetailsOmdb(imdbId,constants.THEOMDB.API_KEY)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.body()!=null) {

                            String body = null;
                            try {
                                body = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                JSONObject root= new JSONObject(body);
                                String rated=root.optString("Rated");
                                String Runtime=root.optString("Runtime");
                                String Director=root.optString("Director");
                                String Writer=root.optString("Writer");
                                String Actors=root.optString("Actors");
                                String Plot=root.optString("Plot");//
                                String Language=root.optString("Language");
                                String Country=root.optString("Country");
                                String Awards=root.optString("Awards");//
                                String Poster=root.optString("Poster");//
                                String rottenTomatosRate=null;
                                String MetacriticRate=null;
                                JSONArray ratings=root.getJSONArray("Ratings");
                                for (int i=0;i<ratings.length();i++){
                                    JSONObject rating=ratings.getJSONObject(i);
                                    String source=rating.optString("Source");
                                    String value=rating.optString("Value");
                                    if (source.contains("Rotten Tomatoes")){

                                        rottenTomatosRate=value;
                                    }
                                    if (source.contains("Metacritic")){
                                        MetacriticRate=value;
                                    }
                                }
                                String imdbRate=root.optString("imdbRating");
                                String BoxOffice=root.optString("BoxOffice");
                                String Production=root.optString("Production");

                                Movie_Omdb movieOmdb= new Movie_Omdb(rated,Runtime,Director,Writer,Actors,Plot,Language,
                                                                    Country,Awards,Poster,rottenTomatosRate,
                                                                    MetacriticRate,imdbRate,BoxOffice,Production);
                                mutableLiveDataOmdb.postValue(movieOmdb);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
        return mutableLiveDataOmdb;
    }



}
