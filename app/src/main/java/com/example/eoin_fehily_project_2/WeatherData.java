package com.example.eoin_fehily_project_2;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
public class WeatherData {
    @SerializedName("consolidated_weather")

    private ArrayList<Weather> weathers = new ArrayList<>();

    public ArrayList<Weather> getWeathers() {
        return weathers;
    }

    public Weather getLatestWeather(){
        return weathers.get(0);
    }
    public Weather getWeather(int index){
        return  weathers.get(index);
    }

    public void setWeathers(ArrayList<Weather> weathers) {
            this.weathers = weathers;
    }
}