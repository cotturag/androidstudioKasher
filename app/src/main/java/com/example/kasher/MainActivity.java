package com.example.kasher;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.navigation.NavigationView;

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
    DrawerLayout main;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //  MainActivity.this.deleteDatabase("kasherD");

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        main = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, main, toolbar, R.string.open, R.string.close);
        abdt.syncState();
        NavigationView navw = findViewById(R.id.nav);

        navw.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.funds: {
                        Intent funds = new Intent(MainActivity.this, FundsPage.class);
                        startActivity(funds);
                    }
                    break;
                }
                return false;
            }
        });
    }
}


