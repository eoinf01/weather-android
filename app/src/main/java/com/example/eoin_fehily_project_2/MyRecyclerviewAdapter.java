package com.example.eoin_fehily_project_2;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.MyViewHolder>{
    private ArrayList<Weather> mList;
    private ArrayList<String> mLocations;
    private Context mContext;

    public MyRecyclerviewAdapter(MainActivity context, ArrayList<Weather> mList, ArrayList<String> locationList){
        this.mList = mList;
        this.mContext = context;
        this.mLocations = locationList;
    }

    @NonNull
    @Override
    public MyRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerviewAdapter.MyViewHolder holder, int position) {
        String weather_abbrv = mList.get(position).getWeather_state_abbr();
        String weather = mList.get(position).getWeather_state_name();
        String placename = mLocations.get(position);
        Integer temp = Math.round(mList.get(position).getThe_temp());
        Integer minTemp = Math.round(mList.get(position).getMin_temp());
        Integer maxTemp = Math.round(mList.get(position).getMax_temp());

        holder.weather.setText(weather);
        holder.placeName.setText(placename);
        holder.temp.setText(temp.toString());
        holder.minTemp.setText("Min: " +minTemp.toString());
        holder.maxTemp.setText("Max: " + maxTemp.toString());
        String iconURL = "https://www.metaweather.com/static/img/weather/png/" + weather_abbrv +".png";

        GlideApp.with(this.mContext)
                .load(iconURL)
                .fitCenter()
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void removeItem(int position){
        mLocations.remove(position);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {

        private CardView cards;
        private ImageView icon;
        private TextView weather;
        private TextView placeName;
        private TextView temp;
        private TextView minTemp;
        private TextView maxTemp;

        public MyViewHolder(View view)
        {
            super(view);
            cards = view.findViewById(R.id.card_view);
            placeName = view.findViewById(R.id.placeName);
            icon= view.findViewById(R.id.weatherIcon);
            weather= view.findViewById(R.id.weatherType);
            temp = view.findViewById(R.id.temperature);
            minTemp = view.findViewById(R.id.minTemp);
            maxTemp = view.findViewById(R.id.maxTemp);
            cards.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getBindingAdapterPosition(),R.id.option1,0,"Delete from Database");
            contextMenu.add(getBindingAdapterPosition(),R.id.option2,0,"See 5 day forecast");
        }
    }

}
