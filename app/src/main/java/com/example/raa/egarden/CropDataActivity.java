package com.example.raa.egarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CropDataActivity extends AppCompatActivity {

    private Crop crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_data);

        Intent myIntent = getIntent();
        String serialized = myIntent.getStringExtra("serialized");

        setCrop(Crop.FromString(serialized));
    }

    private void setCrop(Crop crop) {

        this.crop = crop;

        TextView cropName = findViewById(R.id.textCropName);
        cropName.setText(crop.getName());

        TextView textTemperatureMinMax = findViewById(R.id.textTemperatureMinMax);
        textTemperatureMinMax.setText(String.format("Ideal values: %sº - %sº",
                crop.getTempMin(), crop.getTempMax()));

        TextView textHumidityMinMax = findViewById(R.id.textHumidityMinMax);
        textHumidityMinMax.setText(String.format("Ideal values: %s - %s",
                "" + crop.getHumidityMin() + "%", "" + crop.getHumidityMax() + "%"));
    }




    private void displaySensorValues()
    {
        // TODO
    }
}
