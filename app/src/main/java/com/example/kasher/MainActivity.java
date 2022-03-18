package com.example.kasher;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONObject;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    URL urlc = null;
    InputStream is = null;

    static TextView szoveg;
    SQLiteDatabase db;
    Button listvre;
    Button button;




    public static void ir(String sz){
        szoveg.setText(sz);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        szoveg=findViewById(R.id.szoveg);
     //  MainActivity.this.deleteDatabase("kasherD");
        listvre=findViewById(R.id.szamlak);
        listvre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountSettings = new Intent(MainActivity.this, FundsPage.class);
                startActivity(accountSettings);
            }
        });



        button=findViewById(R.id.asyncTask);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute();
            }
        });
    }

    private class AsyncTaskExample extends AsyncTask<String, String, String> {
        String szovege;
        @Override
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(String... strings) {
            ArrayList<String> urls=new ArrayList<String>();
            try {
                urlc = new URL("http://192.168.1.2/access.php");
                HttpURLConnection conn = (HttpURLConnection) urlc.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json; utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.connect();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("method","update");
                    jsonObject.put("id","1");
                    jsonObject.put("family","cotturag@gmail.com");
                    jsonObject.put("money",0);
                }catch (Exception e){

                }
                BufferedOutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();


                int responseCode = conn.getResponseCode();
                if (responseCode==HttpURLConnection.HTTP_OK){
                    szovege="ok";
                }
                else szovege="nemok";

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                szovege=e.toString();
            }
            return szovege;
        }
        @Override
        protected void onPostExecute(String szove) {
            super.onPostExecute(szove);
            //ir(szove);
        }

        /*
        private String getPostDataString(JSONObject values) throws Exception{
            StringBuilder res = new StringBuilder();
            boolean first=true;
            Iterator<String> itr = values.keys();
            while (itr.hasNext()){
                String key=itr.next();
                Object value = values.get(key);

                if (first) first=false;
                else res.append("&");

                res.append(URLEncoder.encode(key,"UTF-8"));
                res.append("=");
                res.append(URLEncoder.encode(value.toString(),"UTF-8"));


            }
        return res.toString();
        }

       */
    }

}


