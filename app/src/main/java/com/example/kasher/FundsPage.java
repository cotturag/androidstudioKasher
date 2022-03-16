package com.example.kasher;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FundsPage extends AppCompatActivity {
    private RecyclerView fundsRec;
    private String loggedUser="cotturag@gmail.com";
   // private String loggedUser="kissmartina0821@gmail.com";
   // private String loggedUser="fuldugo@fuldugo.hu";
    static TextView fundsPageLabel;
    Button g;
    Button m;
    Button f;


    String usr;
    static FundsViewM pr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundspage);
        fundsRec=findViewById(R.id.fundsrec);
        fundsRec.setLayoutManager(new LinearLayoutManager(this));
        fundsRec.setHasFixedSize(true);
        fundsRec.setItemAnimator(new DefaultItemAnimator());
        FundsListAdapter adapter = new FundsListAdapter();
        fundsRec.setAdapter(adapter);
        fundsPageLabel=findViewById(R.id.fundspagelabel);
        g=findViewById(R.id.g);
        m=findViewById(R.id.m);


        f=findViewById(R.id.f);

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // loggedUser="cotturag@gmail.com";
                //fundsPageLabel.setText(loggedUser);
                adapter.notifyDataSetChanged();
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedUser="kissmartina0821@gmail.com";
                fundsPageLabel.setText(loggedUser);
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedUser="fuldugo@fuldugo.hu";
                fundsPageLabel.setText(loggedUser);
            }
        });

        UsersAndPrivilegesViewM uAndPVM = new ViewModelProvider(this).get(UsersAndPrivilegesViewM.class);
/*
        Privileges p1=new Privileges("1","privát számla","W","X","");
        Privileges p2=new Privileges("2","közös számla","W","RX","");
        Privileges p3=new Privileges("3","gyermek számla","W","R","X");
        Privileges p4=new Privileges("A","privát kiadási kategória","W","X","");
        Privileges p5=new Privileges("B","közös kiadási kategória","W","RX","");
        Privileges p6=new Privileges("C","gyermek kiadási kategória","W","R","X");
        uAndPVM.createNewPrivileges(p1);
        uAndPVM.createNewPrivileges(p2);
        uAndPVM.createNewPrivileges(p3);
        uAndPVM.createNewPrivileges(p4);
        uAndPVM.createNewPrivileges(p5);
        uAndPVM.createNewPrivileges(p6);

        Users u1 = new Users("cotturag@gmail.com","cotturag@gmail.com","Szuklics Gellért","A");
        Users u2 = new Users("kissmartina0821@gmail.com","cotturag@gmail.com","Kiss Martina","P");
        Users u3 = new Users("fuldugo@fuldugo.hu","cotturag@gmail.com","füldugó","C");
        uAndPVM.createNewUsers(u1);
        uAndPVM.createNewUsers(u2);
        uAndPVM.createNewUsers(u3);
*/


        String privilege= "";
        try {
            Users privO=uAndPVM.getPrivilegesByOwner(loggedUser);
            privilege = privO.getPrivilege();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> s = new ArrayList<String>();
        s.add(loggedUser);
        s.add(privilege);

        pr = new ViewModelProvider(this,new FundsViewM.FundsViewMFactory(this.getApplication(),s)).get(FundsViewM.class);

/*

        Funds fund1=new Funds("0","cotturag@gmail.com","1",1,"0","Otp","",0);
        Funds fund2=new Funds("0","cotturag@gmail.com","2",1,"0","Unicredit","cotturag@gmail.com",0);
        Funds fund3=new Funds("0","fuldugo@fuldugo.hu","3",1,"0","gyerekszámla","",0);
        Funds fund4=new Funds("0","fuldugo@fuldugo.hu","3",1,"0","másik gyerekszámla","cotturag@gmail.com",0);
        Funds fund5=new Funds("0","cotturag@gmail.com","A",1,"0","benzin","",0);
        Funds fund6=new Funds("0","cotturag@gmail.com","B",1,"0","közös áram","cotturag@gmail.com",0);
        Funds fund7=new Funds("0","cotturag@gmail.com","B",1,"0","közös áram","kissmartina0821@gmail.com",6);
        Funds fund8=new Funds("0","fuldugo@fuldugo.hu","C",1,"0","csoki","kissmartina0821@gmail.com",0);
        Funds fund9=new Funds("0","fuldugo@fuldugo.hu","C",1,"0","csoki","cotturag@gmail.com",8);

        pr.createNew(fund1);
        pr.createNew(fund2);
        pr.createNew(fund3);
        pr.createNew(fund4);
        pr.createNew(fund5);
        pr.createNew(fund6);
        pr.createNew(fund7);
        pr.createNew(fund8);
        pr.createNew(fund9);


*/




        TransactionsViewM transactionsViewM = new ViewModelProvider(this).get(TransactionsViewM.class);













        /*

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
                //pr.plusMoney(fund,"50");
              //  pr.delete(fund);
            }
        });



        adapter.setButtonClickListener(new FundsListAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(FundsForList fund) {
                //fundsPageLabel.setText(fund.getId());


                if (fund.getOtherOwner().equals(loggedUser)){
                    ListenableFuture<Integer> pickDownFuture=pr.pickDown(fund);
                  /*  try {
                        Integer res=pickDownFuture.get();
                      //  adapter.notifyDataSetChanged();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
*/
                }
                else {
                    if (fund.getOtherOwner().equals("")||(!fund.getOtherOwner().equals("")&&fund.getHookedTo()==0)){
                        ListenableFuture<Integer> pickUpFuture = pr.pickUpFund(fund);
                       /* try {
                            Integer res=pickUpFuture.get();
                           // adapter.notifyDataSetChanged();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
*/
                    }
                }
                //adapter.onBindViewHolder();
                adapter.notifyDataSetChanged();
              //  adapter.notifyAll();












            }
        });


    }
}
