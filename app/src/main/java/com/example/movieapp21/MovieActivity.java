package com.example.movieapp21;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = "MovieActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private FirebaseAuth mAuth3;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView titleField, descField, genreField, ratingField, yearField;
    ImageView backArrow;
    Button saveBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        mAuth3 = FirebaseAuth.getInstance();
        if(mAuth3.getCurrentUser()==null){
            Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
            Toast.makeText(this, "Please log in!", Toast.LENGTH_SHORT).show();
            getApplicationContext().startActivity(startIntent);
        }

        Log.d(TAG,"onCreate: Starting.");
        String moviecode = getIntent().getExtras().getString("moviecode");
       // Map<String, Object> note = new HashMap<>();
        //db.document("Notebook/My First Note");
        titleField = findViewById(R.id.titleTV);
        descField = findViewById(R.id.descTV);
        yearField = findViewById(R.id.yearTV);
        genreField = findViewById(R.id.genreTV);
        ratingField = findViewById(R.id.ratingTV);
        backArrow = findViewById(R.id.backArrow);
        saveBtn = findViewById(R.id.savebtn);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(startIntent);
            }
        });

        FragmentMovies fragment = new FragmentMovies();
        Bundle args = new Bundle();
        args.putString("genre", "Action");
        fragment.setArguments(args);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.newMoviesAM, fragment)
//                .addToBackStack(null)
                .commit();





        db.collection("movies").whereEqualTo("movieCode", moviecode).limit(10).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        List<DocumentSnapshot> snapshotsList = documentSnapshots.getDocuments();
                        if (snapshotsList.get(0).exists()) {
                            String title = snapshotsList.get(0).getString(KEY_TITLE);
                            String description = snapshotsList.get(0).getString(KEY_DESCRIPTION);
                            String youtubeID =  snapshotsList.get(0).getString("youtubeID");
                            String year =  snapshotsList.get(0).getString("year");
                            String genre =  snapshotsList.get(0).getString("genre");
                            String rating =  snapshotsList.get(0).getString("rating");
                            String userSaved =  snapshotsList.get(0).getString(mAuth3.getUid());
                            ArrayList<String> actors = (ArrayList<String>) snapshotsList.get(0).get("actors");
                            ArrayList<String> actorsNames = (ArrayList<String>) snapshotsList.get(0).get("actorsNames");
                            //String docid = snapshotsList.get(0).getId();
                            titleField.setText(title);
                            descField.setText(description);
                            yearField.setText(year);
                            genreField.setText(genre);
                            ratingField.setText(rating+"/10");


                            Map<String, Object> map = new HashMap<>();
                            String userId = mAuth3.getCurrentUser().getUid();
                            map.put(userId+"", "true");
                            saveBtn.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    db.collection("movies").document(documentSnapshots.getDocuments().get(0).getId()).update(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(MovieActivity.this, "Movie Saved", Toast.LENGTH_SHORT).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(MovieActivity.this, "Movie Save Failed", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }
                            });


                            FragmentCast fragment1 = new FragmentCast();
                            Bundle args1 = new Bundle();
                            args1.putStringArrayList("actors", actors);
                            args1.putStringArrayList("actorsNames", actorsNames);
                            fragment1.setArguments(args1);
                            FragmentManager manager1 = getSupportFragmentManager();
                            manager1.beginTransaction().replace(R.id.cast, fragment1)
                                    .commit();

                            YoutubeFragment fragment = new YoutubeFragment();
                            Bundle args = new Bundle();
                            args.putString("moviecode", youtubeID);
                            fragment.setArguments(args);
                            FragmentManager manager = getSupportFragmentManager();
                            manager.beginTransaction().replace(R.id.youtubePlay, fragment)
//                                    .addToBackStack(null)
                                    .commit();

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MovieActivity.this, "Error Loading!", Toast.LENGTH_SHORT).show();
                                       }
                });



    }
}
