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
    ListenableFuture<Users> getPrivilegeByOwnerInLFUser(String owner);
    @Query("SELECT id,family,name,privilege FROM users WHERE id=:owner")
    ListenableFuture<Users> getFamilyByOwnerInLFUser(String owner);
    @Query("SELECT COUNT(*) FROM users")
    ListenableFuture<Integer> checkIfTableEmpty();
    @Insert
    ListenableFuture<Long> insert(Users user);
    @Query("SELECT password FROM users WHERE id=:user")
    ListenableFuture<String> getPassByUser(String user);
}