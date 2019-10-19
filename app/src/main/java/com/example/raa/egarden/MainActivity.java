package com.example.raa.egarden;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements GardenSensorListener {


    private FloatingActionButton fabScan;
    private FloatingActionButton fabTutorials;

    private TextView textTemperature;
    private TextView textHumidity;
    private TextView textLuminosity;


    private GardenSensorManager sensors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabScan = findViewById(R.id.fabScan);
        fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        fabTutorials = findViewById(R.id.fabTutorials);
        fabTutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });


        this.textTemperature = findViewById(R.id.textTemperature);
        this.textTemperature.setTextColor(Color.RED);
        this.textTemperature.setText("-");

        this.textHumidity = findViewById(R.id.textHumidity);
        this.textHumidity.setTextColor(Color.RED);
        this.textHumidity.setText("-");

        this.textLuminosity = findViewById(R.id.textLuminosity);
        this.textLuminosity.setTextColor(Color.RED);
        this.textLuminosity.setText("-");

        this.sensors = new GardenSensorManager(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        this.sensors.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.sensors.onPause();
    }



    @Override
    public void onTemperatureChange(float temperature) {

        this.textTemperature.setTextColor(Color.DKGRAY);
        this.textTemperature.setText("" + temperature + "ºC");
    }

    @Override
    public void onLightChange(float light) {

        this.textLuminosity.setTextColor(Color.DKGRAY);
        this.textLuminosity.setText("" + light + " lx");
    }

    @Override
    public void onHumidityChange(float humidity) {

        this.textHumidity.setTextColor(Color.DKGRAY);
        this.textHumidity.setText("" + humidity + "%");
    }
}
