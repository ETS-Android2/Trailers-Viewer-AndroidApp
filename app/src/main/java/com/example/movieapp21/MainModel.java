package com.example.movieapp21;

public class MainModel {
    String langLogo;
    String langName;

    public MainModel(String langLogo, String langName){
        this.langLogo = langLogo;
        this.langName = langName;

    }

    public String getLangLogo(){
        return langLogo;
    }

    public  String getLangName(){
        return langName;
    }

}
