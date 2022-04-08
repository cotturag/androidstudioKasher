package com.example.kasher;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Funds.class, Codes.class,Transactions.class,Users.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FundsDao fundsDao();
    public abstract CodesDao privilegesDao();
    public abstract TransactionsDao transactionsDao();
    public abstract UsersDao usersDao();
    private static AppDatabase instance;
    public static synchronized AppDatabase getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"kasherD").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
     return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        PopulateDbAsyncTask(AppDatabase instance) {FundsDao dao=instance.fundsDao();
            CodesDao codesDao = instance.privilegesDao();TransactionsDao transactionsDao= instance.transactionsDao();UsersDao usersDao= instance.usersDao();}

        @Override
        protected Void doInBackground(Void... voids){
            return null;
        }
    }
}
