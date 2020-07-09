package com.example.project_movies.model.SQLite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.SQLite.DAO.favorite_movie_DAO;

@Database(entities = {Movie_1.class},version = 1)
public abstract class favoriteDB extends RoomDatabase {

    private static favoriteDB mInstance;

    public  abstract favorite_movie_DAO favoriteMovieDao();


    public static synchronized favoriteDB getInstance(Context context){

        if (mInstance==null){
            mInstance= Room.databaseBuilder(context.getApplicationContext(),favoriteDB.class,"movies")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return mInstance;
    }

    public static RoomDatabase.Callback roomCallback= new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
