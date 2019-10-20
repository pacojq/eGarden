package com.example.raa.egarden;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CropDataActivity extends AppCompatActivity implements GardenSensorListener {

    private Crop crop;
    private GardenSensorManager sensors;


    private TextView cropName;

    private ProgressBar progressLuminosity;
    private TextView textLuminosity;
    private ImageView imgLuminosity;

    private ProgressBar progressTemperature;
    private TextView textTemperatureMinMax;
    private TextView textTemperature;
    private ImageView imgTemperature;

    private ProgressBar progressHumidity;
    private TextView textHumidityMinMax;
    private TextView textHumidity;
    private ImageView imgHumidity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_data);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent myIntent = getIntent();
        String serialized = myIntent.getStringExtra("serialized");

        setCrop(Crop.FromString(serialized));

        this.sensors = new GardenSensorManager(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(CropDataActivity.this, ScanActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
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
        this.textTemperatureMinMax.setText(String.format("%s %sº - %sº",
                getString(R.string.ideal_range), crop.getTempMin(), crop.getTempMax()));

        this.progressTemperature = findViewById(R.id.progressTemperature);
        this.imgTemperature = findViewById(R.id.imgTemperature);


        this.textHumidity = findViewById(R.id.textHumidity);
        this.textHumidity.setTextColor(Color.RED);
        this.textHumidity.setText("-");

        this.textHumidityMinMax = findViewById(R.id.textHumidityMinMax);
        this.textHumidityMinMax.setText(String.format("%s %s%s - %s%s",
                getString(R.string.ideal_range), crop.getHumidityMin(), "%", crop.getHumidityMax(), "%"));

        this.progressHumidity = findViewById(R.id.progressHumidity);
        this.imgHumidity = findViewById(R.id.imgHumidity);



        this.textLuminosity = findViewById(R.id.textLuminosity);
        this.textLuminosity.setTextColor(Color.RED);
        this.textLuminosity.setText("-");
        this.progressLuminosity = findViewById(R.id.progressLuminosity);
        this.imgLuminosity = findViewById(R.id.imgLuminosity);
    }





    @Override
    public void onTemperatureChange(float temperature) {

        this.textTemperature.setTextColor(Color.DKGRAY);
        this.textTemperature.setText("" + temperature + "ºC");

        this.progressTemperature.setProgress((int) temperature);

        if (temperature < this.crop.getTempMin() || temperature > this.crop.getTempMax()) {

            this.imgTemperature.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_cross_24dp)
                );

            Drawable progressDrawable = this.progressTemperature.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            this.progressTemperature.setProgressDrawable(progressDrawable);

            this.textTemperature.setTextColor(Color.RED);
        }
        else {
            this.imgTemperature.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_check_24dp)
                );

            Drawable progressDrawable = this.progressTemperature.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            this.progressTemperature.setProgressDrawable(progressDrawable);
        }
    }

    @Override
    public void onLightChange(float light) {

        this.textLuminosity.setTextColor(Color.DKGRAY);
        this.textLuminosity.setText("" + light + " lx");

        this.progressLuminosity.setProgress((int) light);
        float percent = light / this.progressLuminosity.getMax();

        if (percent > 0.3f) {

            this.imgLuminosity.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_check_24dp)
                );

            Drawable progressDrawable = this.progressLuminosity.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            this.progressLuminosity.setProgressDrawable(progressDrawable);
        }
        else {
            this.imgLuminosity.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_cross_24dp)
                );

            Drawable progressDrawable = this.progressLuminosity.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            this.progressLuminosity.setProgressDrawable(progressDrawable);

            this.textLuminosity.setTextColor(Color.RED);
        }
    }

    @Override
    public void onHumidityChange(float humidity) {

        this.textHumidity.setTextColor(Color.DKGRAY);
        this.textHumidity.setText("" + humidity + "%");

        this.progressHumidity.setProgress((int) humidity);

        if (humidity < this.crop.getHumidityMin() || humidity > this.crop.getHumidityMax()) {

            this.imgHumidity.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_cross_24dp)
                );

            Drawable progressDrawable = this.progressHumidity.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            this.progressHumidity.setProgressDrawable(progressDrawable);

            this.textHumidity.setTextColor(Color.RED);
        }
        else {
            this.imgHumidity.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.ic_check_24dp)
                );

            Drawable progressDrawable = this.progressHumidity.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            this.progressHumidity.setProgressDrawable(progressDrawable);
        }
    }
}
