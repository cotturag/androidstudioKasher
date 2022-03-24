package com.example.kasher;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

public class ActionFragment extends Fragment {


    public static final String ARG_OBJECT = "object";
    public static final String NUMBER_OF_COLUMNS = "c";
    public static final String LOGGED_USER = "loggedUser";


    RecyclerView fundsRec;
    ActionsFundsListAdapter adapter;
    TextView date;
    EditText moneyx;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {




        View returnView = inflater.inflate(R.layout.calendarfragment, container, false);
        Bundle args = getArguments();
        adapter = new ActionsFundsListAdapter();
        if (args.getInt(NUMBER_OF_COLUMNS) == 4) {
            switch (args.getInt(ARG_OBJECT)) {
                case 1:{
                        returnView = inflater.inflate(R.layout.calendarfragment, container, false);
                        date=returnView.findViewById(R.id.date);
                }
                break;
                case 2: {
                    returnView = inflater.inflate(R.layout.fromfragment, container, false);
                    fundsRec = returnView.findViewById(R.id.fundsrecactionfrom);
                    fundsRec.setLayoutManager(new LinearLayoutManager(returnView.getContext()));
                    fundsRec.setHasFixedSize(true);
                    fundsRec.setItemAnimator(new DefaultItemAnimator());
                    fundsRec.setAdapter(adapter);
                }

                break;
                case 3:{
                    returnView = inflater.inflate(R.layout.moneyfragment, container, false);
                    moneyx=returnView.findViewById(R.id.moneyfield);

                }
                break;
                case 4:
                    returnView = inflater.inflate(R.layout.tofragment, container, false);
                    fundsRec = returnView.findViewById(R.id.fundsrecactionto);
                    fundsRec.setLayoutManager(new LinearLayoutManager(returnView.getContext()));
                    fundsRec.setHasFixedSize(true);
                    fundsRec.setItemAnimator(new DefaultItemAnimator());
                    fundsRec.setAdapter(adapter);
                    break;
            }
        } else {
            switch (args.getInt(ARG_OBJECT)) {
                case 1:{
                        returnView = inflater.inflate(R.layout.calendarfragment, container, false);
                        date=returnView.findViewById(R.id.date);

                }
                    break;
                case 2:
                    returnView = inflater.inflate(R.layout.moneyfragment, container, false);
                    break;
                case 3: {
                    returnView = inflater.inflate(R.layout.tofragment, container, false);
                    fundsRec = returnView.findViewById(R.id.fundsrecactionto);
                    fundsRec.setLayoutManager(new LinearLayoutManager(returnView.getContext()));
                    fundsRec.setHasFixedSize(true);
                    fundsRec.setItemAnimator(new DefaultItemAnimator());

                    fundsRec.setAdapter(adapter);
                }
                break;
            }

        }
        return returnView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();

        UsersAndPrivilegesViewM uAndPVM = new ViewModelProvider(this).get(UsersAndPrivilegesViewM.class);
        String privilege = "";
        String loggedUser = args.getString(LOGGED_USER);
        try {
            Users privO = uAndPVM.getPrivilegesByOwner(loggedUser);
            privilege = privO.getPrivilege();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> s = new ArrayList<String>();
        s.add(loggedUser);
        s.add(privilege);

        FundsViewM pr = new ViewModelProvider(this, new FundsViewM.FundsViewMFactory(this.getActivity().getApplication(), s)).get(FundsViewM.class);

        if (args.getInt(NUMBER_OF_COLUMNS) == 4) {
            switch (args.getInt(ARG_OBJECT)) {
                case 1: {
                    Date currentTime= Calendar.getInstance().getTime();
                    date.setText(String.valueOf(currentTime));
                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
                    date.setText(pref.getString("moneyText",""));



                }
                break;
                case 2: {
                    pr.getAccounts().observe(getViewLifecycleOwner(), new Observer<List<FundsForList>>() {
                        @Override
                        public void onChanged(List<FundsForList> funds) {
                            adapter.submitList(funds);
                        }
                    });
                }
                case 3: {

                    SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
                    //pref.getString("moneyText","").toString()
                    //moneyx.setText("hányok");
                    SharedPreferences.Editor money = pref.edit();
                    //moneyText.getText().toString()
                    money.putString("moneyText","5078015zyz");
                    money.apply();



                }
                case 4: {
                    pr.getCostCategories().observe(getViewLifecycleOwner(), new Observer<List<FundsForList>>() {
                        @Override
                        public void onChanged(List<FundsForList> funds) {
                            adapter.submitList(funds);
                        }
                    });
                }
                //
            }
        } else {
            switch (args.getInt(ARG_OBJECT)) {
                case 1: {
                    Date currentTime= Calendar.getInstance().getTime();
                    date.setText(String.valueOf(currentTime));
                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                }
                break;
                case 2: {

                }
                case 3: {
                    pr.getAccounts().observe(getViewLifecycleOwner(), new Observer<List<FundsForList>>() {
                        @Override
                        public void onChanged(List<FundsForList> funds) {
                            adapter.submitList(funds);
                        }
                    });
                }
            }





        }
    }
}


