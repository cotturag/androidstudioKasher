package com.example.kasher;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface FundsDao {

    /*@Query("SELECT * FROM funds WHERE owner IN (:owners) AND type IN (:types)")
    LiveData<List<Funds>> getAll(List<String> owners,List<String> types);
  */

    @Query("SELECT id,money,owner,funds.type,activity,inactivity,funds.name,pickedup,funds.parent," +
            "privileges.type AS typeInPrivileges," +
            "privileges.name AS nameInPrivileges" +
            " FROM funds,privileges" +
            " WHERE owner IN (:owners) AND funds.type IN (:types) AND funds.type=privileges.type")
    LiveData<List<FundsForList>> getAll(List<String> owners,List<String> types);





    @Insert
    void insert(Funds fund);
    @Update
    void update(Funds fund);
    @Delete
    void delete(Funds fund);



}
