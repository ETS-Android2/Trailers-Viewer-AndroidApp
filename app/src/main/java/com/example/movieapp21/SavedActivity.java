package com.example.movieapp21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.movieapp21.FragmentMovies;
import com.example.movieapp21.LoginActivity;
import com.example.movieapp21.R;
import com.example.movieapp21.UserActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SavedActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser()==null){
            Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
            Toast.makeText(this, "Please log in!", Toast.LENGTH_SHORT).show();
            getApplicationContext().startActivity(startIntent);
        }

        FragmentSavedMovies fragment = new FragmentSavedMovies();
        Bundle args = new Bundle();
         args.putString("userid", mAuth.getUid());
         fragment.setArguments(args);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.savedframe, fragment)
//                .addToBackStack(null)
                .commit();



    }
}