package com.example.kasher;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@androidx.room.Dao
public interface FundsDao {



    String querySelectFrom ="" +
            "SELECT id,money,owner,otherowner,funds.type,activity,inactivity,funds.name," +
            "privileges.type AS typeInPrivileges," +
            "privileges.name AS nameInPrivileges," +
            "hookedTo " +
            "FROM funds,privileges ";
    String queryMyPrivateFunds ="WHERE owner= :owner" +
            " AND (funds.type='1' OR funds.type='A')" +
            " AND funds.type=privileges.type";
    String queryMySharedFunds ="WHERE otherOwner= :owner " +
            "AND hookedTo>0 " +
            "AND funds.type=privileges.type";
    String unionAll=" UNION ALL ";
    String queryPublicFunds="WHERE hookedTo=0" +
            " AND (funds.type='2'" +
            " OR funds.type='3'" +
            " OR funds.type='B'" +
            " OR funds.type='C') " +
            "AND (SELECT COUNT(*) FROM funds "+queryMySharedFunds+")=0" +
            " AND funds.type=privileges.type";
    @Query(querySelectFrom +
            queryMyPrivateFunds +
            unionAll+
            querySelectFrom+
            queryMySharedFunds+
            unionAll+
            querySelectFrom +
            queryPublicFunds)
    LiveData<List<FundsForList>> getAll(String owner);

    @Query(querySelectFrom+ queryMyPrivateFunds)
    LiveData<List<FundsForList>> getPrivates(String owner);

    @Query("SELECT COUNT(*) FROM funds")
    ListenableFuture<Integer> checkIfTableEmpty();








    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public ListenableFuture<Long> insert(Funds fund);

    @Update
    public ListenableFuture<Integer> update(Funds fund);

    @Delete
    public ListenableFuture<Integer> delete(Funds fund);



}
