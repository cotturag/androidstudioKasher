package com.example.kasher;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionsRepo {
    private TransactionsDao dao;
    private LiveData<List<Transactions>> transactions;
    TransactionsRepo(Application app){
        AppDatabase db =AppDatabase.getInstance(app);
        dao=db.transactionsDao();
        transactions=dao.getAll();

    }
    public LiveData<List<Transactions>> getTransaction(){return this.transactions;}
    public void insert(Transactions transactions){new InsertAsyncTask(dao).execute(transactions);
    }

    private static class InsertAsyncTask extends AsyncTask<Transactions,Void,Void> {
        private TransactionsDao dao;
        public InsertAsyncTask(TransactionsDao dao){this.dao=dao;}
        @Override
        protected Void doInBackground(Transactions... transactions){
            dao.insert(transactions[0]);
            return null;
        }
    }

}
