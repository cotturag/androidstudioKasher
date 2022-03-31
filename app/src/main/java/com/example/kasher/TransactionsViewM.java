package com.example.kasher;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionsViewM extends AndroidViewModel {
    private TransactionsRepo repo;
    private LiveData<List<Transactions>> transactions;

    public TransactionsViewM(@NonNull Application app){
        super(app);
        repo=new TransactionsRepo(app);
      //  transactions=repo.getTransaction();

    }
    public void createNew(Transactions transactions){
        repo.insert(transactions);

    }

}
