package com.example.project_movies.viewModel;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.project_movies.model.DataSource.MovieDataSource;
import com.example.project_movies.model.DataSource.MovieDataSourceFactory;
import com.example.project_movies.model.DataSource.MovieDataSourceFilter;
import com.example.project_movies.model.DataSource.MovieDataSourceFilterFactory;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.Models.Movie_filter;

public class recylcer_view_model extends ViewModel {

    public LiveData<PagedList<Movie_1>> moviesList;
    public LiveData<PagedList<Movie_1>> movieListFilter;

    Integer state=null;

    public  void setState(Integer state) {
        this.state = state;
    }

    public void recylcer_view_model_start(Integer state,Integer type) {

        MovieDataSourceFactory movieDataSourceFactory= new MovieDataSourceFactory(state,type);
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.PAGE_SIZE).build();

        moviesList = (new LivePagedListBuilder(movieDataSourceFactory, pagedListConfig))
                .build();
    }

    public void recylcer_view_model_start(Integer state,Double greaterThan,Double lessThan,String releaseType,String releaseValue,String genres) {
        Log.v("main","fil");

        MovieDataSourceFilterFactory movieFilterDataSourceFactory= new MovieDataSourceFilterFactory(state,greaterThan,lessThan,releaseType,releaseValue,genres);
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.PAGE_SIZE).build();

        movieListFilter = (new LivePagedListBuilder(movieFilterDataSourceFactory, pagedListConfig))
                .build();
    }



}
