package com.example.project_movies.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.project_movies.Repository.repo_favoriteMovie;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.SQLite.favoriteDB ;

import java.util.List;

public class view_model_favorite extends AndroidViewModel {


    private  repo_favoriteMovie repository;
    //private  LiveData<List<Favorite_movie>> allFavoriteMovies;
    public LiveData<PagedList<Movie_1>> allMoviesPaged;
    public LiveData<List<Movie_1>> moviesAtId;

    public view_model_favorite(@NonNull Application application) {
        super(application);
        repository= new repo_favoriteMovie(application);
        //allFavoriteMovies=repository.allMovies;

        DataSource.Factory<Integer,Movie_1>  factory=favoriteDB.getInstance(application).favoriteMovieDao().getFavoriteMoviesPaged();
        LivePagedListBuilder<Integer,Movie_1> livePagedListBuilder=new LivePagedListBuilder<Integer,Movie_1> (factory,10);
        allMoviesPaged=livePagedListBuilder.build();


    }

    public LiveData<List<Movie_1>> getFavoriteMoviesAtId(int id){

        return moviesAtId=repository.getFavoriteMoviesAtId(id);
    }


    public LiveData<PagedList<Movie_1>> getPagedLiveData(){
        return allMoviesPaged;
    }


    public void deleteAtId(int id){
        repository.deleteAtId(id);
    }

    public void insert(Movie_1 movie){
        repository.insert(movie);
    }

    public void delete(Movie_1 movie){
        repository.delete(movie);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
