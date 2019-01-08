package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 03/03/2018.
 */

public class Clouds {

    private int cloudiness;
    private final char UNIT = '%';

    public Clouds(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    /**
     * Method to the percentage of cloudiness
     * as an int
     * @return
     */
    public int getCloudiness() {
        return this.cloudiness;
    }

    /**
     * Method to get the unit used for the
     * cloudiness, i.e. percentage
     * @return
     */
    public char getUnit() {
        return this.UNIT;
    }

    /**
     * Method to set the cloudiness
     * @param cloudiness
     */
    public void setCloudiness(int cloudiness) {
        this.cloudiness = cloudiness;
    }

    /**
     * Method to get a String containing
     * data saved in this object.
     * @return
     */
    @Override
    public String toString() {
        return Integer.toString(this.cloudiness) + this.UNIT;
    }


}
