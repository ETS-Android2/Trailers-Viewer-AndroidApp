package com.example.movieapp21;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FragmentCast extends Fragment {
    RecyclerView recyclerViewFC;

    ArrayList<MainModel> mainModels;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RoundAdapter mainAdapter;

    ArrayList<String> movieCode= new ArrayList<>();
    String temp;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_1, container, false);
        Bundle args = getArguments();
        ArrayList<String> actors = args.getStringArrayList("actors");
        ArrayList<String> actorsNames = args.getStringArrayList("actorsNames");
        recyclerViewFC = rootView.findViewById(R.id.recycler_view);

                        mainModels = new ArrayList<>();
                        for(int i=0; i<actors.size(); i++){
                            MainModel model = new MainModel(actors.get(i), actorsNames.get(i));
                            mainModels.add(model);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(
                                getContext(), LinearLayoutManager.HORIZONTAL, false
                        );
                        recyclerViewFC.setLayoutManager(layoutManager);
                        recyclerViewFC.setItemAnimator(new DefaultItemAnimator());

                        mainAdapter = new RoundAdapter(getContext(), mainModels);

                        recyclerViewFC.setAdapter(mainAdapter);
                        
        return rootView;
    }
}