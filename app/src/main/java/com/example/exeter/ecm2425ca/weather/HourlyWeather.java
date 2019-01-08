package com.example.exeter.ecm2425ca.weather;

import java.util.GregorianCalendar;

/**
 * Created by Jonas on 03/03/2018.
 */

public class HourlyWeather {

    private City city;
    private Clouds clouds;
    private Humidity humidity;
    private Pressure pressure;
    private Wind wind;
    private Temperature temperature;
    private GregorianCalendar date;
    private Weather weather;
    private HourInterval interval;

    public HourlyWeather(Clouds clouds, Humidity humidity, Pressure pressure, Wind wind, Temperature temperature, GregorianCalendar date, Weather weather, City city){
        this.clouds = clouds;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.temperature = temperature;
        this.date = date;
        this.weather = weather;
        this.city = city;
    }

    /**
     * Method to get the HourInterval
     * of this particular weather forecast
     * @return
     */
    public HourInterval getInterval() {
        return this.interval;
    }

    /**
     * Method to set the HourInterval
     * of this particular weather forecast
     * @param interval
     */
    public void setInterval(HourInterval interval) {
        this.interval = interval;
    }

    /**
     * Method to get the Clouds object
     * of this particular weather forecast
     * @return
     */
    public Clouds getClouds() {
        return this.clouds;
    }

    /**
     * Method to get the Humidity object
     * of this particular weather forecast
     * @return
     */
    public Humidity getHumidity() {
        return this.humidity;
    }

    /**
     * Method to get the Wind object
     * of this particular weather forecast
     * @return
     */
    public Wind getWind() {
        return this.wind;
    }

    /**
     * Method to get the Temperature object
     * of this particular weather forecast
     * @return
     */
    public Temperature getTemperature() {
        return this.temperature;
    }

    /**
     * Method to get the Pressure object
     * of this particular weather forecast
     * @return
     */
    public Pressure getPressure() {
        return this.pressure;
    }

    /**
     * Method to get the City object
     * of this particular weather forecast
     * @return
     */
    public City getCity() {
        return this.city;
    }

    /**
     * Method to get the GregorianCalendar object
     * of this particular weather forecast
     * @return
     */
    public GregorianCalendar getDate() {
        return this.date;
    }

    /**
     * Method to get the Weather object
     * of this particular weather forecast
     * @return
     */
    public Weather getWeather() {
        return this.weather;
    }

    /**
     * Method to set the Clouds
     * of this particular weather forecast
     * @param clouds
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * Method to set the Humidity
     * of this particular weather forecast
     * @param humidity
     */
    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    /**
     * Method to set the Wind
     * of this particular weather forecast
     * @param wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * Method to set the Pressure
     * of this particular weather forecast
     * @param pressure
     */
    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }

    /**
     * Method to set the City
     * of this particular weather forecast
     * @param city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Method to set the GregorianCalendar
     * of this particular weather forecast
     * @param calendar
     */
    public void setCalendar(GregorianCalendar calendar) {
        this.date = calendar;
    }

    /**
     * Method to set the Weather
     * of this particular weather forecast
     * @param weather
     */
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    /**
     * Method to get the date of this
     * forecast in DD/MM/YYYY format
     * @return
     */
    public String getDateString() {
        String year = Integer.toString(this.date.get(GregorianCalendar.YEAR));
        String month = Integer.toString(this.date.get(GregorianCalendar.MONTH) + 1);
        String day = Integer.toString(this.date.get(GregorianCalendar.DAY_OF_MONTH));

        return day + "/" + month + "/" + year;

    }

    /**
     * Method to get a String representing
     * the data of this object
     * @return
     */
    @Override
    public String toString() {
        return this.city.toString() + "\n"
                + this.date.getTime().toString() + "\n"
                + this.weather.toString() + "\n"
                + this.temperature.toString() + "\n"
                + this.clouds.toString() + "\n"
                + this.pressure.toString() + "\n"
                + this.wind.toString() + "\n"
                + this.humidity.toString();
    }

    /**
     * Method to get the WeekDay
     * of this forecast
     * @return
     */
    public WeekDay getWeekDay() {
        int weekday = this.date.get(GregorianCalendar.DAY_OF_WEEK);

        switch (weekday) {
            case 1:

                return WeekDay.SUNDAY;

            case 2:

                return WeekDay.MONDAY;

            case 3:

                return WeekDay.TUESDAY;

            case 4:

                return WeekDay.WEDNESDAY;

            case 5:

                return WeekDay.THURSDAY;

            case 6:

                return WeekDay.FRIDAY;

            case 7:

                return WeekDay.SATURDAY;

            default:
                return null;
        }
    }

    /**
     * Method to set the temperature unit
     * of this forecast
     * @param unit
     */
    public void setTemperatureUnit(TemperatureUnit unit) {
        switch (unit) {

            case Celsius:
                this.temperature.toCelsius();
                break;

            case Fahrenheit:
                this.temperature.toFahrenHeit();
                break;

            case Kelvin:
                this.temperature.toKelvin();
                break;

            default:
                this.temperature.toCelsius();
        }
    }

    /**
     * Method to get a GregorianCalendar object
     * based on the String passed in the DD/MM/YYYY
     * format
     * @param date
     * @param hour
     * @return
     */
    public static GregorianCalendar getCalendar(String date, int hour) {
        String[] dates = date.split("/");

        /*Get the year, month, and day*/
        int year = Integer.parseInt(dates[2]);

        /*Subtract 1 from the month because Gregorian Calendar
        * starts the month count at 0*/
        int month = Integer.parseInt(dates[1]) - 1;
        int day = Integer.parseInt(dates[0]);

        return new GregorianCalendar(year, month, day, hour, 0, 0);
    }
}
