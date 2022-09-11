package com.example.eoin_fehily_project_2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.MyViewHolder>{
    private ArrayList<Weather> mList;
    private String place;
    private Context mContext;

    public ForecastRecyclerViewAdapter(Forecast context, ArrayList<Weather> mList,String place){
        this.mList = mList;
        this.mContext = context;
        this.place = place;
    }

    @NonNull
    @Override
    public ForecastRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastRecyclerViewAdapter.MyViewHolder holder, int position) {
        String date = mList.get(position).getApplicable_date();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date newdate = format.parse(date);
            String day = new SimpleDateFormat("EEEE").format(newdate);
            holder.forecastDay.setText(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String weather_abbrv = mList.get(position).getWeather_state_abbr();
        String weather = mList.get(position).getWeather_state_name();
        Integer temp = Math.round(mList.get(position).getThe_temp());
        Integer minTemp = Math.round(mList.get(position).getMin_temp());
        Integer maxTemp = Math.round(mList.get(position).getMax_temp());

        holder.forecastType.setText(weather);
        holder.place.setText(date);
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


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        private ImageView icon;
        private TextView place;
        private TextView forecastDay;
        private TextView temp;
        private TextView minTemp;
        private TextView maxTemp;
        private TextView forecastType;

        public MyViewHolder(View view)
        {
            super(view);
            forecastType = view.findViewById(R.id.forecastType);
            forecastDay = view.findViewById(R.id.forecastDay);
            icon= view.findViewById(R.id.forecastWeatherIcon);
            place= view.findViewById(R.id.forecastPlace);
            temp = view.findViewById(R.id.forecastTemperature);
            minTemp = view.findViewById(R.id.forecastMinTemp);
            maxTemp = view.findViewById(R.id.forecastMaxTemp);

        }
    }

}
