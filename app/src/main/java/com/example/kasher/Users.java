package com.example.kasher;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class Users {
    @NonNull
    @PrimaryKey
    private String id;
    private String family;
    private String name;
    private String privilege;

    public Users(String id,String family, String name, String privilege) {
        this.id=id;
        this.family = family;
        this.name = name;
        this.privilege = privilege;
    }

    public String getId() {return this.id;}
    public void setId(String id) {this.id = id;}
    public String getFamily() {return this.family;}
    public void setFamily(String family) {this.family = family;}
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public String getPrivilege() {return this.privilege;}
    public void setPrivilege(String privilege) {this.privilege = privilege;}
}