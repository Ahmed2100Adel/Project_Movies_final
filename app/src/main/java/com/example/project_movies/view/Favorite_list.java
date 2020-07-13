package com.example.project_movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.project_movies.view.Fragments.recyclerView_for_all;
import com.example.project_movies.databinding.ActivityFavoriteListBinding;
import com.example.project_movies.view.Fragments.recyclerView_for_all;

public class Favorite_list extends AppCompatActivity {

    private ActivityFavoriteListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteListBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                if (getIntent().getAction() != null) {
                    if (getIntent().getAction().equals(String.valueOf(recyclerView_for_all.CURRENT_STATE_I_WANT_TO_WATCH))) {
                        recyclerView_for_all fragment_trending = new recyclerView_for_all();
                        fragment_trending.CURRENT_STATE = fragment_trending.CURRENT_STATE_I_WANT_TO_WATCH;
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .add(binding.recyclerViewTrending.getId(), fragment_trending)
                                .commit();

                        binding.close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });

                        binding.toolbar.setTitle("I want to watch ");
                    }
                    if (getIntent().getAction().equals(String.valueOf(recyclerView_for_all.CURRENT_STATE_WATCHED))) {
                        recyclerView_for_all fragment_trending = new recyclerView_for_all();
                        fragment_trending.CURRENT_STATE = fragment_trending.CURRENT_STATE_WATCHED;
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .add(binding.recyclerViewTrending.getId(), fragment_trending)
                                .commit();

                        binding.close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                        binding.toolbar.setTitle("Watched ");

                    }
                } else {
                    recyclerView_for_all fragment_trending = new recyclerView_for_all();
                    fragment_trending.CURRENT_STATE = fragment_trending.CURRENT_STATE_FAVORITES;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(binding.recyclerViewTrending.getId(), fragment_trending)
                            .commit();

                    binding.close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });

                }
                }
        });
        thread.start();



    }
}