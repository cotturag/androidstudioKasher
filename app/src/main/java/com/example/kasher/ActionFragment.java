package com.example.kasher;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
    int position;









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



                }

                break;
                case 3:{
                    returnView = inflater.inflate(R.layout.moneyfragment, container, false);


                }
                break;
                case 4: {
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
        if (args.getInt(NUMBER_OF_COLUMNS) == 3) {
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
        SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);


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
                    Button f1=view.findViewById(R.id.f1);
                    f1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor calendar = pref.edit();
                            calendar.putString("calendarText",date.getText().toString());
                            calendar.apply();
                        }
                    });





                }
                break;
                case 2: {
                    fundsRec = view.findViewById(R.id.fundsrecactionfrom);
                    fundsRec.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    fundsRec.setHasFixedSize(true);
                    fundsRec.setItemAnimator(new DefaultItemAnimator());
                    fundsRec.setAdapter(adapter);
                    TextView ize;
                    ize =view.findViewById(R.id.ize);

                    adapter.setOnItemClickListener(new ActionsFundsListAdapter.MyOnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            String izeke=String.valueOf(pr.getAccounts().getValue().get(position).getName());
                            ize.setText(izeke);
                        }
                    });

                    pr.getAccounts().observe(getViewLifecycleOwner(), new Observer<List<FundsForList>>() {
                        @Override
                        public void onChanged(List<FundsForList> funds) {
                            adapter.submitList(funds);
                        }
                    });






                    Button f2=view.findViewById(R.id.f2);
                    f2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor from = pref.edit();
                            //String g =ize.getText().toString();
                            //from.putString("fromText", g);
                            from.apply();
                        }
                    });

                }
                break;
                case 3: {
                    EditText moneyx;
                    moneyx=view.findViewById(R.id.moneyfield);

                    SharedPreferences.Editor money = pref.edit();
                    Button bu=view.findViewById(R.id.f3);

                    bu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String ge=moneyx.getText().toString();
                            money.putString("moneyText",ge);
                            money.apply();
                        }
                    });



                }
                break;
                case 4: {
                    pr.getCostCategories().observe(getViewLifecycleOwner(), new Observer<List<FundsForList>>() {
                        @Override
                        public void onChanged(List<FundsForList> funds) {
                            adapter.submitList(funds);
                        }
                    });
                }
                break;
                //
            }
        }
        if (args.getInt(NUMBER_OF_COLUMNS) == 3){
            switch (args.getInt(ARG_OBJECT)) {
                case 1: {
                    Date currentTime= Calendar.getInstance().getTime();
                  //  date.setText(String.valueOf(currentTime));
                   /* date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });*/

                }
                break;
                case 2: {

                }
                break;
                case 3: {
                    pr.getAccounts().observe(getViewLifecycleOwner(), new Observer<List<FundsForList>>() {
                        @Override
                        public void onChanged(List<FundsForList> funds) {
                            adapter.submitList(funds);
                        }
                    });
                }
                break;
            }






        }
    }
}


