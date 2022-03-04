package com.example.kasher;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class FundsViewM extends AndroidViewModel {
    private FundsRepo repo;

    private LiveData<List<FundsForList>> actualFunds;
    public FundsViewM(@NonNull Application app){
        super(app);
        List<String> owners = new ArrayList<String>();
        owners.add("cotturag@gmail.com");
        owners.add("kissmartina0821@gmail.com");
        owners.add("fuldugo@fuldugo.hu");

        List<String> types = new ArrayList<String>();
        types.add("1");
        types.add("2");
        types.add("3");
        types.add("4");
        types.add("5");
        types.add("6");
        types.add("A");
        types.add("B");
        repo= new FundsRepo(app,owners,types);
        actualFunds= repo.getActualFunds();
    }
    public void createNew(Funds fund){
        repo.insert(fund);
    }
    public void setMoney(Funds fund,String money){
         fund.setMoney(money);
         repo.update(fund);
    }
    public void plusMoney(Funds fund,String money){
        int oldMoney=Integer.valueOf(fund.getMoney());
        int newMoney=oldMoney+Integer.valueOf(money);
        fund.setMoney(String.valueOf(newMoney));
        repo.update(fund);
    }
    public void minusMoney(Funds fund,String money){
        int oldMoney=Integer.valueOf(fund.getMoney());
        int newMoney=oldMoney-Integer.valueOf((money));
        fund.setMoney(String.valueOf(newMoney));
        repo.update(fund);
    }
    public LiveData<List<FundsForList>> getActualFunds() {
        return this.actualFunds;
    }
}
