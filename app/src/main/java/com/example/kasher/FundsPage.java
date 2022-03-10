package com.example.kasher;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FundsPage extends AppCompatActivity {
    private RecyclerView fundsRec;
  //  private String loggedUser="cotturag@gmail.com";
    //private String loggedUser="kissmartina0821@gmail.com";
    private String loggedUser="fuldugo@fuldugo.hu";
    static TextView fundsPageLabel;
    Button fetch;
    String usr;
    static FundsViewM pr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundspage);
        fundsRec=findViewById(R.id.fundsrec);
        fundsRec.setLayoutManager(new LinearLayoutManager(this));
        fundsRec.setHasFixedSize(true);
        FundsListAdapter adapter = new FundsListAdapter();
        fundsRec.setAdapter(adapter);
        fundsPageLabel=findViewById(R.id.fundspagelabel);
        fetch=findViewById(R.id.fetch);





        //UsersAndPrivilegesViewM uAndPVM = new ViewModelProvider(this).get(UsersAndPrivilegesViewM.class);
        /*
        String privilege= null;
        try {
            privilege = uAndPVM.getPrivilegesByOwner(loggedUser).getPrivilege();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
        //pr = new ViewModelProvider(this).get(FundsViewM.class);

       // pr.setRepo(getApplication(),loggedUser,privilege);

       // fundsPageLabel.setText(privilege);




        TransactionsViewM transactionsViewM = new ViewModelProvider(this).get(TransactionsViewM.class);
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

        Privileges p1=new Privileges("1","privát számla","W","X","");
        Privileges p2=new Privileges("2","közös számla","W","RX","");
        Privileges p3=new Privileges("3","gyermek számla","W","R","X");
        Privileges p4=new Privileges("A","privát kiadási kategória","W","X","");
        Privileges p5=new Privileges("B","közös kiadási kategória","W","RX","");
        Privileges p6=new Privileges("C","gyermek kiadási kategória","W","R","X");
        usersAndPrivilegesViewM.createNewPrivileges(p1);
        usersAndPrivilegesViewM.createNewPrivileges(p2);
        usersAndPrivilegesViewM.createNewPrivileges(p3);
        usersAndPrivilegesViewM.createNewPrivileges(p4);
        usersAndPrivilegesViewM.createNewPrivileges(p5);
        usersAndPrivilegesViewM.createNewPrivileges(p6);

        Users u1 = new Users("cotturag@gmail.com","cotturag@gmail.com","Szuklics Gellért","A");
        Users u2 = new Users("kissmartina0821@gmail.com","cotturag@gmail.com","Kiss Martina","P");
        Users u3 = new Users("fuldugo@fuldugo.hu","cotturag@gmail.com","füldugó","C");
        usersAndPrivilegesViewM.createNewUsers(u1);
        usersAndPrivilegesViewM.createNewUsers(u2);
        usersAndPrivilegesViewM.createNewUsers(u3);



 */
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
                pr.plusMoney(fund,"50");

            }
        });


    }
}
