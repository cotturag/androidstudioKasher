package com.example.kasher;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsersAndPrivilegesRepo {
    private CodesDao codesDao;
    private UsersDao usersDao;
    private LiveData<List<Codes>> privileges;
    private LiveData<List<Users>> users;

    UsersAndPrivilegesRepo(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        codesDao =db.privilegesDao();
        privileges= codesDao.getAll();
        usersDao =db.usersDao();
        users=usersDao.getAll();
    }
    public Users getUserPrivilegeByOwnerInUser(String owner) throws ExecutionException, InterruptedException {
        return usersDao.getPrivilegeByOwnerInLFUser(owner).get();
    }
    public Users getFamilyByOwnerFromUsersInUser(String owner) throws ExecutionException, InterruptedException {
        return usersDao.getFamilyByOwnerInLFUser(owner).get();
    }
    public boolean checkIfUsersTableEmpty() throws ExecutionException, InterruptedException {
        return usersDao.checkIfTableEmpty().get()==0;
    }
    public boolean checkIfPrivilegesTableEmpty() throws ExecutionException, InterruptedException {
        return codesDao.checkIfTableEmpty().get()==0;
    }

    public LiveData<List<Codes>> getPrivileges(){return this.privileges;}
    public LiveData<List<Users>> getUsers(){return this.users;}

    public void insertToPrivileges(Codes codes){
        codesDao.insert(codes);}
    public void insertToUsers(Users users){usersDao.insert(users);}
    public boolean checkLoginCredentials(String user, String pass) throws ExecutionException, InterruptedException {

        return  (usersDao.getPassByUser(user).get().equals(pass));
    }
}
