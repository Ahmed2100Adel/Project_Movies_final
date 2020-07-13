package com.example.project_movies.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.example.project_movies.viewModel.adapter_articles ;

import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
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
import com.google.android.gms.common.internal.ISignInButtonCreator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


public class Movie_details extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    String netflixUrl=null;
    view_mode_Movie2 viewModel;
    boolean horiMoreIsShown=false;
    Boolean isIWantToWatch =false;
    Boolean isWatched=false;
    Boolean isFavorite=false;
    Boolean isExisted=false;
    int idDB=-1;

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

                getOmdb(movie_2.getImdbId());
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
        viewModel.getArticlesAtTile(title).observe(this, new Observer<List<article>>() {
            @Override
            public void onChanged(List<article> articles) {
                if (!articles.isEmpty()){
                    binding.textSuggestedArtices.setVisibility(View.VISIBLE);
                }
                adapter_articles adapter_articles= new adapter_articles(articles);
                binding.recyclerViewArticles.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager= new LinearLayoutManager(Movie_details.this);
                binding.recyclerViewArticles.setLayoutManager(linearLayoutManager);
                binding.recyclerViewArticles.setAdapter(adapter_articles);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recyclerViewArticles.getContext(),
                        linearLayoutManager.getOrientation());
                binding.recyclerViewArticles.addItemDecoration(dividerItemDecoration);
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
        viewModelFavorits.getFavoritePagedLiveData().observe(this, new Observer<List<Movie_1>>() {
            @Override
            public void onChanged(List<Movie_1> favorite_movies) {

            }
        });

        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExisted){
                    if (isFavorite){

                        if (!isWatched&&!isIWantToWatch&&isFavorite){
                            viewModelFavorits.deleteAtId(Integer.valueOf(id));
                            isFavorite=false;
                            isExisted=false;

                        }else{
                            Long tsLong = System.currentTimeMillis()/1000;
                            Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,false,isIWantToWatch,isWatched);
                            movie1.setIdFav(idDB);
                            viewModelFavorits.update(movie1);
                            isFavorite=false;

                        }
                    }else{
                        Long tsLong = System.currentTimeMillis()/1000;
                        Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,true,isIWantToWatch,isWatched);
                        movie1.setIdFav(idDB);
                        viewModelFavorits.update(movie1);
                        isFavorite=true;

                    }
                }
                else{
                    Long tsLong = System.currentTimeMillis()/1000;
                    Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,true,isIWantToWatch,isFavorite);
                    viewModelFavorits.insert(movie1);
                    isFavorite=true;
                    isExisted=true;

                }


            }
        });
        view_model_favorite ViewModelFav= ViewModelProviders.of(this).get(view_model_favorite.class);
        ViewModelFav.getMoviesAtId(Integer.valueOf(id)).observe(this, new Observer<List<Movie_1>>() {
            @Override
            public void onChanged(List<Movie_1> movie_1s) {
                if (movie_1s.size()>0){
                    isExisted=true;

                    idDB=movie_1s.get(0).getIdFav();

                    Movie_1 movie1=movie_1s.get(0);
                    if (movie1.getIWantToWatch()){
                        isIWantToWatch=true;
                        binding.addToIWantToWatch.setImageResource(R.drawable.ic_action_add_to_list_done);
                    }else{

                        binding.addToIWantToWatch.setImageResource(R.drawable.ic_action_add_to_list);

                    }


                    if (movie1.getWatched()){

                        isWatched=true;
                        binding.addToWatched.setImageResource(R.drawable.ic_action_watched_done);
                    }else{

                        binding.addToWatched.setImageResource(R.drawable.ic_action_done);

                    }

                    if (movie1.getFavorite()){

                        isFavorite=true;
                        binding.favorite.setImageResource(R.drawable.icon_favorite_on);
                    }else{

                        binding.favorite.setImageResource(R.drawable.icon_favorite_not);

                    }
                }else{
                    binding.favorite.setImageResource(R.drawable.icon_favorite_not);
                    binding.addToIWantToWatch.setImageResource(R.drawable.ic_action_add_to_list);
                    binding.addToWatched.setImageResource(R.drawable.ic_action_done);

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


        binding.horizontalMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!horiMoreIsShown){
                    if (isIWantToWatch){
                        binding.addToIWantToWatch.setImageResource(R.drawable.ic_action_add_to_list_done);
                    }else{
                        binding.addToIWantToWatch.setImageResource(R.drawable.ic_action_add_to_list);

                    }

                    if (isWatched){
                        binding.addToWatched.setImageResource(R.drawable.ic_action_watched_done);
                    }else{
                        binding.addToWatched.setImageResource(R.drawable.ic_action_done);

                    }
                    Slide slide= new Slide();
                    slide.setSlideEdge(Gravity.LEFT);
                    slide.setDuration(700);
                    ViewGroup root=binding.parentIWantToWatch;
                    TransitionManager.beginDelayedTransition(root,slide);
                    binding.addToIWantToWatch.setVisibility(View.VISIBLE);

                    Slide slide2= new Slide();
                    slide2.setSlideEdge(Gravity.LEFT);
                    slide2.setDuration(700);
                    ViewGroup root2=binding.parentWatched;
                    TransitionManager.beginDelayedTransition(root2,slide2);
                    binding.addToWatched.setVisibility(View.VISIBLE);


                    binding.textIWantTowatched.setVisibility(View.VISIBLE);
                    binding.textWatched.setVisibility(View.VISIBLE);




                    Animation animation = new AlphaAnimation(1, 1); // Change alpha from fully visible to invisible
                    animation.setDuration(500); // duration - half a second
                    animation.setInterpolator(new LinearInterpolator());
                    //Use this animation where ever you want to use
                    binding.horizontalMore.startAnimation(animation);
                    binding.horizontalMore.setImageResource(R.drawable.faboptions_ic_close);

                    horiMoreIsShown=true;
                }else{

                    Slide slide= new Slide();
                    slide.setSlideEdge(Gravity.RIGHT);
                    slide.setDuration(700);
                    ViewGroup root=binding.parentIWantToWatch;
                    TransitionManager.beginDelayedTransition(root,slide);
                    binding.addToIWantToWatch.setVisibility(View.GONE);

                    Slide slide2= new Slide();
                    slide2.setSlideEdge(Gravity.RIGHT);
                    slide2.setDuration(700);
                    ViewGroup root2=binding.parentWatched;
                    TransitionManager.beginDelayedTransition(root2,slide2);
                    binding.addToWatched.setVisibility(View.GONE);

                    binding.textIWantTowatched.setVisibility(View.GONE);
                    binding.textWatched.setVisibility(View.GONE);

                    Animation animation = new AlphaAnimation(1, 1); // Change alpha from fully visible to invisible
                    animation.setDuration(500); // duration - half a second
                    animation.setInterpolator(new LinearInterpolator());
                    //Use this animation where ever you want to use
                    binding.horizontalMore.startAnimation(animation);
                    binding.horizontalMore.setImageResource(R.drawable.ic_action_more_horizental);

                    horiMoreIsShown=false;

                }
            }
        });


        binding.addToIWantToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExisted){
                    Log.v("main","21");
                    if (isIWantToWatch){
                        Log.v("main","20");

                        if (!isWatched&&isIWantToWatch&&!isFavorite){
                            viewModelFavorits.deleteAtId(Integer.valueOf(id));
                            isIWantToWatch=false;
                            isExisted=false;
                            Log.v("main","23");

                        }else{
                            Long tsLong = System.currentTimeMillis()/1000;
                            Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,false,isWatched);
                            movie1.setIdFav(idDB);
                            viewModelFavorits.update(movie1);
                            isIWantToWatch=false;
                            Log.v("main","24");

                        }
                    }else{
                        Long tsLong = System.currentTimeMillis()/1000;
                        Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,true,isWatched);
                        movie1.setIdFav(idDB);
                        viewModelFavorits.update(movie1);
                        isIWantToWatch=true;
                        Log.v("main","25");

                    }
                }
                else{
                    Long tsLong = System.currentTimeMillis()/1000;
                    Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,true,isWatched);
                    viewModelFavorits.insert(movie1);
                    isIWantToWatch=true;
                    isExisted=true;
                    Log.v("main","26");

                }


            }
        });


        binding.addToWatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExisted){
                    Log.v("main","21");
                    if (isWatched){
                        Log.v("main","20");

                        if (isWatched&&!isIWantToWatch&&!isFavorite){
                            viewModelFavorits.deleteAtId(Integer.valueOf(id));
                            isWatched=false;
                            isExisted=false;
                            Log.v("main","23");

                        }else{
                            Long tsLong = System.currentTimeMillis()/1000;
                            Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,isIWantToWatch,false);
                            movie1.setIdFav(idDB);
                            viewModelFavorits.update(movie1);
                            isWatched=false;
                            Log.v("main","24");

                        }
                    }else{
                        Long tsLong = System.currentTimeMillis()/1000;
                        Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,isIWantToWatch,true);
                        movie1.setIdFav(idDB);
                        viewModelFavorits.update(movie1);
                        isWatched=true;
                        Log.v("main","25");

                    }
                }
                else{
                    Long tsLong = System.currentTimeMillis()/1000;
                    Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,isIWantToWatch,true);
                    viewModelFavorits.insert(movie1);
                    isWatched=true;
                    isExisted=true;
                    Log.v("main","26");

                }


            }

        });
    }

    public void addToDB( view_model_favorite viewModelFavorits,Boolean isExisted,Boolean isWatched,Boolean isIWantToWatch,Boolean isFavorite,String id,String vote_average,String title,
                        String release_date,String poster_url
    ){
        if (isExisted){
            Log.v("main","21");
            if (isWatched){
                Log.v("main","20");

                if (isWatched&&!isIWantToWatch&&!isFavorite){
                    viewModelFavorits.deleteAtId(Integer.valueOf(id));
                    isWatched=false;
                    isExisted=false;
                    Log.v("main","23");

                }else{
                    Long tsLong = System.currentTimeMillis()/1000;
                    Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,isIWantToWatch,false);
                    movie1.setIdFav(idDB);
                    viewModelFavorits.update(movie1);
                    isWatched=false;
                    Log.v("main","24");

                }
            }else{
                Long tsLong = System.currentTimeMillis()/1000;
                Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,isIWantToWatch,true);
                movie1.setIdFav(idDB);
                viewModelFavorits.update(movie1);
                isWatched=true;
                Log.v("main","25");

            }
        }
        else{
            Long tsLong = System.currentTimeMillis()/1000;
            Movie_1 movie1= new Movie_1(Integer.valueOf(id),Double.valueOf(vote_average),title,release_date,poster_url,tsLong,isFavorite,isIWantToWatch,true);
            viewModelFavorits.insert(movie1);
            isWatched=true;
            isExisted=true;
            Log.v("main","26");

        }


    }

    //set text to differenct views like author,director,..
    private void getOmdb(String imdbId){
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
        //binding.speedDial.inflate(R.menu.menu);



    }

    //sett margens for all views
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    //set TextView of actor and director and writer with see more in collaboration of library ReadMoreOptions
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


    //determines what is is the rate of that move if it's R or G...
    private void setImageRate(String imageRate){
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