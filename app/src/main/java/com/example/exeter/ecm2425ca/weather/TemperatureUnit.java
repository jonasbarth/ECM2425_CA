package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 25/03/2018.
 */

public enum TemperatureUnit {

    Celsius("Celsius"), Fahrenheit("Fahrenheit"), Kelvin("Kelvin");

    private final String UNIT;
    TemperatureUnit(String unit) {
        this.UNIT = unit;
    }

    /**
     * Method to get the full unit name
     * as a String, e.g. Celsius
     * @return
     */
    public String getFullUnit() {
        return this.UNIT;
    }

    /**
     * Method to get the first
     * letter of the unit name,
     * e.g. C
     * @return
     */
    public String getUnit() {
        return this.UNIT.substring(0, 1);
    }

    /**
     * Static method to find the
     * unit corresponding to the String
     * passed in
     * @param unit
     * @return
     */
    public static TemperatureUnit findUnit(String unit) {

        switch (unit) {
            case "C":
                return TemperatureUnit.Celsius;

            case "F":
                return TemperatureUnit.Fahrenheit;

            case "K":
                return TemperatureUnit.Kelvin;

            default:
                return TemperatureUnit.Kelvin;
        }
    }
}
