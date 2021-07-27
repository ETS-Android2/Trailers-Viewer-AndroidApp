package com.example.movieapp21;

public class SavedModel {
    String movieCode;
    String title;
    String year;

    public SavedModel(String movieCode, String title, String year){
        this.movieCode = movieCode;
        this.title = title;
        this.year = year;

    }

    public String getMovieCode(){
        return movieCode;
    }

    public  String getTitle(){
        return title;
    }

    public  String getYear(){
        return year;
    }

}
