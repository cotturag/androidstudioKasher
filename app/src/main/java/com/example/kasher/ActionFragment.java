package com.example.kasher;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        UsersAndCodesViewM uAndPVM = new ViewModelProvider(this).get(UsersAndCodesViewM.class);
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
                    case 2:account(pr,view,"sourcemode");break;
                    case 3:money(pr,view);break;
                    case 4:costCategory(pr,view);break;
                }
            }break;
            case 2:{
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:calendar(pr,view);break;
                    case 2:money(pr,view);break;
                    case 3:account(pr,view,"destinationmode");break;
                }
            }break;
            case 3:{
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:calendar(pr,view);break;
                    case 2:account(pr,view,"sourcemode");break;
                    case 3:money(pr,view);break;
                    case 4:account(pr,view,"destinationmode");break;
                }
            }break;
        }
    }
    void calendar(FundsViewM pr,View view){
        SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
        TextView date=view.findViewById(R.id.calendarDate);
        TextView calendarResult=view.findViewById(R.id.calendarResult);

        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentTime=Calendar.getInstance().getTime();
        String myDate=sdf.format(currentTime);

        date.setText(myDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarResult.setText(date.getText());
                SharedPreferences.Editor calendar = pref.edit();
                calendar.putString("calendar",calendarResult.getText().toString());
                calendar.apply();

            }
        });
    }
    void money(FundsViewM pr,View view){
        SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
        TextView monResult=view.findViewById(R.id.monResult);
        TextView detailsResult=view.findViewById(R.id.detailsResult);
        EditText moneyx=view.findViewById(R.id.moneyfield);
        EditText details=view.findViewById(R.id.detailsfield);

        moneyx.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                moneyx.setText("");
                return false;
            }
        });
       details.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               details.setText("");
               return false;
           }
       });


        moneyx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                monResult.setText(moneyx.getText());
                SharedPreferences.Editor money = pref.edit();
                money.putString("money",moneyx.getText().toString());
                money.apply();
            }
        });
        details.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                detailsResult.setText(details.getText());
                SharedPreferences.Editor money = pref.edit();
                money.putString("details",detailsResult.getText().toString());
                money.apply();
            }
        });


    }
    void account(FundsViewM pr,View view,String mode){
        SharedPreferences pref=this.getActivity().getSharedPreferences("action", Context.MODE_PRIVATE);
        TextView fromToResult =view.findViewById(R.id.fromToResult);
        TextView hiddenIdResult=view.findViewById(R.id.hiddenIdResultt);
        RecyclerView fundsRec;
        ActionsFundsListAdapter adapter;
        adapter = new ActionsFundsListAdapter();
        fundsRec = view.findViewById(R.id.action);
        fundsRec.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fundsRec.setHasFixedSize(true);
        fundsRec.setItemAnimator(new DefaultItemAnimator());
        fundsRec.setAdapter(adapter);

        adapter.setOnItemClickListener(new ActionsFundsListAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                hiddenIdResult.setText(String.valueOf(pr.getAccounts().getValue().get(position).getId()));
                fromToResult.setText(String.valueOf(pr.getAccounts().getValue().get(position).getName()));
                SharedPreferences.Editor from = pref.edit();
                if (mode.equals("sourcemode")) from.putString("fromsourcemode",hiddenIdResult.getText().toString());
                if (mode.equals("destinationmode")) from.putString("fromdestinationmode",hiddenIdResult.getText().toString());
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
        TextView fromToResult =view.findViewById(R.id.fromToResult);
        TextView hiddenIdResult=view.findViewById(R.id.hiddenIdResultt);
        RecyclerView fundsRec;
        ActionsFundsListAdapter adapter;
        adapter = new ActionsFundsListAdapter();
        fundsRec = view.findViewById(R.id.action);
        fundsRec.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fundsRec.setHasFixedSize(true);
        fundsRec.setItemAnimator(new DefaultItemAnimator());
        fundsRec.setAdapter(adapter);


        adapter.setOnItemClickListener(new ActionsFundsListAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                hiddenIdResult.setText(String.valueOf(pr.getCostCategories().getValue().get(position).getId()));
                fromToResult.setText(String.valueOf(pr.getCostCategories().getValue().get(position).getName()));

                SharedPreferences.Editor to = pref.edit();
                to.putString("to",hiddenIdResult.getText().toString());
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


