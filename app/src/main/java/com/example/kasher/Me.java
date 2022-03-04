package com.example.kasher;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;

import java.sql.Date;
import java.util.List;

public class Me {



    private static class Privileges {
        static boolean check(String userPrivilege,String accountType,String operationId,SQLiteDatabase db){
            String usPriv=null;
            switch (userPrivilege){
                case "A":usPriv="admin";
                break;
                case "P":usPriv="parent";
                break;
                case "C":usPriv="child";
            }
            Cursor c = db.query("privileges",new String[]{""+usPriv+""},"type='"+accountType+"'",null,null,null,null);
            c.moveToFirst();
            String r =c.getString(0);


            int n= r.indexOf(operationId);
            boolean re=false;
            if (n!=-1) {
                    re=true;
            }
            return re;

        }
    }
    /*
    public static class FinanceObjectManager {
        public String userId;
        public String userFamily;
        public String userName;
        public String userPrivilege="A";
        FinanceObjectManager(String userId){
            //adatbázisból feltölti ezeket userId alapján
        }

        public void get(int accountId,SQLiteDatabase db){

        }
        public void create(String owner, String type, int activity, String inactivity,String name, int pickedUp,String money,SQLiteDatabase db){
            FinanceObject.createNew(owner,type,activity,inactivity,name,pickedUp,money);
            if (Privileges.check(this.userPrivilege,FinanceObject.getType(),"R",db)){
                FinanceObject.writeToDatabase();
            }
        }
        public void delete(int accountId,SQLiteDatabase db){
            FinanceObject.fillFromDataBaseById(accountId);
            if (Privileges.check(this.userPrivilege,FinanceObject.getType(),"D",db)){
                FinanceObject.delFromDatabase();
            }

        }
        public void rename(int accountId,String newName,SQLiteDatabase db){
            FinanceObject.fillFromDataBaseById(accountId);
            if (Privileges.check(this.userPrivilege,FinanceObject.getType(),"R",db)){
                FinanceObject.setName(newName);
                FinanceObject.updateDatabase();
            }


        }
        public void pickUp(int accountId,SQLiteDatabase db){
            FinanceObject.fillFromDataBaseById(accountId);
            if (Privileges.check(this.userPrivilege,FinanceObject.getType(),"G",db)){
                FinanceObject.setPickedUp(1);
                FinanceObject.updateDatabase();
            }

        }
        public void pickDown(int accountId,SQLiteDatabase db){
            FinanceObject.fillFromDataBaseById(accountId);
            if (Privileges.check(this.userPrivilege,FinanceObject.getType(),"P",db)){
                FinanceObject.setPickedUp(0);
                FinanceObject.updateDatabase();
            }

        }

*/
    }










