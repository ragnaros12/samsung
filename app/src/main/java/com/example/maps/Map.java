package com.example.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.maps.Maps.RouterByOne;
import com.github.pwittchen.swipe.library.rx2.SimpleSwipeListener;
import com.github.pwittchen.swipe.library.rx2.Swipe;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.net.Inet4Address;

public class Map extends AppCompatActivity {
    public int MAX = 100;
    public int MIN = 70;
    Point loc = null;
    Swipe swipe = new Swipe();
    MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.card1);
        Context context = this;
        Next next = () -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.next);
            dialog.findViewById(R.id.skip).setOnClickListener(v -> dialog.cancel());
            dialog.findViewById(R.id.photo).setOnClickListener(v -> {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);
                dialog.cancel();
            });
            dialog.show();
        };
        Next last = () -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.next);
            ((TextView)dialog.findViewById(R.id.skip)).setText("");
            ((TextView)dialog.findViewById(R.id.next)).setText(R.string.last);
            ((TextView)dialog.findViewById(R.id.photo)).setText("закончить");
            dialog.findViewById(R.id.photo).setOnClickListener(v -> {
                Single.getRoutes().get(Single.getCurrentRoute()).setEnd(true);
                Database.Set(Single.getRoutes());
                Intent in = new Intent(getApplicationContext(), ListActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
            });
            dialog.show();
        };
        RouterByOne router = new RouterByOne(mapView, getResources(), Single.getRoutes().get(Single.getCurrentRoute()).getPoints(), findViewById(R.id.time), findViewById(R.id.width), next, last, getApplicationContext());
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        while (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 7000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                loc = new Point(location.getLatitude(), location.getLongitude());
                router.draw(loc);
                ((TextView)findViewById(R.id.name)).setText(Single.getRoutes().get(Single.getCurrentRoute()).getNames().get(router.curr));
                ((TextView)findViewById(R.id.desc)).setText(Single.getRoutes().get(Single.getCurrentRoute()).getDescs().get(router.curr));
                findViewById(R.id.image_point).setBackground(Single.getRoutes().get(Single.getCurrentRoute()).getImages(getApplicationContext()).get(router.curr));
            }
            @Override
            public void onProviderDisabled(@NonNull String provider) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    new AlertDialog.Builder(Map.this).setTitle("включите геолокацию").setMessage("без геолокации будут недосутпны некоторые функции").setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            onProviderDisabled(provider);
                        }
                    }).show();
                }
            }
        });
        swipe.setListener(new SimpleSwipeListener() {
            @Override
            public void onSwipingUp(MotionEvent event) {
                FrameLayout frameLayout = findViewById(R.id.swiper);
                if (frameLayout.getY() < getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + MIN) {
                    frameLayout.setY(getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + MIN);
                } else if (frameLayout.getY() > getResources().getDisplayMetrics().heightPixels - MAX) {
                    frameLayout.setY(getResources().getDisplayMetrics().heightPixels - MAX);
                } else {
                    frameLayout.setY(event.getY() - 150);
                    if (frameLayout.getY() < getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + MIN) {
                        frameLayout.setY(getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + MIN);
                    } else if (frameLayout.getY() > getResources().getDisplayMetrics().heightPixels - MAX) {
                        frameLayout.setY(getResources().getDisplayMetrics().heightPixels - MAX);
                    }
                }
                super.onSwipingUp(event);
            }
            @Override
            public boolean onSwipedUp(MotionEvent event){
                FrameLayout frameLayout = findViewById(R.id.swiper);
                if (frameLayout.getY() < getResources().getDisplayMetrics().heightPixels - (frameLayout.getY() / 3)) {
                    frameLayout.animate().y(getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + MIN).setDuration(300).start();
                } else {
                    frameLayout.animate().y(getResources().getDisplayMetrics().heightPixels - MAX).setDuration(300).start();
                }
                return super.onSwipedUp(event);
            }
            @Override
            public boolean onSwipedDown(MotionEvent event){
                FrameLayout frameLayout = findViewById(R.id.swiper);
                if (frameLayout.getY() < getResources().getDisplayMetrics().heightPixels - (frameLayout.getY() / 3)) {
                    frameLayout.animate().y(getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + MIN).setDuration(300).start();
                } else {
                    frameLayout.animate().y(getResources().getDisplayMetrics().heightPixels - MAX).setDuration(300).start();
                }
                return super.onSwipedDown(event);
            }
            @Override
            public void onSwipingDown(MotionEvent event) {
                FrameLayout frameLayout = findViewById(R.id.swiper);
                if (frameLayout.getY() < getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + MIN) {
                    frameLayout.setY(getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + MIN);
                } else if (frameLayout.getY() > getResources().getDisplayMetrics().heightPixels - MAX) {
                    frameLayout.setY(getResources().getDisplayMetrics().heightPixels - MAX);
                } else {
                    frameLayout.setY(event.getY() - 150);
                    if (frameLayout.getY() < getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + 15) {
                        frameLayout.setY(getResources().getDisplayMetrics().heightPixels - frameLayout.getHeight() + 15);
                    } else if (frameLayout.getY() > getResources().getDisplayMetrics().heightPixels - MAX) {
                        frameLayout.setY(getResources().getDisplayMetrics().heightPixels - MAX);
                    }
                }
                super.onSwipingDown(event);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getY() > findViewById(R.id.swiper).getY()) {
            if(ev.getY() < findViewById(R.id.swiper).getY() + 500) {
                swipe.dispatchTouchEvent(ev);
            }
            return false;
        }
        else{
            return super.dispatchTouchEvent(ev);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("вы уверены").setMessage("вы точно хотите закончить маршрут").setNegativeButton("нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("да", (dialog, which) -> {
            finish();
            Intent in = new Intent(getApplicationContext(), ListActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(in);
        }).show();
    }

    @Override
    protected void onStart() {
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
        super.onStart();
    }


    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    public void click_to_location(View w){
        if(loc != null){
            mapView.getMap().move(new CameraPosition(loc, 14f, 0.0f, 0.0f), new Animation(Animation.Type.SMOOTH, 1.5f), null);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        check();
    }
    public void check(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new AlertDialog.Builder(Map.this).setTitle("включите геолокацию").setMessage("без геолокации будут недосутпны некоторые функции").setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    check();
                }
            }).show();
        }
    }
    public void click_cancel(View w){
        new AlertDialog.Builder(this).setTitle("вы уверены").setMessage("вы точно хотите закончить маршрут").setNegativeButton("нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("да", (dialog, which) -> {
            finish();
            Intent in = new Intent(getApplicationContext(), ListActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
        }).show();
    }
}