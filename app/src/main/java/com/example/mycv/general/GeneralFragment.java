package com.example.mycv.general;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mycv.R;
import com.example.mycv.db.MyResponse;
import com.example.mycv.interfaces.ActivityAction;

import java.util.Calendar;

public class GeneralFragment extends Fragment{

    private ActivityAction activityAction;
    private GeneralViewModel viewModel;
    private ConstraintLayout generalConstraint;
    private ImageView main_photo;
    private TextView name;
    private TextView position;
    private TextView city;
    private TextView age;
    private TextView expirienceAll;
    private TextView expirienceDev;
    private TextView education;
    private TextView univercity;
    private TextView fakulty;
    private TextView educationYears;


    public static GeneralFragment newInstance() {
        return new GeneralFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityAction = (ActivityAction) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GeneralViewModel.class);
        initObserves();
        viewModel.getResponseFromBase();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);
        activityAction.setTopBarTitle(getString(R.string.menu_general_label));
        activityAction.showPreloader(true);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        generalConstraint = view.findViewById(R.id.general_constraint);
        main_photo = view.findViewById(R.id.photo);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels / 2;

        name = view.findViewById(R.id.name);
        position = view.findViewById(R.id.position);
        city = view.findViewById(R.id.city_value);
        age = view.findViewById(R.id.age_value);
        expirienceAll = view.findViewById(R.id.exp_value);
        expirienceDev = view.findViewById(R.id.exp_dev_value);
        education = view.findViewById(R.id.education_value);
        univercity = view.findViewById(R.id.university_value);
        fakulty = view.findViewById(R.id.fakulty_value);
        educationYears = view.findViewById(R.id.education_years_value);

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(width, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        main_photo.setLayoutParams(params);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(generalConstraint);
        constraintSet.connect(R.id.photo, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(R.id.photo, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.applyTo(generalConstraint);
    }

    private void setInfo(MyResponse response) {
        Glide.with(main_photo.getContext())
                .load(response.getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                       activityAction.showPreloader(false);
                        return false;
                    }
                })
                .into(main_photo)
                .waitForLayout();

        name.setText(response.getName());
        position.setText(response.getPosition());
        String expAll = getExpirience(response.getStart());
        String expDev = getExpirience(response.getDeveloperStart());

        city.setText(response.getCity());
        age.setText(response.getBirth());
        expirienceAll.setText(expAll);
        expirienceDev.setText(expDev);
        education.setText(response.getEducation().getEducationLevel());
        univercity.setText(response.getEducation().getUniversity());
        fakulty.setText(response.getEducation().getFaculty());
        educationYears.setText(response.getEducation().getEducationYears());
    }

    private String getExpirience(String yearString) {

        Calendar calendar = Calendar.getInstance();
        int currentYear =calendar.get(Calendar.YEAR);

        int expirience = currentYear - Integer.parseInt(yearString);
        char last = String.valueOf(expirience).charAt(String.valueOf(expirience).length()-1);
        String expirienceYears = "";

        if(expirience>10&&expirience<20){
            expirienceYears =getString(R.string.years1);
        }else{
            switch(last){
                case '0':
                    expirienceYears =getString(R.string.years1);
                    break;
                case '1':
                    expirienceYears =getString(R.string.years2);
                    break;
                case '2':
                    expirienceYears =getString(R.string.years3);
                    break;
                case '3':
                    expirienceYears =getString(R.string.years3);
                    break;
                case '4':
                    expirienceYears =getString(R.string.years3);
                    break;
                case '5':
                    expirienceYears =getString(R.string.years1);
                    break;
                case '6':
                    expirienceYears =getString(R.string.years1);
                    break;
                case '7':
                    expirienceYears =getString(R.string.years1);
                    break;
                case '8':
                    expirienceYears =getString(R.string.years1);
                    break;
                case '9':
                    expirienceYears =getString(R.string.years1);
                    break;
            }
        }
        return expirience + " " + expirienceYears;
    }

    private void initObserves() {
        viewModel.result.observe(this, new Observer<MyResponse>() {
            @Override
            public void onChanged(MyResponse myResponse) {
                setInfo(myResponse);
            }
        });

        viewModel.error.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean error) {
                if(error){
                    Toast.makeText(getContext(), R.string.connection_lost, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.clear();
    }
}
