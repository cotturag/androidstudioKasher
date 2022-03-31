package com.example.kasher;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface TransactionsDao {
    @Query("SELECT * FROM transactions")
    LiveData<List<Transactions>> getAll();
    @Insert
    ListenableFuture<Long> insert(Transactions transaction);
}
