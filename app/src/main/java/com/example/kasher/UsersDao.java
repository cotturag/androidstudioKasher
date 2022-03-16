package com.example.kasher;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;


import java.util.List;

@Dao
public interface UsersDao {
    @Query("SELECT * FROM users")
    LiveData<List<Users>> getAll();
    @Query("SELECT * FROM users WHERE id=:owner")
    ListenableFuture<Users> getPrivilegeByOwner(String owner);
    @Query("SELECT COUNT(*) FROM users")
    ListenableFuture<Integer> checkIfTableEmpty();
    @Insert
    ListenableFuture<Long> insert(Users user);
}