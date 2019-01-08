package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 24/03/2018.
 */

public enum HourInterval {

    ZERO(0), THREE(3), SIX(6), NINE(9), TWELVE(12), FIFTEEN(15), EIGHTEEN(18), TWENTY_ONE(21);

    private final int HOUR;
    HourInterval(int hour) {
        this.HOUR = hour;
    }

    /**
     * Method to get the
     * hour as an int
     * @return
     */
    public int getHour() {
        return this.HOUR;
    }

    /**
     * Method to get the hour
     * as a String with :00 at the end
     * @return
     */
    public String getHourString() {
        return Integer.toString(this.HOUR) + ":00";
    }
}
