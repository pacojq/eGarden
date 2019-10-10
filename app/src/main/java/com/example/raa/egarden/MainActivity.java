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

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;

    private FloatingActionButton fabScan;


    private TextView textValueTemperature;


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

        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList	= sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder str = new StringBuilder();

        for	(Sensor	currentSensor	:	sensorList )	{
            str.append(currentSensor.getName()).append(
                    System.getProperty("line.separator"));

            if (currentSensor.getType() == Sensor.TYPE_PRESSURE)
                Log.d("Logger", "BAROMETRO!!!!!");
        }

        TextView available = findViewById(R.id.textAvailable);
        available.setText(str.toString());

        Log.d("Logger", "AVAILABLE SENSORS: " + str.toString());

        this.textValueTemperature = findViewById(R.id.textValueTemperature);
    }



    @Override
    protected void onResume() {
        super.onResume();
        onResumeSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        onResumeSensor(Sensor.TYPE_LIGHT);
        onResumeSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    private void onResumeSensor(int id) {
        Sensor sensor = this.sensorManager.getDefaultSensor(id);
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.sensorManager.unregisterListener(this);
    }





    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()) {

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                onTemperatureChanged(event);
                break;

            case Sensor.TYPE_LIGHT:
                onLightChanged(event);
                break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                onHumidityChanged(event);
                break;
        }
    }


    private void onTemperatureChanged(SensorEvent event) {

        float[] values = event.values;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < values.length; i ++) {
            str.append(values[i]);
            str.append(" ");
        }
        Log.d("Logger", str.toString());
        System.out.println(str.toString());

        this.textValueTemperature .setText("" + values[0] + "º");
        /*
        TextView textView = findViewById(R.id.textViewAccelerometer);
        String strAccelerometer = getResources().getString(R.string.accelerometer);

        float[] values = event.values;
        textView.setText(String.format("%s\nx:%f\ny:%f\nz:%f", strAccelerometer,
                values[0], values[1], values[2]));
        */
    }

    private void onLightChanged(SensorEvent event) {

    }

    private void onHumidityChanged(SensorEvent event) {

    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }





}
