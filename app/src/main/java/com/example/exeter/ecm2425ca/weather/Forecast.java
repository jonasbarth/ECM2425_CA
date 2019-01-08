package com.example.exeter.ecm2425ca.weather;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Jonas on 18/03/2018.
 */

public class Forecast {

    private Map<WeekDay, DailyWeather> weatherForecast;

    private Context context;

    public Forecast (Context context) {
        this.weatherForecast = new LinkedHashMap<WeekDay, DailyWeather>();
        this.context = context;
    }

    /**
     * Method to get a JSONObject of data
     * and populate weatherForecast Map
     * @param data
     */
    public void populateForecast(JSONObject data) {
        try {
            JSONArray list = data.getJSONArray("list");
            JSONObject city = data.getJSONObject("city");

            /*Iterate over each forecast*/
            for (int i = 0; i < list.length(); i++) {
                JSONObject forecast = list.getJSONObject(i);
                setWeather(forecast, city);
            }
        }
        catch (JSONException e) {
            Toast.makeText(this.context, "Could not load data", Toast.LENGTH_LONG).show();
            Log.d("populateForecast", e.getMessage());
        }

    }

    /**
     * Method to set the weatherForecast Map
     * of this object
     * @param weatherForecast
     */
    public void setWeatherForecast(Map<WeekDay, DailyWeather> weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    /**
     * Method to get the weatherForecast Map
     * of this object
     * @return
     */
    public Map<WeekDay, DailyWeather> getWeatherForecast() {
        return this.weatherForecast;
    }

    /**
     * Method to get DailyWeather object containing the
     * weather forecast for today
     * @return
     */
    public DailyWeather getTodaysWeather() {
        DailyWeather weather = this.weatherForecast.values().iterator().next();

        for (DailyWeather daily : this.weatherForecast.values()) {

            Calendar date = daily.getCurrent().getDate();
            Calendar weatherDate = weather.getCurrent().getDate();

            /*If the date is before the previous one*/
            if (date.before(weatherDate)) {
                weather = daily;
            }
        }
        return weather;
    }

    /**
     * Method to get a Map of WeekDay, DailyWeather
     * key value pairs which does not contain the weather
     * for today
     * @return
     */
    public Map<WeekDay, DailyWeather> getFutureWeather() {

        Map<WeekDay, DailyWeather> future = new LinkedHashMap<WeekDay, DailyWeather>();

        for (WeekDay key : this.weatherForecast.keySet()) {
            DailyWeather weather = this.weatherForecast.get(key);

            if (weather != getTodaysWeather()) {
                future.put(key, this.weatherForecast.get(key));
            }
        }
        return future;
    }

    /**
     * Method to set the temperature unit
     * for the entire forecast
     * @param unit
     */
    public void setTemperatureUnit(TemperatureUnit unit) {
        for (DailyWeather weather : this.weatherForecast.values()) {
            weather.setTemperatureUnit(unit);
        }
    }


    private void setWeather(JSONObject data, JSONObject jsonCity) {
        try {
            int intDate = data.getInt("dt");
            String dateText = data.getString("dt_txt");
            JSONObject jsonMain = data.getJSONObject("main");
            JSONObject jsonWeather = data.getJSONArray("weather").getJSONObject(0);
            JSONObject jsonClouds = data.getJSONObject("clouds");
            JSONObject jsonRain = data.getJSONObject("rain");
            JSONObject jsonWind = data.getJSONObject("wind");

            /*Construct the objects necessary to build a HourlyWeather object*/
            Clouds clouds = constructClouds(jsonClouds);
            Humidity humidity = constructHumidity(jsonMain);
            Wind wind = constructWind(jsonWind);
            Temperature temperature = constructTemperature(jsonMain);
            City city = constructCity(jsonCity);
            Pressure pressure = constructPressure(jsonMain);
            GregorianCalendar date = constructDate(data);
            Weather weather = constructWeather(jsonWeather);

            /*Build the HourlyWeather Object*/
            HourlyWeather w = new HourlyWeather(clouds, humidity, pressure, wind, temperature, date, weather, city);
            putHourlyWeather(w);

            if (this.weatherForecast.size() == 5) {
                Log.d("setWeather", "Make API call for today");
            }

        }
        catch (JSONException e) {
            Toast.makeText(this.context, "Error while reading forecast data", Toast.LENGTH_LONG);
            Log.d("setWeather", e.getMessage());
        }

    }

    /**
     * Method to construct a Cloud object
     * from the JSONObject passed into the
     * method
     * @param clouds
     * @return
     */
    private Clouds constructClouds(JSONObject clouds) {
        try {
            return new Clouds(clouds.getInt("all"));
        }
        catch (JSONException e) {
            Log.d("ForecastClass", e.getMessage());
            return null;
        }
    }

    /**
     * Method to construct a Humidity object
     * from the JSONObject passed into the
     * method
     * @param humidity
     * @return
     */
    private Humidity constructHumidity(JSONObject humidity) {
        try {
            return new Humidity(humidity.getInt("humidity"));
        }
        catch (JSONException e) {
            Log.d("ForecastClass", e.getMessage());
            return null;
        }
    }

    /**
     * Method to construct a Wind object
     * from the JSONObject passed into the
     * method
     * @param wind
     * @return
     */
    private Wind constructWind(JSONObject wind) {
        try {
            return new Wind(wind.getDouble("speed"), wind.getDouble("deg"));
        }
        catch (JSONException e) {
            Log.d("ForecastClass", e.getMessage());
            return null;
        }
    }

    /**
     * Method to construct a Temperature object
     * from the JSONObject passed into the method
     * @param temperature
     * @return
     */
    private Temperature constructTemperature(JSONObject temperature) {
        try {
            return new Temperature(temperature.getDouble("temp_min"), temperature.getDouble("temp_max"), temperature.getDouble("temp"), TemperatureUnit.Kelvin);
        }
        catch (JSONException e) {
            Log.d("ForecastClass", e.getMessage());
            return null;
        }
    }

    /**
     * Method to construct a GregorianCalendar object
     * from the JSONObject passed into the method.
     * It is assumed the JSONObject contains the date string
     * YYYY:MM:DD HH:MM:SS
     * @param date
     * @return
     */
    private GregorianCalendar constructDate(JSONObject date) {
        /*Get the necessary indexes*/
        final int YEAR_INDEX = 4;
        final int MONTH_INDEX = 7;
        final int DAY_INDEX = 10;
        final int HOUR_INDEX = 13;
        final int MIN_INDEX = 16;
        final int SECOND_INDEX = 19;
        try {
            String dateString = date.getString("dt_txt");
            /*Get the ints representing the different values to construct a
            * GregorianCalendar object*/
            int year = Integer.parseInt(dateString.substring(0, YEAR_INDEX));
            int month = Integer.parseInt(dateString.substring(YEAR_INDEX + 1, MONTH_INDEX)) - 1;
            int day = Integer.parseInt(dateString.substring(MONTH_INDEX + 1, DAY_INDEX));
            int hour = Integer.parseInt(dateString.substring(DAY_INDEX + 1, HOUR_INDEX));
            int minute = Integer.parseInt(dateString.substring(HOUR_INDEX + 1, MIN_INDEX));
            int seconds = Integer.parseInt(dateString.substring(MIN_INDEX + 1, SECOND_INDEX));
            return new GregorianCalendar(year, month, day, hour, minute, seconds);
        }
        catch (JSONException e) {
            Log.d("constructDate", e.getMessage());
            return null;
        }
    }

    /**
     * Method to construct a City object
     * from the JSONObject given to the method
     * @param city
     * @return
     */
    private City constructCity(JSONObject city) {
        try {
            JSONObject coord = city.getJSONObject("coord");
            return new City(city.getInt("id"), city.getString("name"), city.getString("country"), coord.getDouble("lon"), coord.getDouble("lat"));
        }
        catch (JSONException e) {
            Log.d("ForecastClass", e.getMessage());
            return null;
        }
    }

    /**
     * Method to construct a Pressure object from
     * the JSONObject given to the method
     * @param pressure
     * @return
     */
    private Pressure constructPressure(JSONObject pressure) {
        try {
            Log.d("constructPressure", "pressure " + pressure.getDouble("grnd_level"));
            return new Pressure(pressure.getDouble("grnd_level"), pressure.getDouble("sea_level"));
        }
        catch (JSONException e) {
            Log.d("ForecastClass", e.getMessage());
            return null;
        }
    }

    /**
     * Method to construct a Weather object
     * from the JSONObject given to the method
     * @param weather
     * @return
     */
    private Weather constructWeather(JSONObject weather) {
        try {
            return new Weather(weather.getInt("id"), weather.getString("main"), weather.getString("description"), weather.getString("icon"));
        }
        catch (JSONException e) {
            Log.d("ForecastClass", e.getMessage());
            return null;
        }
    }

    /**
     * Method to put a DailyWeather object
     * into the Map
     * @param dailyWeather
     */
    public void putDailyWeather(DailyWeather dailyWeather) {
        WeekDay day = dailyWeather.getWeekDay();
        this.weatherForecast.put(day, dailyWeather);
    }

    /**
     * Method to put a HourlyWeather object into the
     * Map
     * @param hourlyWeather
     */
    public void putHourlyWeather(HourlyWeather hourlyWeather) {
        WeekDay day = hourlyWeather.getWeekDay();

        /*If there already exists a DailyWeather object
        * for the same day, add it to that object*/
        if (this.weatherForecast.containsKey(day)) {
            DailyWeather dailyWeather = this.weatherForecast.get(hourlyWeather.getWeekDay());
            dailyWeather.addWeather(hourlyWeather);
        }
        /*If no DailyWeather object exists for
        * the same day, create one and add it*/
        else {
            DailyWeather dailyWeather = new DailyWeather();
            dailyWeather.addWeather(hourlyWeather);
            this.weatherForecast.put(day, dailyWeather);
        }
    }

    /**
     * Method to get a String representing
     * the data of this object
     * @return
     */
    @Override
    public String toString() {
        String result = "";

        for (WeekDay key : this.weatherForecast.keySet()) {
            result += key.getDay() + " " + this.weatherForecast.get(key).toString() + "\n";
        }
        return result;
    }
}

