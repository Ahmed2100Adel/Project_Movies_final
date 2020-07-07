package com.example.project_movies.model.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.project_movies.model.Models.Movie_1;

public class MovieDataSourceFactory extends DataSource.Factory {

    MutableLiveData<PageKeyedDataSource<Integer, Movie_1>> movieDataSourceLive=new MutableLiveData<PageKeyedDataSource<Integer, Movie_1>>();

    @NonNull
    @Override
    public DataSource create() {
        MovieDataSource movieDataSource= new MovieDataSource();
        movieDataSourceLive.postValue(movieDataSource);

        return movieDataSource;
    }


}
