package com.example.movieapp21;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    ArrayList<SavedModel> savedModels;
    Context context;

    public SavedAdapter(Context context, ArrayList<SavedModel> savedModels){
        this.context=context;
        this.savedModels =savedModels;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_item, parent, false);
        return new ViewHolder(view);
    }
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.imageView.setImageResource(getImageId(context, savedModels.get(position).getMovieCode()));
        holder.title.setText(savedModels.get(position).getTitle());
        holder.year.setText(savedModels.get(position).getYear());

        holder.imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent startIntent = new Intent(context, MovieActivity.class);
                String strname = savedModels.get(position).getMovieCode();
                startIntent.putExtra("moviecode", strname);
                context.startActivity(startIntent);
            }
        });
    }

    public int getItemCount(){
        return savedModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, year;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageSaved);
            title=itemView.findViewById(R.id.titleSaved);
            year=itemView.findViewById(R.id.yearSaved);
        }
    }
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

}
