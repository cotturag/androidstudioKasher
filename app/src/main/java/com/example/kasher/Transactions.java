package com.example.kasher;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transactions {
    @PrimaryKey(autoGenerate = true)
    int id;

    String date;
    String money;
    String source;
    String destination;
    String details;
    int transactiontype;
    Transactions(int id, String date, String money, String source, String destination, String details, int transactiontype){
        this.id=id;
        this.date=date;
        this.money=money;
        this.source=source;
        this.destination=destination;
        this.details=details;
        this.transactiontype=transactiontype;
    }

    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}
    public String getDate() {return this.date;}
    public void setDate(String date) {this.date = date;}
    public String getMoney() {return this.money;}
    public void setMoney(String money) {this.money = money;}
    public String getSource() {return this.source;}
    public void setSource(String source) {this.source = source;}
    public String getDestination() {return this.destination;}
    public void setDestination(String destination) {this.destination = destination;}
    public String getDetails() {return this.details;}
    public void setDetails(String details) {this.details = details;}
    public int getTransactiontype() {return this.transactiontype;}
    public void setTransactiontype(int transactiontype) {this.transactiontype = transactiontype;}
}
