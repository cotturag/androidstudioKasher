package com.example.kasher;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PrivilegesDao {
    @Query("SELECT * FROM privileges")
    LiveData<List<Privileges>> getAll();
    @Insert
    void insert(Privileges privileges);
}
