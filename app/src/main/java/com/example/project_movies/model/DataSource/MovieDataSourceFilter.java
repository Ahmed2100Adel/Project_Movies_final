package com.example.project_movies.model.DataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.project_movies.constants.constants;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.Models.Movie_filter;
import com.example.project_movies.model.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.project_movies.constants.constants.CURRENT_STATE;

public class MovieDataSourceFilter extends PageKeyedDataSource<Integer, Movie_1> {


     public static  Integer CURRENT_STATE=-1;


    public static final int FIRST_PAGE=1;
    public static final int PAGE_SIZE=20;


    public MovieDataSourceFilter(Integer state) {
        if (state.equals(constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER)){
            this.CURRENT_STATE=state;
        }
    }
    private Double greaterThan=-1.0;
    private Double lessThan=-1.0;
    private String releaseType="-1";
    private String releaseValue="-1";
    String [] genres;

    public void setConditions(Double greaterThan,Double lessThan,String releaseType,String releaseValue,String genres){
        this.greaterThan=greaterThan;
        this.lessThan=lessThan;
        this.releaseType=releaseType;
        this.releaseValue=releaseValue;
        this.genres=genres.split(",");
        Log.v("main","gen"+this.genres.length);
        Log.v("main","fil4");

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie_1> callback) {

        if (CURRENT_STATE== constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER){
            load(callback, constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie_1> callback) {
        if (CURRENT_STATE== constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER){
            load(callback, constants.CURRENT_STATE.STATE_LOADING_BEFORE, constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER,params);
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie_1> callback) {
        if (CURRENT_STATE== constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER){
            load(callback, constants.CURRENT_STATE.STATE_LOADING_AFTER, constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER,params);
        }
    }


    public void load(LoadInitialCallback<Integer, Movie_1> callback, Integer Current_State_section){
        if (Current_State_section.equals(constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER)){
            RetrofitClient.clearClient().getInstance().getApi().getTrending(constants.THEMOVIEDB.API_KEY, constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC, FIRST_PAGE)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            callback.onResult(parseJsonFilter(response), null, FIRST_PAGE + 1);
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
        }
    }
    public void load( @NonNull final LoadCallback<Integer, Movie_1> callback,Integer State_loading,Integer Current_State_section,@NonNull final LoadParams<Integer> params) {

        if (Current_State_section.equals(constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER)) {
            RetrofitClient.clearClient().getInstance().getApi().getTrending(constants.THEMOVIEDB.API_KEY, constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC, params.key)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (State_loading .equals(constants.CURRENT_STATE.STATE_LOADING_BEFORE)) {
                                Integer adjacentKey = (params.key < 100) ? params.key - 1 : null;
                                if (response.body() != null) {

                                    //passing the loaded data
                                    //and the previous page key
                                    callback.onResult(parseJsonFilter(response), adjacentKey);
                                }
                            } else if (State_loading .equals(constants.CURRENT_STATE.STATE_LOADING_AFTER)) {
                                    Integer adjacentKey = (params.key < 100) ? params.key + 1 : null;
                                    if (response.body() != null) {

                                        //passing the loaded data
                                        //and the previous page key
                                        callback.onResult(parseJsonFilter(response), adjacentKey);
                                    }


                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                        }
                    });
        }
    }

    private List<Movie_1> parseJsonFilter(Response<ResponseBody> response){
        String body = null;
        List<Movie_1> movie_1List = new ArrayList<Movie_1>();
        try {
            if (response.body()!=null){
                body = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (body!=null) {


                JSONObject root = new JSONObject(body);
                JSONArray results = root.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject jsonObject = results.getJSONObject(i);
                    int id = jsonObject.optInt("id");
                    double vote_average = jsonObject.optDouble("vote_average");
                    String title = jsonObject.optString("title");
                    String release_date = jsonObject.optString("release_date");
                    String poster_path = jsonObject.optString("poster_path");

                    if (!greaterThan.equals(-1.0)&&!lessThan.equals(-1.0)){
                        if (vote_average<greaterThan||vote_average>lessThan){
                            continue;
                        }
                    }else if(!greaterThan.equals(-1.0)){

                        if (vote_average<greaterThan){
                            continue;

                        }
                    }else if(!lessThan.equals(-1.0)){

                        if (vote_average>lessThan){
                            continue;

                        }
                    }

                    //2020-7-10
                    String year=release_date.substring(0,4);
                    if (releaseType.equals(constants.FILTER.RELEASED_AFTER)){
                        if (Integer.compare(Integer.valueOf(year),Integer.valueOf(releaseValue))<0){

                            continue;
                        }
                            //Integer.valueOf(year).compareTo(Integer.valueOf(releaseValue))<=0
                    }
                    else if (releaseType.equals(constants.FILTER.RELEASED_AT)){
                        if (Integer.valueOf(year).compareTo(Integer.valueOf(releaseValue))!=0){


                            continue;
                        }
                    }

                    JSONArray genre_ids= jsonObject.getJSONArray("genre_ids");

                    boolean isGernreExist=false;
                    if (genres.length==1){
                        isGernreExist=true;
                    }else {


                        for (int j = 0; j < genre_ids.length(); j++) {
                            Integer currentGenreId = genre_ids.getInt(j);
                            //Log.v("main", "curr" + String.valueOf(currentGenreId));
                            for (String genreChosen : genres) {
                                if (Integer.valueOf(genreChosen).equals(currentGenreId)) {
                                    isGernreExist = true;
                                    //Log.v("main", "6");

                                    break;
                                }
                            }
                        }
                    }
                    if (isGernreExist){
                        movie_1List.add(new Movie_1(id, vote_average, title, release_date, poster_path));

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie_1List;
    }
}
