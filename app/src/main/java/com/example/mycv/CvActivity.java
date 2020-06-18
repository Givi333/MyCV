package com.example.mycv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mycv.db.App;
import com.example.mycv.db.AppDatabase;
import com.example.mycv.db.CvDao;
import com.example.mycv.general.GeneralFragment;
import com.example.mycv.interfaces.ActivityAction;
import com.example.mycv.programs.ProgramsFragment;
import com.example.mycv.skills.SkillsFragment;
import com.example.mycv.workplace.WorkplacesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

public class CvActivity extends AppCompatActivity implements ActivityAction{

    private ConstraintLayout activityConstraint;
    private ConstraintLayout fragmentContainer;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);
        initUI();
        setSupportActionBar(toolbar);
        showGeneral();

    }

    private void initUI() {
        activityConstraint = findViewById(R.id.activity_constraint);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        progressBar = findViewById(R.id.progress_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getTitle().equals(getString(R.string.menu_general_label))) {
                    showGeneral();
                } else if (item.getTitle().equals(getString(R.string.menu_workplaces_label))) {
                    showWorkplaces();
                } else if (item.getTitle().equals(getString(R.string.menu_programs_label))) {
                    showPrograms();
                } else if (item.getTitle().equals(getString(R.string.menu_skills_label))) {
                    showSkills();
                } else if (item.getTitle().equals(getString(R.string.menu_contacts_label))) {
                    showContacts();
                }
                return true;
            }
        });
    }


    private void showGeneral() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new GeneralFragment().newInstance()).commit();
    }

    private void showWorkplaces() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new WorkplacesFragment().newInstance()).commit();

    }

    private void showPrograms() {
        ProgramsFragment.ProgramAction programAction = new ProgramsFragment.ProgramAction() {
            @Override
            public void openMarket(String link) {
              toMarket(link);
            }
        };
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProgramsFragment().newInstance(programAction)).commit();

    }

    private void showSkills() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new SkillsFragment().newInstance()).commit();

    }


    private void showContacts() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ContactsFragment().newInstance()).commit();
    }



    @Override
    public void setTopBarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void showPreloader(boolean show) {
        if(show){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }

    }

    public void toMarket(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent
                .FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}
