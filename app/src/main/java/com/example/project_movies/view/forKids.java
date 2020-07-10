package com.example.project_movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import com.example.project_movies.databinding.ActivityForKidsBinding;
import com.example.project_movies.view.Fragments.recyclerView_for_all;


public class forKids extends AppCompatActivity {

    private ActivityForKidsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForKidsBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        setContentView(root);

        recyclerView_for_all fragment_for_kids=new recyclerView_for_all();
        //null meaning it came from trending activity

        fragment_for_kids.CURRENT_STATE=fragment_for_kids.CURRENT_STATE_FOR_KIDS;
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(binding.recyclerViewTrending.getId(),fragment_for_kids)
                .commit();

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}