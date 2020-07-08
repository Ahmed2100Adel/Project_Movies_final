package com.example.project_movies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Choreographer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
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
import com.example.project_movies.view.Fragments.recyclerView_for_all ;

import jp.wasabeef.blurry.Blurry;


public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =156 ;
    private ActivityMainBinding binding;

    //Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static  int STATE_MORE_LIST_NOT=1;
    public static  int STATE_MORE_LIST_SHOWN=2;
    public static  int STATE=STATE_MORE_LIST_NOT;

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

        recyclerView_for_all fragment_trending=new recyclerView_for_all();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(binding.recyclerViewTrending.getId(),fragment_trending)
                .commit();

        binding.iconMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.iconMore.setEnabled(false);
                Slide slide6= new Slide();
                slide6.setSlideEdge(Gravity.RIGHT);
                slide6.setDuration(700);
                ViewGroup root6=binding.moreList;
                TransitionManager.beginDelayedTransition(root6,slide6);
                binding.moreList.setVisibility(View.VISIBLE);

                Blurry.with(MainActivity.this)
                        .radius(25).
                        sampling(2)
                        .animate()
                        .onto(binding.recyclerViewTrending);
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
                binding.popular.setVisibility(View.VISIBLE);


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
                binding.highestRate.setVisibility(View.VISIBLE);


                Slide slide5= new Slide();
                slide5.setSlideEdge(Gravity.BOTTOM);

                slide5.setDuration(1200);
                ViewGroup root5=binding.root;
                TransitionManager.beginDelayedTransition(root5,slide5);
                binding.favorite.setVisibility(View.VISIBLE);



               /* binding.moreList.setAlpha(0.0f);
                // Start the animation
                binding.moreList.animate()
                        .alpha(1.0f)
                        .setListener(null);
                binding.moreList.setVisibility(View.VISIBLE);*/
                STATE=STATE_MORE_LIST_SHOWN;

            }
        });



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
                                    Log.e("main", "sendEmailVerification", task.getException());
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

            Slide slide= new Slide();
            slide.setSlideEdge(Gravity.RIGHT);
            ViewGroup root=findViewById(R.id.moreList);
            TransitionManager.beginDelayedTransition(root,slide);
            binding.moreList.setVisibility(View.GONE);




            Blurry.delete(binding.recyclerViewTrending);
            STATE=STATE_MORE_LIST_NOT;
        }else{
            finish();

        }
    }

}