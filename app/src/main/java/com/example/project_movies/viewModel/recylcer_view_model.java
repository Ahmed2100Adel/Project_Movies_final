package com.example.project_movies.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.project_movies.model.DataSource.MovieDataSource;
import com.example.project_movies.model.DataSource.MovieDataSourceFactory;
import com.example.project_movies.model.Models.Movie_1;

public class recylcer_view_model extends ViewModel {

    public LiveData<PagedList<Movie_1>> moviesList;

    Integer state=null;

    public  void setState(Integer state) {
        this.state = state;
    }

    public void recylcer_view_model_start(Integer state) {

        MovieDataSourceFactory movieDataSourceFactory= new MovieDataSourceFactory(state);
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.PAGE_SIZE).build();

        moviesList = (new LivePagedListBuilder(movieDataSourceFactory, pagedListConfig))
                .build();
    }

}
