package com.example.raa.egarden;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GardenSensorListener {


    private FloatingActionButton fabScan;
    private TextView textValueTemperature;


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

        this.sensors = new GardenSensorManager(this);

        this.textValueTemperature = findViewById(R.id.textValueTemperature);
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

    }

    @Override
    public void onLightChange(float light) {

    }

    @Override
    public void onHumidityChange(float humidity) {

    }
}
