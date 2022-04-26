package com.example.kasher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FundsPage extends AppCompatActivity {
    private RecyclerView fundsRec;
    private String loggedUser;
    static FundsViewM pr;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundspage);
        pref=this.getSharedPreferences("action", Context.MODE_PRIVATE);
        loggedUser=pref.getString("loggedowner","");
        fundsRec=findViewById(R.id.fundsrec);
        fundsRec.setLayoutManager(new LinearLayoutManager(this));
        fundsRec.setHasFixedSize(true);
        fundsRec.setItemAnimator(new DefaultItemAnimator());
        FundsListAdapter adapter = new FundsListAdapter();
        fundsRec.setAdapter(adapter);
    //    fundsPageLabel=findViewById(R.id.fundspagelabel);
      //  fundsPageLabelTwo=findViewById(R.id.fundspagelabeltwo);


        UsersAndCodesViewM uAndPVM = new ViewModelProvider(this).get(UsersAndCodesViewM.class);


        String privilege= "";
        try {
            Users privO=uAndPVM.getPrivilegesByOwner(loggedUser);
            privilege = privO.getPrivilege();
        } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}
        List<String> s = new ArrayList<String>();
        s.add(loggedUser);
        s.add(privilege);

        pr = new ViewModelProvider(this,new FundsViewM.FundsViewMFactory(this.getApplication(),s)).get(FundsViewM.class);

/*
        TransactionsViewM transactionsViewM = new ViewModelProvider(this).get(TransactionsViewM.class);
        Transactions trans= new Transactions(1,"1","1",1,1,1,"1",1);
        transactionsViewM.createNew(trans);
*/

        pr.getActualFunds().observe(this, new Observer<List<FundsForList>>() {
            @Override
            public void onChanged(List<FundsForList> funds) {
                adapter.submitList(funds);
            }
        });
        adapter.setOnItemClickListener(new FundsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FundsForList fund) {

                /*try {
                    pr.insertRemote(fund,loggedUser);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                 */
            }
        });
        adapter.setButtonClickListener(new FundsListAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(FundsForList fund) {
                if (fund.getOtherOwner().equals(loggedUser)){
                    try {
                        String family= uAndPVM.getFamilyByOwnerFromUsersInString(loggedUser);
                        pr.pickDown(fund,family);
                    } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}
                }
                else {
                    if (fund.getOtherOwner().equals("")||(!fund.getOtherOwner().equals("")&&fund.getHookedTo()==0)){
                        try {
                            String family= uAndPVM.getFamilyByOwnerFromUsersInString(loggedUser);
                            pr.pickUpFund(fund,family);
                        } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
