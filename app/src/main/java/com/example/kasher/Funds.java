package com.example.kasher;

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
    private int pickedup;
    private int parent;

    public Funds(){}
    public Funds(String money,String owner,String type,int activity,String inactivity,String name,int pickedup,int parent){
        this.money=money;
        this.owner=owner;
        this.type=type;
        this.activity=activity;
        this.inactivity=inactivity;
        this.name=name;
        this.pickedup=pickedup;
        this.parent=parent;
    }

    public void setId(int id){this.id=id;}
    public void setMoney(String money){this.money=money;}
    public void setOwner(String owner){this.owner=owner;}
    public void setType(String type){this.type=type;}
    public void setActivity(int activity){this.activity=activity;}
    public void setInactivity(String inactivity){this.inactivity=inactivity;}
    public void setName(String name){this.name=name;}
    public void setPickedup(int pickedup){this.pickedup=pickedup;}
    public void setParent(int parent){this.parent=parent;}

    public int getId(){return this.id;}
    public String getMoney(){return this.money;}
    public String getOwner(){return this.owner;}
    public String getType(){return this.type;}
    public int getActivity(){return this.activity;}
    public String getInactivity(){return this.inactivity;}
    public String getName(){return  this.name;}
    public int getPickedup(){return this.pickedup;}
    public int getParent(){return this.parent;}


}
