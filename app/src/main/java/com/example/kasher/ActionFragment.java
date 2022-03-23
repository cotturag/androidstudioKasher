package com.example.kasher;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

public class ActionFragment extends Fragment {


        public static final String ARG_OBJECT = "object";
        public static final String NUMBER_OF_COLUMNS = "c";

        RecyclerView fundsRec;
        ActionsFundsListAdapter adapter;
/*
    View inflateCalendar(){
        return inflater.inflate(R.layout.actionfragment, container, false);
    }
  */
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View returnView = inflater.inflate(R.layout.calendarfragment, container, false);
            Bundle args = getArguments();

            if (args.getInt(NUMBER_OF_COLUMNS) == 4) {
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:
                        returnView = inflater.inflate(R.layout.calendarfragment, container, false);
                        break;
                    case 2:
                        returnView = inflater.inflate(R.layout.fromfragment, container, false);
                        break;
                    case 3:
                        returnView = inflater.inflate(R.layout.moneyfragment, container, false);
                        break;
                    case 4:
                        returnView = inflater.inflate(R.layout.tofragment, container, false);
                        break;
                }
            } else {
                switch (args.getInt(ARG_OBJECT)) {
                    case 1:
                        returnView = inflater.inflate(R.layout.calendarfragment, container, false);
                        break;
                    case 2:
                        returnView = inflater.inflate(R.layout.moneyfragment, container, false);
                        break;
                    case 3:
                        returnView = inflater.inflate(R.layout.tofragment, container, false);
                        break;
                }

            }
                return returnView;
        }


        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

            Bundle args = getArguments();
            if (args.getInt(NUMBER_OF_COLUMNS)==4){
                if (args.getInt(ARG_OBJECT)==2){
                    fundsRec=view.findViewById(R.id.fundsrecactionfrom);
                    fundsRec.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    fundsRec.setHasFixedSize(true);
                    fundsRec.setItemAnimator(new DefaultItemAnimator());
                    adapter = new ActionsFundsListAdapter();
                    fundsRec.setAdapter(adapter);
                }
            }else {
                if (args.getInt(ARG_OBJECT)==3){
                    fundsRec=view.findViewById(R.id.fundsrecactionto);
                    fundsRec.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    fundsRec.setHasFixedSize(true);
                    fundsRec.setItemAnimator(new DefaultItemAnimator());
                    adapter = new ActionsFundsListAdapter();
                    fundsRec.setAdapter(adapter);
                }
            }

            UsersAndPrivilegesViewM uAndPVM = new ViewModelProvider(this).get(UsersAndPrivilegesViewM.class);
            String privilege= "";
            String loggedUser="cotturag@gmail.com"; //TODO ezt majd el kell kérni
            try {
                Users privO=uAndPVM.getPrivilegesByOwner(loggedUser);
                privilege = privO.getPrivilege();
            } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}
            List<String> s = new ArrayList<String>();
            s.add(loggedUser);
            s.add(privilege);

            FundsViewM pr = new ViewModelProvider(this,new FundsViewM.FundsViewMFactory(this.getApplication(),s)).get(FundsViewM.class);
            pr.getActualFunds().observe(this, new Observer<List<FundsForList>>() {
                @Override
                public void onChanged(List<FundsForList> funds) {
                    adapter.submitList(funds);
                }
            });







        }
    }


