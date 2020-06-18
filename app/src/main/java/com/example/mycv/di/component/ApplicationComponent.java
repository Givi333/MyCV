package com.example.mycv.di.component;

import android.app.Application;
import android.content.Context;

import com.example.mycv.CvActivity;
import com.example.mycv.db.App;
import com.example.mycv.di.ApplicationContext;
import com.example.mycv.di.DatabaseInfo;
import com.example.mycv.di.module.ApplicationModule;
import com.example.mycv.di.module.DatabaseModule;
import com.example.mycv.general.GeneralViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class,
})
public interface ApplicationComponent {
    void inject (App app);
    void inject (CvActivity cvActivity);
    void inject (GeneralViewModel generalViewModel);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    @DatabaseInfo
    String getDatabaseName();
}
