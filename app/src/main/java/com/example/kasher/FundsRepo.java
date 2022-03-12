package com.example.kasher;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FundsRepo {
    private FundsDao dao;

    private LiveData<List<FundsForList>> actualFunds;

    public FundsRepo(Application app, String owner,String privilege){
        AppDatabase db = AppDatabase.getInstance(app);
        dao=db.fundsDao();
        if (privilege.equals("C")) actualFunds= dao.getPrivates(owner);
        else actualFunds= dao.getAll(owner);
    }
    public LiveData<List<FundsForList>> getActualFunds(){
        return this.actualFunds;
    }

    public LiveData<List<FundsForList>> getActualPrivateFundsOnly(String owner){
        return dao.getPrivates(owner);
    }

    public void insert(Funds fund){
        dao.insert(fund);
    }
    public void update(Funds fund) {
        dao.update(fund);
    }
    public void delete(Funds fund){
        dao.delete(fund);
    }

/*
    private static class InsertAsyncTask extends AsyncTask<Funds,Void,Void>{
        private FundsDao dao;
        public InsertAsyncTask(FundsDao dao){this.dao=dao;}
        @Override
        protected Void doInBackground(Funds... fund){
                dao.insert(fund[0]);
        return null;
        }
    }

 */
    /*
    private static class UpdateAsyncTask extends AsyncTask<Funds,Void,Void>{
        private FundsDao dao;
        public UpdateAsyncTask(FundsDao dao){this.dao=dao;}
        @Override
        protected Void doInBackground(Funds... fund){
            dao.update(fund[0]);
            return null;
        }
    }
*/

}
