package com.example.project_movies.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.project_movies.R;
import com.example.project_movies.constants.constants;
import com.example.project_movies.databinding.ActivityTrendingNowBinding;
import com.example.project_movies.view.Fragments.recyclerView_for_all;

public class trending_now extends AppCompatActivity {

    private recyclerView_for_all fragment_trending;

    private ActivityTrendingNowBinding binding;
    private int REQUEST_CODE_FILTER=100;
    Double greaterThan=-1.0;
    Double lessThan=-1.0;
    String releaseValue="-1";
    String releaseType="-1";
    String genres="-1";
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
                Intent intent= new Intent(trending_now.this,filter.class);
                startActivityForResult(intent,REQUEST_CODE_FILTER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_FILTER&&resultCode==RESULT_OK){

            if (data.getStringExtra(constants.FILTER.GREATER_THAN)!=null){
                greaterThan=Double.valueOf(data.getStringExtra(constants.FILTER.GREATER_THAN));
            }
            if (data.getStringExtra(constants.FILTER.LESS_THAN)!=null){
                lessThan=Double.valueOf(data.getStringExtra(constants.FILTER.LESS_THAN));
            }
            if (data.getStringExtra(constants.FILTER.RELEASE_VALUE)!=null){
                releaseValue=data.getStringExtra(constants.FILTER.RELEASE_VALUE);
                releaseType=data.getStringExtra(constants.FILTER.TYPE_RELEASE);
            }
            genres=data.getStringExtra(constants.FILTER.GENRES);

            if (greaterThan!=null||lessThan!=null||releaseValue!=null||genres.length()>4) {
                filterResults();
            }
            }
    }

    private void filterResults() {
        fragment_trending.CURRENT_STATE=constants.CURRENT_STATE.CURRENT_STATE_TRENDING_FILTER;
        fragment_trending.clearRecyclerView(greaterThan,lessThan,releaseValue,releaseType,genres);
    }
}