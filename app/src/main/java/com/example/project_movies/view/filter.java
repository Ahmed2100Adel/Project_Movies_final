package com.example.project_movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.project_movies.R;
import com.example.project_movies.constants.constants;
import com.example.project_movies.databinding.ActivityFilterBinding;
import com.google.android.material.snackbar.Snackbar;

public class filter extends AppCompatActivity {

    private ActivityFilterBinding binding;
    private Boolean isAction=false;
    private Boolean isFantasy=false;
    private Boolean isDrama=false;
    private Boolean isWar=false;
    private Boolean isComedy=false;
    private Boolean isRomance=false;
    private Boolean isScienceFiction=false;
    private Boolean isScienceHorrer=false;
    private Boolean isScience=false;
    private Boolean isAdventure=false;
    private Boolean isHistory=false;
    private Boolean isAnimations=false;
    private Boolean isMystery=false;
    private Boolean isFamily=false;
    private Boolean isThriller=false;
    private Boolean isCrime=false;
    private final int releasedAfterChosen=1;
    private final int releasedAtChosen=2;
    private int releasedChosen=releasedAfterChosen;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFilterBinding.inflate(getLayoutInflater());
        View root= binding.getRoot();
        setContentView(root);


        binding.action.setOnClickListener(v -> {
            if (isAction){
                isAction=false;
                binding.action.setBackgroundResource(R.drawable.black_panther);
            }else{
                isAction=true;
                binding.action.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.fantasy.setOnClickListener(v -> {
            if (isFantasy){
                isFantasy=false;
                binding.fantasy.setBackgroundResource(R.drawable.lord_of_the_rings);
            }else{
                isFantasy=true;
                binding.fantasy.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.drama.setOnClickListener(v -> {
            if (isDrama){
                isDrama=false;
                binding.drama.setBackgroundResource(R.drawable.irish_man_drama);
            }else{
                isDrama=true;
                binding.drama.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.war.setOnClickListener(v -> {
            if (isWar){
                isWar=false;
                binding.war.setBackgroundResource(R.drawable.war_1917);
            }else{
                isWar=true;
                binding.war.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.comedy.setOnClickListener(v -> {
            if (isComedy){
                isComedy=false;
                binding.comedy.setBackgroundResource(R.drawable.friends_comdy);
            }else{
                isComedy=true;
                binding.comedy.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });
        binding.romance.setOnClickListener(v -> {
            if (isRomance){
                isRomance=false;
                binding.romance.setBackgroundResource(R.drawable.lalaland_romance);
            }else{
                isRomance=true;
                binding.romance.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.scienceFiction.setOnClickListener(v -> {
            if (isScienceFiction){
                isScienceFiction=false;
                binding.scienceFiction.setBackgroundResource(R.drawable.inceptoin_scince_fiction);
            }else{
                isScienceFiction=true;
                binding.scienceFiction.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });
        binding.scienceHorrer.setOnClickListener(v -> {
            if (isScienceHorrer){
                isScienceHorrer=false;
                binding.scienceHorrer.setBackgroundResource(R.drawable.alien_science_horror);
            }else{
                isScienceHorrer=true;
                binding.scienceHorrer.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });
        binding.science.setOnClickListener(v -> {
            if (isScience){
                isScience=false;
                binding.science.setBackgroundResource(R.drawable.intersteller_science);
            }else{
                isScience=true;
                binding.science.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.adventure.setOnClickListener(v -> {
            if (isAdventure){
                isAdventure=false;
                binding.adventure.setBackgroundResource(R.drawable.wonder_women_adventure);
            }else{
                isAdventure=true;
                binding.adventure.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.history.setOnClickListener(v -> {
            if (isHistory){
                isHistory=false;
                binding.history.setBackgroundResource(R.drawable.dunkiri_history);
            }else{
                isHistory=true;
                binding.history.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.animations.setOnClickListener(v -> {
            if (isAnimations){
                isHistory=false;
                binding.animations.setBackgroundResource(R.drawable.big_her_animations);
            }else{
                isAnimations=true;
                binding.animations.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.mystery.setOnClickListener(v -> {
            if (isMystery){
                isMystery=false;
                binding.mystery.setBackgroundResource(R.drawable.sherlok_mystery);
            }else{
                isMystery=true;
                binding.mystery.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });
        binding.family.setOnClickListener(v -> {
            if (isFamily){
                isFamily=false;
                binding.family.setBackgroundResource(R.drawable.karate_kid_family);
            }else{
                isFamily=true;
                binding.family.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });
        binding.thriller.setOnClickListener(v -> {
            if (isThriller){
                isFamily=false;
                binding.thriller.setBackgroundResource(R.drawable.dark_knigh_thriller);
            }else{
                isThriller=true;
                binding.thriller.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });
        binding.crime.setOnClickListener(v -> {
            if (isCrime){
                isCrime=false;
                binding.crime.setBackgroundResource(R.drawable.god_father_crime);
            }else{
                isCrime=true;
                binding.crime.setBackgroundColor(ContextCompat.getColor(filter.this,R.color.colorPrimary));

            }
        });

        binding.releaseAfter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                releasedChosen=releasedAfterChosen;
                binding.releaseAfter.setPaintFlags(binding.releaseAfter.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                binding.releaseAt.setPaintFlags(binding.releaseAt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                return false;
            }
        });

        binding.releaseAt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                releasedChosen=releasedAtChosen;
                binding.releaseAt.setPaintFlags(binding.releaseAt.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                binding.releaseAfter.setPaintFlags(binding.releaseAfter.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                binding.releaseAfterParent.setEnabled(false);
                return false;
            }
        });
        binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExtract_data()){
                    Intent intent= new Intent();
                    if (getGreaterThan()!=null){
                        intent.putExtra(constants.FILTER.GREATER_THAN,getGreaterThan());
                    }
                    if (getLessThan()!=null){
                        intent.putExtra(constants.FILTER.LESS_THAN,getLessThan());
                    }
                    if (getReleaseValue()!=null){
                        intent.putExtra(constants.FILTER.TYPE_RELEASE,getReleaseType());
                        intent.putExtra(constants.FILTER.RELEASE_VALUE,getReleaseValue());
                    }

                    intent.putExtra(constants.FILTER.GENRES,getGenres());

                    setResult(RESULT_OK,intent);
                    finish();
                }else{

                }
            }
        });
    }

    public String getGenres(){
        StringBuilder stringBuilderGenres= new StringBuilder();

        if (isAction){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_ACTION+",");
        }
        if (isFantasy){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_FANTASY+",");
        }
        if (isDrama){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_DRAMA+",");
        }if (isWar){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_WAR+",");
        }if (isComedy){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_COMEDY+",");
        }if (isRomance){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_ROMANCE+",");
        }if (isScienceFiction){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_SCIENCE_FICTION+",");
        }if (isScienceHorrer){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_SCIENCE_HORROR+",");
        }if (isScience){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_SCIENCE_SCIENCE+",");
        }if (isAdventure){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_ADVENTURE+",");
        }if (isHistory){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_HISTORY+",");
        }if (isAnimations){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_ANIMATIONS+",");
        }if (isMystery){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_MYSTERY+",");
        }if (isFamily){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_FAMILY+",");
        }if (isThriller){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_THRILLER+",");
        }if (isCrime){
            stringBuilderGenres.append(constants.THEMOVIEDB.GENRE_CRIME+",");
        }
        stringBuilderGenres.append(-1);
        String genres=stringBuilderGenres.toString();
        return genres;
    }

    private String getReleaseType(){
        switch (releasedChosen){
            case releasedAfterChosen:{
                return constants.FILTER.RELEASED_AFTER;
            }
            case releasedAtChosen:{
                return constants.FILTER.RELEASED_AT;

            }
        }
        return null;
    }

    private String getReleaseValue(){
        if (binding.releaseAfter.getText().toString().isEmpty()&&binding.releaseAt.getText().toString().isEmpty()){
            return null;
        }
        switch (releasedChosen){
            case releasedAfterChosen:{
                return binding.releaseAfter.getText().toString();
            }
            case releasedAtChosen:{
                return binding.releaseAt.getText().toString();

            }
        }
        return null;
    }

    private String getGreaterThan(){
        return !binding.rateGreater.getText().toString().isEmpty()?binding.rateGreater.getText().toString():null;
    }

    private String getLessThan(){
        return !binding.rateLess.getText().toString().isEmpty()?binding.rateLess.getText().toString():null;
    }

    private boolean isExtract_data(){

        if (!binding.rateGreater.getText().toString().isEmpty()&&!binding.rateLess.getText().toString().isEmpty()) {
            if (Double.parseDouble(binding.rateGreater.getText().toString()) > Double.parseDouble(binding.rateLess.getText().toString())) {

                Snackbar.make(binding.getRoot(), "filed \"greater than\" must be less than filed \"less than\"", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Change", v -> {

                                    binding.rateGreater.setFocusable(true);
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.showSoftInput(binding.rateGreater, InputMethodManager.SHOW_IMPLICIT);
                                }
                        )
                        .setActionTextColor(getResources().getColor(R.color.white))
                        .show();
                return false;

            }
        }
        if (!binding.rateGreater.getText().toString().isEmpty()){
            if (Double.parseDouble(binding.rateGreater.getText().toString())>10){
                Snackbar.make(binding.getRoot(), "The greatest value for rate is 10", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Change", v -> {

                                    binding.rateGreater.setFocusable(true);
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.showSoftInput(binding.rateGreater, InputMethodManager.SHOW_IMPLICIT);
                                }
                        )
                        .setActionTextColor(getResources().getColor(R.color.white))
                        .show();
                return false;
            }
        }
        if (!binding.rateLess.getText().toString().isEmpty()){
            if (Double.parseDouble(binding.rateLess.getText().toString())<0){
                Snackbar.make(binding.getRoot(), "The least value for rate is 0", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Change", v -> {

                                    binding.rateLess.setFocusable(true);
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethodManager.showSoftInput(binding.rateLess, InputMethodManager.SHOW_IMPLICIT);
                                }
                        )
                        .setActionTextColor(getResources().getColor(R.color.white))
                        .show();
                return false;
            }
        }

        return true;
    }



}