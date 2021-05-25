package com.example.maps;

import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import com.example.maps.Adapters.Routes;
import com.example.maps.Models.Route;

import java.util.ArrayList;

public class Single {
    public static ArrayList<String> tags;
    public static SharedPreferences settings;
    public static ArrayList<Route> Routes;
    public static RecyclerView RoutesView;
    public static int CurrentRoute;
    public static Routes AdapterRoute;
    public static RecyclerView Type;
    public static SharedPreferences.Editor writer;

    public static ArrayList<String> getTags() {
        return tags;
    }

    public static void setTags(ArrayList<String> tags) {
        Single.tags = tags;
    }

    public static SharedPreferences.Editor getWriter() {
        return writer;
    }

    public static void setWriter(SharedPreferences.Editor writer) {
        Single.writer = writer;
    }

    public static void setSettings(SharedPreferences settings) {
        Single.settings = settings;
    }

    public static SharedPreferences getSettings() {
        return settings;
    }

    public static RecyclerView getType() {
        return Type;
    }

    public static void setType(RecyclerView type) {
        Type = type;
    }

    public static ArrayList<Route> getRoutes() {
        return Routes;
    }

    public static void setRoutes(ArrayList<Route> routes) {
        Routes = routes;
    }

    public static com.example.maps.Adapters.Routes getAdapterRoute() {
        return AdapterRoute;
    }

    public static void setAdapterRoute(com.example.maps.Adapters.Routes adapterRoute) {
        AdapterRoute = adapterRoute;
    }

    public static RecyclerView getRoutesView() {
        return RoutesView;
    }

    public static void setRoutesView(RecyclerView routesView) {
        RoutesView = routesView;
    }

    public static int getCurrentRoute() {
        return CurrentRoute;
    }

    public static void setCurrentRoute(int currentRoute) {
        CurrentRoute = currentRoute;
    }
}
