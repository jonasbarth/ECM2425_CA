package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 03/03/2018.
 */

public class Humidity {

    private int value;
    private final char UNIT = '%';

    public Humidity(int value) {
        this.value = value;
    }

    /**
     * Method to get the value of humidity
     * as an int.
     * @return
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Method to get the humidity value
     * as a String
     * @return
     */
    public String getValueString() {
        return Integer.toString(this.value);
    }

    /**
     * Method to get the unit used to represent
     * the humidity, i.e. percent
     * @return
     */
    public char getUnit() {
        return this.UNIT;
    }

    /**
     * Method to get a String containing the
     * data stored in this object-
     * @return
     */
    @Override
    public String toString() {
        return Integer.toString(this.value) + UNIT;
    }
}
