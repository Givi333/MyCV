package com.example.mycv;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.mycv.db.AppDatabase;
import com.example.mycv.db.MyResponse;
import com.example.mycv.general.GeneralViewModel;
import com.example.mycv.interfaces.ActivityAction;

import org.w3c.dom.Text;

public  class ContactsFragment extends Fragment {
    private ActivityAction activityAction;
    private GeneralViewModel viewModel;
    private TextView contactsPhone;
    private TextView contactsEmail;

    public static ContactsFragment newInstance(){
        return new ContactsFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        activityAction.setTopBarTitle(getString(R.string.menu_contacts_label));
        initUi(view);
        return view;
    }

    private void initObserves() {
        viewModel.result.observe(this, new Observer<MyResponse>() {
            @Override
            public void onChanged(MyResponse myResponse) {
                setInfo(myResponse);
                activityAction.showPreloader(false);
            }
        });
    }

    private void initUi(View view) {
        contactsPhone = view.findViewById(R.id.contacts_phone_value);
        contactsEmail = view.findViewById(R.id.contacts_email_value);
    }


    private void setInfo(MyResponse myResponse){
        contactsPhone.setText("+38 066 300 31 43");
        contactsEmail.setText("s.khevoyan@gmail.com");
    }
}
