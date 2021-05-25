package com.example.maps;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ArrayAdapter;

import com.example.maps.Models.Route;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.search.POIObjectMetadata;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Database {
    static db db;
    static Context context;

    public static void Init(Context context1){
        context = context1;
        db = new db(context);
    }
    public static void Set(ArrayList<Route> routes){
        db.Set(routes);
    }

    public static ArrayList<Route> GetRoutes() {
        return db.Get();
    }
}
