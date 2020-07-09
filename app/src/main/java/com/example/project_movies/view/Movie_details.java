package com.example.project_movies.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.project_movies.R;
import com.example.project_movies.constants.constants;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.Models.Movie_2;
import com.example.project_movies.viewModel.view_model_favorite;
import com.example.project_movies.viewModel.view_mode_Movie2 ;
import com.example.project_movies.databinding.ActivityMovieDetailsBinding;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class Movie_details extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    String netflixUrl=null;
    boolean favorite=false;

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
                        binding.wholeBackground1.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });



        view_mode_Movie2 viewModel= ViewModelProviders.of(this).get(view_mode_Movie2.class);

        viewModel.getMovie2(id).observe(this, new Observer<Movie_2>() {
            @Override
            public void onChanged(Movie_2 movie_2) {
                if (movie_2.isAdult()){
                    binding.forAdults.setVisibility(View.VISIBLE);
                }
                binding.overView.setText(movie_2.getOverview());
                binding.genres.setText(movie_2.getGenres());
                netflixUrl=movie_2.getHomepage();
                if (movie_2.getHomepage().contains("netflix.com")){
                    binding.netflix.setVisibility(View.VISIBLE);

                }else{
                    binding.netflix.setVisibility(View.GONE);
                    movie_2.setHomepage(null);
                }

            }
        });

        view_model_favorite viewModelFavorits=ViewModelProviders.of(this).get(view_model_favorite.class);
        viewModelFavorits.getPagedLiveData().observe(this, new Observer<List<Movie_1>>() {
            @Override
            public void onChanged(List<Movie_1> favorite_movies) {

            }
        });

        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorite){
                    viewModelFavorits.deleteAtId(Integer.valueOf(id));
                }else{
                    Long tsLong = System.currentTimeMillis()/1000;
                    Movie_1 movie= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong);
                    viewModelFavorits.insert(movie);
                }

            }
        });
        view_model_favorite ViewModelFav= ViewModelProviders.of(this).get(view_model_favorite.class);
        ViewModelFav.getMoviesAtId(Integer.valueOf(id)).observe(this, new Observer<List<Movie_1>>() {
            @Override
            public void onChanged(List<Movie_1> movie_1s) {
                if (movie_1s.size()>0){

                    binding.favorite.setImageResource(R.drawable.icon_favorite_on);
                    favorite=true;
                }else{
                    binding.favorite.setImageResource(R.drawable.icon_favorite_not);
                    favorite=false;
                }
            }
        });
        binding.netflix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent netflix = new Intent();
                netflix.setAction(Intent.ACTION_VIEW);
                netflix.setData(Uri.parse(netflixUrl));
                netflix.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(netflix);
            }
        });

    }
}