package com.example.mycv.di.module;

import android.content.Context;

import androidx.room.Room;

import com.example.mycv.db.AppDatabase;
import com.example.mycv.db.CvDao;
import com.example.mycv.di.ApplicationContext;
import com.example.mycv.di.DatabaseInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @ApplicationContext
    private final Context mContext;

    @DatabaseInfo
    private final String mDBName = "mycv.db";

    public DatabaseModule(@ApplicationContext Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    AppDatabase provideDatabase() {
        return Room.databaseBuilder(
                mContext,
                AppDatabase.class,
                mDBName
        )
         .fallbackToDestructiveMigration().build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return mDBName;
    }

    @Singleton
    @Provides
    CvDao provideCvDao(AppDatabase db) {
        return db.cvDao();
    }


}

