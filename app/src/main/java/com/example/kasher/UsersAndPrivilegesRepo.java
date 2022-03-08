package com.example.kasher;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UsersAndPrivilegesRepo {
    private PrivilegesDao privilegesDao;
    private UsersDao usersDao;
    private LiveData<List<Privileges>> privileges;
    private LiveData<List<Users>> users;
    private static String privilegeByOwner;


    UsersAndPrivilegesRepo(Application app) {
        AppDatabase db = AppDatabase.getInstance(app);
        privilegesDao=db.privilegesDao();
        privileges=privilegesDao.getAll();
        usersDao =db.usersDao();
        users=usersDao.getAll();
    }

    public static void setPrivilegeByOwner(String privilege){
        privilegeByOwner=privilege;
    }

    public LiveData<List<Privileges>> getPrivileges(){return this.privileges;}
    public LiveData<List<Users>> getUsers(){return this.users;}
    public void getPrivilegeByOwner(String owner){
        queryPrivilegeByOwner(owner);
       // return privilegeByOwner;
    }
    public String getPrivilegeByOwnerResult(){
        return privilegeByOwner;
    }
    /*public List<Users> getPrivilegeByOwner(String owner){
        return usersDao.getPrivilegeByOwner(owner);
    }


     */

    public void queryPrivilegeByOwner(String owner){new GetPrivilegeByOwnerFromUsersAsyncTask(usersDao).execute(owner);}
    public void insertToPrivileges(Privileges privileges){new InsertToPrivilegesAsyncTask(privilegesDao).execute(privileges);}
    public void insertToUsers(Users users){new InsertToUsersAsyncTask(usersDao).execute(users);}

    private static class InsertToPrivilegesAsyncTask extends AsyncTask<Privileges,Void,Void> {
        private PrivilegesDao dao;
        public InsertToPrivilegesAsyncTask(PrivilegesDao dao){this.dao=dao;}
        @Override
        protected Void doInBackground(Privileges... privileges){
            dao.insert(privileges[0]);
            return null;
        }
    }
    private static class InsertToUsersAsyncTask extends AsyncTask<Users,Void,Void> {
        private UsersDao dao;
        public InsertToUsersAsyncTask(UsersDao dao){this.dao=dao;}
        @Override
        protected Void doInBackground(Users...users){
            dao.insert(users[0]);
            return null;
        }
    }
    private static class GetPrivilegeByOwnerFromUsersAsyncTask extends AsyncTask<String,Void,String> {
        private UsersDao dao;
        public GetPrivilegeByOwnerFromUsersAsyncTask(UsersDao dao){this.dao=dao;}
        @Override
        protected String doInBackground(String...strings){
            List<Users> user = dao.getPrivilegeByOwner(strings[0]);
            return user.get(0).getPrivilege();
        }
        @Override
        protected  void onPostExecute(String o){
            super.onPostExecute(o);
            UsersAndPrivilegesRepo.setPrivilegeByOwner(o);
            FundsPage.setFundsByPrivilege(o);


        }
    }


}
