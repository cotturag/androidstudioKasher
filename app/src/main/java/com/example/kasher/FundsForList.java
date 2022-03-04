package com.example.kasher;

public class FundsForList extends Funds{
String typeInPrivileges;
String nameInPrivileges;

FundsForList(String typeInPrivileges,String nameInPrivileges){
    this.typeInPrivileges=typeInPrivileges;
    this.nameInPrivileges=nameInPrivileges;
}
    //TODO ha nincs default konstruktor akkor superrel kéne
    public String getTypeInPrivileges(){return this.typeInPrivileges;}
    public String getNameInPrivileges(){return this.nameInPrivileges;}

    public void setTypeInPrivileges(String typeInPrivileges){this.typeInPrivileges=typeInPrivileges;}
    public void setNameInPrivileges(String nameInPrivileges){this.nameInPrivileges=nameInPrivileges;}
}
