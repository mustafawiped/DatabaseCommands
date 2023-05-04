package com.example.databasecommands;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class VeriListeleme extends AppCompatActivity {

    SQLiteDatabase database;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veri_listeleme);
        database = this.openOrCreateDatabase("veritabani",MODE_PRIVATE,null);
        listview = findViewById(R.id.listView);
        verileriListele();
    }

    @Override
    protected void onStart() {
        super.onStart();
        verileriListele();
    }

    private void verileriListele() {
        ArrayList veriler = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM veriler",null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String kadi = cursor.getString(cursor.getColumnIndex("kadi"));
                @SuppressLint("Range") String sifre = cursor.getString(cursor.getColumnIndex("sifre"));
                @SuppressLint("Range") String eposta = cursor.getString(cursor.getColumnIndex("eposta"));
                @SuppressLint("Range") String tel = cursor.getString(cursor.getColumnIndex("tel"));
                veriler.add(new VerilerClass(kadi,sifre,eposta,tel));

            } while (cursor.moveToNext());
        }
        cursor.close();
        VeriAdapterClass veriAdapterClass = new VeriAdapterClass(veriler,this);
        veriAdapterClass.notifyDataSetChanged();
        listview.setAdapter(veriAdapterClass);
    }
}