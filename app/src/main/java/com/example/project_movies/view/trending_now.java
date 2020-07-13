package com.example.project_movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.project_movies.R;
import com.example.project_movies.databinding.ActivityTrendingNowBinding;
import com.example.project_movies.view.Fragments.recyclerView_for_all;

public class trending_now extends AppCompatActivity {

    private recyclerView_for_all fragment_trending;

    private ActivityTrendingNowBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTrendingNowBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        setContentView(root);

        fragment_trending=new recyclerView_for_all();
        //null meaning it came from trending activity
        fragment_trending.CURRENT_STATE=fragment_trending.CURRENT_STATE_TRENDING;
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(binding.recyclerViewTrending.getId(),fragment_trending)
                .commit();

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(trending_now.this, "aHMED", Toast.LENGTH_SHORT).show();
            }
        });
    }
}