package com.example.project_movies.model.DataSource;


import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import com.example.project_movies.model.Models.Movie_1;
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

public class MovieDataSource  extends PageKeyedDataSource<Integer, Movie_1> {


    public static final int FIRST_PAGE=1;
    public static final int PAGE_SIZE=20;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie_1> callback) {

        RetrofitClient.getInstance().getApi().getTrending(constants.THEMOVIEDB.API_KEY,constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC,FIRST_PAGE)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String body=null;
                        List<Movie_1> movie_1List= new ArrayList<Movie_1>();
                        try {
                            body =response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject root= new JSONObject (body);
                            JSONArray results= root.getJSONArray("results");
                            for (int i=0; i<results.length();i++){
                                JSONObject jsonObject=results.getJSONObject(i);
                                int id=jsonObject.optInt("id");
                                double vote_average=jsonObject.optDouble("vote_average");
                                String title=jsonObject.optString("title");
                                String release_date=jsonObject.optString("release_date");
                                String poster_path=jsonObject.optString("poster_path");
                                movie_1List.add(new Movie_1(id,vote_average,title,release_date,poster_path));
                            }

                            callback.onResult(movie_1List,null,FIRST_PAGE+1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {


                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie_1> callback) {
        RetrofitClient.getInstance().getApi().getTrending(constants.THEMOVIEDB.API_KEY,constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC,params.key)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String body=null;
                        List<Movie_1> movie_1List= new ArrayList<Movie_1>();
                        try {
                            body =response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject root= new JSONObject (body);
                            JSONArray results= root.getJSONArray("results");
                            for (int i=0; i<results.length();i++){
                                JSONObject jsonObject=results.getJSONObject(i);
                                int id=jsonObject.optInt("id");
                                double vote_average=jsonObject.optDouble("vote_average");
                                String title=jsonObject.optString("title");
                                String release_date=jsonObject.optString("release_date");
                                String poster_path=jsonObject.optString("poster_path");
                                movie_1List.add(new Movie_1(id,vote_average,title,release_date,poster_path));
                            }

                            Integer adjacentKey = (params.key < 500) ? params.key - 1 : null;
                            if (response.body() != null) {

                                //passing the loaded data
                                //and the previous page key
                                callback.onResult(movie_1List, adjacentKey);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {


                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie_1> callback) {
        RetrofitClient.getInstance().getApi().getTrending(constants.THEMOVIEDB.API_KEY,constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC,params.key)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String body=null;
                        List<Movie_1> movie_1List= new ArrayList<Movie_1>();
                        try {
                            body =response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject root= new JSONObject (body);
                            JSONArray results= root.getJSONArray("results");
                            for (int i=0; i<results.length();i++){
                                JSONObject jsonObject=results.getJSONObject(i);
                                int id=jsonObject.optInt("id");
                                double vote_average=jsonObject.optDouble("vote_average");
                                String title=jsonObject.optString("title");
                                String release_date=jsonObject.optString("release_date");
                                String poster_path=jsonObject.optString("poster_path");
                                movie_1List.add(new Movie_1(id,vote_average,title,release_date,poster_path));
                            }

                            Integer adjacentKey = (params.key < 500) ? params.key + 1 : null;
                            if (response.body() != null) {

                                //passing the loaded data
                                //and the previous page key
                                callback.onResult(movie_1List, adjacentKey);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {


                    }
                });
    }
}
