package com.example.project_movies.view.Fragments;

import android.os.Bundle;
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

import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.viewModel.recylcer_view_model;
import com.example.project_movies.viewModel.adapter_recycler_view;
import com.example.project_movies.R;

public class recyclerView_for_all extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.reclerview_for_all,container,false);

        RecyclerView recyclerView=(RecyclerView) root;
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(container.getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                gridLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        final adapter_recycler_view adapter=new adapter_recycler_view();
        recylcer_view_model ViewModel= ViewModelProviders.of((FragmentActivity) container.getContext()).get(recylcer_view_model.class);
        ViewModel.moviesList.observe(this, new Observer<PagedList<Movie_1>>() {
            @Override
            public void onChanged(PagedList<Movie_1> movie_1s) {
                adapter.submitList(movie_1s);
            }
        });

        recyclerView.setAdapter(adapter);
        return root;
    }


}

