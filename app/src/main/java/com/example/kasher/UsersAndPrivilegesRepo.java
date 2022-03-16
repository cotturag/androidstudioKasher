package com.example.kasher;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsersAndPrivilegesRepo {
    private PrivilegesDao privilegesDao;
    private UsersDao usersDao;
    private LiveData<List<Privileges>> privileges;
    private LiveData<List<Users>> users;

    UsersAndPrivilegesRepo(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        privilegesDao=db.privilegesDao();
        privileges=privilegesDao.getAll();
        usersDao =db.usersDao();
        users=usersDao.getAll();
    }
    public Users getPrivilegeByOwner(String owner) throws ExecutionException, InterruptedException {
        return usersDao.getPrivilegeByOwner(owner).get();
    }
    public boolean checkIfUsersTableEmpty() throws ExecutionException, InterruptedException {
        return usersDao.checkIfTableEmpty().get()==0;
    }
    public boolean checkIfPrivilegesTableEmpty() throws ExecutionException, InterruptedException {
        return privilegesDao.checkIfTableEmpty().get()==0;
    }

    public LiveData<List<Privileges>> getPrivileges(){return this.privileges;}
    public LiveData<List<Users>> getUsers(){return this.users;}

    public void insertToPrivileges(Privileges privileges){privilegesDao.insert(privileges);}
    public void insertToUsers(Users users){usersDao.insert(users);}
}
