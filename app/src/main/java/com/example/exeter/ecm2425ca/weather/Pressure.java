package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 17/03/2018.
 */

public class Pressure {

    private double groundPressure;
    private double seaPressure;
    private final static String UNIT = "hPa";

    public Pressure(double groundPressure, double seaPressure) {
        this.groundPressure = groundPressure;
        this.seaPressure = seaPressure;
    }

    /**
     * Method to set the ground level pressure
     * @param pressure
     */
    public void setGroundPressure(double pressure) {
        this.groundPressure = pressure;
    }

    /**
     * Method to set the sea level pressure
     * @param pressure
     */
    public void setSeaPressure(double pressure) {
        this.seaPressure = pressure;
    }

    /**
     * Method to get the ground level pressure
     * as a double
     * @return
     */
    public double getGroundPressure() {
        return this.groundPressure;
    }

    /**
     * Method to get the sea level pressure
     * as a double
     * @return
     */
    public double getSeaPressure() {
        return this.seaPressure;
    }

    /**
     * Method to get the ground level pressure
     * as a String
     * @return
     */
    public String getGroundPressureString() {
        return Double.toString(this.groundPressure);
    }


    /**
     * Method to get the sea level pressure
     * as a String
     * @return
     */
    public String getSeaPressureString() {
        return Double.toString(this.seaPressure);
    }

    /**
     * Method to get the unit used to represent
     * the pressure levels, i.e. hPa
     * @return
     */
    public String getUnit() {
        return this.UNIT;
    }

    /**
     * Method to get a String containing the
     * data stored in this object
     * @return
     */
    @Override
    public String toString() {
        String ground = Double.toString(this.groundPressure) + this.UNIT;
        String sea = Double.toString(this.seaPressure) + this.UNIT;
        String pressure = "Ground: " + ground + UNIT + " Sea: " + sea + UNIT;
        return pressure;
    }
}
