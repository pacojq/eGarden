package com.example.raa.egarden;

public class Crop {

    /**
     * STRING FORMAT:
     *      "name-TmD-TMD-Hm-HM"
     *
     * name: String name
     * TmD: double tempMinDay
     * TMD: double tempMaxDay
     * Hm: double humidityMin
     * HM: double humidityMax
     *
     * @param serialized
     * @return
     */
    public static Crop FromString(String serialized) {

        Crop result = new Crop();



        return result;
    }


    // Minimum temperature during day
    double tempMinDay;

    // Maximum temperature during day
    double tempMaxDay;

    // Minimum humidity
    double humidityMin;

    // Maximum humidity
    double humidityMax;

    private Crop() {

    }


}
