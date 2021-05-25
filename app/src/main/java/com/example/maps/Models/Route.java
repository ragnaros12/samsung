package com.example.maps.Models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.yandex.mapkit.geometry.Point;

import java.util.ArrayList;

public class Route {

    private boolean is_bookmark;

    private ArrayList<Point> points;
    private String tags;
    private String desc;
    private String name;
    private Integer drawable;
    private final ArrayList<String> names;
    private final ArrayList<String> descs;
    private final ArrayList<Integer> images;


    public boolean End;

    public boolean isEnd() {
        return End;
    }

    public void setEnd(boolean end) {
        End = end;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Drawable> getImages(Context context) {
        ArrayList<Drawable> drawables = new ArrayList<>();
        for (int i : images){
            drawables.add(context.getDrawable(i));
        }
        return drawables;
    }

    public ArrayList<String> getDescs() {
        return descs;
    }

    public boolean isIs_bookmark() {
        return is_bookmark;
    }

    public void setIs_bookmark(boolean is_bookmark) {
        this.is_bookmark = is_bookmark;
    }

    public String getDesc() {
        return desc;
    }


    public String getTags() {
        return tags;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getDrawable(Context context) {
        return context.getDrawable(drawable);
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }


    public Route(String desc, String name, int drawable, ArrayList<Point> points, String tags, boolean is_bookmark, ArrayList<String> names, ArrayList<String> descs, ArrayList<Integer> images) {
        this.desc = desc;
        this.drawable = drawable;
        this.name = name;
        this.points = points;
        this.tags = tags;
        this.is_bookmark = is_bookmark;
        this.names = names;
        this.descs = descs;
        this.images = images;
        setEnd(false);
    }
    public Route(String desc, String name, int drawable, ArrayList<Point> points, String tags, boolean is_bookmark, ArrayList<String> names, ArrayList<String> descs, ArrayList<Integer> images, boolean End) {
        this.desc = desc;
        this.drawable = drawable;
        this.name = name;
        this.points = points;
        this.tags = tags;
        this.is_bookmark = is_bookmark;
        this.names = names;
        this.descs = descs;
        this.images = images;
        this.End = End;
    }


}
