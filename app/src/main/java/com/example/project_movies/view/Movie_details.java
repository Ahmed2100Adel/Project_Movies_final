package com.example.project_movies.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.project_movies.constants.constants;
import com.example.project_movies.model.Models.Movie_2;
import com.example.project_movies.viewModel.recylcer_view_model;
import com.example.project_movies.viewModel.view_mode_Movie2 ;
import com.example.project_movies.databinding.ActivityMovieDetailsBinding;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class Movie_details extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    String netflix=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        setContentView(root);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        String  title= getIntent().getStringExtra(constants.Movie_details.TITLE);
        String  release_date= getIntent().getStringExtra(constants.Movie_details.RELEASE_DATE);
        String  poster_url= getIntent().getStringExtra(constants.Movie_details.POSTER_URL);
        String  vote_average= getIntent().getStringExtra(constants.Movie_details.VOTE_AVERAGE);
        String  id= getIntent().getStringExtra(constants.Movie_details.ID);
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

        binding.textReleaseDate.setText(release_date);
        binding.texVoteAverage.setText(vote_average);

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



        view_mode_Movie2 viewModel= ViewModelProviders.of(this).get(view_mode_Movie2.class);
        Log.v("main","5");

        viewModel.getMovie2(id).observe(this, new Observer<Movie_2>() {
            @Override
            public void onChanged(Movie_2 movie_2) {
                if (!movie_2.isAdult()){
                    binding.forAdults.setVisibility(View.GONE);
                }
                binding.overView.setText(movie_2.getOverview());
                binding.genres.setText(movie_2.getGenres());
                netflix=movie_2.getHomepage();

            }
        });

        /*Intent netflix = new Intent();
        netflix.setAction(Intent.ACTION_VIEW);
        netflix.setData(Uri.parse("https://www.netflix.com/title/80238203"));
        netflix.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(netflix);*/
    }
}