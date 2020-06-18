package com.example.mycv.db;

import android.app.Application;

import androidx.room.Room;

import com.example.mycv.di.component.ApplicationComponent;
import com.example.mycv.di.component.DaggerApplicationComponent;
import com.example.mycv.di.module.ApplicationModule;
import com.example.mycv.di.module.DatabaseModule;

public class App extends Application {
    protected ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
