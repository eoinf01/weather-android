package com.example.eoin_fehily_project_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Weather> mWeather;
    private ArrayList<WeatherData> mWeatherData;
    private ArrayList<HashMap<String, String>> userList;

    private ArrayList<String> users;
    private ArrayList<String> locationNames;
    private DbHandler db;
    private MyRecyclerviewAdapter myRecyclerViewAdapter;
    private RecyclerView recyclerView;
    FloatingActionButton fab;
    private Handler mainHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWeather = new ArrayList<Weather>();
        mWeatherData = new ArrayList<WeatherData>();
        db = new DbHandler(this);
        recyclerView = findViewById(R.id.RV);
        myRecyclerViewAdapter = new MyRecyclerviewAdapter(MainActivity.this, mWeather, users);
        userList = db.GetUsers();
        users = new ArrayList<String>();
        locationNames = new ArrayList<String>();
        myRecyclerViewAdapter = new MyRecyclerviewAdapter(MainActivity.this, mWeather, users);
        Log.println(Log.ERROR, "HIIiiiiiiiiii", userList.toString());
        startThread(userList);

        fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddToDatabaseActivity.class);
                intent.putExtra("locations",users);
                startActivity(intent);
            }
        });

    }

    public void standardJsonObject(String url, String name,String ids) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson1 = new Gson();
                mWeatherData.add((WeatherData) gson1.fromJson(String.valueOf((response)), WeatherData.class));
                mWeather.add(gson1.fromJson(String.valueOf((response)), WeatherData.class).getLatestWeather());
                users.add(name);
                locationNames.add(ids);


                myRecyclerViewAdapter = new MyRecyclerviewAdapter(MainActivity.this, mWeather, users);
                recyclerView.setAdapter(myRecyclerViewAdapter);
                GridLayoutManager linearLayoutManager;
                linearLayoutManager = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Invalid Location ID Added to Database", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue1 = Volley.newRequestQueue(MainActivity.this);
        rQueue1.add(request);
    }// end of standardJsonObject(String url)



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case R.id.option1:
                String nameItem = users.get(item.getGroupId());
                db.DeleteUser(nameItem);
                userList = db.GetUsers();
                mWeather.clear();
                locationNames.clear();
                users.clear();
                startThread(userList);
                return true;
            case R.id.option2:
                Intent intent = new Intent(this,Forecast.class);
                intent.putExtra("name",users.get(item.getGroupId()));
                intent.putExtra("locationID",locationNames.get(item.getGroupId()));
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_database:
                // do your code
                db.ResetDatabase();
                users.clear();
                mWeather.clear();
                userList.clear();
                myRecyclerViewAdapter = new MyRecyclerviewAdapter(this, mWeather, users);
                recyclerView.setAdapter(myRecyclerViewAdapter);
                return true;
            case R.id.changeTheme:
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class getWeather extends Thread{
        ArrayList<HashMap<String, String>> location;
        getWeather(ArrayList<HashMap<String, String>> location){
            this.location = location;
        }
        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < location.size(); i++) {
                        int finalI = i;
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                String url = "https://www.metaweather.com/api/location/" + location.get(finalI).get("WOIED") + "/";
                                standardJsonObject(url, location.get(finalI).get("name"),location.get(finalI).get("WOIED"));
                            }
                        });
                    }
                }
            });
        }

    }
    public void startThread(ArrayList<HashMap<String, String>> location){
        getWeather newThread = new getWeather(location);
        new Thread(newThread).start();
    }
}