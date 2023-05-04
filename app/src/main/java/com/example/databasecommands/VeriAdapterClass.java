package com.example.databasecommands;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VeriAdapterClass extends BaseAdapter {  // base adapter sınıfını extend ettik yani üst sınıf olarak tanımladık. Böylece içindeki tüm methodlara ulasabilicez. (Private ler ve defoult lar hariç)
    ArrayList<VerilerClass> list;   // Global bir arraylist tanımladık ve verileri VerilerClass isimli class a eşitledik.
    Context context;  // Context tanımladık

    public VeriAdapterClass(ArrayList<VerilerClass> list, Context context) {
        this.list = list;   // Constractor tanımladık.
        this.context = context;   // context tanımladık. Böylelikle main layoutu çekebiliriz.
    }

    @Override
    public int getCount() {
        return list.size();  // ek methodlar kullanmak isterseniz :)
    }

    @Override
    public Object getItem(int position) {
        return list.get(position); // ek methodlar kullanmak isterseniz :)
    }

    @Override
    public long getItemId(int position) {
        return position; // ek methodlar kullanmak isterseniz :)
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if(view == null){  // Eğer ekran null ise yani boşsa seçilmemişse
            view = LayoutInflater.from(context).inflate(R.layout.liste_ogeler,parent,false);  // list_ogeler isimli layoutu çek | seç
        }
        VerilerClass veri = list.get(i);  // hangi verideyiz? Bunu VerilerClass ile hangisindeysek onu çekiyoruz.
        TextView kadi = view.findViewById(R.id.kadiList),  // list_ogeler layoutundaki componentleri id leri ile çekiyoruz.
                sifre = view.findViewById(R.id.sifreList),
                eposta = view.findViewById(R.id.epostaList);
        kadi.setText(veri.getKadi());   // text özelliklerini ayarlıyoruz.
        sifre.setText(veri.getSifre());
        eposta.setText(veri.getEposta());
        return view;  // Ayarladığımız ekranı return işlemini kullanarak geriye dönderiyoruz böylelikle görünümümüz veri listeleme layoutuna gidiyor.
    }
}
