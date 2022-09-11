package com.example.eoin_fehily_project_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;

public class AddToDatabaseActivity extends AppCompatActivity implements Serializable {
    Button submitButton;
    EditText name,location;
    DbHandler database;
    ArrayList<String> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_database);
        submitButton = findViewById(R.id.button);
        database = new DbHandler(AddToDatabaseActivity.this);
        Bundle bundle = getIntent().getExtras();
        locations = (ArrayList<String>) bundle.get("locations");
        Log.e("ERROR:",locations.toString());
        Spinner spinner = (Spinner) findViewById(R.id.locations_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        name = findViewById(R.id.nameEntry);
        name.setHint("Enter location Name");
        location = findViewById(R.id.locationEntry);
        location.setHint("Enter Location ID");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String spinnerSelection = spinner.getSelectedItem().toString();

                if(!(spinnerSelection.equals("Select location.."))){
                    if(database.checkForLocation(spinnerSelection)){
                        Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Location's weather already added!",Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        switch(spinnerSelection){
                            case "London":
                                database.insertUserDetails(spinnerSelection,"44418");
                                break;
                            case "Paris":
                                database.insertUserDetails(spinnerSelection,"615702");
                                break;
                            case "Dublin":
                                database.insertUserDetails(spinnerSelection,"560743");
                                break;
                            case "Milan":
                                database.insertUserDetails(spinnerSelection,"718345");
                                break;
                            case "Rome":
                                database.insertUserDetails(spinnerSelection,"721943");
                                break;
                            case "Sofia":
                                database.insertUserDetails(spinnerSelection,"839722");
                                break;
                            case "Berlin":
                                database.insertUserDetails(spinnerSelection,"638242");
                                break;
                            case "Geneva":
                                database.insertUserDetails(spinnerSelection,"782538");
                                break;
                            case "Kiev":
                                database.insertUserDetails(spinnerSelection,"924938");
                                break;
                            case "Prague":
                                database.insertUserDetails(spinnerSelection,"796597");
                                break;
                            case "Edinburgh":
                                database.insertUserDetails(spinnerSelection,"19344");
                                break;
                            case "Moscow":
                                database.insertUserDetails(spinnerSelection,"2122265");
                                break;
                            case "Bucharest":
                                database.insertUserDetails(spinnerSelection,"868274");
                                break;
                        }
                        Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Location's weather added!",Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }

                }
                else{
                    if(!(name.getText().toString().equals("") && location.getText().toString().equals(""))){
                        if(database.checkForLocation(name.getText().toString())){
                            Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Location's weather already added!",Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            database.insertUserDetails(name.getText().toString(),location.getText().toString());
                            Intent intent = new Intent(AddToDatabaseActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        Snackbar.make(findViewById(R.id.myCoordinatorLayout),"Please enter location name and location id",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}