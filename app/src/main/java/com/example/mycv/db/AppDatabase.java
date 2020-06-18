package com.example.mycv.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyResponse.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CvDao cvDao();
}
