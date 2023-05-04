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

public class VeriAdapterClass extends BaseAdapter {
    ArrayList<VerilerClass> list;
    Context context;

    public VeriAdapterClass(ArrayList<VerilerClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.liste_ogeler,parent,false);
        }
        VerilerClass veri = list.get(i);
        TextView kadi = view.findViewById(R.id.kadiList),
                sifre = view.findViewById(R.id.sifreList),
                eposta = view.findViewById(R.id.epostaList);
        kadi.setText(veri.getKadi());
        sifre.setText(veri.getSifre());
        eposta.setText(veri.getEposta());
        return view;
    }
}
