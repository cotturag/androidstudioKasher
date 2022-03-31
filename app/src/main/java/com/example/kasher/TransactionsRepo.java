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
    //public LiveData<List<Transactions>> getTransaction(){return this.transactions;}
    public void insert(Transactions transaction){
        dao.insert(transaction);
    }
    public void insertRemote(){

    }



}
