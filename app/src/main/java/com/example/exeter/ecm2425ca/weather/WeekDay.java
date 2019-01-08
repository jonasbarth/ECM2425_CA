package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 24/03/2018.
 */

public enum WeekDay {

    MONDAY("Monday", 0), TUESDAY("Tuesday", 1), WEDNESDAY("Wednesday", 2), THURSDAY("Thursday", 3), FRIDAY("Friday", 4), SATURDAY("Saturday", 5), SUNDAY("Sunday", 6);

    private final String DAY;
    private final int INDEX;

    WeekDay(String day, int index) {
        this.DAY = day;
        this.INDEX = index;
    }

    /**
     * Method to get the first
     * three letters of the String
     * representing the day, e.g. Mon
     * @return
     */
    public String getDay() {
        return this.DAY.substring(0, 3);
    }

    /**
     * Method get the full day as a String
     * e.g., Monday
     * @return
     */
    public String getFullDay() {
        return this.DAY;
    }

    /**
     * Method to get the index of the day
     * @return
     */
    public int getIndex() {
        return this.INDEX;
    }
}
