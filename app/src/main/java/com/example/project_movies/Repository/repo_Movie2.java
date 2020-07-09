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

public class repo_Movie2{


    private MutableLiveData<Movie_2> mutableLiveData= new MutableLiveData<>();



    public MutableLiveData<Movie_2> getMutableLiveData(String id){
        RetrofitClient.getInstance().getApi().getMovieAtId(id,constants.THEMOVIEDB.API_KEY,constants.THEMOVIEDB.LANGUAGE_EN)
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
                                JSONArray genres=root.getJSONArray("genres");
                                List<String> names= new ArrayList<String>();
                                for (int i=0;i<genres.length();i++){
                                    JSONObject jsonObject= genres.getJSONObject(i);
                                    names.add(jsonObject.optString("name"));

                                }
                                Movie_2 movie_2= new Movie_2(adult,names,homepage,overview);
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


}
