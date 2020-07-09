package com.example.project_movies.model.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.project_movies.model.Models.Movie_1;

public class MovieDataSourceFactory extends DataSource.Factory {

    MutableLiveData<PageKeyedDataSource<Integer, Movie_1>> movieDataSourceLive=new MutableLiveData<PageKeyedDataSource<Integer, Movie_1>>();

    Integer state=null;
    public MovieDataSourceFactory(Integer state) {
        this.state=state;
    }

    @NonNull
    @Override
    public DataSource create() {
        MovieDataSource movieDataSource= new MovieDataSource(state);
        movieDataSourceLive.postValue(movieDataSource);

        return movieDataSource;
    }


}
