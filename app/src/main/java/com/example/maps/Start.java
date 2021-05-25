package com.example.maps;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.maps.Maps.RouterWhitoutMaps;
import com.example.maps.Models.Route;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;

import java.util.ArrayList;

public class Start extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ((TextView) findViewById(R.id.name)).setText(Single.getRoutes().get(Single.getCurrentRoute()).getName());
        ((TextView) findViewById(R.id.desc)).setText(Single.getRoutes().get(Single.getCurrentRoute()).getDesc());
        ((TextView)findViewById(R.id.tags)).setText(Single.getRoutes().get(Single.getCurrentRoute()).getTags().replace(",", "/"));
        ((ImageView)findViewById(R.id.image)).setBackground(Single.getRoutes().get(Single.getCurrentRoute()).getDrawable(getApplicationContext()));
        if(Single.getRoutes().get(Single.getCurrentRoute()).isIs_bookmark()){
            ((ImageView)findViewById(R.id.bookmark)).setImageResource(R.drawable.bookmarkred);
        }
        else{
            ((ImageView)findViewById(R.id.bookmark)).setImageResource(R.drawable.bookmark);
        }
        RouterWhitoutMaps routerWhitoutMaps = new RouterWhitoutMaps(Single.getRoutes().get(Single.getCurrentRoute()).getPoints(),findViewById(R.id.time_travel), findViewById(R.id.width), getApplicationContext());
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        while (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                routerWhitoutMaps.draw(new Point(location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    new AlertDialog.Builder(Start.this).setTitle("включите геолокацию").setMessage("без геолокации будут недосутпны некоторые функции").setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            onProviderDisabled(provider);
                        }
                    }).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        ArrayList<Route> routes1 = new ArrayList<>(Single.getRoutes());
        Single.getAdapterRoute().setList(routes1);
        GridLayoutManager GridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 1);
        GridLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        Single.getRoutesView().setLayoutManager(GridLayoutManager1);
        Single.getRoutesView().setAdapter(Single.getAdapterRoute());
        super.onBackPressed();
    }

    public void click_start(View w){
        Intent in = new Intent(getApplicationContext(), Map.class);
        startActivity(in);
    }

    public void click_to_favorite(View view) {
        int pos = Single.getCurrentRoute();
        Single.getRoutes().get(pos).setIs_bookmark(!Single.getRoutes().get(pos).isIs_bookmark());
        if(Single.getRoutes().get(pos).isIs_bookmark()){
            ((ImageView)findViewById(R.id.bookmark)).setImageResource(R.drawable.bookmarkred);
        }
        else{
            ((ImageView)findViewById(R.id.bookmark)).setImageResource(R.drawable.bookmark);
        }

    }

    @Override
    protected void onStart() {
        MapKitFactory.getInstance().onStart();
        super.onStart();
    }

    @Override
    protected void onStop() {
        MapKitFactory.getInstance().onStop();;
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        check();
    }
    public void check(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new AlertDialog.Builder(Start.this).setTitle("включите геолокацию").setMessage("без геолокации будут недосутпны некоторые функции").setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    check();
                }
            }).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.Set(Single.getRoutes());
    }
}