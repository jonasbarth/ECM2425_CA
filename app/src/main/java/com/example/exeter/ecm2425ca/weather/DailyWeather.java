package com.example.exeter.ecm2425ca.weather;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Jonas on 24/03/2018.
 */

public class DailyWeather {

    private Map<HourInterval,HourlyWeather> weatherMap;

    public DailyWeather() {
        this.weatherMap = new LinkedHashMap<HourInterval, HourlyWeather>();
    }

    /**
     * Method to add a HourlyWeather object
     * to the weatherMap Map
     * @param key
     * @param value
     */
    public void addWeather(HourInterval key, HourlyWeather value) {
        this.weatherMap.put(key, value);
    }

    /**
     * Method to add a HourlyWeather object
     * to the weatherMap Map
     * @param value
     */
    public void addWeather(HourlyWeather value) {
        int hour = value.getDate().getTime().getHours();

        /*Determine the HourInterval key of this HourlyWeather object*/
        switch (hour) {

            case (0) :
                value.setInterval(HourInterval.ZERO);
                this.weatherMap.put(HourInterval.ZERO, value);
                break;

            case (3) :
                value.setInterval(HourInterval.THREE);
                this.weatherMap.put(HourInterval.THREE, value);
                break;
            case (6) :
                value.setInterval(HourInterval.SIX);
                this.weatherMap.put(HourInterval.SIX, value);
                break;

            case (9) :
                value.setInterval(HourInterval.NINE);
                this.weatherMap.put(HourInterval.NINE, value);
                break;

            case (12):
                value.setInterval(HourInterval.TWELVE);
                this.weatherMap.put(HourInterval.TWELVE, value);
                break;

            case (15) :
                value.setInterval(HourInterval.FIFTEEN);
                this.weatherMap.put(HourInterval.FIFTEEN, value);
                break;

            case (18) :
                value.setInterval(HourInterval.EIGHTEEN);
                this.weatherMap.put(HourInterval.EIGHTEEN, value);
                break;

            case (21) :
                value.setInterval(HourInterval.TWENTY_ONE);
                this.weatherMap.put(HourInterval.TWENTY_ONE, value);
                break;
        }
    }

    /**
     * Get the Map of HourInterval, HourlyWeather
     * key value pairs
     * @return
     */
    public Map<HourInterval, HourlyWeather> getWeatherMap() {
        return this.weatherMap;
    }

    /**
     * Get the HourlyWeather object associated
     * with the HourInterval key
     * @param key
     * @return
     */
    public HourlyWeather getHourlyWeather(HourInterval key) {
        return this.weatherMap.get(key);
    }

    /**
     * Get the WeekDay enum
     * associated with this daily forecast
     * @return
     */
    public WeekDay getWeekDay() {
        return this.weatherMap.entrySet().iterator().next().getValue().getWeekDay();
    }

    /**
     * Method to get String representing
     * the data of this object
     * @return
     */
    @Override
    public String toString() {
        String result = "";
        for (HourInterval time : this.weatherMap.keySet()) {
            result += this.weatherMap.get(time).toString() + "\n";
        }
        return result;
    }

    /**
     * Method to get the HourlyWeather object
     * with the highest temperature for this
     * day
     * @return
     */
    public HourlyWeather getMaxTemp() {
        double temp = 0.0;
        HourlyWeather maxWeather = null;
        for (HourlyWeather weather : this.weatherMap.values()) {
            if (weather.getTemperature().getMax() > temp) {
                temp = weather.getTemperature().getMax();
                maxWeather = weather;
            }
        }
        return maxWeather;
    }

    /**
     * Get the most recent HourlyWeather object.
     * Because this design uses a LinkedHashMap
     * it will be the very first object
     * @return
     */
    public HourlyWeather getCurrent() {
        return this.weatherMap.values().iterator().next();
    }

    /**
     * Method to set the temperature unit of
     * all HourlyWeather objects
     * @param unit
     */
    public void setTemperatureUnit(TemperatureUnit unit) {
        for (HourlyWeather weather : this.weatherMap.values()) {
            weather.setTemperatureUnit(unit);
        }
    }

}
