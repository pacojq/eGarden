package com.example.raa.egarden;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CropDataActivity extends AppCompatActivity implements GardenSensorListener {

    private Crop crop;
    private GardenSensorManager sensors;


    private TextView cropName;

    private ProgressBar progressLuminosity;
    private TextView textLuminosity;

    private ProgressBar progressTemperature;
    private TextView textTemperatureMinMax;
    private TextView textTemperature;

    private ProgressBar progressHumidity;
    private TextView textHumidityMinMax;
    private TextView textHumidity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_data);

        Intent myIntent = getIntent();
        String serialized = myIntent.getStringExtra("serialized");

        setCrop(Crop.FromString(serialized));

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


    private void setCrop(Crop crop) {

        this.crop = crop;

        this.cropName = findViewById(R.id.textCropName);
        this.cropName.setText(crop.getName());


        this.textTemperature = findViewById(R.id.textTemperature);
        this.textTemperature.setTextColor(Color.RED);
        this.textTemperature.setText("-");

        this.textTemperatureMinMax = findViewById(R.id.textTemperatureMinMax);
        this.textTemperatureMinMax.setText(String.format("Ideal values: %sº - %sº",
                crop.getTempMin(), crop.getTempMax()));

        this.progressTemperature = findViewById(R.id.progressTemperature);



        this.textHumidity = findViewById(R.id.textHumidity);
        this.textHumidity.setTextColor(Color.RED);
        this.textHumidity.setText("-");

        this.textHumidityMinMax = findViewById(R.id.textHumidityMinMax);
        this.textHumidityMinMax.setText(String.format("Ideal values: %s - %s",
                "" + crop.getHumidityMin() + "%", "" + crop.getHumidityMax() + "%"));

        this.progressHumidity = findViewById(R.id.progressHumidity);



        this.textLuminosity = findViewById(R.id.textLuminosity);
        this.textLuminosity.setTextColor(Color.RED);
        this.textLuminosity.setText("-");

        this.progressLuminosity = findViewById(R.id.progressLuminosity);
    }





    @Override
    public void onTemperatureChange(float temperature) {

        float celsius = temperature - 273;
        this.textTemperature.setTextColor(Color.DKGRAY);
        this.textTemperature.setText("" + celsius + "ºC");

        this.progressTemperature.setProgress((int) celsius);
    }

    @Override
    public void onLightChange(float light) {

        this.textLuminosity.setTextColor(Color.DKGRAY);
        this.textLuminosity.setText("" + light);

        this.progressLuminosity.setProgress((int) light);
    }

    @Override
    public void onHumidityChange(float humidity) {

        this.textHumidity.setTextColor(Color.DKGRAY);
        this.textHumidity.setText("" + humidity + "%");

        this.progressHumidity.setProgress((int) humidity);
    }
}
