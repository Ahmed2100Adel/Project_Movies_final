package com.example.project_movies.view.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_movies.MyItemRecyclerViewAdapter;
import com.example.project_movies.R;
import com.example.project_movies.constants.constants;
import com.example.project_movies.dummy.DummyContent;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.view.Movie_details;
import com.example.project_movies.viewModel.adapter_recycler_view;
import com.example.project_movies.viewModel.recylcer_view_model;

import static com.example.project_movies.model.DataSource.MovieDataSource.TYPE_HORIZENTAL;

/**
 * A fragment representing a list of Items.
 */
public class horizental_recyclerView_for_all extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Integer type=-1;
    public static final Integer TYPE_TRENDING=3;
    public static  Integer CURRENT_STATE_TRENDING=3;
    adapter_recycler_view adapter;
    RecyclerView recyclerView;

    public static final Integer TYPE_HORIZENTAL=100;
    public static final Integer TYPE_VERTICAL=101;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public horizental_recyclerView_for_all() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static horizental_recyclerView_for_all newInstance(int columnCount) {
        horizental_recyclerView_for_all fragment = new horizental_recyclerView_for_all();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public void setType(Integer type){
        if (type!=null){
            if (type==TYPE_TRENDING){
                this.type=type;
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horizental_recycler_view_for_all_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            LinearLayoutManager linearLayout= new LinearLayoutManager(context,  LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(linearLayout);
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
            adapter=new adapter_recycler_view();




            if (type.equals(TYPE_TRENDING)){
                recylcer_view_model ViewModel= ViewModelProviders.of((FragmentActivity) container.getContext()).get(recylcer_view_model.class);
                ViewModel.recylcer_view_model_start(CURRENT_STATE_TRENDING,TYPE_HORIZENTAL);
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
        return view;
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
}