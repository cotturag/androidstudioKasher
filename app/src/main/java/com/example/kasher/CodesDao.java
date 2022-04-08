package com.example.kasher;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface CodesDao {
    @Query("SELECT * FROM Codes")
    LiveData<List<Codes>> getAll();
    @Insert
    ListenableFuture<Long> insert(Codes codes);
    @Query("SELECT COUNT(*) FROM Codes")
    ListenableFuture<Integer> checkIfTableEmpty();
}
