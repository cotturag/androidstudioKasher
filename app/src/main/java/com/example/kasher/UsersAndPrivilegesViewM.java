package com.example.kasher;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsersAndPrivilegesViewM extends AndroidViewModel {
    private UsersAndPrivilegesRepo repo;
    private LiveData<List<Privileges>> privileges;
    private LiveData<List<Users>> users;
    public UsersAndPrivilegesViewM(@NonNull Application app){
        super(app);
        repo=new UsersAndPrivilegesRepo(app);
        privileges=repo.getPrivileges();
        users=repo.getUsers();
    }
    public void createNewPrivileges(Privileges privileges){
        repo.insertToPrivileges(privileges);
    }
    public LiveData<List<Privileges>> getPrivileges(){return this.privileges;}

    public void createNewUsers(Users user){
        repo.insertToUsers(user);
    }
    public LiveData<List<Users>> getUsers(){return this.users;}

    public Users getPrivilegesByOwner(String owner) throws ExecutionException, InterruptedException {
        return repo.getUserPrivilegeByOwnerInUser(owner);
    }
    public String getFamilyByOwnerFromUsersInString(String owner) throws ExecutionException, InterruptedException {
        return repo.getFamilyByOwnerFromUsersInUser(owner).getFamily();
    }
    public boolean checkIfUsersTableEmpty() throws ExecutionException, InterruptedException {
        return repo.checkIfUsersTableEmpty();
    }
    public boolean checkIfPrivilegesTableEmpty() throws ExecutionException, InterruptedException {
        return repo.checkIfPrivilegesTableEmpty();
    }

}
