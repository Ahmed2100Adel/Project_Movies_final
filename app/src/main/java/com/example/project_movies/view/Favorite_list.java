package com.example.project_movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.project_movies.R;
import com.example.project_movies.databinding.ActivityFavoriteListBinding;
import com.example.project_movies.view.Fragments.recyclerView_for_all;

public class Favorite_list extends AppCompatActivity {

    private ActivityFavoriteListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFavoriteListBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        setContentView(root);

        recyclerView_for_all fragment_trending=new recyclerView_for_all();
        fragment_trending.CURRENT_STATE=fragment_trending.CURRENT_STATE_FAVORITES;
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(binding.recyclerViewTrending.getId(),fragment_trending)
                .commit();
    }
}