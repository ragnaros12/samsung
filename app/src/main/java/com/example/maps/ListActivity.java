package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.example.maps.Adapters.Routes;
import com.example.maps.Adapters.Types;
import com.example.maps.Models.Route;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    public ArrayList<ImageView> imageViews = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        imageViews.add(findViewById(R.id.home));
        imageViews.add(findViewById(R.id.bookmark));
        imageViews.add(findViewById(R.id.end));

        Routes routes = new Routes(getApplicationContext());

        routes.setList(Single.getRoutes());

        Single.setAdapterRoute(routes);

        RecyclerView Routes = findViewById(R.id.first_adapter);
        Single.setRoutesView(Routes);

        GridLayoutManager GridRoutes = new GridLayoutManager(getApplicationContext(), 1);
        GridRoutes.setOrientation(LinearLayoutManager.VERTICAL);
        Routes.setLayoutManager(GridRoutes);
        Routes.setAdapter(routes);


        Types Types = new Types(getApplicationContext(), Single.getTags());

        RecyclerView Type = findViewById(R.id.categories);
        Single.setType(Type);
        GridLayoutManager GridTypes = new GridLayoutManager(getApplicationContext(), 1);
        GridTypes.setOrientation(LinearLayoutManager.HORIZONTAL);
        Type.setLayoutManager(GridTypes);

        Type.setAdapter(Types);
    }
    public void click_home(View w){
        Single.getAdapterRoute().setList(Single.getRoutes());
        Single.getRoutesView().setAdapter(Single.getAdapterRoute());
        ((RecyclerView)findViewById(R.id.categories)).setVisibility(View.VISIBLE);
        imageViews.get(1).setImageResource(R.drawable.bookmark);
        imageViews.get(2).setImageResource(R.drawable.finish);
        imageViews.get(0).setImageResource(R.drawable.home1);
    }
    public void click_book(View w){
        ArrayList<Route> routes = new ArrayList<>();
        for (Route route : Single.getRoutes()) {
            if(route.isIs_bookmark()){
                routes.add(route);
            }
        }
        Single.getAdapterRoute().setList(routes);
        Single.getRoutesView().setAdapter(Single.getAdapterRoute());
        imageViews.get(2).setImageResource(R.drawable.finish);
        imageViews.get(1).setImageResource(R.drawable.bookmarkred);
        ((RecyclerView)findViewById(R.id.categories)).setVisibility(View.INVISIBLE);
        imageViews.get(0).setImageResource(R.drawable.home);
    }

    public void click_end(View w){
        ArrayList<Route> routes = new ArrayList<>();
        for (Route route : Single.getRoutes()) {
            if(route.isEnd()){
                routes.add(route);
            }
        }
        Single.getAdapterRoute().setList(routes);
        Single.getRoutesView().setAdapter(Single.getAdapterRoute());
        imageViews.get(2).setImageResource(R.drawable.finish1);
        imageViews.get(1).setImageResource(R.drawable.bookmark);
        ((RecyclerView)findViewById(R.id.categories)).setVisibility(View.INVISIBLE);
        imageViews.get(0).setImageResource(R.drawable.home);
    }
}
