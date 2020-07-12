package com.example.project_movies.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.example.project_movies.viewModel.adapter_articles ;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.devs.readmoreoption.ReadMoreOption;
import com.example.project_movies.R;
import com.example.project_movies.constants.constants;
import com.example.project_movies.model.Models.Movie_1;
import com.example.project_movies.model.Models.Movie_2;
import com.example.project_movies.model.Models.Movie_Omdb;
import com.example.project_movies.model.Models.article;
import com.example.project_movies.viewModel.view_model_favorite;
import com.example.project_movies.viewModel.view_mode_Movie2 ;
import com.example.project_movies.databinding.ActivityMovieDetailsBinding;

import java.net.URI;
import java.util.List;

import jp.wasabeef.blurry.Blurry;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class Movie_details extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    String netflixUrl=null;
    boolean favorite=false;
    private String imdbId=null;
    view_mode_Movie2 viewModel;
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



        viewModel= ViewModelProviders.of(this).get(view_mode_Movie2.class);

        viewModel.getMovie2(id).observe(this, new Observer<Movie_2>() {
            @Override
            public void onChanged(Movie_2 movie_2) {
                Log.v("main","1");

                //Toast.makeText(Movie_details.this, movie_2.getImdbId(), Toast.LENGTH_SHORT).show();
                getOmdb(movie_2.getImdbId());
                /*if (movie_2.isAdult()){
                    binding.forAdults.setVisibility(View.VISIBLE);
                }*/
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
        Log.v("main","15");
        viewModel.getArticlesAtTile(title).observe(this, new Observer<List<article>>() {
            @Override
            public void onChanged(List<article> articles) {
                if (!articles.isEmpty()){
                    binding.textSuggestedArtices.setVisibility(View.VISIBLE);
                }
                adapter_articles adapter_articles= new adapter_articles(articles);
                binding.recyclerViewArticles.setHasFixedSize(true);
                binding.recyclerViewArticles.setLayoutManager(new LinearLayoutManager(Movie_details.this));
                binding.recyclerViewArticles.setAdapter(adapter_articles);
                adapter_articles.setOnClick(new adapter_articles.OnClick() {
                    @Override
                    public void OnItemClickListener(String url) {
                        Intent NYtimes = new Intent();
                        NYtimes.setAction(Intent.ACTION_VIEW);
                        NYtimes.setData(Uri.parse(url));
                        NYtimes.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(NYtimes);
                    }
                });
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

    private void getOmdb(String imdbId){
        Log.v("main",imdbId);
        viewModel.getMovieOmdb(imdbId).observe(this, new Observer<Movie_Omdb>() {
            @Override
            public void onChanged(Movie_Omdb movie_omdb) {
                if (movie_omdb.getRottenTomatosRate()!=null){
                    binding.layoutRottenTomatos.setVisibility(View.VISIBLE);
                    binding.texVoteAverageRotten.setText(movie_omdb.getRottenTomatosRate());
                }
                binding.actorsProgressBar.setVisibility(View.GONE);
                if (movie_omdb.getActors()!=null){
                    settingTextView(binding.actors,movie_omdb.getFullActors());
                }
                if (movie_omdb.getWriter()!=null){
                    settingTextView(binding.writes,movie_omdb.getFullWriter());
                }
                if (movie_omdb.getDirector()!=null){
                    settingTextView(binding.directors,movie_omdb.getFullDirector());

                }
                setImageRate(movie_omdb.getRated());
                if (movie_omdb.getMetacriticRate()!=null){
                    binding.layoutMetaCritic.setVisibility(View.VISIBLE);
                    binding.textVotemetaCritic.setText(movie_omdb.getMetacriticRate());
                }

                if (movie_omdb.getFullCountryAndLang()!=null){
                    binding.countyLang.setText(movie_omdb.getFullCountryAndLang());
                }

                if (movie_omdb.getRuntime()!=null){
                    binding.runtime.setText(movie_omdb.getRuntime());
                }

                if (movie_omdb.getFullBoxOfficeAndProduction()!=null){
                    binding.productionBoxOffice.setText(movie_omdb.getFullBoxOfficeAndProduction());
                }else{
                    binding.productionBoxOffice.setVisibility(View.GONE);
                }
                if (movie_omdb.getAwards()!=null){
                    if (movie_omdb.getAwards().contains("N/A")){
                        binding.awards.setVisibility(View.GONE);
                    }else{
                        binding.awards.setText(movie_omdb.getAwards());

                    }
                }

                if (movie_omdb.getFullBoxOfficeAndProduction()==null&&movie_omdb.getFullCountryAndLang()!=null){

                    setMargins(binding.countyLang,8,8,8,60);
                }


            }
        });
    }
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
    private void settingTextView(TextView textView,String text){
        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(Movie_details.this)
                .textLength(1, ReadMoreOption.TYPE_LINE) // OR
                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel("see more")
                .lessLabel("see less")
                .moreLabelColor(ContextCompat.getColor(Movie_details.this,R.color.gold))
                .lessLabelColor(ContextCompat.getColor(Movie_details.this,R.color.gold))
                .expandAnimation(true)
                .build();

        readMoreOption.addReadMoreTo(textView,text);
    }

    private void setImageRate(String imageRate){
        Log.v("main",imageRate);
        Log.v("main","imageRate");
        if (imageRate.contains("G")){
            binding.movieRating.setImageResource(R.drawable.movie_rating_g);
        }else if (imageRate.contains("PG")){
            binding.movieRating.setImageResource(R.drawable.movie_rating_pg);
        }else if (imageRate.contains("PG-13")){
            binding.movieRating.setImageResource(R.drawable.movie_rating_pg13);
        }else if (imageRate.contains("R")){
            binding.movieRating.setImageResource(R.drawable.movie_rating_r);
        }else if (imageRate.contains("NC-17")){
            binding.movieRating.setImageResource(R.drawable.movie_rating_unrelated);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}