package com.example.project_movies.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.project_movies.constants.constants;
import com.example.project_movies.R;
import com.example.project_movies.databinding.ActivityMovieDetailsBinding;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class Movie_details extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        setContentView(root);

        String  title= getIntent().getStringExtra(constants.Movie_details.TITLE);
        String  release_date= getIntent().getStringExtra(constants.Movie_details.RELEASE_DATE);
        String  poster_url= getIntent().getStringExtra(constants.Movie_details.POSTER_URL);
        String  vote_average= getIntent().getStringExtra(constants.Movie_details.VOTE_AVERAGE);
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();


        binding.title.setTitle(title);
        Glide.with(this)
                .load(poster_url)
                .into(binding.imagePoster);

        Glide.with(this)
                .load(poster_url)
                .apply(bitmapTransform(new BlurTransformation(50, 5)))
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        binding.wholeBackground.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}