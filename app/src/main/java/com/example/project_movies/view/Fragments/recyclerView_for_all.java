package com.example.project_movies.view.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_movies.constants.constants;
import com.example.project_movies.view.Movie_details;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.viewModel.recylcer_view_model;
import com.example.project_movies.viewModel.adapter_recycler_view;
import com.example.project_movies.R;
import com.example.project_movies.viewModel.view_model_favorite;

import java.io.Serializable;

public class recyclerView_for_all extends Fragment implements Serializable {
    adapter_recycler_view adapter;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    public static  Integer CURRENT_STATE_FAVORITES=1;
    public static  Integer CURRENT_STATE_FOR_KIDS=2;
    public static  Integer CURRENT_STATE_TRENDING=3;
    public static  Integer CURRENT_STATE_I_WANT_TO_WATCH=4;
    public static  Integer CURRENT_STATE=null;
    public static final Integer TYPE_HORIZENTAL=100;
    public static final Integer TYPE_VERTICAL=101;
    public adapter_recycler_view getAdapter(){
        return adapter;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.reclerview_for_all,container,false);

        //main configuration for all situations
        recyclerView=(RecyclerView) root;
        recyclerView.setHasFixedSize(true);

        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                settingDecoration(container);
            }

        });
        thread.start();
        adapter=new adapter_recycler_view();


        if (CURRENT_STATE!=null){
            if (CURRENT_STATE==CURRENT_STATE_FAVORITES){
                Thread thread1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        settingAdapterAndClickListener(container);
                    }
                });
                thread1.start();
                view_model_favorite ViewModel= ViewModelProviders.of((FragmentActivity) container.getContext()).get(view_model_favorite.class);
                ViewModel.getFavoritePagedLiveData().observe(this, new Observer<PagedList<Movie_1>>() {
                    @Override
                    public void onChanged(PagedList<Movie_1> favorite_movies) {
                        adapter.submitList(favorite_movies);

                    }
                });


            }else  if (CURRENT_STATE==CURRENT_STATE_I_WANT_TO_WATCH){
                Thread thread1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        settingAdapterAndClickListener(container);
                    }
                });
                thread1.start();
                view_model_favorite ViewModel= ViewModelProviders.of((FragmentActivity) container.getContext()).get(view_model_favorite.class);
                ViewModel.getIWantToWatchPagedLiveData().observe(this, new Observer<PagedList<Movie_1>>() {
                    @Override
                    public void onChanged(PagedList<Movie_1> favorite_movies) {
                        adapter.submitList(favorite_movies);

                    }
                });
            }
            else if (CURRENT_STATE==CURRENT_STATE_FOR_KIDS){
                Thread thread1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        settingAdapterAndClickListener(container);
                    }
                });
                thread1.start();
                recylcer_view_model ViewModel= ViewModelProviders.of((FragmentActivity) container.getContext()).get(recylcer_view_model.class);
                ViewModel.setState(CURRENT_STATE_FOR_KIDS);
                ViewModel.recylcer_view_model_start(CURRENT_STATE_FOR_KIDS,TYPE_VERTICAL);
                ViewModel.moviesList.observe(this, new Observer<PagedList<Movie_1>>() {
                    @Override
                    public void onChanged(PagedList<Movie_1> movie_1s) {
                        adapter.submitList(movie_1s);
                    }
                });


            }
            else if (CURRENT_STATE.equals(CURRENT_STATE_TRENDING)){
                recylcer_view_model ViewModel= ViewModelProviders.of((FragmentActivity) container.getContext()).get(recylcer_view_model.class);
                ViewModel.recylcer_view_model_start(CURRENT_STATE_TRENDING,TYPE_VERTICAL);
                ViewModel.moviesList.observe(this, new Observer<PagedList<Movie_1>>() {
                    @Override
                    public void onChanged(PagedList<Movie_1> movie_1s) {
                        Thread thread1=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.submitList(movie_1s);

                            }
                        });
                        thread1.start();
                    }
                });

                Thread thread1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        settingAdapterAndClickListener(container);
                    }
                });
                thread1.start();
            }
        }


        return root;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager.setSpanCount(4);
            recyclerView.setLayoutManager(gridLayoutManager);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager.setSpanCount(3);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    private void settingAdapterAndClickListener(@Nullable ViewGroup container){
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new adapter_recycler_view.OnItemClickListener() {
            @Override
            public void OnItemClick(int id,double voteAverage,String title,String releaseDate,String posterUrl) {
                Intent intent= new Intent(container.getContext(), Movie_details.class);
                intent.putExtra(constants.Movie_details.ID,String.valueOf(id));
                intent.putExtra(constants.Movie_details.VOTE_AVERAGE,String.valueOf(voteAverage));
                intent.putExtra(constants.Movie_details.TITLE,title);
                intent.putExtra(constants.Movie_details.RELEASE_DATE,releaseDate);
                intent.putExtra(constants.Movie_details.POSTER_URL,posterUrl);
                startActivity(intent);
            }
        });
    }


    private void settingDecoration(@Nullable ViewGroup container){
        int orientation=getResources().getConfiguration().orientation;

        if (orientation== Configuration.ORIENTATION_LANDSCAPE){
            gridLayoutManager=new GridLayoutManager(container.getContext(),4);
        }else{
            gridLayoutManager=new GridLayoutManager(container.getContext(),3);
        }
        recyclerView.setLayoutManager(gridLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                gridLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}

