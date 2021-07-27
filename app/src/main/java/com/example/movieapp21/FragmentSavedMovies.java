package com.example.movieapp21;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentSavedMovies extends Fragment {
    RecyclerView recyclerView;

    ArrayList<SavedModel> savedModels;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    SavedAdapter savedAdapter;

    ArrayList<String> movieCode= new ArrayList<>();
    ArrayList<String> title= new ArrayList<>();
    ArrayList<String> year= new ArrayList<>();
    String temp;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved_movies, container, false);
        String userid;
        Bundle args = getArguments();
        userid = args.getString("userid");

        db.collection("movies").whereEqualTo(userid, "true").limit(10).get()
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
                        title.add(snapshotsList.get(i).getString("title"));
                        year.add(snapshotsList.get(i).getString("year"));
                    }
                }
                recyclerView = rootView.findViewById(R.id.recycler_view_saved);




                savedModels = new ArrayList<>();
                for(int i=0; i<movieCode.size(); i++){
                    SavedModel model = new SavedModel(movieCode.get(i), title.get(i), year.get(i));
                    savedModels.add(model);
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        getContext(), LinearLayoutManager.VERTICAL, false
                );
                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());

                savedAdapter = new SavedAdapter(getContext(), savedModels);

                recyclerView.setAdapter(savedAdapter);


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