package com.example.project_movies.model.DataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.project_movies.constants.constants;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.Models.Movie_filter;

public class MovieDataSourceFilterFactory extends DataSource.Factory {

    MutableLiveData<PageKeyedDataSource<Integer, Movie_1>> movieFilterDataSourceLive= new MutableLiveData<PageKeyedDataSource<Integer, Movie_1>>();
    private Integer state=null;
    private Double greaterThan;
    private Double lessThan;
    private String releaseType;
    private String releaseValue;
    private String genres;

    public MovieDataSourceFilterFactory(Integer state,Double greaterThan,Double lessThan,String releaseType,String releaseValue,String genres) {
        if (state.equals(constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER)){
            this.state=state;
        }
        this.greaterThan=greaterThan;
        this.lessThan=lessThan;
        this.releaseType=releaseType;
        this.releaseValue=releaseValue;
        this.genres=genres;

    }

    @NonNull
    @Override
    public DataSource create() {
        MovieDataSourceFilter movieDataSourceFilter= new MovieDataSourceFilter(state);
        movieDataSourceFilter.setConditions(greaterThan,lessThan,releaseType,releaseValue,genres);
        movieFilterDataSourceLive.postValue(movieDataSourceFilter);
        return movieDataSourceFilter;
    }
}
