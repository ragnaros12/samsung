package com.example.maps.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maps.Database;
import com.example.maps.Models.Route;
import com.example.maps.R;
import com.example.maps.Single;
import com.example.maps.Start;

import java.util.ArrayList;

public class Routes extends RecyclerView.Adapter<Routes.ViewHolder> {

    ArrayList<Route> list;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ViewHolder> viewHolders = new ArrayList<>();

    public ArrayList<ViewHolder> getViewHolders() {
        return viewHolders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.element_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.image.setImageDrawable(list.get(position).getDrawable(context));
        holder.pos = position;
        if(list.get(position).getDesc().length() > 139){
            holder.desc.setText(list.get(position).getDesc().substring(0, 130) + "...");
        }
        else{
            holder.desc.setText(list.get(position).getDesc());
        }
        if(list.get(position).isIs_bookmark()) {
            holder.bookmark.setImageResource(R.drawable.bookmarkred);
        }
        else{
            holder.bookmark.setImageResource(R.drawable.bookmark);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getPoints() != null) {
                    Single.setCurrentRoute(position);
                    Intent i = new Intent(context, Start.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else{
                    Toast.makeText(context, "это не маршрут", Toast.LENGTH_LONG).show();
                }
            }
        });
        if(list.get(position).isEnd()){
            holder.end.setText("Завершен");
            holder.end.setTextColor(Color.rgb(251, 63, 81));
        }
        else{
            holder.end.setText("");
        }
    }

    public void setList(ArrayList<Route> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Routes(Context context){
        this.list = Single.getRoutes();
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;
        TextView end;
        TextView desc;
        ImageView bookmark;
        int pos = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            end = itemView.findViewById(R.id.end);
            bookmark = itemView.findViewById(R.id.bookmark);
            name = itemView.findViewById(R.id.name_element);
            desc = itemView.findViewById(R.id.desciption_element);
            image = itemView.findViewById(R.id.image_element);
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Single.getRoutes().get(pos).setIs_bookmark(!Single.getRoutes().get(pos).isIs_bookmark());
                    Database.Set(Single.getRoutes());
                    if(Single.getRoutes().get(pos).isIs_bookmark()){
                        bookmark.setImageResource(R.drawable.bookmarkred);
                    }
                    else{
                        bookmark.setImageResource(R.drawable.bookmark);
                    }

                }
            });
        }
    }
}
