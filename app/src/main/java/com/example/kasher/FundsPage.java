package com.example.kasher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FundsPage extends AppCompatActivity {
    private RecyclerView fundsRec;
    TextView fundsPageLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundspage);
        fundsRec=findViewById(R.id.fundsrec);
        fundsRec.setLayoutManager(new LinearLayoutManager(this));
        fundsRec.setHasFixedSize(true);
        FundsListAdapter adapter = new FundsListAdapter();
        fundsRec.setAdapter(adapter);



        FundsViewM pr = new ViewModelProvider(this).get(FundsViewM.class);
        UsersAndPrivilegesViewM usersAndPrivilegesViewM = new ViewModelProvider(this).get(UsersAndPrivilegesViewM.class);
        TransactionsViewM transactionsViewM = new ViewModelProvider(this).get(TransactionsViewM.class);



/*
        Funds fund1=new Funds("0","cotturag@gmail.com","1",1,"0","Otp",0,0);
        Funds fund2=new Funds("0","cotturag@gmail.com","3",1,"0","OTP",1,0);
        Funds fund3=new Funds("0","cotturag@gmail.com","2",1,"0","Unicredit",0,0);
        Funds fund4=new Funds("0","cotturag@gmail.com","4",1,"0","UNICREDIT",1,3);
        Funds fund5=new Funds("0","fuldugo@fuldugo.hu","5",1,"0","fizikai gyermekszámla",0,0);
        Funds fund6=new Funds("0","fuldugo@fuldugo.hu","6",1,"0","virtuális gyermekszámla",1,5);
        Funds fund7=new Funds("0","cotturag@gmail.com","A",1,"0","benzin",0,0);
        Funds fund8=new Funds("0","cotturag@gmail.com","A",1,"0","rezsi",0,0);
        Funds fund9=new Funds("0","cotturag@gmail.com","B",1,"0","közös rezsi",0,0);
        Funds fund10=new Funds("0","kissmartina0821@gmail.com","1",1,"0","Otp",0,0);
        Funds fund11=new Funds("0","kissmartina0821@gmail.com","3",1,"0","OTP",1,10);
        Funds fund12=new Funds("0","kissmartina0821@gmail.com","4",1,"0","UNICREDIT",1,3);
        Funds fund13=new Funds("0","kissmartina0821@gmail.com","A",1,"0","rezsi",0,0);
        Funds fund14=new Funds("0","kissmartina0821@gmail.com","B",1,"0","közös rezsi",0,0);

        pr.createNew(fund1);
        pr.createNew(fund2);
        pr.createNew(fund3);
        pr.createNew(fund4);
        pr.createNew(fund5);
        pr.createNew(fund6);
        pr.createNew(fund7);
        pr.createNew(fund8);
        pr.createNew(fund9);
        pr.createNew(fund10);
        pr.createNew(fund11);
        pr.createNew(fund12);
        pr.createNew(fund13);
        pr.createNew(fund14);


        Privileges p1=new Privileges("1","fizikai privát számla","CDR","","");
        Privileges p2=new Privileges("2","fizikai közös számla","CDR","","");
        Privileges p3=new Privileges("3","virtuális privát számla","CDRGP","CDRGP","");
        Privileges p4=new Privileges("4","virtuális közös számla","CDRGP","GP","");
        Privileges p5=new Privileges("5","fizikai felügyelt számla","CDR","","");
        Privileges p6=new Privileges("6","virtuális felügyelt számla","CDRGP","","G");
        Privileges p7=new Privileges("A","privát kiadási kategória","CDRGP","CDRGP","G");
        Privileges p8=new Privileges("B","közös kiadási kategória","CDRGP","GP","");

        usersAndPrivilegesViewM.createNewPrivileges(p1);
        usersAndPrivilegesViewM.createNewPrivileges(p2);
        usersAndPrivilegesViewM.createNewPrivileges(p3);
        usersAndPrivilegesViewM.createNewPrivileges(p4);
        usersAndPrivilegesViewM.createNewPrivileges(p5);
        usersAndPrivilegesViewM.createNewPrivileges(p6);
        usersAndPrivilegesViewM.createNewPrivileges(p7);
        usersAndPrivilegesViewM.createNewPrivileges(p8);

        Users u1 = new Users("cotturag@gmail.com","cotturag@gmail.com","Szuklics Gellért","A");
        Users u2 = new Users("kissmartina0821@gmail.com","cotturag@gmail.com","Kiss Martina","P");
        Users u3 = new Users("fuldugo@fuldugo.hu","cotturag@gmail.com","füldugó","C");

        usersAndPrivilegesViewM.createNewUsers(u1);
        usersAndPrivilegesViewM.createNewUsers(u2);
        usersAndPrivilegesViewM.createNewUsers(u3);
*/
      /*  Transactions trans= new Transactions(1,"1","1",1,1,1,"1",1);
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
