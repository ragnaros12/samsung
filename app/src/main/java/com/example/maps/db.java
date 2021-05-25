package com.example.maps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.DebugUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.maps.Models.Route;
import com.google.gson.Gson;
import com.yandex.mapkit.geometry.Point;

import java.io.File;
import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class db extends SQLiteOpenHelper {
    Gson converter = new Gson();
    final static int DB_VER = 1;
    final static String DB_NAME = "db.db";

    Context mContext;

    public db(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE routes (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, route TEXT NOT NULL)");
        fillData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void fillData(SQLiteDatabase db) {

        ArrayList<Route> routes = new ArrayList<>();
        ArrayList<Point> points = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        points.add(new Point(54.774122, 31.789684));
        names.add("Мемориал «Катынь»");

        names.add("Смоленское поозерье");
        points.add(new Point(55.510012, 31.838683));

        names.add("Историко-архитектурный комплекс «Теремок»");
        points.add(new Point(54.659180, 32.209065));

        names.add("Музей-усадьба М.И. Глинки в Новоспасском");
        points.add(new Point(54.375980, 33.119148));

        names.add("Усадьба Барышниковых в Алексино");
        points.add(new Point(54.783542, 33.407882));

        names.add("Соловьёвская переправа");
        points.add(new Point(54.923059, 32.718515));

        names.add("Валутина гора");
        points.add(new Point(54.809478, 32.134656));

        names.add("Музей-заповедник «Гнёздово»");
        points.add(new Point(54.785732, 31.879890));

        ArrayList<String> desc = new ArrayList<>(Arrays.asList(mContext.getResources().getStringArray(R.array.descs)));
        ArrayList<Integer> imgs = new ArrayList<>(Arrays.asList(R.drawable.route1, R.drawable.route2, R.drawable.route3
                , R.drawable.route4, R.drawable.route5, R.drawable.route6, R.drawable.route7, R.drawable.route8));
        routes.add(new Route("описание", "Путешествие по области", R.drawable.route_main,points, "область", false,names, desc, imgs));





        for (Route route : routes){
            Add(route, db);
        }
    }
    public ArrayList<Route> Get(){
        ArrayList<Route> get = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query("routes", new String[] {"id", "route"}, null, null ,null , null, null);
        while (cursor.moveToNext()){
            get.add(converter.fromJson(cursor.getString(cursor.getColumnIndex("route")), Route.class));
        }
        return get;
    }
    public void Set(ArrayList<Route> routes){
        getWritableDatabase().execSQL("DELETE from routes");
        for (Route route : routes) {
            String str = converter.toJson(route);
            ContentValues values = new ContentValues();
            values.put("route" , str);
            getWritableDatabase().insert("routes", null , values);
        }
    }

    public void Add(Route route, SQLiteDatabase db) {
        String str = converter.toJson(route);
        ContentValues values = new ContentValues();
        values.put("route" , str);
        db.insert("routes", null , values);
    }
}
