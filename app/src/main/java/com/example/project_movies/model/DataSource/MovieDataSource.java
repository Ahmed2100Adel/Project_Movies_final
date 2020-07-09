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

    public static  Integer CURRENT_STATE_FOR_KIDS=2;
    public static  Integer CURRENT_STATE_FOR_MAIN=null;
    public static  Integer CURRENT_STATE=null;

    public static final int STATE_LOADING_INITIAL=12;
    public static final int STATE_LOADING_AFTER=13;
    public static final int STATE_LOADING_BEFORE=14;

    public MovieDataSource(Integer state) {
        if (state==null||state==CURRENT_STATE_FOR_KIDS){
            CURRENT_STATE=state;
        }
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie_1> callback) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (CURRENT_STATE==null) {
                    load(callback,CURRENT_STATE_FOR_MAIN);

                }else if (CURRENT_STATE==CURRENT_STATE_FOR_KIDS){
                    load(callback,CURRENT_STATE_FOR_KIDS);
                }
            }
        });
        thread.start();

    }
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie_1> callback) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                if (CURRENT_STATE==null) {

                    load(callback,STATE_LOADING_BEFORE,CURRENT_STATE_FOR_MAIN,params);
                }else if (CURRENT_STATE==CURRENT_STATE_FOR_KIDS){
                    load(callback,STATE_LOADING_BEFORE,CURRENT_STATE_FOR_KIDS,params);

                }
            }
        });
        thread.start();


    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie_1> callback) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                if (CURRENT_STATE==null) {
                    load(callback,STATE_LOADING_AFTER,CURRENT_STATE_FOR_MAIN,params);

                } else if (CURRENT_STATE==CURRENT_STATE_FOR_KIDS){
                    load(callback,STATE_LOADING_AFTER,CURRENT_STATE_FOR_KIDS,params);

                }
            }
        });
        thread.start();


    }
    public void load(  LoadInitialCallback<Integer, Movie_1> callback,Integer Current_State_section){
                if (Current_State_section==null){
                    RetrofitClient.getInstance().getApi().getTrending(constants.THEMOVIEDB.API_KEY, constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC, FIRST_PAGE)
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    callback.onResult(parseJson(response), null, FIRST_PAGE + 1);
                                }
                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                }else if (Current_State_section.equals(CURRENT_STATE_FOR_KIDS)) {
                    RetrofitClient.getInstance().getApi().getKidsMovies(constants.THEMOVIEDB.CERTIFICATION_COUNTRY_US,
                            constants.THEMOVIEDB.CERTIFICATION_LTE,
                            constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC,
                            constants.THEMOVIEDB.API_KEY,
                            FIRST_PAGE)
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    callback.onResult(parseJson(response), null, FIRST_PAGE + 1);
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                }
                            });
        }
    }
    public void load( @NonNull final LoadCallback<Integer, Movie_1> callback,int State_loading,Integer Current_State_section,@NonNull final LoadParams<Integer> params) {

          if (Current_State_section==null) {
              RetrofitClient.getInstance().getApi().getTrending(constants.THEMOVIEDB.API_KEY, constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC, params.key)
                      .enqueue(new Callback<ResponseBody>() {
                          @Override
                          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                  if (State_loading == STATE_LOADING_BEFORE) {
                                      Integer adjacentKey = (params.key < 500) ? params.key - 1 : null;
                                      if (response.body() != null) {

                                          //passing the loaded data
                                          //and the previous page key
                                          callback.onResult(parseJson(response), adjacentKey);
                                      }
                                  } else if (State_loading == STATE_LOADING_AFTER) {
                                      Integer adjacentKey = (params.key < 500) ? params.key + 1 : null;
                                      if (response.body() != null) {

                                          //passing the loaded data
                                          //and the previous page key
                                          callback.onResult(parseJson(response), adjacentKey);
                                      }
                                  }
                          }
                          @Override
                          public void onFailure(Call<ResponseBody> call, Throwable t) {
                          }
                      });
          } else if (Current_State_section.equals(CURRENT_STATE_FOR_KIDS)) {
              RetrofitClient.getInstance().getApi().getKidsMovies(constants.THEMOVIEDB.CERTIFICATION_COUNTRY_US,
                      constants.THEMOVIEDB.CERTIFICATION_LTE,
                      constants.THEMOVIEDB.SORT_BY_POPULARITY_DESC,
                      constants.THEMOVIEDB.API_KEY,
                      params.key)
                      .enqueue(new Callback<ResponseBody>() {
                          @Override
                          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                              if (State_loading == STATE_LOADING_BEFORE) {
                                  Integer adjacentKey = (params.key < 500) ? params.key - 1 : null;
                                  if (response.body() != null) {

                                      //passing the loaded data
                                      //and the previous page key
                                      callback.onResult(parseJson(response), adjacentKey);
                                  }
                              } else if (State_loading == STATE_LOADING_AFTER) {
                                  Integer adjacentKey = (params.key < 500) ? params.key + 1 : null;
                                  if (response.body() != null) {

                                      //passing the loaded data
                                      //and the previous page key
                                      callback.onResult(parseJson(response), adjacentKey);
                                  }
                              }
                          }

                          @Override
                          public void onFailure(Call<ResponseBody> call, Throwable t) {


                          }
                      });
          }
    }
    private  List<Movie_1> parseJson(Response<ResponseBody> response){
        String body = null;
        List<Movie_1> movie_1List = new ArrayList<Movie_1>();
        try {
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject root = new JSONObject(body);
            JSONArray results = root.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);
                int id = jsonObject.optInt("id");
                double vote_average = jsonObject.optDouble("vote_average");
                String title = jsonObject.optString("title");
                String release_date = jsonObject.optString("release_date");
                String poster_path = jsonObject.optString("poster_path");
                movie_1List.add(new Movie_1(id, vote_average, title, release_date, poster_path));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie_1List;
    }

}
