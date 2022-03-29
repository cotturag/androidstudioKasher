package com.example.kasher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class Actions extends Fragment {
        ActionFragmentStateAdapter actionFragmentStateAdapter;
        ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.actions, container, false);
    }

    @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            actionFragmentStateAdapter= new ActionFragmentStateAdapter(this);
            viewPager = view.findViewById(R.id.pager2);
            viewPager.setAdapter(actionFragmentStateAdapter);
            TabLayout tabLayout = view.findViewById(R.id.actionstab);
            int actionCode=requireArguments().getInt("actionCode");

            List<String> nameList =new ArrayList<String>();
            if (actionCode==1||actionCode==3){
                nameList.add("Dátum");
                nameList.add("Honnan");
                nameList.add("Összeg");
                nameList.add("Hova");
            }
            else {
                nameList.add("Dátum");
                nameList.add("Összeg");
                nameList.add("Hova");
            }

            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) -> tab.setText(nameList.get(position))
            ).attach();

        }

    public class ActionFragmentStateAdapter extends FragmentStateAdapter {
        private int numberOfColumns;
        private String loggedUser;
        private int actionCode;

        public void setNumberOfColumns(int numberOfColumns) {this.numberOfColumns = numberOfColumns;}
        public void setLoggedUser(String loggedUser) {this.loggedUser = loggedUser;}
        public void setActionCode(int actionCode) {this.actionCode = actionCode;}
        public int getNumberOfColumns() {return numberOfColumns;}
        public String getLoggedUser() {return loggedUser;}
        public int getActionCode() {return actionCode;}

        public ActionFragmentStateAdapter(Fragment fragment) {
            super(fragment);
            int actionCode=fragment.requireArguments().getInt("actionCode");
            if (actionCode==2) setNumberOfColumns(3); else setNumberOfColumns(4);
            setActionCode(fragment.requireArguments().getInt("actionCode"));
            setLoggedUser(fragment.requireArguments().getString("loggedUser"));
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new ActionFragment();
            Bundle args = new Bundle();
            SharedPreferences preferences = getContext().getSharedPreferences("action", Context.MODE_PRIVATE);
            args.putInt(ActionFragment.ARG_OBJECT, position + 1);
            args.putInt(ActionFragment.NUMBER_OF_COLUMNS,this.numberOfColumns);
            args.putString(ActionFragment.LOGGED_USER,getLoggedUser());
            args.putInt(ActionFragment.ACTION_CODE,getActionCode());
            fragment.setArguments(args);

            return fragment;
        }
        @Override
        public int getItemCount() {
            return getNumberOfColumns();
        }
    }

}
