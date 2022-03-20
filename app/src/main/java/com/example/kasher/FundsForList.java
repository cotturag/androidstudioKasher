package com.example.kasher;

public class FundsForList extends Funds{
String typeInPrivileges;
String nameInPrivileges;
String ownerinusers;

FundsForList(String typeInPrivileges,String nameInPrivileges,String ownerinusers){
    this.typeInPrivileges=typeInPrivileges;
    this.nameInPrivileges=nameInPrivileges;
    this.ownerinusers = ownerinusers;
}

    public String getTypeInPrivileges(){return this.typeInPrivileges;}
    public String getNameInPrivileges(){return this.nameInPrivileges;}
    public String getOwnerinusers(){return  this.ownerinusers;}

    public void setTypeInPrivileges(String typeInPrivileges){this.typeInPrivileges=typeInPrivileges;}
    public void setNameInPrivileges(String nameInPrivileges){this.nameInPrivileges=nameInPrivileges;}
    public void setOwnerinusers(String ownerinusers){this.ownerinusers = ownerinusers;}
}
