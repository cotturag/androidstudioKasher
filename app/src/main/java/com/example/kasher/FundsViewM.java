package com.example.kasher;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class FundsViewM extends AndroidViewModel {
    private FundsRepo repo;
   // private static String loggedUser="fuldugo@fuldugo.hu";
    private static String loggedUser="cotturag@gmail.com";
    private String owner;
    //private static String loggedUser="kissmartina0821@gmail.com";

    public static String getLoggedUser() {
        return loggedUser;
    }
    public void setLoggedUser(String user){
        this.loggedUser=user;
    }

    private LiveData<List<FundsForList>> actualFunds;
    public FundsViewM(@NonNull Application app){
        super(app);
        this.owner=this.loggedUser;
        repo= new FundsRepo(app,this.owner);
        actualFunds= repo.getActualFunds();
    }
    public void setOnlyPrivateFundsQuery(){
        this.actualFunds= repo.getActualPrivateFundsOnly(this.owner);
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
