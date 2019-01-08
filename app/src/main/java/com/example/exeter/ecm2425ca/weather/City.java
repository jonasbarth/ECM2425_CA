package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 18/03/2018.
 */

public class City {

    private int id;
    private String name;
    private String country;
    private double lon;
    private double lat;

    public City(int id, String name, String country, double lon, double lat) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.lon = lon;
        this.lat = lat;
    }

    /**
     * Method to get the id of the city
     * as an int
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * Method to get the name of the city
     * as a String
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to get the country of the city
     * as a String
     * @return
     */
    public String country() {
        return this.country;
    }

    /**
     * Method to get the longitude of the city
     * as a double
     * @return
     */
    public double getLon() {
        return this.lon;
    }

    /**
     * Method to get the latitude of the city
     * as a double
     * @return
     */
    public double getLat() {
        return this.lat;
    }

    /**
     * Method to get a String containing the data
     * stored in this object
     * @return
     */
    @Override
    public String toString() {
        String id = Integer.toString(this.id);
        String lon = Double.toString(this.lon);
        String lat = Double.toString(this.lat);
        String city = "ID: " + id + " Name: " + this.name + " Country: " + this.country + " Latitude: " + this.lat + " Longitude: " + this.lon;
        return city;
    }


}
