package com.example.maps.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maps.Models.Route;
import com.example.maps.R;
import com.example.maps.Single;

import java.util.ArrayList;

public class Types extends RecyclerView.Adapter<Types.ViewHolder1> {

    ArrayList<String> list;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ViewHolder1> viewHolders = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.lsit_of_types , parent, false);
        view.getLayoutParams().width = 500;
        ViewHolder1 viewHolder1 = new ViewHolder1(view);
        viewHolders.add(viewHolder1);
        return viewHolder1;
    }
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        if(position == 0){
            holder.under.setVisibility(View.VISIBLE);
            holder.text.setTextColor(Color.rgb(251,63,81));
        }
        holder.text.setText(list.get(position));
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public Types(Context context, ArrayList<String> list){
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    class ViewHolder1 extends RecyclerView.ViewHolder{
        TextView text;
        CardView under;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.type);
            text.setOnClickListener(this::click_text);
            under = itemView.findViewById(R.id.underline);
        }
        public void click_text(View w){
            for (ViewHolder1 viewHolder1 : viewHolders){
                viewHolder1.under.setVisibility(View.INVISIBLE);
                viewHolder1.text.setTextColor(Color.BLACK);
            }
            under.setVisibility(View.VISIBLE);
            text.setTextColor(Color.rgb(251,63,81));
            String text = (String)((TextView)w).getText();
            if(!text.toLowerCase().equals("все".toLowerCase())) {
                ArrayList<Route> routes = new ArrayList<>();
                for (Route e : Single.getRoutes()) {
                    for (String tags : e.getTags().split(",")) {
                        if (tags.toLowerCase().equals(text.toLowerCase())) {
                            routes.add(e);
                        }
                    }
                }
                if(routes.size() == 0){
                    routes.add(new Route("нет маршрктов, посмотрите информацию о смоленске", "Смоленск" , R.drawable.smolensk, null, "", false, null, null,  null));
                }

                Single.getRoutesView().setAdapter(null);
                Single.getAdapterRoute().setList(routes);
            }
            else{
                Single.getRoutesView().setAdapter(null);
                Single.getAdapterRoute().setList(Single.getRoutes());
            }
            GridLayoutManager GridLayoutManager1 = new GridLayoutManager(itemView.getContext(), 1);
            GridLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            Single.getRoutesView().setLayoutManager(GridLayoutManager1);
            Single.getRoutesView().setAdapter(Single.getAdapterRoute());
        }
    }
}
