package com.example.project_movies.model.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.project_movies.model.Models.Movie_1;

public class MovieDataSourceFactory extends DataSource.Factory {

    MutableLiveData<PageKeyedDataSource<Integer, Movie_1>> movieDataSourceLive=new MutableLiveData<PageKeyedDataSource<Integer, Movie_1>>();

    Integer state=null;
    Integer type=null;
    public MovieDataSourceFactory(Integer state,Integer type) {
        this.state=state;
        this.type=type;
    }

    @NonNull
    @Override
    public DataSource create() {

        MovieDataSource movieDataSource= new MovieDataSource(state,type);
        movieDataSourceLive.postValue(movieDataSource);

        return movieDataSource;
    }


}
