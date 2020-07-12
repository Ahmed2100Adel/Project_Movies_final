package com.example.project_movies.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.project_movies.constants.constants;
import com.example.project_movies.model.Models.article;
import com.example.project_movies.model.Retrofit.RetrofitClient;

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

public class repo_articles {
    private MutableLiveData<List<article>> mutableLiveDataNytimes= new MutableLiveData<>();


    public LiveData<List<article>> getArticlesAtName(String title){

        RetrofitClient.clearClient().getInstance(RetrofitClient.getBaseUrlNytimes()).getApi().getNyTimesAtTitle(title, constants.THENYTIME.API_KEY)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        String body=null;

                        List<article> articles= new ArrayList<>();
                        try {
                            body=response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {

                            JSONObject root=new JSONObject(body);
                            JSONArray results=root.getJSONArray("results");
                            for (int i=0;i<results.length();i++){
                                JSONObject jsonObject= results.getJSONObject(i);
                                String summary_short=jsonObject.optString("summary_short");
                                JSONObject link=jsonObject.getJSONObject("link");
                                String url=link.optString("url");
                                String suggested_link_text=link.optString("suggested_link_text");
                                articles.add(new article(url,suggested_link_text,summary_short));
                            }
                            mutableLiveDataNytimes.postValue(articles);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

        return mutableLiveDataNytimes;

    }
}
