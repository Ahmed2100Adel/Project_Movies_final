package com.example.project_movies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.project_movies.R;
import com.example.project_movies.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.example.project_movies.view.Fragments.horizental_recyclerView_for_all;
import com.example.project_movies.view.Fragments.recyclerView_for_all;
import jp.wasabeef.blurry.Blurry;

import static com.example.project_movies.view.Fragments.horizental_recyclerView_for_all.CURRENT_STATE_FAVORITES;
import static com.example.project_movies.view.Fragments.horizental_recyclerView_for_all.CURRENT_STATE_WATCHED;
import static com.example.project_movies.view.Fragments.recyclerView_for_all.CURRENT_STATE_I_WANT_TO_WATCH;


public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =156 ;
    private ActivityMainBinding binding;

    //Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static  int STATE_MORE_LIST_NOT=1;
    public static  int STATE_MORE_LIST_SHOWN=2;
    public static  int STATE=STATE_MORE_LIST_NOT;


    public static final Integer TYPE_TRENDING=3;
    private horizental_recyclerView_for_all recyclerViewForAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();


        //Checking if user is signed in
        firebaseAuth=FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();

                if (user==null){
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build());

                    // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setTheme(R.style.LoginTheme)
                                    .build(),
                            RC_SIGN_IN);
                }else{
                    user.reload();
                    if (!user.isEmailVerified()){
                        Intent intent= new Intent(MainActivity.this,verify_to_conplete.class);
                        startActivity(intent);
                    }else if (user.isEmailVerified()){

                    }
                }
            }
        };

        setContentView(root);



        buileFragment(TYPE_TRENDING);
        binding.iconMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showMoreList();
                STATE=STATE_MORE_LIST_SHOWN;
                disableRecyclerView();
            }
        });

        binding.trendingNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOtherActivity();
                Intent intent= new Intent(MainActivity.this,trending_now.class);
                startActivity(intent);
            }
        });

        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOtherActivity();
                Intent intent= new Intent(MainActivity.this,Favorite_list.class);
                startActivity(intent);
            }
        });

        binding.forKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOtherActivity();
                Intent intent= new Intent(MainActivity.this,forKids.class);
                startActivity(intent);
            }
        });

        binding.seeMoreTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOtherActivity();
                Intent intent= new Intent(MainActivity.this,trending_now.class);
                startActivity(intent);
            }
        });

        binding.IWantToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOtherActivity();
                Intent intent= new Intent(MainActivity.this,Favorite_list.class);
                intent.setAction(String.valueOf(CURRENT_STATE_I_WANT_TO_WATCH));
                startActivity(intent);
            }
        });
        binding.watched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOtherActivity();
                Intent intent= new Intent(MainActivity.this,Favorite_list.class);
                intent.setAction(String.valueOf(recyclerView_for_all.CURRENT_STATE_WATCHED));
                startActivity(intent);
            }
        });

        Random random= new Random();
        int max=3;
        int min=1;
        int randomNumber= random.nextInt((max - min) + 1) + min;
        switch (randomNumber){
            case 1:{
                binding.textViewRandom.setText("I want to watch");
                binding.randomGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToOtherActivity();
                        Intent intent= new Intent(MainActivity.this,Favorite_list.class);
                        intent.setAction(String.valueOf(CURRENT_STATE_I_WANT_TO_WATCH));
                        startActivity(intent);
                    }
                });
                horizental_recyclerView_for_all recyclerViewRandom=  horizental_recyclerView_for_all.newInstance(3);
                recyclerViewRandom.setType(CURRENT_STATE_I_WANT_TO_WATCH);
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(binding.recyclerViewFavoriteOrOthers.getId(),recyclerViewRandom)
                        .commit();
                break;
            }
            case 2:{
                binding.textViewRandom.setText("I watched");
                binding.randomGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToOtherActivity();
                        Intent intent= new Intent(MainActivity.this,Favorite_list.class);
                        intent.setAction(String.valueOf(CURRENT_STATE_WATCHED));
                        startActivity(intent);
                    }
                });
                horizental_recyclerView_for_all recyclerViewRandom=  horizental_recyclerView_for_all.newInstance(3);
                recyclerViewRandom.setType(CURRENT_STATE_WATCHED);
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(binding.recyclerViewFavoriteOrOthers.getId(),recyclerViewRandom)
                        .commit();
                break;
            }
            case 3:{
                binding.textViewRandom.setText("Favorites");
                binding.randomGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToOtherActivity();
                        Intent intent= new Intent(MainActivity.this,Favorite_list.class);
                        intent.setAction(String.valueOf(CURRENT_STATE_FAVORITES));
                        startActivity(intent);
                    }
                });
                horizental_recyclerView_for_all recyclerViewRandom=  horizental_recyclerView_for_all.newInstance(3);
                recyclerViewRandom.setType(CURRENT_STATE_FAVORITES);
                FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(binding.recyclerViewFavoriteOrOthers.getId(),recyclerViewRandom)
                        .commit();
                break;
            }
        }

    }

    private void goToOtherActivity(){
        binding.iconMore.setEnabled(true);
        binding.recyclerViewTrending.setClickable(true);
        Slide slide= new Slide();
        slide.setSlideEdge(Gravity.RIGHT);
        ViewGroup root=findViewById(R.id.moreList);
        TransitionManager.beginDelayedTransition(root,slide);
        binding.moreList.setVisibility(View.GONE);

        enableRecyclerView();
        Blurry.delete(binding.recyclerViewTrending);
        STATE=STATE_MORE_LIST_NOT;
    }
    private void disableRecyclerView() {



    }

    private void enableRecyclerView(){

    }



    private void buileFragment(Integer type){
        recyclerViewForAll=  horizental_recyclerView_for_all.newInstance(3);
        recyclerViewForAll.setType(TYPE_TRENDING);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(binding.recyclerViewTrending.getId(),recyclerViewForAll)
                .commit();
    }
    public void showMoreList(){
        binding.iconMore.setEnabled(false);
        binding.recyclerViewTrending.setClickable(false);
        Slide slide6= new Slide();
        slide6.setSlideEdge(Gravity.RIGHT);
        slide6.setDuration(700);
        ViewGroup root6=binding.moreList;
        TransitionManager.beginDelayedTransition(root6,slide6);
        binding.moreList.setVisibility(View.VISIBLE);

        if (binding.recyclerViewTrending!=null){
            Blurry.with(MainActivity.this)
                    .radius(25).
                    sampling(2)
                    .animate()
                    .onto(binding.recyclerViewTrending);
        }

        Slide slide= new Slide();
        slide.setSlideEdge(Gravity.RIGHT);
        slide.setDuration(700);
        ViewGroup root=binding.root;
        TransitionManager.beginDelayedTransition(root,slide);
        binding.forKids.setVisibility(View.VISIBLE);


        Slide slide2= new Slide();
        slide2.setSlideEdge(Gravity.RIGHT);
        slide2.setDuration(700);
        ViewGroup root2=binding.root;
        TransitionManager.beginDelayedTransition(root2,slide2);
        binding.IWantToWatch.setVisibility(View.VISIBLE);


        Slide slide3= new Slide();
        slide3.setSlideEdge(Gravity.LEFT);
        slide3.setDuration(700);
        TransitionManager.beginDelayedTransition(root,slide3);
        binding.best2020.setVisibility(View.VISIBLE);


        Slide slide4= new Slide();
        slide4.setSlideEdge(Gravity.LEFT);
        slide4.setDuration(700);
        ViewGroup root4=binding.root;
        TransitionManager.beginDelayedTransition(root4,slide4);
        binding.watched.setVisibility(View.VISIBLE);


        Slide slide5= new Slide();
        slide5.setSlideEdge(Gravity.BOTTOM);

        slide5.setDuration(1200);
        ViewGroup root5=binding.root;
        TransitionManager.beginDelayedTransition(root5,slide5);
        binding.favorite.setVisibility(View.VISIBLE);


    }
    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.sendEmailVerification()
                        .addOnCompleteListener(this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                /* Check Success */
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Verification Email Sent To: " + user.getEmail(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Failed To Send Verification Email!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
               finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (STATE==STATE_MORE_LIST_SHOWN){

            binding.iconMore.setEnabled(true);
            binding.recyclerViewTrending.setClickable(true);
            Slide slide= new Slide();
            slide.setSlideEdge(Gravity.RIGHT);
            ViewGroup root=findViewById(R.id.moreList);
            TransitionManager.beginDelayedTransition(root,slide);
            binding.moreList.setVisibility(View.GONE);
            Blurry.delete(binding.recyclerViewTrending);
            STATE=STATE_MORE_LIST_NOT;
            enableRecyclerView();
        }else{
            finish();

        }
    }

}