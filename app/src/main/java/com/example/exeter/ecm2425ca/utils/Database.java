package com.example.exeter.ecm2425ca.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.exeter.ecm2425ca.weather.City;
import com.example.exeter.ecm2425ca.weather.Clouds;
import com.example.exeter.ecm2425ca.weather.DailyWeather;
import com.example.exeter.ecm2425ca.weather.Forecast;
import com.example.exeter.ecm2425ca.weather.HourInterval;
import com.example.exeter.ecm2425ca.weather.HourlyWeather;
import com.example.exeter.ecm2425ca.weather.Humidity;
import com.example.exeter.ecm2425ca.weather.Pressure;
import com.example.exeter.ecm2425ca.weather.Temperature;
import com.example.exeter.ecm2425ca.weather.TemperatureUnit;
import com.example.exeter.ecm2425ca.weather.Weather;
import com.example.exeter.ecm2425ca.weather.WeekDay;
import com.example.exeter.ecm2425ca.weather.Wind;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonas on 17/03/2018.
 */

public class Database {
    private SQLiteDatabase database;
    private String path;
    private Context context;

    public Database(Context context, String path) {
        this.context = context;
        this.path = path;
    }

    /**
     * Method to set the context.
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Method to reset the context of this class.
     * Sets the context to null.
     */
    public void resetContext() {
        this.context = null;
    }

    /**
     * Method to delete the database of
     * this object. A toast is shown
     * upon deletion.
     */
    public void deleteDatabase() {
        this.context.deleteDatabase(this.path);
        Toast.makeText(this.context, "Database successfully deleted ", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to add a city to the cities table
     * in the database
     * @param cityID The id of the city
     * @param cityName The name of the city
     * @param country The country of the city
     * @param lon The longitude of the city
     * @param lat The latitude of the city
     */
    public void addCity(int cityID, String cityName, String country, double lon, double lat) {

        ContentValues values = new ContentValues();
        values.put("ID", cityID);
        values.put("name", cityName);
        values.put("country", country);
        values.put("lon", lon);
        values.put("lat", lat);
        this.database.insert("cities", null, values);
    }


    /**
     * Method to create the cities table
     * and insert the cities specified in
     * assets/cities.json into the database
     */
    public void initCitiesTable() {

        this.database.beginTransaction();
        try {
            this.database.execSQL("create table if not exists cities ("
                    + " ID integer PRIMARY KEY, "
                    + " name text NOT NULL, "
                    + " country text NOT NULL, "
                    + " lon REAL, "
                    + " lat REAL); ");


            if (!doesTableExist("cities") || getCities().size() == 0) {
                readJSON();
            }
            this.database.setTransactionSuccessful();
        }
        catch (SQLException e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            this.database.endTransaction();

        }
    }

    /**
     * Method create the weather table in the database.
     * A toast is shown if the table could not be created.
     */
    public void initWeatherTable() {
        this.database.beginTransaction();
        try {
            this.database.execSQL("create table if not exists weather ("
                + " date text, "
                + " hour integer, "
                + " cityId integer, "
                + " windSpeed real, "
                + " windDeg real, "
                + " maxTemp real, "
                + " minTemp real, "
                    + " tempUnit text, "
                + " pressureSea real, "
                + " pressureGround real, "
                + " weather text, "
                    + " weatherDetailed text, "
                    + " icon text, "
                + " humidity integer, "
                + " clouds integer, "
                + " primary key (date, hour, cityId), "
                + " foreign key (cityId) references cities(ID));"
            );

            this.database.setTransactionSuccessful();
        }
        catch (SQLException e) {
            Toast.makeText(this.context, "Database error, could not create weather tabel", Toast.LENGTH_LONG).show();
        }
        finally {
            this.database.endTransaction();
        }
    }

    /**
     * Method to create a JSON object
     * of the asset/cities.json file
     * and pass a JSONArray to the insertCities
     * method
     */
    private void readJSON() {
        try {
            JSONObject cities = new JSONObject(loadJSON());
            JSONArray citiesArr = cities.getJSONArray("cities");
            insertCities(citiesArr);
        }
        catch (JSONException e) {
            Log.d("readJSON", e.getMessage());
            Toast.makeText(this.context, "Error reading cities file", Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Method to load the assets/cities.json file
     * and turn it into a string
     * @return
     */
    private String loadJSON() {
        String json = null;
        try {
            /*Create input stream for assets/cities.json file */
            InputStream in = this.context.getAssets().open("cities.json");
            int size = in.available();

            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            Log.d("loadJSON", e.getMessage());
            Toast.makeText(this.context, "Error loading cities file", Toast.LENGTH_LONG).show();
            return null;
        }
        return json;
    }

    /**
     * Method to use a JSONArray to populate the SQLite
     * cities table with the data from the assets/cities.json
     * file
     * @param cities
     */
    private void insertCities(JSONArray cities) {
        try {
            /*Iterate through the array, getting each
             * city object in turn */
            for (int i = 0; i < cities.length(); i++) {
                JSONObject city = cities.getJSONObject(i);
                JSONObject coord = city.getJSONObject("coord");

                int cityID = city.getInt("id");
                String cityName = city.getString("name");
                String country = city.getString("country");
                double lon = coord.getDouble("lon");
                double lat = coord.getDouble("lat");

                addCity(cityID, cityName, country, lon, lat);

            }
        }
        catch (JSONException e) {
            Log.d("insertCities", e.getMessage());
            Toast.makeText(this.context, "Error while logging file", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to get a List of all of the cities
     * stored in the SQLite cities table.
     * @return
     */
    public List<City> getCities() {
        this.database.beginTransaction();
        List<City> cities = new ArrayList<City>();
        try {
            /*Query string */
            String query = "select * from cities;";
            Cursor cursor = this.database.rawQuery(query, null);

            /*Find the column indexes*/
            int idIndex = cursor.getColumnIndex("ID");
            int nameIndex = cursor.getColumnIndex("name");
            int countryIndex = cursor.getColumnIndex("country");
            int lonIndex = cursor.getColumnIndex("lon");
            int latIndex = cursor.getColumnIndex("lat");

            while (cursor.moveToNext()) {

                /*Get all the data necessary to create a City object */
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String country = cursor.getString(countryIndex);
                double lon = cursor.getDouble(lonIndex);
                double lat = cursor.getDouble(latIndex);

                /*Add a new city to the list */
                cities.add(new City(id, name, country, lon, lat));
            }
            this.database.setTransactionSuccessful();
            cursor.close();
        }
        catch (SQLException e) {
            Log.d("getCities", e.getMessage());
        }
        finally {
            this.database.endTransaction();
            return cities;
        }
    }

    /**
     * Method to determine whether a table exists
     * in this particular database.
     * @param tableName
     * @return true if table exists, false if not
     */
    public boolean doesTableExist(String tableName) {
        Cursor cursor = this.database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    /**
     * Method to drop a specified table
     * in this SQLite database
     * @param table
     */
    public void dropTable(String table) {
        this.database.beginTransaction();
        try {
            String query = "drop table if exists " + table + ";";
            this.database.execSQL(query);
            this.database.setTransactionSuccessful();
        }
        catch (SQLException e) {
            Log.d("dropTable", e.getMessage());
        }
        finally {
            this.database.endTransaction();
        }
    }


    /**
     * Method to open the database and
     * create it if it does not already exist.
     * This method should be called before any method
     * making modifications to the database is invoked.
     */
    public void openDatabase() {
        try {
            this.database = SQLiteDatabase.openDatabase(this.path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        }
        catch (SQLiteException e) {
            Log.d("openDatabase" , e.getMessage());
        }

    }

    /**
     * Method to close the SQLite database.
     * This method should be called after any other
     * transaction methods have been completed.
     */
    public void closeDatabase() {
        this.database.close();
    }


    /**
     * Method to insert the data contained in a forecast
     * object into the weather table.
     * @param forecast
     */
    public void insertForecast(Forecast forecast) {

        /*Iterate through all DailyWeather objects*/
        for (DailyWeather daily : forecast.getWeatherForecast().values()) {
            insertDailyWeather(daily);
        }
    }

    /**
     * Method to insert the data contained in a DailyWeather object
     * into the weather table
     * @param dailyWeather
     */
    public void insertDailyWeather(DailyWeather dailyWeather) {

        /*Iterate through all HourlyWeather objects*/
        for (HourInterval hour : dailyWeather.getWeatherMap().keySet()) {
            insertHourlyWeather(dailyWeather.getWeatherMap().get(hour));
        }
    }

    /**
     * Method to insert the data contained in a HourlyWeather object
     * into the weather table. The table is created if it does not
     * exist
     * @param hourlyWeather
     */
    public void insertHourlyWeather(HourlyWeather hourlyWeather) {
        if (!doesTableExist("weather")) {
            initWeatherTable();
        }
        /*Get the ContentValues to be added*/
        ContentValues values = createContentValues(hourlyWeather);
        this.database.insert("weather", null, values);


    }

    /**
     * Method to create the ContentValues object for
     * a HourlyWeather object
     * @param hourlyWeather
     * @return
     */
    private ContentValues createContentValues(HourlyWeather hourlyWeather) {
        ContentValues values = new ContentValues();

        /*Get the data from the HourlyWeather object*/
        String date = hourlyWeather.getDateString();
        int hour = hourlyWeather.getInterval().getHour();
        double windSpeed = hourlyWeather.getWind().getSpeed();
        double windDegrees = hourlyWeather.getWind().getDegrees();
        double maxTemp = hourlyWeather.getTemperature().getMax();
        double minTemp = hourlyWeather.getTemperature().getMin();
        String tempUnit = hourlyWeather.getTemperature().getUnit().getUnit();
        double pressureSea = hourlyWeather.getPressure().getSeaPressure();
        double pressureGround = hourlyWeather.getPressure().getGroundPressure();
        String weatherDetailed = hourlyWeather.getWeather().getDetailedDescription();
        String weatherMain = hourlyWeather.getWeather().getMainDescription();
        String icon = hourlyWeather.getWeather().getIcon();
        int humidity = hourlyWeather.getHumidity().getValue();
        int clouds = hourlyWeather.getClouds().getCloudiness();
        int cityId = hourlyWeather.getCity().getId();

        /*Place the data into the ContentValues object*/
        values.put("date", date);
        values.put("hour", hour);
        values.put("cityId", cityId);
        values.put("windSpeed", windSpeed);
        values.put("windDeg", windDegrees);
        values.put("maxTemp", maxTemp);
        values.put("minTemp", minTemp);
        values.put("tempUnit", tempUnit);
        values.put("pressureSea", pressureSea);
        values.put("pressureGround", pressureGround);
        values.put("weather", weatherMain);
        values.put("weatherDetailed", weatherDetailed);
        values.put("icon", icon);
        values.put("humidity", humidity);
        values.put("clouds", clouds);

        return values;
    }

    /**
     * Method to the a Forecast object containing
     * weather data stored in the SQLite database
     * under the specified cityId
     * @param cityId
     * @return
     */
    public Forecast getForecast(int cityId) {
        this.database.beginTransaction();
        Map<WeekDay, DailyWeather> weatherForecast = new LinkedHashMap<WeekDay, DailyWeather>();
        Forecast forecast = null;
        try {
            String sql = "select * from weather "
                    + " inner join cities on cities.Id = ?;";

            String[] args = {Integer.toString(cityId)};
            Cursor cursor = this.database.rawQuery(sql, args);

            /*Get all the necessary indexes to construct a forecast object*/
            int dateIndex = cursor.getColumnIndex("date");
            int hourIndex = cursor.getColumnIndex("hour");
            int cityIdIndex = cursor.getColumnIndex("cityId");
            int windSpeedIndex = cursor.getColumnIndex("windSpeed");
            int windDegreesIndex = cursor.getColumnIndex("windDeg");
            int maxTempIndex = cursor.getColumnIndex("maxTemp");
            int minTempIndex = cursor.getColumnIndex("minTemp");
            int tempUnitIndex = cursor.getColumnIndex("tempUnit");
            int pressureSeaIndex = cursor.getColumnIndex("pressureSea");
            int pressureGroundIndex = cursor.getColumnIndex("pressureGround");
            int weatherIndex = cursor.getColumnIndex("weather");
            int weatherDetailedIndex = cursor.getColumnIndex("weatherDetailed");
            int iconIndex = cursor.getColumnIndex("icon");
            int humidityIndex = cursor.getColumnIndex("humidity");
            int cloudsIndex = cursor.getColumnIndex("clouds");
            int cityNameIndex = cursor.getColumnIndex("name");
            int countryIndex = cursor.getColumnIndex("country");
            int lonIndex = cursor.getColumnIndex("lon");
            int latIntex = cursor.getColumnIndex("lat");

            cursor.moveToFirst();
            /*Use a GregorianCalendar object to check when weather data is of a different day */
            GregorianCalendar cal = HourlyWeather.getCalendar(cursor.getString(dateIndex), cursor.getInt(hourIndex));

            forecast = new Forecast(this.context);
            DailyWeather dailyWeather = new DailyWeather();

            while (cursor.moveToNext()) {

                /*Construct all necessary objects to create an HourlyWeather object*/
                City city = new City(cursor.getInt(cityIdIndex), cursor.getString(cityNameIndex), cursor.getString(countryIndex), cursor.getDouble(lonIndex), cursor.getDouble(latIntex));
                Clouds clouds = new Clouds(cursor.getInt(cloudsIndex));
                Humidity humidity = new Humidity(cursor.getInt(humidityIndex));
                Pressure pressure = new Pressure(cursor.getDouble(pressureGroundIndex), cursor.getDouble(pressureSeaIndex));
                Temperature temperature = new Temperature(cursor.getDouble(minTempIndex), cursor.getDouble(maxTempIndex), cursor.getDouble(maxTempIndex), TemperatureUnit.findUnit(cursor.getString(tempUnitIndex)));
                Weather weather = new Weather(0, cursor.getString(weatherIndex), cursor.getString(weatherDetailedIndex), cursor.getString(iconIndex));
                Wind wind = new Wind(cursor.getDouble(windSpeedIndex), cursor.getDouble(windDegreesIndex));
                GregorianCalendar calendar = HourlyWeather.getCalendar(cursor.getString(dateIndex), cursor.getInt(hourIndex));

                HourlyWeather hourlyWeather = new HourlyWeather(clouds, humidity, pressure, wind, temperature, calendar, weather, city);

                /*If the date of the current forecast is greater than that of the previous one,
                * the DailyWeather object will be added to the forecastWeather Map
                * and the previous calendar becomes the current one*/
                if (calendar.get(GregorianCalendar.DAY_OF_MONTH) > cal.get(GregorianCalendar.DAY_OF_MONTH) || calendar.get(GregorianCalendar.MONTH) > cal.get(GregorianCalendar.MONTH)) {
                    weatherForecast.put(dailyWeather.getWeekDay(), dailyWeather);
                    dailyWeather = new DailyWeather();
                    cal = calendar;
                }
                /*Add the HourlyWeather object to the DailyWeather object */
                dailyWeather.addWeather(hourlyWeather);
            }
            /*Add the last DailyWeather object to the weatherForecast Map*/
            weatherForecast.put(dailyWeather.getWeekDay(), dailyWeather);

            /*Set the forecast Map of the Forecast object*/
            forecast.setWeatherForecast(weatherForecast);
            this.database.setTransactionSuccessful();
        }
        catch (SQLException e) {
            Log.d("getForecast", e.getMessage());
            return null;
        }
        finally {
            this.database.endTransaction();
            return forecast;
        }

    }

    /**
     * Method to delete all data from the specified table
     * @param tablename
     */
    public void deleteFromTable(String tablename) {
        this.database.beginTransaction();
        try {
            String query = "delete from table if exists " + tablename + ";";
            this.database.execSQL(query);
            this.database.setTransactionSuccessful();
        }
        catch (SQLException e) {
            Log.d("deleteFromTable", e.getMessage());
        }
        finally {
            this.database.endTransaction();
        }
    }


    /**
     * Method to delete all specific forecast data
     * associated with a city
     * @param cityId
     */
    public void deleteForecast(int cityId) {
        this.database.beginTransaction();
        try {
            String query = "delete from weather"
                    + " where cityId = ?;";
            String[] args = {Integer.toString(cityId)};
            this.database.execSQL(query, args);
            this.database.setTransactionSuccessful();
            Log.d("deleteForacst", "Forecast deleted");
        }
        catch (SQLException e) {
            Log.d("deleteForacst", e.getMessage());
        }
        finally {
            this.database.endTransaction();
        }
    }

    /**
     * Method to get a List of all the cities which
     * have forecasts stored in the database.
     * @return
     */
    public List<City> getAvailableCities() {
        this.database.beginTransaction();
        List<City> cities = new ArrayList<City>();
        try {
            String query = "select * from cities c, weather w "
                    + "where c.ID = w.cityId "
                    + "group by c.name;";

            Cursor cursor = this.database.rawQuery(query, null);

            int idIndex = cursor.getColumnIndex("ID");
            int nameIndex = cursor.getColumnIndex("name");
            int countryIndex = cursor.getColumnIndex("country");
            int lonIndex = cursor.getColumnIndex("lon");
            int latIndex = cursor.getColumnIndex("lat");

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String country = cursor.getString(countryIndex);
                double lon = cursor.getDouble(lonIndex);
                double lat = cursor.getDouble(latIndex);
                Log.d("getCities", name);
                cities.add(new City(id, name, country, lon, lat));
            }
            this.database.setTransactionSuccessful();
            cursor.close();
        }
        catch (SQLException e) {
            Log.d("getAvailableCities", e.getMessage());
        }
        finally {
            this.database.endTransaction();
            return cities;
        }
    }





}
