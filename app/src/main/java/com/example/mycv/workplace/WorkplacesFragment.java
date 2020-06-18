package com.example.mycv.workplace;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycv.R;
import com.example.mycv.db.MyResponse;
import com.example.mycv.general.GeneralViewModel;
import com.example.mycv.interfaces.ActivityAction;

public class WorkplacesFragment extends Fragment {
    private ActivityAction activityAction;
    private RecyclerView workplacesRv;
    private GeneralViewModel viewModel;
    private WorkplaceAdapter workplaceAdapter;
    public static WorkplacesFragment newInstance(){
        return new WorkplacesFragment();
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
        workplaceAdapter = new WorkplaceAdapter();
        workplaceAdapter.setItems(myResponse.getJobs());
        workplacesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        workplacesRv.setAdapter(workplaceAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workplace, container, false);
        activityAction.setTopBarTitle(getString(R.string.menu_workplaces_label));
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        workplacesRv = view.findViewById(R.id.workplaces_rv);
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.clear();
    }
}
