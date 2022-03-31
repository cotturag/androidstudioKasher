package com.example.kasher;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.navigation.NavigationView;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    URL urlc = null;
    DrawerLayout main;
    Toolbar toolbar;
    ImageButton imageButtonLeft;
    ImageButton imageButtonCenter;
    ImageButton imageButtonRight;
    String loggedUser ="cotturag@gmail.com";
    SharedPreferences pref;
    static FundsViewM pr;
    boolean syncRemote=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        pref=this.getSharedPreferences("action", Context.MODE_PRIVATE);

        // MainActivity.this.deleteDatabase("kasherD");
        UsersAndPrivilegesViewM uAndPVM = new ViewModelProvider(this).get(UsersAndPrivilegesViewM.class);
        try {
            if (uAndPVM.checkIfUsersTableEmpty()){
                Users u1 = new Users("cotturag@gmail.com","cotturag@gmail.com","Szuklics Gellért","A");
                Users u2 = new Users("kissmartina0821@gmail.com","cotturag@gmail.com","Kiss Martina","P");
                Users u3 = new Users("fuldugo@fuldugo.hu","cotturag@gmail.com","Füldugó","C");
                uAndPVM.createNewUsers(u1);
                uAndPVM.createNewUsers(u2);
                uAndPVM.createNewUsers(u3);
            }
            if (uAndPVM.checkIfPrivilegesTableEmpty()){
                Privileges p1=new Privileges("1","Privát számla","W","X","");
                Privileges p2=new Privileges("2","Közös számla","W","RX","");
                Privileges p3=new Privileges("3","Gyermek számla","W","R","X");
                Privileges p4=new Privileges("A","Privát költéskategória","W","X","");
                Privileges p5=new Privileges("B","Közös költéskategória","W","RX","");
                Privileges p6=new Privileges("C","Gyermek költéskategória","W","R","X");
                uAndPVM.createNewPrivileges(p1);
                uAndPVM.createNewPrivileges(p2);
                uAndPVM.createNewPrivileges(p3);
                uAndPVM.createNewPrivileges(p4);
                uAndPVM.createNewPrivileges(p5);
                uAndPVM.createNewPrivileges(p6);
            }
        } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}

        String privilege= "";
        try {
            Users privO=uAndPVM.getPrivilegesByOwner(loggedUser);
            privilege = privO.getPrivilege();
        } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}
        List<String> s = new ArrayList<String>();
        s.add(loggedUser);
        s.add(privilege);

        pr = new ViewModelProvider(this,new FundsViewM.FundsViewMFactory(this.getApplication(),s)).get(FundsViewM.class);
        try {
            if (pr.checkIfTableEmpty()){
                Funds fund1=new Funds("0","cotturag@gmail.com","1",1,"0","Otp","",0);
                Funds fund2=new Funds("0","cotturag@gmail.com","2",1,"0","Unicredit","cotturag@gmail.com",0);
                Funds fund3=new Funds("0","fuldugo@fuldugo.hu","3",1,"0","Otp junior","",0);
                Funds fund4=new Funds("0","fuldugo@fuldugo.hu","3",1,"0","Otp junior","cotturag@gmail.com",0);
                Funds fund5=new Funds("0","cotturag@gmail.com","A",1,"0","Benzin","",0);
                Funds fund6=new Funds("0","cotturag@gmail.com","B",1,"0","Közös áram","cotturag@gmail.com",0);
                Funds fund7=new Funds("0","cotturag@gmail.com","B",1,"0","Közös áram","kissmartina0821@gmail.com",6);
                Funds fund8=new Funds("0","fuldugo@fuldugo.hu","C",1,"0","Csoki","kissmartina0821@gmail.com",0);
                Funds fund9=new Funds("0","fuldugo@fuldugo.hu","C",1,"0","Csoki","cotturag@gmail.com",8);
                pr.createNew(fund1).get();
                pr.createNew(fund2).get();
                pr.createNew(fund3).get();
                pr.createNew(fund4).get();
                pr.createNew(fund5).get();
                pr.createNew(fund6).get();
                pr.createNew(fund7).get();
                pr.createNew(fund8).get();
                pr.createNew(fund9).get();
                if (syncRemote){
                    String family=null;
                    try {
                        family=uAndPVM.getFamilyByOwnerFromUsersInString(loggedUser);
                        pr.syncFundsToServer(family);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}


        imageButtonLeft=findViewById(R.id.imageButtonLeft);
        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState == null) {
                    cost();
                }
            }
        });

        imageButtonCenter=findViewById(R.id.imageButtonCenter);
        imageButtonCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState == null) {
                    income();
                }
            }
        });

        imageButtonRight=findViewById(R.id.imageButtonRight);
        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savedInstanceState == null) {
                   movement();
                }
            }
        });
        cost();

        Button store = findViewById(R.id.store);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calendar=pref.getString("calendar","");
                String fromSourceMode=pref.getString("fromsourcemode","");
                String fromDestinationMode=pref.getString("fromdestinationmode","");
                String money=pref.getString("money","");
                String to=pref.getString("to","");
                String details=pref.getString("details","");
                int actionCode=pref.getInt("actionCode",1);

                if (actionCode==1){
                    if (!calendar.equals("")&&!fromSourceMode.equals("")&&!money.equals("")&&!to.equals("")){
                        Transactions transaction= new Transactions(0,calendar,money,fromSourceMode,to,details,actionCode);
                        TransactionsViewM trvm = new ViewModelProvider(MainActivity.this).get(TransactionsViewM.class);
                        trvm.createNew(transaction);
                        SharedPreferences.Editor preferences = pref.edit();
                        preferences.clear();
                        preferences.apply();
                        cost();
                        Toast toast = Toast.makeText(getApplication(), "Az adatok rögzítve", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        Toast toast = Toast.makeText(getApplication(), "Nincs minden kitöltve", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                if (actionCode==2){
                    if (!calendar.equals("")&&!money.equals("")&&!fromDestinationMode.equals("")){
                        Transactions transaction= new Transactions(0,calendar,money,fromSourceMode,fromDestinationMode,details,actionCode);
                        TransactionsViewM trvm = new ViewModelProvider(MainActivity.this).get(TransactionsViewM.class);
                        trvm.createNew(transaction);
                        SharedPreferences.Editor preferences = pref.edit();
                        preferences.clear();
                        preferences.apply();
                        income();
                        Toast toast = Toast.makeText(getApplication(), "Az adatok rögzítve", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        Toast toast = Toast.makeText(getApplication(), "Nincs minden kitöltve", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                if (actionCode==3){
                    if (!calendar.equals("")&&!fromSourceMode.equals("")&&!fromDestinationMode.equals("")&&!money.equals("")){
                            Transactions transaction= new Transactions(0,calendar,money,fromSourceMode,fromDestinationMode,details,actionCode);
                            TransactionsViewM trvm = new ViewModelProvider(MainActivity.this).get(TransactionsViewM.class);
                            trvm.createNew(transaction);
                            SharedPreferences.Editor preferences = pref.edit();
                            preferences.clear();
                            preferences.apply();
                            movement();
                            Toast toast = Toast.makeText(getApplication(), "Az adatok rögzítve", Toast.LENGTH_SHORT);
                            toast.show();
                    }
                    else{
                        Toast toast = Toast.makeText(getApplication(), "Nincs minden kitöltve", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });






        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        main = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, main, toolbar, R.string.open, R.string.close);
        abdt.syncState();
        NavigationView navw = findViewById(R.id.nav);

        navw.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.funds: {
                        Intent funds = new Intent(MainActivity.this, FundsPage.class);
                        startActivity(funds);
                    }
                    break;
                    case R.id.actions: {
                        Intent actionsPage = new Intent(MainActivity.this, ActionsPage.class);
                        startActivity(actionsPage);
                    }
                    break;
                }
                return false;
            }
        });


    Button syn=findViewById(R.id.syn);
    syn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String family=null;
        try {
            family=uAndPVM.getFamilyByOwnerFromUsersInString(loggedUser);
            pr.syncFundsToServer(family);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
});
    }
    void cost(){
        SharedPreferences.Editor actionCode = pref.edit();
        actionCode.putInt("actionCode",1);
        actionCode.apply();
        Bundle bundle = new Bundle();
        bundle.putInt("actionCode",1);
        bundle.putString("loggedUser", loggedUser);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.actionFragmentView, Actions.class,bundle)
                .commit();
        TextView actionCategory=findViewById(R.id.actioncategory);
        actionCategory.setText("Kiadás");
        RelativeLayout actionCategoryLayout = findViewById(R.id.actioncategorylayout);
        actionCategoryLayout.setBackgroundColor(0xffece4db);
        FragmentContainerView actionFragmentView=findViewById(R.id.actionFragmentView);
        actionFragmentView.setBackgroundColor(0xffece4db);
        RelativeLayout storeButtonHolder = findViewById(R.id.storeButtonHolder);
        storeButtonHolder.setBackgroundColor(0xffece4db);




    }


    void income(){
        SharedPreferences.Editor actionCode = pref.edit();
        actionCode.putInt("actionCode",2);
        actionCode.apply();
        Bundle bundle = new Bundle();
        bundle.putInt("actionCode",2);
        bundle.putString("loggedUser", loggedUser);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.actionFragmentView, Actions.class, new Bundle(bundle))
                .commit();
        TextView actionCategory=findViewById(R.id.actioncategory);
        actionCategory.setText("Bevétel");
        RelativeLayout actionCategoryLayout = findViewById(R.id.actioncategorylayout);
        actionCategoryLayout.setBackgroundColor(0xfff9c74f);
        FragmentContainerView actionFragmentView=findViewById(R.id.actionFragmentView);
        actionFragmentView.setBackgroundColor(0xfff9c74f);
        RelativeLayout storeButtonHolder = findViewById(R.id.storeButtonHolder);
        storeButtonHolder.setBackgroundColor(0xfff9c74f);


    }
    void movement(){
        SharedPreferences.Editor actionCode = pref.edit();
        actionCode.putInt("actionCode",3);
        actionCode.apply();
        Bundle bundle = new Bundle();
        bundle.putInt("actionCode",3);
        bundle.putString("loggedUser", loggedUser);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.actionFragmentView, Actions.class,bundle)
                .commit();
        TextView actionCategory=findViewById(R.id.actioncategory);
        actionCategory.setText("Pénzmozgás");
        RelativeLayout actionCategoryLayout = findViewById(R.id.actioncategorylayout);
        actionCategoryLayout.setBackgroundColor(0xffdab6fc);
        FragmentContainerView actionFragmentView=findViewById(R.id.actionFragmentView);
        actionFragmentView.setBackgroundColor(0xffdab6fc);
        RelativeLayout storeButtonHolder = findViewById(R.id.storeButtonHolder);
        storeButtonHolder.setBackgroundColor(0xffdab6fc);

    }
}


