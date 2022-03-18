package com.example.kasher;

public class FundsForRemote extends Funds{
    private String familyinusers;

    public FundsForRemote(String familyinusers) {this.familyinusers = familyinusers;}

    public String getFamily() {return familyinusers;}
    public void setFamily(String familyinusers) {this.familyinusers = familyinusers;}
}
