package com.example.raa.egarden;

public class Crop {

    /**
     * STRING FORMAT:
     *      "name-Tm-TM-Hm-HM"
     *
     * name: String name
     * Tm: double tempMin
     * TM: double tempMax
     * Hm: double humidityMin
     * HM: double humidityMax
     *
     * @param serialized
     * @return
     */
    public static Crop FromString(String serialized) {

        String[] split = serialized.split("-");
        if (split.length != 5)
            throw new RuntimeException("Invalid serialized element count.");

        Crop result = new Crop();
        result.name = split[0];

        try {
            result.tempMin = Double.parseDouble(split[1]);
            result.tempMax = Double.parseDouble(split[2]);
            result.humidityMin = Double.parseDouble(split[3]);
            result.humidityMax = Double.parseDouble(split[4]);
        }
        catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    private double tempMin;

    private double tempMax;

    private double humidityMin;

    private double humidityMax;

    private String name;


    private Crop() {

    }

    public String getName() {
        return this.name;
    }

    public double getTempMin() {
        return this.tempMin;
    }

    public double getTempMax() {
        return this.tempMax;
    }

    public double getHumidityMin() {
        return this.humidityMin;
    }

    public double getHumidityMax() {
        return this.humidityMax;
    }

}
