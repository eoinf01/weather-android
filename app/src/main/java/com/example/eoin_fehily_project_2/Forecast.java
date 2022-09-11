package com.example.eoin_fehily_project_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class Forecast extends AppCompatActivity {
    private String name;
    private String id;
    private WeatherData weatherData;
    private ArrayList<Weather> weathers;
    private RecyclerView forecastRV;
    private ForecastRecyclerViewAdapter adapter;
    private String url;
    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        id = bundle.getString("locationID");
        forecastRV = findViewById(R.id.forecastRV);
        setTitle(name +"Forecast");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        url = "https://www.metaweather.com/api/location/" + id + "/";
        startThread();

    }

    public class setWeatherThread extends Thread{
        setWeatherThread(){

        }
        @Override
        public void run() {

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Gson gson1 = new Gson();
                                weatherData = ((WeatherData) gson1.fromJson(String.valueOf((response)), WeatherData.class));
                                weathers = weatherData.getWeathers();
                                weathers.remove(0);
                                adapter = new ForecastRecyclerViewAdapter(Forecast.this, weathers,name);
                                forecastRV.setAdapter(adapter);
                                GridLayoutManager linearLayoutManager;
                                linearLayoutManager = new GridLayoutManager(Forecast.this, 1, GridLayoutManager.VERTICAL, false);
                                forecastRV.setLayoutManager(linearLayoutManager);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Forecast.this, "Volley Error ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        RequestQueue rQueue1 = Volley.newRequestQueue(Forecast.this);
                        rQueue1.add(request);
                    }
                });
            }
        }
        public void startThread(){
            setWeatherThread runnable = new setWeatherThread();
            new Thread(runnable).start();
        }
    }
