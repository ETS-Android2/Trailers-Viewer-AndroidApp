package com.example.movieapp21;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentMovies extends Fragment {
    RecyclerView recyclerView;

    ArrayList<MainModel> mainModels;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    MainAdapter mainAdapter;

    ArrayList<String> movieCode= new ArrayList<>();
    String temp;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_1, container, false);
        String genre = "Action";
        Bundle args = getArguments();
        genre = args.getString("genre");

        db.collection("movies").whereEqualTo("genre", genre).limit(10).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                List<DocumentSnapshot> snapshotsList = documentSnapshots.getDocuments();

                if(snapshotsList.isEmpty()){
//                        temp="EMPTY";
                }
                else{
                    for (int i=0; i<snapshotsList.size(); i++) {
                        movieCode.add(snapshotsList.get(i).getString("movieCode"));
//                        temp = snapshot.getString("movieCode");
                    }
                }
                recyclerView = rootView.findViewById(R.id.recycler_view);


                String[] langName = {"007", "Back to the Future", "Avengers: Endgame", "Jaws", "Pulp Fiction",
                        "Star Wars", "Wonder Woman", "007", "Back to the Future", "Avengers: Endgame", "Jaws", "Pulp Fiction",
                        "Star Wars", "Wonder Woman", "007", "Back to the Future", "Avengers: Endgame", "Jaws", "Pulp Fiction",
                        "Star Wars", "Wonder Woman", "007", "Back to the Future", "Avengers: Endgame", "Jaws", "Pulp Fiction",
                        "Star Wars", "Wonder Woman"};

                mainModels = new ArrayList<>();
                for(int i=0; i<movieCode.size(); i++){
                    MainModel model = new MainModel(movieCode.get(i), langName[i]);
                    mainModels.add(model);
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        getContext(), LinearLayoutManager.HORIZONTAL, false
                );
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                mainAdapter = new MainAdapter(getContext(), mainModels);

                recyclerView.setAdapter(mainAdapter);


            }})
             .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            TextView txt = rootView.findViewById(R.id.textView2);
//                            txt.setText("FAILED");
                        }
                    });



        return rootView;
    }
}