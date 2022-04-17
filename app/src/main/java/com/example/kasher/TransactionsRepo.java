package com.example.kasher;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class TransactionsRepo {
    boolean onLocalNetwork=true;
    String localNetwork="192.168.1.2";
    String remoteNetwork="cotturag.ddns.net";
    private TransactionsDao dao;

    TransactionsRepo(Application app){
        AppDatabase db =AppDatabase.getInstance(app);
        dao=db.transactionsDao();


    }
    //public LiveData<List<Transactions>> getTransaction(){return this.transactions;}
    public Long insert(Transactions transaction) throws ExecutionException, InterruptedException {
        return dao.insert(transaction).get().longValue();
    }
    public void insertRemote(Transactions transactions){
        new RemoteTransactionsSenderAsyncTask("insertTransaction").execute(transactions);
    }
    public void deleteRemote(Transactions transactions){
        new RemoteTransactionsSenderAsyncTask("deleteTransaction").execute(transactions);
    }

    private class RemoteTransactionsSenderAsyncTask extends AsyncTask<Transactions, String, String> {
        String szovege;
        HttpURLConnection connection;
        String operateType;
        public RemoteTransactionsSenderAsyncTask(String operateType){
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
        private JSONObject jsonObjectBuilder(Transactions transactions){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("method",operateType);
                jsonObject.put("id",transactions.getId());
                jsonObject.put("date",transactions.getDate());
                jsonObject.put("money",transactions.getMoney());
                jsonObject.put("source",transactions.getSource());
                jsonObject.put("destination",transactions.getDestination());
                jsonObject.put("details",transactions.getDetails());
                jsonObject.put("transactiontype",transactions.getTransactiontype());
            }catch (Exception e){
                szovege=e.toString();
            }
            return jsonObject;

        }


        @Override
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(Transactions... transactions) {
            //  ArrayList<String> urls=new ArrayList<String>();
            try {
                if (onLocalNetwork) connection=connectToServer("http://192.168.1.2/access.php");
                if (!onLocalNetwork) connection=connectToServer("http://cotturag.ddns.net/access.php");

                JSONObject jsonObject= jsonObjectBuilder(transactions[0]);

                BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                int responseCode = connection.getResponseCode();
                if (responseCode==HttpURLConnection.HTTP_OK){
                    szovege="ok";

                }
                else {

                }

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
/*
            Toast toast = Toast.makeText(MainActivity.pr.getApplication(), szove, Toast.LENGTH_SHORT);
            toast.show();*/
//            FundsPage.fundsPageLabel.setText(szove);



        }

        public void synchronizeToServer(String what) throws ExecutionException, InterruptedException {
            new TransactionsRepo.RemoteMessageSenderAsyncTask().execute(what);
            copyAllTransactionToServer();
        }
        void copyAllTransactionToServer() throws ExecutionException, InterruptedException {
            for (Transactions transaction:dao.getAll().get()){
                insertRemote(transaction);
            }

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
        private JSONObject jsonMessageBuilder(String operate,String arg1) throws JSONException {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("operate",operate);
            jsonObject.put("arg1",arg1);
            return jsonObject;
        }
        @Override
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(String... strings) {
            //  ArrayList<String> urls=new ArrayList<String>();
            try {
                if (onLocalNetwork) connection=connectToServer("192.168.1.2/acces.php");
                if (!onLocalNetwork) connection=connectToServer("http://cotturag.ddns.net/access.php");

                JSONObject jsonObject= jsonMessageBuilder(strings[0],strings[1]);

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
