package com.example.todolistapp.viewmodel;

import android.app.Application;

import com.example.todolistapp.db.AppDatabase;

public class AppComponent extends Application {
    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this);
    }
}
