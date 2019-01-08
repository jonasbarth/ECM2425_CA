package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 24/03/2018.
 */

public class Weather {

    private int id;
    private String mainDescription;
    private String detailedDescription;
    private String icon;

    public Weather(int id, String mainDescription, String detailedDescription, String icon) {
        this.id = id;
        this.mainDescription = mainDescription;
        this.detailedDescription = capitalise(detailedDescription);
        this.icon = icon;
    }

    /**
     * Method to get the id of the
     * weather as an int
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * Method to get the main description
     * of the weather, e.g. Rain
     * @return
     */
    public String getMainDescription() {
        return this.mainDescription;
    }

    /**
     * Method to get the detailed description
     * of the weather, e.g. Light Rain
     * @return
     */
    public String getDetailedDescription() {
        return this.detailedDescription;
    }

    /**
     * Method to get the icon name
     * as a String
     * @return
     */
    public String getIcon() {
        return this.icon;
    }

    /**
     * Method to get a String representing
     * the data in this object
     * @return
     */
    @Override
    public String toString() {
        String id = Integer.toString(this.id);
        return "Id: " + id + " Main: " + this.mainDescription + " Description: " + this.detailedDescription + " Icon: " + icon;
    }

    /**
     * Helper method to capitalise
     * the first letter of every word
     * in this given sentence
     * @param sentence
     * @return
     */
    private String capitalise(String sentence) {
        if (sentence == null) {
            return null;
        }
        String[] array = sentence.split(" ");
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            buffer.append(Character.toUpperCase(array[i].charAt(0)))
                    .append(array[i].substring(1)).append(" ");
        }
        return buffer.toString().trim();
    }
}
