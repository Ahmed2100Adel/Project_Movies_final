package com.example.project_movies.view.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import java.io.Serializable;

public class recyclerView_for_all extends Fragment implements Serializable {
    adapter_recycler_view adapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.reclerview_for_all,container,false);

        recyclerView=(RecyclerView) root;
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(container.getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                gridLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter=new adapter_recycler_view();
        recylcer_view_model ViewModel= ViewModelProviders.of((FragmentActivity) container.getContext()).get(recylcer_view_model.class);
        ViewModel.moviesList.observe(this, new Observer<PagedList<Movie_1>>() {
            @Override
            public void onChanged(PagedList<Movie_1> movie_1s) {
                adapter.submitList(movie_1s);
            }
        });

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

        return root;
    }


}

