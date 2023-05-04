package com.example.databasecommands;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class VeriListeleme extends AppCompatActivity {

    SQLiteDatabase database;   //sqlite database tanımlamamızı yaptık.
    ListView listview;   // listview imizi tanımladık

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // onCreate methodu layout ilk kez oluşturulduğunda çalışır yani uygulamayı alta aldığınızda ve geri girdiğinizde bu method çalışmaz.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veri_listeleme);
        database = this.openOrCreateDatabase("veritabani",MODE_PRIVATE,null); // veri tabanımızı açtık.
        listview = findViewById(R.id.listView);  // listview i bulduk
        verileriListele();  // verilerilistele methodunu çağırdık.
    }

    @Override
    protected void onStart() { // onStart methodu uygulamaya her girildiğinde yani uygulamayı alta alıp tekrar girdiğinizde bile çalışan methoddur.
        super.onStart();
        verileriListele();  // her uygulamaya girildiğinde listeyi güncel tutmak istediğimiz için burda tekrar methodu çağırdık ve listView i yeniledik.
    }

    private void verileriListele() {
        ArrayList veriler = new ArrayList<>();  // veriler ismminde arraylsit tanımladık.
        Cursor cursor = database.rawQuery("SELECT * FROM veriler",null);  // cursor ile sorgumuzu oluşturduk
        if (cursor != null && cursor.moveToFirst()) {  // eğer boş veri gelmiyorsa veya null değeri döndürmüyorsa
            do {
                @SuppressLint("Range") String kadi = cursor.getString(cursor.getColumnIndex("kadi"));
                @SuppressLint("Range") String sifre = cursor.getString(cursor.getColumnIndex("sifre"));  // veritabanından verilerimizi çekip değişkenlere eşitledik.
                @SuppressLint("Range") String eposta = cursor.getString(cursor.getColumnIndex("eposta"));
                @SuppressLint("Range") String tel = cursor.getString(cursor.getColumnIndex("tel"));
                veriler.add(new VerilerClass(kadi,sifre,eposta,tel));  // tanımladığımız Arraylist in içine VerilerClass sınıfı aracılığıyla tüm verileri ekledik. Böylece kolaylıkla hem adapter hemde VerilerClass ı kullanabileceğiz

            } while (cursor.moveToNext()); // veriler bitene kadar döngü devam eder. Veritabanında veri bitince döngü false değeri alır ve durur.
        }
        cursor.close();  // cursor umuzu kapattık.
        VeriAdapterClass veriAdapterClass = new VeriAdapterClass(veriler,this);  // VeriAdapterClass ımızı çağırdık ve tüm verilerimizi o sınıfa Constractor aracılığıyla gönderdik Böylelikle hem verileri hemde hangi layout üzerinden göstereceksek onu belirttik.
        veriAdapterClass.notifyDataSetChanged();  // Adapter 'ımız üzerinde herhangi bir değişiklik olduysa (yeni veri veya var olan veriyi güncelleme gibi.) o işlemi tespit edip uygulamak için çağırılan method
        listview.setAdapter(veriAdapterClass); // listView 'imize bir adapter bağladık böylelikle tüm verilerimiz orada gözükebilir.
    }
}