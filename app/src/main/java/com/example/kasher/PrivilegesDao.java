package com.example.kasher;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface PrivilegesDao {
    @Query("SELECT * FROM privileges")
    LiveData<List<Privileges>> getAll();
    @Insert
    ListenableFuture<Long> insert(Privileges privileges);
    @Query("SELECT COUNT(*) FROM privileges")
    ListenableFuture<Integer> checkIfTableEmpty();
}
