package com.example.raa.egarden;

public interface GardenSensorListener {

    public void onTemperatureChange(float temperature);


    public void onLightChange(float light);


    public void onHumidityChange(float humidity);
}
