package com.example.databasecommands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnEkleSil(View view) {
        Intent i = new Intent(MainActivity.this,VeriEkleSilGuncelle.class);
        startActivity(i);
    }

    public void btnVeriListele(View view) {
        Intent i = new Intent(MainActivity.this,VeriListeleme.class);
    }
}