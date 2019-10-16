package com.example.raa.egarden;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class GardenSensorManager<T extends AppCompatActivity, GardenSensorListener>
            implements SensorEventListener {

    private SensorManager sensorManager;

    private T activity;


    public GardenSensorManager(T activity) {
        this.activity = activity;

        this.sensorManager = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);
        List<Sensor> sensorList	= sensorManager.getSensorList(Sensor.TYPE_ALL);


        StringBuilder str = new StringBuilder();

        for	(Sensor	currentSensor : sensorList) {
            str.append(currentSensor.getName())
               .append(System.getProperty("line.separator"));
        }

        Log.d("Logger", "AVAILABLE SENSORS: " + str.toString());
    }



    protected void onResume() {
        onResumeSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        onResumeSensor(Sensor.TYPE_LIGHT);
        onResumeSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    private void onResumeSensor(int id) {
        Sensor sensor = this.sensorManager.getDefaultSensor(id);
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        this.sensorManager.unregisterListener(this);
    }




    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < values.length; i ++) {
            str.append(values[i]);
            str.append(" ");
        }

        switch (event.sensor.getType()) {

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                System.out.println("TEMP: " + str.toString());

                // v v    WTF Java are you kidding me      v v
                ((com.example.raa.egarden.GardenSensorListener) activity).onTemperatureChange(values[0]);
                break;

            case Sensor.TYPE_LIGHT:
                System.out.println("LIGHT: " + str.toString());
                ((com.example.raa.egarden.GardenSensorListener) activity).onLightChange(values[0]);
                break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                System.out.println("HUMIDITY: " + str.toString());
                ((com.example.raa.egarden.GardenSensorListener) activity).onHumidityChange(values[0]);
                break;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
