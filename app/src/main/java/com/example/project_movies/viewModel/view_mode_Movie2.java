package com.example.project_movies.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.project_movies.Repository.repo_Movie2 ;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.Models.Movie_2;
import com.example.project_movies.model.Models.Movie_Omdb;

public class view_mode_Movie2  extends ViewModel {
    repo_Movie2 viewModel;


    public view_mode_Movie2() {

        viewModel=new repo_Movie2();

    }

    public LiveData<Movie_2> getMovie2(String id){
        return viewModel.getMutableLiveData(id);
    }

    public LiveData<Movie_Omdb> getMovieOmdb(String imdbId){
        return viewModel.getMutableLiveDataOmdb(imdbId);
    }
}



