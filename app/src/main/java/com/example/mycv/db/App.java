package com.example.mycv.db;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "cvBase.db").allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}