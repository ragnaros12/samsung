package com.example.maps.Maps;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.maps.Start;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.search.POIObjectMetadata;
import com.yandex.mapkit.transport.TransportFactory;
import com.yandex.mapkit.transport.masstransit.PedestrianRouter;
import com.yandex.mapkit.transport.masstransit.Route;
import com.yandex.mapkit.transport.masstransit.Session;
import com.yandex.mapkit.transport.masstransit.TimeOptions;
import com.yandex.runtime.Error;

import java.util.ArrayList;
import java.util.List;

public class RouterWhitoutMaps implements Session.RouteListener {
    PedestrianRouter pedestrianRouter;
    TextView time;
    TextView width;
    Point start;
    ArrayList<Point> points;
    Context context;


    public RouterWhitoutMaps(ArrayList<Point> points, TextView time, TextView width, Context context){
        pedestrianRouter = TransportFactory.getInstance().createPedestrianRouter();
        this.points = points;
        this.time = time;
        this.width = width;
        this.context = context;
    }

    public void draw(Point StartPoint){
        start = StartPoint;
        ArrayList<RequestPoint> requestPoints = new ArrayList<>();
        requestPoints.add(new RequestPoint(StartPoint, RequestPointType.WAYPOINT, null));
        for (int i = 0; i < points.size(); i++) {
            requestPoints.add(new RequestPoint(points.get(i), RequestPointType.WAYPOINT, null));
        }
        pedestrianRouter.requestRoutes(requestPoints, new TimeOptions(), this);
    }
    @Override
    public void onMasstransitRoutes(@NonNull List<Route> list) {
        if(list.size() != 0) {
            int i1 = 0;
            for (int i = 0; i < list.size();i++){
                if(list.get(i1).getMetadata().getWeight().getTime().getValue() > list.get(i).getMetadata().getWeight().getTime().getValue()){
                    i1 = i;
                }
            }
            if(time != null){
                time.setText(list.get(i1).getMetadata().getWeight().getTime().getText());

            }
            if(width != null){
                width.setText(list.get(i1).getMetadata().getWeight().getWalkingDistance().getText());
            }
        }
    }

    @Override
    public void onMasstransitRoutesError(@NonNull Error error) {
        draw(start);
    }
}
