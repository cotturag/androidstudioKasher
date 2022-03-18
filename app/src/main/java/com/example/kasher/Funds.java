package com.example.kasher;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "funds")
public class Funds {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String money;
    private String owner;
    private String type;
    private int activity;
    private String inactivity;
    private String name;
    @ColumnInfo(name = "otherowner")
    private String otherOwner;
    @ColumnInfo(name = "hookedto")
    private int hookedTo;

    public Funds(){}
    public Funds(String money,String owner,String type,int activity,String inactivity,String name,String otherOwner,int hookedTo){
        this.money=money;
        this.owner=owner;
        this.type=type;
        this.activity=activity;
        this.inactivity=inactivity;
        this.name=name;
        this.otherOwner = otherOwner;
        this.hookedTo=hookedTo;

    }

    public void setId(int id){this.id=id;}
    public void setMoney(String money){this.money=money;}
    public void setOwner(String owner){this.owner=owner;}
    public void setType(String type){this.type=type;}
    public void setActivity(int activity){this.activity=activity;}
    public void setInactivity(String inactivity){this.inactivity=inactivity;}
    public void setName(String name){this.name=name;}
    public void setOtherOwner(String otherOwner){this.otherOwner = otherOwner;}
    public void setHookedTo(int hookedTo){this.hookedTo=hookedTo;}


    public int getId(){return this.id;}
    public String getMoney(){return this.money;}
    public String getOwner(){return this.owner;}
    public String getType(){return this.type;}
    public int getActivity(){return this.activity;}
    public String getInactivity(){return this.inactivity;}
    public String getName(){return  this.name;}
    public String getOtherOwner(){return this.otherOwner;}
    public int getHookedTo(){return  this.hookedTo;}


}
