package com.example.movieapp21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( android.view.Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Button userbtn = (Button) findViewById(R.id.userbtn);
        char[] uname = mAuth.getCurrentUser().getEmail().toCharArray();


        userbtn.setText((uname[0]+"").toUpperCase());
        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), UserActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(startIntent);
            }
        });

        ImageView playArrow = findViewById(R.id.playArrow);
        playArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MovieActivity.class);
                startIntent.putExtra("moviecode", "m103");
                getApplicationContext().startActivity(startIntent);
            }
        });

        if(mAuth.getCurrentUser()==null){
            Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
            Toast.makeText(this, "Please log in!", Toast.LENGTH_SHORT).show();
            getApplicationContext().startActivity(startIntent);
        }

        FragmentMovies fragment = new FragmentMovies();
        Bundle args = new Bundle();
         args.putString("genre", "New Movies");
         fragment.setArguments(args);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.newMovies, fragment)
//                .addToBackStack(null)
                .commit();

        FragmentMovies fragment1 = new FragmentMovies();
        Bundle args1 = new Bundle();
        args1.putString("genre", "Action");
        fragment1.setArguments(args1);
        manager.beginTransaction().replace(R.id.action, fragment1)
//                .addToBackStack(null)
                .commit();

        FragmentMovies fragment2 = new FragmentMovies();
        Bundle args2 = new Bundle();
        args2.putString("genre", "Comedy");
        fragment2.setArguments(args2);
        manager.beginTransaction().replace(R.id.comedy, fragment2)
//                .addToBackStack(null)
                .commit();

    }
}