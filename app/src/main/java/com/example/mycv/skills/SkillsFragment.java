package com.example.mycv.skills;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycv.R;
import com.example.mycv.db.MyResponse;
import com.example.mycv.general.GeneralViewModel;
import com.example.mycv.interfaces.ActivityAction;

public class SkillsFragment extends Fragment {
    private ActivityAction activityAction;
    private GeneralViewModel viewModel;
    private TextView languagesValue;
    private TextView databasesValue;
    private TextView networkValue;
    private TextView otherValue;
    public static SkillsFragment newInstance(){
        return new SkillsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityAction = (ActivityAction)context;
        activityAction.showPreloader(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(GeneralViewModel.class);
        initObserves();
        viewModel.getResponseFromBase();
    }

    private void initObserves() {
        viewModel.result.observe(this, new Observer<MyResponse>() {
            @Override
            public void onChanged(MyResponse myResponse) {
                setInfo(myResponse);
                activityAction.showPreloader(false);
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

    private void setInfo(MyResponse myResponse) {
        languagesValue.setText(myResponse.getSkills().getLanguages());
        databasesValue.setText(myResponse.getSkills().getDatabases());
        networkValue.setText(myResponse.getSkills().getNetwork());
        otherValue.setText(myResponse.getSkills().getOther());

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skills, container, false);
        activityAction.setTopBarTitle(getString(R.string.menu_skills_label));
        initUi(view);
        return view;
    }

    private void initUi(View view) {
        languagesValue = view.findViewById(R.id.languages_value);
        databasesValue = view.findViewById(R.id.databases_value);
        networkValue = view.findViewById(R.id.network_value);
        otherValue = view.findViewById(R.id.other_value);
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.clear();
    }


}
