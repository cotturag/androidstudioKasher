package com.example.kasher;

public class FundsForList extends Funds{
String typeInCodes;
String nameInCodes;
String ownerinusers;

FundsForList(String typeInCodes, String nameInCodes, String ownerinusers){
    this.typeInCodes = typeInCodes;
    this.nameInCodes = nameInCodes;
    this.ownerinusers = ownerinusers;
}

    public String getTypeInCodes(){return this.typeInCodes;}
    public String getNameInCodes(){return this.nameInCodes;}
    public String getOwnerinusers(){return  this.ownerinusers;}

    public void setTypeInCodes(String typeInCodes){this.typeInCodes = typeInCodes;}
    public void setNameInCodes(String nameInCodes){this.nameInCodes = nameInCodes;}
    public void setOwnerinusers(String ownerinusers){this.ownerinusers = ownerinusers;}
}
