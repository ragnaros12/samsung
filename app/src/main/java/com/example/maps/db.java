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

        ArrayList<Point> points1 = new ArrayList<>();
        ArrayList<String> names1 = new ArrayList<>();

        ArrayList<Point> points2 = new ArrayList<>();
        ArrayList<String> names2 = new ArrayList<>();
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
        ArrayList<Integer> imgs = new ArrayList<>(Arrays.asList(R.drawable.route1_1, R.drawable.route2_1, R.drawable.route3_1
                , R.drawable.route4_1 , R.drawable.route5_1, R.drawable.route6_1, R.drawable.route7_1, R.drawable.route8_1));
        routes.add(new Route("Когда едешь в какой-то новый для себя город на очень короткое время, то всегда возникает проблема: что успеть посмотреть за такой ограниченный срок и при этом не уподобиться загнанному зверю. Потом, конечно, жалеешь, что многое осталось «за кадром» и надеешься, что еще будут поездки сюда, и не раз. Ибо в Смоленске и его окрестностях посмотреть есть на что.", "Путешествие по области", R.drawable.route_main_1,points, "по области", false,names, desc, imgs));



        points1.add(new Point(54.788716, 32.054347));
        names1.add("Успенский собор");

        points1.add(new Point(54.761055, 32.019618));
        names1.add("Курган Бессмертия");

        points1.add(new Point(54.773408, 32.053649));
        names1.add("Костел");

        points1.add(new Point(54.782356, 32.049970));
        names1.add("Исторический музей");

        points1.add(new Point(54.779508, 32.043382));
        names1.add("Башня громовая");

        points1.add(new Point(54.780408, 32.046113));
        names1.add("Художественная галерея");

        ArrayList<String> desc1 = new ArrayList<>(Arrays.asList(mContext.getResources().getStringArray(R.array.descs2)));
        ArrayList<Integer> imgs1 = new ArrayList<>(Arrays.asList(R.drawable.route_main_2,R.drawable.route1_2,R.drawable.route3_2,R.drawable.route4_2,
                R.drawable.route5_2,R.drawable.route6_2));
        routes.add(new Route("Когда едешь в какой-то новый для себя город на очень короткое время, то всегда возникает проблема: что успеть посмотреть за такой ограниченный срок и при этом не уподобиться загнанному зверю. Потом, конечно, жалеешь, что многое осталось «за кадром» и надеешься, что еще будут поездки сюда, и не раз. Ибо в Смоленске и его окрестностях посмотреть есть на что.", "Прогулка по городу", R.drawable.route_main_2,points1, "по городу", false,names1, desc1, imgs1));




        points2.add(new Point(54.781624, 32.055349));
        names2.add("«Изба»");

        points2.add(new Point(58.686724, 59.490705));
        names2.add("Батутный центр «Космос»");

        points2.add(new Point(48.330867, 38.053336));
        names2.add("Детское кафе «Смешарики»");

        points2.add(new Point(54.771279, 32.042457));
        names2.add("Клуб виртуальной реальности «Рубильник»");

        points2.add(new Point(54.771803, 32.054378));
        names2.add("Смоленский зоопарк");

        points2.add(new Point(54.762127, 32.027147));
        names2.add("ФОК «Юбилейный»");

        ArrayList<String> desc2 = new ArrayList<>(Arrays.asList(mContext.getResources().getStringArray(R.array.descs3)));
        ArrayList<Integer> imgs2 = new ArrayList<>(Arrays.asList(R.drawable.route1_3,R.drawable.route2_3,R.drawable.route3_3,R.drawable.route4_3,
                R.drawable.route5_3,R.drawable.route6_3));
        routes.add(new Route("Здесь вы можете найти информацию о том, куда в Смоленске пойти с ребенком, чем заняться с детьми в выходной или на каникулах, какие детские игровые центры работают в городе, где отметить детский день рождения и какие еще развлечения для детей бывают в Смоленске.\n" +
                "\n" +
                "Наиболее популярные места отдыха с детьми – это парки развлечений, которые чаще всего находятся в крупных торговых центрах и имеют в своем арсенале большое количество игровых автоматов, детские лабиринты, комплекс игр с аниматорами, 6D кинотеатры и многое другое.", "Прогулка по городу", R.drawable.route_main_3,points2, "для детей", false,names2, desc2, imgs2));




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
