package com.example.project_movies.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.project_movies.Repository.repo_Movie2 ;
import com.example.project_movies.Repository.repo_articles ;
import com.example.project_movies.model.Models.Movie_2;
import com.example.project_movies.model.Models.Movie_Omdb;
import com.example.project_movies.model.Models.article;

import java.util.List;

public class view_mode_Movie2  extends ViewModel {
    repo_Movie2 viewModel;
    repo_articles viewModelArticles;


    public view_mode_Movie2() {

        viewModel=new repo_Movie2();
        viewModelArticles= new repo_articles();

    }

    public LiveData<Movie_2> getMovie2(String id){
        return viewModel.getMutableLiveData(id);
    }

    public LiveData<Movie_Omdb> getMovieOmdb(String imdbId){
        return viewModel.getMutableLiveDataOmdb(imdbId);
    }

    public LiveData<List<article>> getArticlesAtTile(String title){
        Log.v("main","1");

        return viewModelArticles.getArticlesAtName(title);
    }
}



