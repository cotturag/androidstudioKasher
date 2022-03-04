package com.example.kasher;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FundsRepo {
    private FundsDao dao;

    private LiveData<List<FundsForList>> actualFunds;

    public FundsRepo(Application app, List<String> owners, List<String> types){
        AppDatabase db = AppDatabase.getInstance(app);
        dao=db.fundsDao();
        actualFunds= dao.getAll(owners,types);
    }
    public LiveData<List<FundsForList>> getActualFunds(){
        return this.actualFunds;
    }

    public void insert(Funds fund){
        new InsertAsyncTask(dao).execute(fund);
    }
    public void update(Funds fund) {new UpdateAsyncTask(dao).execute(fund);}


    private static class InsertAsyncTask extends AsyncTask<Funds,Void,Void>{
        private FundsDao dao;
        public InsertAsyncTask(FundsDao dao){this.dao=dao;}
        @Override
        protected Void doInBackground(Funds... fund){
                dao.insert(fund[0]);
        return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Funds,Void,Void>{
        private FundsDao dao;
        public UpdateAsyncTask(FundsDao dao){this.dao=dao;}
        @Override
        protected Void doInBackground(Funds... fund){
            dao.update(fund[0]);
            return null;
        }
    }


}
