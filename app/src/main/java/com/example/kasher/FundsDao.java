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
    String queryMyPrivateFunds=" " +
            "WHERE owner= :owner " +
            "AND (funds.type='1' OR funds.type='A') " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id ";
    //a queryMyPickedupPublicAndSupervisedFundsInsertedben
    //azokat a felvett közös és felügyelt fundokat kéri,
    //ahol a felvételnél új példányt kellett beszúrni
    String queryMyPickedupPublicAndSupervisedFundsInserted =" " +
            "WHERE otherOwner= :owner " +
            "AND hookedTo>0 " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id";
    //a queryMyPickeddownPublicAndSupervisedFundsAndPickedupUpdateben
    //a leadott közös és felügyelt fundokat kéri,
    //és a felvett közös és felügyelteket,
    // ahol a felvételnél az owner lett beupdatelve az otherownerhez
    String queryMyPickeddownPublicAndSupervisedFundsAndPickedupUpdated =" " +
            "WHERE hookedTo=0" +
            " AND (funds.type='2'" +
            " OR funds.type='3'" +
            " OR funds.type='B'" +
            " OR funds.type='C')" +
            " AND (SELECT COUNT(*) FROM funds "+ queryMyPickedupPublicAndSupervisedFundsInserted +")=0" +
            " AND funds.type=privileges.type" +
            " AND funds.owner=users.id";
    String unionAll=" UNION ALL ";
    String orderBy=" ORDER BY funds.type ASC";

    @Query(querySelectFrom +
            queryMyPrivateFunds +
            unionAll+
            querySelectFrom+
            queryMyPickedupPublicAndSupervisedFundsInserted +
            unionAll+
            querySelectFrom +
            queryMyPickeddownPublicAndSupervisedFundsAndPickedupUpdated +orderBy)
    LiveData<List<FundsForList>> getAll(String owner);

    @Query(querySelectFrom+ queryMyPrivateFunds)
    LiveData<List<FundsForList>> getPrivates(String owner);

    /*
    String where=" WHERE owner= :owner ";
    String tableConnections="AND funds.type=privileges.type AND funds.owner=users.id ";


     */
    String queryMyPrivateAccounts = " " +
            "WHERE owner= :owner " +
            "AND (funds.type='1') " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id  ";
    //a queryMyPublicAccountsInsertedben azokat felvett közös számlákat kéri,
    //ahol a felvételnél új példányt kellett beszúrni
    String queryMyPublicAccountsInserted =" " +
            "WHERE otherOwner= :owner " +
            "AND hookedTo>0  " +
            "AND (funds.type='2') " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id";
    //a queryMyPublicAccountsUpdatedben azokat a felvett közös számlákat kéri,
    //ahol a felvételnél az owner lett beupdatelve az otherownerhez
    String queryMyPublicAccountsUpdated =" " +
            "WHERE hookedTo=0 " +
            "AND otherOwner= :owner " +
            "AND" +
            " (funds.type='2') " +
            "AND (SELECT COUNT(*) FROM funds "+ queryMyPublicAccountsInserted +")=0 " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id ";
    @Query(querySelectFrom+
            queryMyPrivateAccounts+
            unionAll+
            querySelectFrom+
            queryMyPublicAccountsInserted +
            unionAll+
            querySelectFrom+
            queryMyPublicAccountsUpdated +
            orderBy)
    LiveData<List<FundsForList>> getAccounts(String owner);

    String queryMyPrivateCostCategorys= " " +
            "WHERE owner= :owner " +
            "AND (funds.type='A') " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id  ";
    //a queryMypublicCostCategoriesInserted a queryMyPublicAccountsInsertedben lévő logika
    //alapján kérdez, csak költségkategóriákat
    String queryMyPublicCostCategoriesInserted =" " +
            "WHERE otherOwner= :owner " +
            "AND hookedTo>0  " +
            "AND (funds.type='B') " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id";
    //a queryMyPublicCostCategoriesUpdated a queryMyPublicAccountsUpdateben lévő logika
    //alapján kérdez, csak költségkategóriákat
    String queryMyPublicCostCategoriesUpdated =" " +
            "WHERE hookedTo=0 " +
            "AND otherOwner= :owner " +
            "AND" +
            " (funds.type='B') " +
            "AND (SELECT COUNT(*) FROM funds "+ queryMyPublicCostCategoriesInserted +")=0 " +
            "AND funds.type=privileges.type " +
            "AND funds.owner=users.id ";
    @Query(querySelectFrom+
            queryMyPrivateCostCategorys+
            unionAll+
            querySelectFrom+
            queryMyPublicCostCategoriesInserted +
            unionAll+
            querySelectFrom+
            queryMyPublicCostCategoriesUpdated +
            orderBy)
    LiveData<List<FundsForList>> getCostCategories(String owner);

    @Query("SELECT * FROM funds")
    ListenableFuture<List<Funds>> getAll();



    /*
    String queryMyPrivateCostCategorys= " WHERE owner= :owner AND (funds.type='A') AND funds.type=privileges.type AND funds.owner=users.id  ";
    String queryMySharedCostCategory=" ";
    String queryMyPublicCostCategory=" ";
    @Query(querySelectFrom+where+queryMyPrivateCostCategorys+tableConnections)
    LiveData<List<FundsForList>> getCostCategories(String owner);

*/


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
