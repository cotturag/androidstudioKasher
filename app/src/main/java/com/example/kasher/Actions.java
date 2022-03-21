package com.example.kasher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Actions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions);
       /* if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentcontainerview, ActionFragment.class, null)
                    .commit();
        }

        */

    }
}
