package com.example.project_movies.model.SQLite.DAO;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_movies.model.Models.Movie_1;

import java.util.List;

@Dao
public interface favorite_movie_DAO {

    @Insert
    void insertFavoriteMovie(Movie_1 movie);

    @Delete
    void deleteFavoriteMovie(Movie_1 movie);

    @Query("DELETE FROM favorite_movies WHERE id =:id")
    void deleteAtId(int id);

    @Update
    void updateMovie(Movie_1 movie);


    @Query("SELECT * FROM favorite_movies WHERE favorite == 1 ORDER BY timeOfInsertion DESC")
    DataSource.Factory<Integer,Movie_1> getFavoriteMoviesPaged();


    @Query("SELECT * FROM favorite_movies WHERE iWantToWatch == 1 ORDER BY timeOfInsertion DESC")
    DataSource.Factory<Integer,Movie_1> getIWantToWatchPaged();

    @Query("SELECT * FROM favorite_movies WHERE watched == 1 ORDER BY timeOfInsertion DESC")
    DataSource.Factory<Integer,Movie_1> getWatchPaged();



    @Query("DELETE FROM FAVORITE_MOVIES")
    void deleteAllMovies();


    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    LiveData<List<Movie_1>> getMovieAtId(int id);


    //getting one Instance
    @Query("SELECT * FROM favorite_movies WHERE id = :id AND favorite=1")
    LiveData<List<Movie_1>> getMovieFavoriteAtId(int id);



}
