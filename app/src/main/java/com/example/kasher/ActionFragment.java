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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

public class ActionFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    public static final String NUMBER_OF_COLUMNS = "c";
    public static final String LOGGED_USER = "loggedUser";
    public static final String ACTION_CODE = "action";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        View returnView = inflater.inflate(R.layout.calendarfragment, container, false);
        switch (args.getInt(ACTION_CODE)){
            case 1:{
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:returnView = inflater.inflate(R.layout.calendarfragment, container, false);break;
                    case 2:returnView = inflater.inflate(R.layout.fromfragment, container, false);break;
                    case 3:returnView = inflater.inflate(R.layout.moneyfragment, container, false);break;
                    case 4:returnView = inflater.inflate(R.layout.tofragment, container, false);break;
                }
            }break;
            case 2:{
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:returnView = inflater.inflate(R.layout.calendarfragment, container, false);break;
                    case 2:returnView = inflater.inflate(R.layout.moneyfragment, container, false);break;
                    case 3:returnView = inflater.inflate(R.layout.tofragment, container, false);break;
                }
            }break;
            case 3:{
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:returnView = inflater.inflate(R.layout.calendarfragment, container, false);break;
                    case 2:returnView = inflater.inflate(R.layout.fromfragment, container, false);break;
                    case 3:returnView = inflater.inflate(R.layout.moneyfragment, container, false);break;
                    case 4:returnView = inflater.inflate(R.layout.tofragment, container, false);break;
                }
            }break;
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

        switch (args.getInt(ACTION_CODE)){
            case 1:{
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:calendar(pr,view);break;
                    case 2:account(pr,view);break;
                    case 3:money(pr,view);break;
                    case 4:costCategory(pr,view);break;
                }
            }break;
            case 2:{
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:calendar(pr,view);break;
                    case 2:money(pr,view);break;
                    case 3:account(pr,view);break;
                }
            }break;
            case 3:{
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:calendar(pr,view);break;
                    case 2:account(pr,view);break;
                    case 3:money(pr,view);break;
                    case 4:account(pr,view);break;
                }
            }break;
        }
    }
    void calendar(FundsViewM pr,View view){
        SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
        TextView date;
        date=view.findViewById(R.id.calendarDate);

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentTime=Calendar.getInstance().getTime();
        String myDate=sdf.format(currentTime);

        TextView calendarResult=view.findViewById(R.id.calendarResult);
        date.setText(myDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarResult.setText(date.getText());
                SharedPreferences.Editor calendar = pref.edit();
                calendar.putString("calendar",date.getText().toString());
                calendar.apply();
            }
        });
    }
    void money(FundsViewM pr,View view){
        SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
        TextView monResult;
        EditText moneyx;
        moneyx=view.findViewById(R.id.moneyfield);
        Button moneyfieldbutton=view.findViewById(R.id.moneyfieldbutton);
        monResult=view.findViewById(R.id.monResult);
        moneyfieldbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monResult.setText(String.valueOf(moneyx.getText()));
                SharedPreferences.Editor money = pref.edit();
                money.putString("money",moneyx.getText().toString());
                money.apply();

            }
        });
    }
    void account(FundsViewM pr,View view){
        SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
        RecyclerView fundsRec;
        ActionsFundsListAdapter adapter;
        adapter = new ActionsFundsListAdapter();
        fundsRec = view.findViewById(R.id.action);
        fundsRec.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fundsRec.setHasFixedSize(true);
        fundsRec.setItemAnimator(new DefaultItemAnimator());
        fundsRec.setAdapter(adapter);
        TextView fromToResult;
        fromToResult =view.findViewById(R.id.fromToResult);

        adapter.setOnItemClickListener(new ActionsFundsListAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String frtId=String.valueOf(pr.getAccounts().getValue().get(position).getId());
                String frtName=String.valueOf(pr.getAccounts().getValue().get(position).getName());
                fromToResult.setText(frtName);
                SharedPreferences.Editor from = pref.edit();
                from.putString("from",frtId);
                from.apply();
            }
        });

        pr.getAccounts().observe(getViewLifecycleOwner(), new Observer<List<FundsForList>>() {
            @Override
            public void onChanged(List<FundsForList> funds) {
                adapter.submitList(funds);
            }
        });
    }
    void costCategory(FundsViewM pr,View view){
        SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
        RecyclerView fundsRec;
        ActionsFundsListAdapter adapter;
        adapter = new ActionsFundsListAdapter();
        fundsRec = view.findViewById(R.id.action);
        fundsRec.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fundsRec.setHasFixedSize(true);
        fundsRec.setItemAnimator(new DefaultItemAnimator());
        fundsRec.setAdapter(adapter);
        TextView fromToResult;
        fromToResult =view.findViewById(R.id.fromToResult);

        adapter.setOnItemClickListener(new ActionsFundsListAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String frtId=String.valueOf(pr.getCostCategories().getValue().get(position).getId());
                String frtName=String.valueOf(pr.getCostCategories().getValue().get(position).getName());
                fromToResult.setText(frtName);
                SharedPreferences.Editor to = pref.edit();
                to.putString("to",frtId);
                to.apply();
            }
        });
        pr.getCostCategories().observe(getViewLifecycleOwner(), new Observer<List<FundsForList>>() {
            @Override
            public void onChanged(List<FundsForList> funds) {
                adapter.submitList(funds);
            }
        });

    }
}


