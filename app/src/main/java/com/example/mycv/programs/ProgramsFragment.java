package com.example.mycv.programs;

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
import com.example.mycv.workplace.WorkplaceAdapter;

public class ProgramsFragment extends Fragment {

    private ActivityAction activityAction;
    private RecyclerView programsRv;
    private GeneralViewModel viewModel;
    private ProgramsAdapter programsAdapter;
    private ProgramAction programAction;

    public static ProgramsFragment newInstance(ProgramAction programAction) {
        ProgramsFragment fragment = new ProgramsFragment();
        fragment.programAction =programAction;
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_programs, container, false);
        activityAction.setTopBarTitle(getString(R.string.menu_programs_label));
        activityAction.showPreloader(true);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        programsRv = view.findViewById(R.id.programs_rv);
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
        programsAdapter = new ProgramsAdapter(new ProgramsAdapter.ProgramCallBack(){
            @Override
            public void clickGp(String link) {
                programAction.openMarket(link);
            }
        });
        programsAdapter.setItems(myResponse.getPrograms());
        programsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        programsRv.setAdapter(programsAdapter);


    }

    public interface ProgramAction{
        void openMarket(String link);
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.clear();
    }

}
