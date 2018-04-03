package com.ashitech.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    private ArrayList<Data>arrayList;
    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        readNumber();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                readNumber();
            }
        };


    }

    public void readNumber(){

        arrayList.clear();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = dbHelper.readNumber(sqLiteDatabase);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){

                String number;
                int count;
                number = cursor.getString(cursor.getColumnIndex(DbContract.INCOMING_NUMBER));
                count = cursor.getInt(cursor.getColumnIndex("id"));
                arrayList.add(new Data(count,number));
            }
            cursor.close();
            dbHelper.close();
            recycleAdapter = new RecycleAdapter(arrayList);
            recycleAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recycleAdapter);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,new IntentFilter(DbContract.UPDATE_UI_FILTTER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}













