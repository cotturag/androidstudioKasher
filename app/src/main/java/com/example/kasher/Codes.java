package com.example.kasher;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "codes")
public class Codes {
    @PrimaryKey
    @NonNull
    String type;

    String name;

    Codes(String type, String name){
        this.type=type;
        this.name=name;
    }

    public String getType() {return this.type;}
    public String getName() {return this.name;}

    public void setType(String type){this.type=type;}
    public void setName(String name){this.name=name;}

}
