package com.example.kasher;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transactions {
    @PrimaryKey(autoGenerate = true)
    int id;

    String date;
    String money;
    int source;
    int destination;
    int virtuality;
    String details;
    int transactiontype;
    Transactions(int id, String date, String money, int source, int destination, int virtuality, String details, int transactiontype){
        this.id=id;
        this.date=date;
        this.money=money;
        this.source=source;
        this.destination=destination;
        this.virtuality=virtuality;
        this.details=details;
        this.transactiontype=transactiontype;
    }

    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}
    public String getDate() {return this.date;}
    public void setDate(String date) {this.date = date;}
    public String getMoney() {return this.money;}
    public void setMoney(String money) {this.money = money;}
    public int getSource() {return this.source;}
    public void setSource(int source) {this.source = source;}
    public int getDestination() {return this.destination;}
    public void setDestination(int destination) {this.destination = destination;}
    public int getVirtuality() {return this.virtuality;}
    public void setVirtuality(int virtuality) {this.virtuality = virtuality;}
    public String getDetails() {return this.details;}
    public void setDetails(String details) {this.details = details;}
    public int getTransactiontype() {return this.transactiontype;}
    public void setTransactiontype(int transactiontype) {this.transactiontype = transactiontype;}
}
