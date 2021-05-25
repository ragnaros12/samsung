package com.example.maps.Maps;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.maps.Next;
import com.example.maps.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.geometry.SubpolylineHelper;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.transport.TransportFactory;
import com.yandex.mapkit.transport.masstransit.PedestrianRouter;
import com.yandex.mapkit.transport.masstransit.Route;
import com.yandex.mapkit.transport.masstransit.Section;
import com.yandex.mapkit.transport.masstransit.Session;
import com.yandex.mapkit.transport.masstransit.TimeOptions;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import java.util.ArrayList;
import java.util.List;

public class RouterByOne implements Session.RouteListener {
    public int curr = 0;
    ArrayList<Point> points;
    Map map;
    PedestrianRouter pedestrianRouter;
    MapObjectCollection mapObject;
    Resources resources;
    TextView width;
    TextView time;
    boolean search = false;
    Next next;
    Next last;

    public RouterByOne(MapView map, Resources resources, ArrayList<Point> points, TextView time, TextView width, Next next, Next last){
        this.mapObject = map.getMap().getMapObjects().addCollection();
        this.map = map.getMap();
        pedestrianRouter = TransportFactory.getInstance().createPedestrianRouter();
        this.resources = resources;
        this.points = points;
        this.time = time;
        this.width = width;
        this.next = next;
        this.last = last;
    }

    public void draw(Point StartPoint){
        ArrayList<RequestPoint> requestPoints = new ArrayList<>();
        requestPoints.add(new RequestPoint(StartPoint, RequestPointType.VIAPOINT, null));
        requestPoints.add(new RequestPoint(points.get(curr), RequestPointType.VIAPOINT, null));
        mapObject.clear();
        mapObject.addPlacemark(StartPoint, ImageProvider.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.my_location)));
        if(!search) {
            map.move(new CameraPosition(StartPoint, 14f, 0.0f, 0.0f), new Animation(Animation.Type.SMOOTH, 5), null);
            search = true;
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
            time.setText(list.get(i1).getMetadata().getWeight().getWalkingDistance().getText());
            width.setText(list.get(i1).getMetadata().getWeight().getTime().getText());
            if(list.get(i1).getMetadata().getWeight().getWalkingDistance().getValue() <= 300) {
                if(points.size() - 1 != curr) {
                    curr++;
                    next.Next();
                }
                else{
                    last.Next();
                }
            }
            else {
                for (Section r : list.get(i1).getSections()) {
                    Polyline polyline = SubpolylineHelper.subpolyline(list.get(i1).getGeometry(), r.getGeometry());
                    PolylineMapObject object = mapObject.addPolyline(polyline);
                    object.setStrokeColor(Color.RED);
                }
            }
        }
    }

    @Override
    public void onMasstransitRoutesError(@NonNull Error error) {

    }
}
