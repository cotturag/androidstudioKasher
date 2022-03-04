package com.example.kasher;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "privileges")
public class Privileges {
    @PrimaryKey
    @NonNull
    String type;

    String name;
    String admin;
    String parent;
    String child;
    Privileges(String type,String name,String admin,String parent,String child){
        this.type=type;
        this.name=name;
        this.admin=admin;
        this.parent=parent;
        this.child=child;
    }

    public String getType() {return this.type;}
    public String getName() {return this.name;}
    public String getAdmin() {return this.admin;}
    public String getParent() {return this.parent;}
    public String getChild() {return this.child;}

    public void setType(String type){this.type=type;}
    public void setName(String name){this.name=name;}
    public void setAdmin(String admin){this.admin=admin;}
    public void setParent(String parent){this.parent=parent;}
    public void setChild(String child){this.child=child;}
}
