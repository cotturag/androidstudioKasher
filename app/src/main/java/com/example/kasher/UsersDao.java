package com.example.kasher;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsersDao {
    @Query("SELECT * FROM users")
    LiveData<List<Users>> getAll();
    @Query("SELECT * FROM users WHERE id=:owner")
    public List<Users> getPrivilegeByOwner(String owner);


    @Insert
    public void insert(Users user);
}