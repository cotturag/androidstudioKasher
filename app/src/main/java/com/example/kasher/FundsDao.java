package com.example.kasher;

import androidx.lifecycle.LiveData;
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

    String querySelectFrom ="" +
            "SELECT id,money,owner,otherowner,funds.type,activity,inactivity,funds.name," +
            "privileges.type AS typeInPrivileges," +
            "privileges.name AS nameInPrivileges," +
            "hookedTo " +
            "FROM funds,privileges ";
    String queryPrivateFunds="WHERE owner= :owner" +
            " AND (funds.type='1' OR funds.type='A')" +
            " AND funds.type=privileges.type";
    String unionAll=" UNION ALL ";
    String queryPublicFunds="WHERE hookedTo=0" +
            " AND (funds.type='2'" +
            " OR funds.type='3'" +
            " OR funds.type='B'" +
            " OR funds.type='C')" +
            " AND funds.type=privileges.type";
    @Query(querySelectFrom +
            queryPrivateFunds +
            unionAll+
            querySelectFrom +
            queryPublicFunds)
    LiveData<List<FundsForList>> getAll(String owner);

    @Query(querySelectFrom+queryPrivateFunds)
    LiveData<List<FundsForList>> getPrivates(String owner);







    @Insert
    void insert(Funds fund);
    @Update
    void update(Funds fund);
    @Delete
    void delete(Funds fund);



}
