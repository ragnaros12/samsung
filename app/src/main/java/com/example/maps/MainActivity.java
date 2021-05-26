package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.yandex.mapkit.MapKitFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database.Init(getApplicationContext());
        MapKitFactory.setApiKey("fc3a9d17-448a-4ead-93e3-d86dbbd7bac1");
        MapKitFactory.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> list = new ArrayList<>();
        list.add("Все");
        list.add("по городу");
        list.add("по области");
        list.add("для детей");
        list.add("для пар");
        Single.setTags(list);



        Single.setSettings(getPreferences(MODE_PRIVATE));
        Single.setWriter(Single.getSettings().edit());
        Single.setRoutes(Database.GetRoutes());



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(i);
            }
        }).start();
    }
}