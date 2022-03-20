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
            "SELECT funds.id,funds.money,funds.owner,funds.otherowner,funds.type,funds.activity,funds.inactivity,funds.name," +
            "privileges.type AS typeInPrivileges," +
            "privileges.name AS nameInPrivileges," +
            "users.name AS ownerinusers," +
            "funds.hookedTo " +
            "FROM funds,privileges,users";
    String queryMyPrivateFunds =" WHERE owner= :owner " +
            "AND (funds.type='1' OR funds.type='A') " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id";
    String queryMySharedFunds =" WHERE otherOwner= :owner " +
            "AND hookedTo>0 " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id";
    String unionAll=" UNION ALL ";
    String queryPublicFunds=" WHERE hookedTo=0" +
            " AND (funds.type='2'" +
            " OR funds.type='3'" +
            " OR funds.type='B'" +
            " OR funds.type='C')" +
            " AND (SELECT COUNT(*) FROM funds "+queryMySharedFunds+")=0" +
            " AND funds.type=privileges.type" +
            " AND funds.owner=users.id";
    String orderBy=" ORDER BY funds.type ASC";
    @Query(querySelectFrom +
            queryMyPrivateFunds +
            unionAll+
            querySelectFrom+
            queryMySharedFunds+
            unionAll+
            querySelectFrom +
            queryPublicFunds+orderBy)
    LiveData<List<FundsForList>> getAll(String owner);

    @Query(querySelectFrom+ queryMyPrivateFunds)
    LiveData<List<FundsForList>> getPrivates(String owner);

    @Query("SELECT funds.id,users.family AS familyinusers,money,owner,otherOwner,type,activity,inactivity,funds.name,hookedTo FROM funds,users WHERE funds.owner=users.id AND funds.id=:fundId")
    ListenableFuture<FundsForRemote> getFromUsersIdExtendsFamily(int fundId);

    /*@Query("SELECT funds.id,users.family AS familyInUsers,money,owner,otherOwner,type,activity,inactivity,funds.name,hookedTo FROM funds,users WHERE funds.owner=users.id AND funds.owner=:fundOwner ")
    ListenableFuture<FundsForRemote> getFundFromUsersByOwnerExtendsFamily(String fundOwner);
*/
    @Query("SELECT COUNT(*) FROM funds")
    ListenableFuture<Integer> checkIfTableEmpty();








    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public ListenableFuture<Long> insert(Funds fund);

    @Update
    public ListenableFuture<Integer> update(Funds fund);

    @Delete
    public ListenableFuture<Integer> delete(Funds fund);



}
