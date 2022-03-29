package com.example.kasher;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FundsRepo {
    private FundsDao dao;


    private LiveData<List<FundsForList>> actualFunds;
    private LiveData<List<FundsForList>> accounts;
    private LiveData<List<FundsForList>> costCategories;
    private LiveData<List<Funds>> all;
    private LiveData<Integer> countItems;

    public FundsRepo(Application app, String owner,String privilege){
        AppDatabase db = AppDatabase.getInstance(app);
        dao=db.fundsDao();
        if (privilege.equals("C")) actualFunds= dao.getPrivates(owner);
        else actualFunds= dao.getAll(owner);
        accounts=dao.getAccounts(owner);
        costCategories=dao.getCostCategories(owner);
        all=dao.getAll();
        countItems=dao.countItems();

    }
    public LiveData<List<FundsForList>> getActualFunds(){
        return this.actualFunds;
    }
    public LiveData<List<FundsForList>> getAccounts(){return this.accounts;}
    public LiveData<List<FundsForList>> getCostCategories(){return this.costCategories;}
    public LiveData<List<Funds>> getAll(){return this.all;}
    public LiveData<Integer> getCountItems(){return this.getCountItems();}





    public LiveData<List<FundsForList>> getActualPrivateFundsOnly(String owner){
        return dao.getPrivates(owner);
    }
    public boolean checkIfTableEmpty() throws ExecutionException, InterruptedException {
        return dao.checkIfTableEmpty().get()==0;
    }


    public ListenableFuture<Long> insert(Funds fund){return dao.insert(fund);}
    public void update(Funds fund) {dao.update(fund);}
    public void delete(Funds fund){dao.delete(fund);}

    public void insertRemote(Funds fund,String family) throws ExecutionException, InterruptedException {


        String operateType="insert";

        //String family=dao.getFundFromUsersByOwnerExtendsFamily(owner).get().getFamilyInUsers();

        FundsForRemote fundsForRemote=new FundsForRemote(family);
        fundsForRemote.setId(fund.getId());
        fundsForRemote.setMoney(fund.getMoney());
        fundsForRemote.setOwner(fund.getOwner());
        fundsForRemote.setType(fund.getType());
        fundsForRemote.setActivity(fund.getActivity());
        fundsForRemote.setInactivity(fund.getInactivity());
        fundsForRemote.setName(fund.getName());
        fundsForRemote.setOtherOwner(fund.getOtherOwner());
        fundsForRemote.setHookedTo(fund.getHookedTo());

        new RemoteObjectSenderAsyncTask(operateType).execute(fundsForRemote);
        //TODO itt jó lenne lemásolni az objektumot
    }
    public void updateRemote(Funds fund) throws ExecutionException, InterruptedException {
        String operateType="update";
        FundsForRemote fundsForRemote=dao.getFromUsersIdExtendsFamily(fund.getId()).get();
        fundsForRemote.setOtherOwner(fund.getOtherOwner());
        new RemoteObjectSenderAsyncTask(operateType).execute(fundsForRemote);
        //FundsPage.fundsPageLabelTwo.setText(fundsForRemote.getOtherOwner());
    }
    public void deleteRemote(Funds fund,String family) throws ExecutionException, InterruptedException {
        String operateType="delete";
        //FundsForRemote fundsForRemote=dao.getFromUsersIdExtendsFamily(fund.getId()).get();
        FundsForRemote fundsForRemote = new FundsForRemote(family);

        //fundsForRemote.setOtherOwner(fund.getOtherOwner());
        fundsForRemote.setId(fund.getId());
        fundsForRemote.setMoney(fund.getMoney());
        fundsForRemote.setOwner(fund.getOwner());
        fundsForRemote.setType(fund.getType());
        fundsForRemote.setActivity(fund.getActivity());
        fundsForRemote.setInactivity(fund.getInactivity());
        fundsForRemote.setName(fund.getName());
        fundsForRemote.setOtherOwner(fund.getOtherOwner());
        fundsForRemote.setHookedTo(fund.getHookedTo());
        new RemoteObjectSenderAsyncTask(operateType).execute(fundsForRemote);
    }
    public void synchronizeToServer(String what){
         new RemoteMessageSenderAsyncTask().execute(what);
        //copyAllFundToServer();
    }
    void copyAllFundToServer(){
        int count = this.getCountItems().getValue().intValue();//TODO jóez így?
        for (int i=0;i<=count-1;i++){
            insert(this.getAll().getValue().get(i));
        }

    }
    private class RemoteObjectSenderAsyncTask extends AsyncTask<FundsForRemote, String, String> {
        String szovege;
        HttpURLConnection connection;
        String operateType;
        public RemoteObjectSenderAsyncTask(String operateType){
            this.operateType=operateType;
        }

        private HttpURLConnection connectToServer(String url){
            HttpURLConnection conn=null;
            try{
                URL urlc = null;
                urlc = new URL(url);
                conn = (HttpURLConnection) urlc.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json; utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.connect();
            }
            catch (Exception e){szovege=e.toString();}
            return conn;
        }
        private JSONObject jsonObjectBuilder(FundsForRemote fund){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("method",operateType);
                jsonObject.put("id",fund.getId());
                jsonObject.put("family",fund.getFamily());
                jsonObject.put("money",fund.getMoney());
                jsonObject.put("owner",fund.getOwner());
                jsonObject.put("type",fund.getType());
                jsonObject.put("activity",fund.getActivity());
                jsonObject.put("inactivity",fund.getInactivity());
                jsonObject.put("name",fund.getName());
                jsonObject.put("otherowner",fund.getOtherOwner());
                jsonObject.put("hookedto",fund.getHookedTo());
            }catch (Exception e){
                szovege=e.toString();
            }
            return jsonObject;

        }


        @Override
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(FundsForRemote... funds) {
          //  ArrayList<String> urls=new ArrayList<String>();
            try {
                connection=connectToServer("http://192.168.1.2/access.php");

                JSONObject jsonObject= jsonObjectBuilder(funds[0]);

                BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                int responseCode = connection.getResponseCode();
                if (responseCode==HttpURLConnection.HTTP_OK){
                    szovege="ok";
                }
                else szovege="nemok";

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                szovege=e.toString();
            }
            return szovege;
        }
        @Override
        protected void onPostExecute(String szove) {
            super.onPostExecute(szove);


            FundsPage.fundsPageLabel.setText(szove);



        }
    }
    private class RemoteMessageSenderAsyncTask extends AsyncTask<String,String,String>{
        String szovege;
        HttpURLConnection connection;
        private HttpURLConnection connectToServer(String url){
            HttpURLConnection conn=null;
            try{
                URL urlc = null;
                urlc = new URL(url);
                conn = (HttpURLConnection) urlc.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json; utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.connect();
            }
            catch (Exception e){szovege=e.toString();}
            return conn;
        }
        private JSONObject jsonMessageBuilder(String message) throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message",message);
            return jsonObject;
        }
        @Override
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(String...strings) {
            //  ArrayList<String> urls=new ArrayList<String>();
            try {
                connection=connectToServer("http://192.168.1.2/access.php");

                JSONObject jsonObject= jsonMessageBuilder(strings[0]);

                BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                int responseCode = connection.getResponseCode();
                if (responseCode==HttpURLConnection.HTTP_OK){
                    szovege="ok";
                }
                else szovege="nemok";

                connection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                szovege=e.toString();
            }
            return szovege;
        }
        @Override
        protected void onPostExecute(String szove) {
            super.onPostExecute(szove);


//            FundsPage.fundsPageLabel.setText(szove);



        }

    }

}
