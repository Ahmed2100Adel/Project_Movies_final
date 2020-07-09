package com.example.project_movies.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.SQLite.DAO.favorite_movie_DAO;
import com.example.project_movies.model.SQLite.favoriteDB ;

public class repo_favoriteMovie {

    public favorite_movie_DAO DAO;
    //public LiveData<List<Favorite_movie>> allMovies;

    public repo_favoriteMovie(Application application) {
        favoriteDB database=favoriteDB.getInstance(application);
        DAO=database.favoriteMovieDao();
        //allMovies=DAO.getMovies();


    }


    public void insert (Movie_1 movie){

        new InsertFavoriteMovieAsync(DAO).execute(movie);
    }

    public void delete(Movie_1 movie){

        new DeleteFavoriteMovieAsync(DAO).execute(movie);
    }

    public void deleteAll(){

        new DeleteAllFavoriteMovieAsync(DAO).execute();
    }



    public static class InsertFavoriteMovieAsync extends AsyncTask<Movie_1,Void,Void>{
    private favorite_movie_DAO DAO;

        public InsertFavoriteMovieAsync(favorite_movie_DAO DAO) {
            this.DAO = DAO;
        }

        @Override
        protected Void doInBackground(Movie_1... favorite_movies) {
            DAO.insertFavoriteMovie(favorite_movies[0]);
            return null;
        }
    }

    public static class DeleteFavoriteMovieAsync extends AsyncTask<Movie_1,Void,Void>{
    private favorite_movie_DAO DAO;

        public DeleteFavoriteMovieAsync(favorite_movie_DAO DAO) {
            this.DAO = DAO;
        }

        @Override
        protected Void doInBackground(Movie_1... favorite_movies) {
            DAO.deleteFavoriteMovie(favorite_movies[0]);
            return null;
        }
    }

    public static class DeleteAllFavoriteMovieAsync extends AsyncTask<Void,Void,Void>{
    private favorite_movie_DAO DAO;

        public DeleteAllFavoriteMovieAsync(favorite_movie_DAO DAO) {
            this.DAO = DAO;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            DAO.deleteAllMovies();
            return null;
        }
    }


}
