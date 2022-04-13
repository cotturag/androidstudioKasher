package com.example.kasher;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


import com.google.android.material.navigation.NavigationView;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    URL urlc = null;
    DrawerLayout main;
    Toolbar toolbar;
    Button costButton;
    Button incomeButton;
    Button movementButton;
   // String loggedUser ="cotturag@gmail.com";
    //String loggedUser ="kissmartina0821@gmail.com";
    //String loggedUser ="fuldugo@fuldugo.hu";
    //String loggedUser ="doroszlai@gmail.com";
    String loggedUser="";
    SharedPreferences pref;
    static FundsViewM pr;




   // boolean syncRemote=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pref=this.getSharedPreferences("action", Context.MODE_PRIVATE);
        loggedUser=pref.getString("loggedowner","");





        // MainActivity.this.deleteDatabase("kasherD");

        UsersAndPrivilegesViewM uAndPVM = new ViewModelProvider(this).get(UsersAndPrivilegesViewM.class);
        try {
            if (uAndPVM.checkIfUsersTableEmpty()){
                Users u1 = new Users("cotturag@gmail.com","cotturag@gmail.com","Szuklics Gellért","P","cotturag");
                Users u2 = new Users("kissmartina0821@gmail.com","cotturag@gmail.com","Kiss Martina","P","kissmartina0821");
                Users u3 = new Users("fuldugo@fuldugo.hu","cotturag@gmail.com","Füldugó","C","fuldugo");
                Users u4 = new Users("doroszlai@gmail.com","cotturag@gmail.com","Doroszlai László","P","doroszlai");
                uAndPVM.createNewUsers(u1);
                uAndPVM.createNewUsers(u2);
                uAndPVM.createNewUsers(u3);
                uAndPVM.createNewUsers(u4);
            }
            if (uAndPVM.checkIfPrivilegesTableEmpty()){
                Codes p1=new Codes("1","Privát számla");
                Codes p2=new Codes("2","Közös számla");
                Codes p3=new Codes("3","Gyermek számla");
                Codes p4=new Codes("A","Privát költéskategória");
                Codes p5=new Codes("B","Közös költéskategória");
                Codes p6=new Codes("C","Gyermek költéskategória");
                uAndPVM.createNewPrivileges(p1);
                uAndPVM.createNewPrivileges(p2);
                uAndPVM.createNewPrivileges(p3);
                uAndPVM.createNewPrivileges(p4);
                uAndPVM.createNewPrivileges(p5);
                uAndPVM.createNewPrivileges(p6);
            }
        } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}

        if (!loggedUser.equals("")){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);




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
                    Funds fund10=new Funds("0","kissmartina0821@gmail.com","1",1,"0","Otp","",0);
                    Funds fund11=new Funds("0","kissmartina0821@gmail.com","A",1,"0","Pipere","",0);
                    Funds fund12=new Funds("0","cotturag@gmail.com","B",1,"0","Közös áram","doroszlai@gmail.com",6);
                    Funds fund13=new Funds("0","doroszlai@gmail.com","1",1,"0","Unicredit","",0);
                    pr.createNew(fund1).get();
                    pr.createNew(fund2).get();
                    pr.createNew(fund3).get();
                    pr.createNew(fund4).get();
                    pr.createNew(fund5).get();
                    pr.createNew(fund6).get();
                    pr.createNew(fund7).get();
                    pr.createNew(fund8).get();
                    pr.createNew(fund9).get();
                    pr.createNew(fund10).get();
                    pr.createNew(fund11).get();
                    pr.createNew(fund12).get();
                    pr.createNew(fund13).get();
               /* if (syncRemote){
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
*/
                }
            } catch (ExecutionException e) {e.printStackTrace();} catch (InterruptedException e) {e.printStackTrace();}






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
//lehet hogy négy tranzakció lesz egy tranzakcióra ha a számla, és a costcategory is hookedtos
                    List<Integer> toList = new ArrayList<Integer>();
                    List<Integer> fromSourceModeList = new ArrayList<Integer>();
                    List<Integer> fromDestinationModeList = new ArrayList<Integer>();
                    toList.clear();
                    fromSourceModeList.clear();
                    fromDestinationModeList.clear();
                    try {
                        if (!to.equals("")) toList=pr.getHookedFunds(Integer.valueOf(to));
                        //      if (fromSourceMode.equals("")) fromSourceMode="0";
                        //      if (fromDestinationMode.equals("")) fromDestinationMode="0";
                        if (!fromSourceMode.equals("")) fromSourceModeList=pr.getHookedFunds(Integer.valueOf(fromSourceMode));
                        if (!fromDestinationMode.equals("")) fromDestinationModeList=pr.getHookedFunds(Integer.valueOf(fromDestinationMode));

                        if (toList.size()==0) {

                            if (!to.equals("")) toList.add(Integer.valueOf(to));
                        }
                        if (fromSourceModeList.size()==0){
                            if (!fromSourceMode.equals("")) fromSourceModeList.add(Integer.valueOf(fromSourceMode));
                        }
                        if (fromDestinationModeList.size()==0){
                            if (!fromDestinationMode.equals("")) fromDestinationModeList.add(Integer.valueOf(fromDestinationMode));
                        }



                        for (int id : fromSourceModeList){
                            System.out.println("sourcemode: "+id);
                        }

                        for (int id : fromDestinationModeList){
                            System.out.println("destinationmode: "+id);
                        }
                        for (int id : toList){
                            System.out.println("to: "+id);
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (actionCode==1){
                        if (!calendar.equals("")&&!fromSourceMode.equals("")&&!money.equals("")&&!to.equals("")){
                            Transactions transaction= new Transactions(0,calendar,money,fromSourceMode,to,details,actionCode);
                            TransactionsViewM trvm = new ViewModelProvider(MainActivity.this).get(TransactionsViewM.class);
                            try {
                                trvm.createNew(transaction);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
                            try {
                                trvm.createNew(transaction);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
                            try {
                                trvm.createNew(transaction);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
                            SharedPreferences.Editor edit = pref.edit();
                            edit.clear();
                            edit.apply();
                            finish();
                            startActivity(getIntent());
                        }
                        break;
                        case R.id.sync: {
                            try {
                                String family=uAndPVM.getFamilyByOwnerFromUsersInString(loggedUser);
                                pr.syncFundsToServer(family);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    return false;
                }
            });
            if (!loggedUser.equals("")) {
                TextView headerText = findViewById(R.id.hText);
                headerText.setText(loggedUser);
            }
     /*
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

 */
            costButton=findViewById(R.id.costButton);
            incomeButton=findViewById(R.id.incomeButton);
            movementButton=findViewById(R.id.movementButton);








            costButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cost();
                }
            });
            incomeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    income();
                }
            });
            movementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movement();
                }
            });



        }else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            pref=this.getSharedPreferences("action", Context.MODE_PRIVATE);
            Button loginButton = findViewById(R.id.loginButton);
            EditText loginUser = findViewById(R.id.loginUser);
            EditText loginPass = findViewById(R.id.loginPass);


            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor login = pref.edit();
                    try {
                        String user=loginUser.getText().toString();
                        String pass=loginPass.getText().toString();
                        System.out.println("user"+user);
                        if (uAndPVM.checkLoginCredentials(user,pass)){
                            login.putString("loggedowner",user);
                            login.apply();
                            finish();
                            startActivity(getIntent());
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

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
        RelativeLayout actionCategoryLayout = findViewById(R.id.actioncategorylayout);
        actionCategoryLayout.setBackgroundColor(0xffEAEA7F);
        FragmentContainerView actionFragmentView=findViewById(R.id.actionFragmentView);
        actionFragmentView.setBackgroundColor(0xffEAEA7F);
        RelativeLayout storeButtonHolder = findViewById(R.id.storeButtonHolder);
        storeButtonHolder.setBackgroundColor(0xffEAEA7F);
        ImageView transactionTypeImage =findViewById(R.id.transactionTypeImage);
        transactionTypeImage.setImageResource(R.drawable.cost);
        RelativeLayout actionTypeImageBack=findViewById(R.id.actionTypeImageBack);
        actionTypeImageBack.setBackgroundColor(0xffEAEA7F);





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
        RelativeLayout actionCategoryLayout = findViewById(R.id.actioncategorylayout);
        actionCategoryLayout.setBackgroundColor(0xff95D1CC);
        FragmentContainerView actionFragmentView=findViewById(R.id.actionFragmentView);
        actionFragmentView.setBackgroundColor(0xff95D1CC);
        RelativeLayout storeButtonHolder = findViewById(R.id.storeButtonHolder);
        storeButtonHolder.setBackgroundColor(0xff95D1CC);
        ImageView transactionTypeImage =findViewById(R.id.transactionTypeImage);
        transactionTypeImage.setImageResource(R.drawable.income);
        RelativeLayout actionTypeImageBack=findViewById(R.id.actionTypeImageBack);
        actionTypeImageBack.setBackgroundColor(0xff95D1CC);



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
        RelativeLayout actionCategoryLayout = findViewById(R.id.actioncategorylayout);
        actionCategoryLayout.setBackgroundColor(0xffF7CCAC);
        FragmentContainerView actionFragmentView=findViewById(R.id.actionFragmentView);
        actionFragmentView.setBackgroundColor(0xffF7CCAC);
        RelativeLayout storeButtonHolder = findViewById(R.id.storeButtonHolder);
        storeButtonHolder.setBackgroundColor(0xffF7CCAC);
        ImageView transactionTypeImage =findViewById(R.id.transactionTypeImage);
        transactionTypeImage.setImageResource(R.drawable.movement);
        RelativeLayout actionTypeImageBack=findViewById(R.id.actionTypeImageBack);
        actionTypeImageBack.setBackgroundColor(0xffF7CCAC);

    }
}


