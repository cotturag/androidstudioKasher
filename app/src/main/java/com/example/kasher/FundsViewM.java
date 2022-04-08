package com.example.kasher;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FundsViewM extends AndroidViewModel {
    private FundsRepo repo;
    private String owner;
    private static String loggedUser;

    public static String getLoggedUser() {
        return loggedUser;
    }
    public void setLoggedUser(String user){
        this.loggedUser=user;
    }

    private LiveData<List<FundsForList>> actualFunds;
    private LiveData<List<FundsForList>> accounts;
    private LiveData<List<FundsForList>> costCategories;

    public FundsViewM(@NonNull Application app,List<String> owner) {
        super(app);
        this.owner=owner.get(0);
        loggedUser=this.owner;
        repo= new FundsRepo(app,this.owner,owner.get(1));
        actualFunds=repo.getActualFunds();
        accounts =repo.getAccounts();
        costCategories=repo.getCostCategories();
    }

    public void delete(FundsForList fund){
        repo.delete(fund);
    }
    public ListenableFuture<Long> createNew(Funds fund){
        return repo.insert(fund);
    }

    public void pickUpFund(FundsForList fund,String family) throws ExecutionException, InterruptedException {
        if (fund.getOtherOwner().equals("")){
            fund.setOtherOwner(owner);
            repo.update(fund);
           // repo.updateRemote(fund);
        }
        else {
            fund.setOtherOwner(owner);
            fund.setHookedTo(fund.getId());
            fund.setId(0);

            Long location=repo.insert(fund).get().longValue();


            String stringLocation=String.valueOf(location);
            int intLocation=Integer.valueOf(stringLocation);
            fund.setId(intLocation);
          //  repo.insertRemote(fund,family);
        }
    }
    public void pickDown(Funds fund,String family) throws ExecutionException, InterruptedException {
        if (fund.getHookedTo()==0){
            fund.setOtherOwner("");
            repo.update(fund);
          //  repo.updateRemote(fund);
        }
        else {
            repo.delete(fund);
           // repo.deleteRemote(fund,family);
        }
    }

    public void setMoney(FundsForList fund,String money){
         fund.setMoney(money);
         repo.update(fund);
    }
    public void plusMoney(FundsForList fund,String money){
        int oldMoney=Integer.valueOf(fund.getMoney());
        int newMoney=oldMoney+Integer.valueOf(money);
        fund.setMoney(String.valueOf(newMoney));
        repo.update(fund);
    }
    /*public void minusMoney(Funds fund,String money){
        int oldMoney=Integer.valueOf(fund.getMoney());
        int newMoney=oldMoney-Integer.valueOf((money));
        fund.setMoney(String.valueOf(newMoney));
        repo.update(fund);
    }

     */

    public void syncFundsToServer(String family) throws ExecutionException, InterruptedException {
        repo.synchronizeToServer("deletefunds",family);
    }
    public boolean checkIfTableEmpty() throws ExecutionException, InterruptedException {
        return repo.checkIfTableEmpty();
    }


    public LiveData<List<FundsForList>> getActualFunds() {
        return this.actualFunds;
    }
    public LiveData<List<FundsForList>> getAccounts(){return this.accounts;}
    public LiveData<List<FundsForList>> getCostCategories(){return this.costCategories;}
    public List<Integer> getHookedFunds(int id) throws ExecutionException, InterruptedException {return repo.getHookedFundById(id);}


    public static class FundsViewMFactory implements ViewModelProvider.Factory {
        private Application mApplication;
        private List<String> mParam;

        public FundsViewMFactory(Application application, List<String> param) {
            mApplication = application;
            mParam = param;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new FundsViewM(mApplication, mParam);
        }

    }
}
