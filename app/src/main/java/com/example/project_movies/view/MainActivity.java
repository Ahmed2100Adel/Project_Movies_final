package com.example.project_movies.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =156 ;
    private ActivityMainBinding binding;

    //Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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
                Toast.makeText(MainActivity.this, "HEEY", Toast.LENGTH_SHORT).show();
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
}