package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 03/03/2018.
 */

public class Wind {

    private double speed;
    private final static String SPEED_NAME = "m/s";
    private double degrees;


    public Wind(double speed, double degrees) {
        this.speed = speed;
        this.degrees = degrees;
    }

    /**
     * Method to get the wind speed
     * as a double
     * @return
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Method to get the measurement
     * unit of the wind, i.e. m/s
     * @return
     */
    public String getSpeedName() {
        return this.SPEED_NAME;
    }

    /**
     * Method to get the degrees of the degrees
     * of the wind as a double
     * @return
     */
    public double getDegrees() {
        return this.degrees;
    }

    /**
     * Method to get the speed of the wind
     * as a String
     * @return
     */
    public String getSpeedString() {
        return Double.toString(this.speed);
    }

    /**
     * Method to get the degrees of the wind
     * as a String
     * @return
     */
    public String getDegreesString() {
        return Double.toString(this.degrees);
    }

    /**
     * Method to get a String representing
     * the data of this object
     * @return
     */
    @Override
    public String toString() {
        String speed = Double.toString(this.speed) + SPEED_NAME;
        String degrees = Double.toString(this.degrees);
        String wind = "Speed: " + speed + " Degrees:" + degrees;
        return wind;

    }

}
