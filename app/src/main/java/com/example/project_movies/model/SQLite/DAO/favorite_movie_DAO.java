package com.example.project_movies.model.SQLite.DAO;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project_movies.model.Models.Movie_1;

import java.util.List;

@Dao
public interface favorite_movie_DAO {

    @Insert
    void insertFavoriteMovie(Movie_1 movie);

    @Delete
    void deleteFavoriteMovie(Movie_1 movie);


    @Query("SELECT * FROM favorite_movies ORDER BY timeOfInsertion DESC")
    DataSource.Factory<Integer,Movie_1> getMoviesPaged();



    @Query("DELETE FROM FAVORITE_MOVIES")
    void deleteAllMovies();


}
