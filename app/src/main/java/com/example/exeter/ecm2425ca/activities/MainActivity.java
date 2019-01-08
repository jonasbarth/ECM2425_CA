package com.example.exeter.ecm2425ca.activities;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.exeter.ecm2425ca.fragments.ForecastRowFragment;
import com.example.exeter.ecm2425ca.fragments.MainDetailedForecastFragment;
import com.example.exeter.ecm2425ca.R;
import com.example.exeter.ecm2425ca.weather.DailyWeather;
import com.example.exeter.ecm2425ca.utils.Database;
import com.example.exeter.ecm2425ca.weather.Forecast;
import com.example.exeter.ecm2425ca.utils.NetworkUtil;
import com.example.exeter.ecm2425ca.utils.Preferences;
import com.example.exeter.ecm2425ca.weather.TemperatureUnit;
import com.example.exeter.ecm2425ca.weather.WeekDay;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    // TODO (1) Define a loader ID
    private final int LOADER_ID = 1;
    private final String BUNDLE_KEY = "weather";
    private final String DB_PATH = "data/data/com.example.jonas.ecm2425ca/weatherDB";
    private final String PREFERENCE_KEY_CITY = "city";
    private final int PREFERENCE_DEFAULT_CITY = 2643743;

    private Forecast forecast = new Forecast(this);
    private DailyWeather todaysWeather = new DailyWeather();
    private MainDetailedForecastFragment today = (MainDetailedForecastFragment) getFragmentManager().findFragmentById(R.id.day1);
    private Map<Integer, ForecastRowFragment> forecastFragments = new LinkedHashMap<Integer, ForecastRowFragment>();
    private Database db = new Database(this, DB_PATH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db.openDatabase();
        //this.db.deleteFromTable("weather");
        this.db.initWeatherTable();
        this.db.initCitiesTable();
        this.db.closeDatabase();

       
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        setFragmentColour();
        weatherQuery();
        Log.d("Activity Callback", "On Create");
    }


    @Override
    public void onRestart() {
        super.onRestart();
        weatherQuery();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Method to be called when either a forecast fragment
     * or the main weather view is clicked
     * @param view
     */
    public void editActions(View view) {
        Intent intent = new Intent(this, DetailedForecastActivity.class);
        int id = view.getId();
        Gson gson = new Gson();

        /*String to contain the dailyWeather object*/
        String json = null;
        DailyWeather dailyWeather = null;
        String jsonKey = "json";
        String dayKey = "day";

        Bundle bundle = new Bundle();

        /*Switch statement to determines which forecast was clicked */
        switch (id) {

            /*Today was clicked*/
            case R.id.day1:
                dailyWeather = this.todaysWeather;
                /*Use gson to turn dailyWeather object into json*/
                json = gson.toJson(dailyWeather);
                bundle.putString(dayKey, "day1");
                bundle.putString(jsonKey, json);
                intent.putExtras(bundle);
                break;

            /*Tomorrow was clicked*/
            case R.id.day2:
                /*Get the weather associated with fragment 2*/
                dailyWeather = this.forecastFragments.get(2).getDailyWeather();

                /*Use gson to turn dailyWeather object into json*/
                json = gson.toJson(dailyWeather);
                bundle.putString(dayKey, "day2");
                bundle.putString(jsonKey, json);
                intent.putExtras(bundle);
                break;

            /*Day 3 was clicked */
            case R.id.day3:
                /*Get the weather associated with fragment 3*/
                dailyWeather = this.forecastFragments.get(3).getDailyWeather();

                /*Use gson to turn dailyWeather object into json*/
                json = gson.toJson(dailyWeather);
                bundle.putString(dayKey, "day3");
                bundle.putString(jsonKey, json);
                intent.putExtras(bundle);
                break;

            /*Day 4 was clicked */
            case R.id.day4:
                /*Get the weather associated with fragment 4*/
                dailyWeather = this.forecastFragments.get(4).getDailyWeather();

                /*Use gson to turn dailyWeather object into json*/
                json = gson.toJson(dailyWeather);
                bundle.putString(dayKey, "day4");
                bundle.putString(jsonKey, json);
                intent.putExtras(bundle);
                break;

            /*Day 5 was clicked */
            case R.id.day5:
                /*Get the weather associated with fragment 5*/
                dailyWeather = this.forecastFragments.get(5).getDailyWeather();

                /*Use gson to turn dailyWeather object into json*/
                json = gson.toJson(dailyWeather);
                bundle.putString(dayKey, "day5");
                bundle.putString(jsonKey, json);
                intent.putExtras(bundle);
                break;

            case R.id.day6:
                /*Get the weather associated with fragment 5*/
                dailyWeather = this.forecastFragments.get(6).getDailyWeather();

                /*Use gson to turn dailyWeather object into json*/
                json = gson.toJson(dailyWeather);
                bundle.putString(dayKey, "day6");
                bundle.putString(jsonKey, json);
                intent.putExtras(bundle);
                break;

            default:
                break;
        }
        for (String key : bundle.keySet()) {
            Log.d("editActions", key);
        }
        startActivity(intent);


    }

    /**
     * Method invoked when menu items are clicked
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.changeLocation:
                /*Start the location activity to let users
                 * change location */
                startLocationActivity();
                return true;

            case R.id.changeTempUnit:
                /*Start the temperatureUnit activity to let
                 * users change the unit of temperature */
                startTemperatureActivity();
                return true;

            case R.id.refreshData:
                /*Refresh the data*/
                weatherQuery();
                return true;
            default:
                return true;
        }
    }


    /**
     * Method to to start the temperature activity
     */
    private void startTemperatureActivity() {
        Intent intent = new Intent(this, TemperatureUnitAcivity.class);
        startActivity(intent);
    }

    /**
     * Method to start the location activity
     */
    private void startLocationActivity() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<String>(this) {

            public void onStartLoading() {
                if (args == null) {
                    return;
                }
                forceLoad();
            }
            @Override
            public String loadInBackground() {

                if (!NetworkUtil.checkInternetConnection(MainActivity.this)) {
                    return null;
                }
                String queryURL = args.getString(BUNDLE_KEY);

                try {
                    URL weatherURL = new URL(queryURL);
                    String results = NetworkUtil.getResponseFromHttpUrl(weatherURL);
                    Log.d("onCreateLoader", results);
                    return results;

                } catch (IOException e) {
                    Log.d("onCreateLoader ", e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d("onLoadFinished", "load has finished");
        try {
            if (data == null) {
                databaseQuery();
                Log.d("onLoadFinished", "data is null " + data);
                Toast.makeText(this, "Cannot Connect To Internet", Toast.LENGTH_LONG).show();
                return;
            }

            JSONObject forecastData = new JSONObject((data));
            /*Create a new forecast object to hold the forecast data*/
            this.forecast = new Forecast(this);
            this.forecast.populateForecast(forecastData);

            /*Set the temperature unit of the forecast to the unit
             * specified in the shared preferences */
            this.forecast.setTemperatureUnit(getTemperatureUnit());
            this.todaysWeather = this.forecast.getTodaysWeather();
            this.today = (MainDetailedForecastFragment) getFragmentManager().findFragmentById(R.id.day1);
            /*Set the weather for today */
            this.today.setWeather(this.todaysWeather, this, false);
            DailyWeather hour = this.forecast.getTodaysWeather();

            this.db.openDatabase();
            this.db.deleteForecast(hour.getCurrent().getCity().getId());
            this.db.insertForecast(this.forecast);
            this.db.closeDatabase();

            /*Populate the fragments for the next few days */
            initFragments(this.forecast.getFutureWeather());
        }
        catch (JSONException e) {
            Toast.makeText(this, "An error occurred while loading weather data", Toast.LENGTH_LONG).show();
        }
    }

    private void weatherQuery() {
        /*Check if 4 hours have passed since the last update */
        int cityID;
        if (Preferences.preferencesContains(PREFERENCE_KEY_CITY, this)) {
            cityID = Preferences.getSharedPreferences(PREFERENCE_KEY_CITY, PREFERENCE_DEFAULT_CITY, this);

        }
        else {
            Preferences.setSharedPreference(PREFERENCE_KEY_CITY, PREFERENCE_DEFAULT_CITY, this);
            cityID = PREFERENCE_DEFAULT_CITY;

        }
        Log.d("Callback", "Making the query with " + cityID);


        makeWeatherForecastQuery(Integer.toString(cityID));


    }


    public String makeWeatherForecastQuery(String cityID) {
        URL weatherUrl = NetworkUtil.buildUrl(cityID);

        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY, weatherUrl.toString());

        LoaderManager loaderManager = getSupportLoaderManager();

        Loader<String> weatherSearchLoader = loaderManager.getLoader(LOADER_ID);

        if (weatherSearchLoader == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this);
        }
        return null;
    }


    /**
     * Method to get the Temperature Unit saved in
     * shared preferences
     * @return
     */
    private TemperatureUnit getTemperatureUnit() {
        /*Get the String saved in the shared preferences */
        String unit = Preferences.getSharedPreferences(TemperatureUnitAcivity.PREFERENCE_KEY, TemperatureUnitAcivity.DEFAULT_UNIT.getUnit(), this);

        switch (unit) {

            case "C":
                return TemperatureUnit.Celsius;

            case "F":
                return TemperatureUnit.Fahrenheit;

            case "K":
                return TemperatureUnit.Kelvin;

            default:
                return TemperatureUnit.Celsius;
        }
    }

    /**
     * Method to make fragments have alternating
     * background colours
     */
    private void setFragmentColour() {
        Fragment day2 = getFragmentManager().findFragmentById(R.id.day2);
        Fragment day4 = getFragmentManager().findFragmentById(R.id.day4);
        Fragment day6 = getFragmentManager().findFragmentById(R.id.day6);
        day2.getView().setBackgroundColor(getResources().getColor(R.color.background));
        day4.getView().setBackgroundColor(getResources().getColor(R.color.background));
        day6.getView().setBackgroundColor(getResources().getColor(R.color.background));



    }

    /**
     * Method to populate the forecastFragments
     * Map with ForecastRowFragment objects
     * @param weatherMap
     */
    private void initFragments(Map<WeekDay, DailyWeather> weatherMap) {
        ForecastRowFragment day2 = (ForecastRowFragment) getFragmentManager().findFragmentById(R.id.day2);
        ForecastRowFragment day3 = (ForecastRowFragment) getFragmentManager().findFragmentById(R.id.day3);
        ForecastRowFragment day4 = (ForecastRowFragment) getFragmentManager().findFragmentById(R.id.day4);
        ForecastRowFragment day5 = (ForecastRowFragment) getFragmentManager().findFragmentById(R.id.day5);
        ForecastRowFragment day6 = (ForecastRowFragment) getFragmentManager().findFragmentById(R.id.day6);

        ForecastRowFragment[] days = {day2, day3, day4, day5, day6};
        int i = 0;

        /*Iterate over each WeekDay and assign the DailyWeather to its
         * corresponding ForecastRowFragment*/
        for (WeekDay day : weatherMap.keySet()) {
            days[i].setWeather(weatherMap.get(day));
            this.forecastFragments.put(i + 2, days[i]);
            i++;
        }
    }


    private void databaseQuery() {
        int cityId = Preferences.getSharedPreferences(PREFERENCE_KEY_CITY, PREFERENCE_DEFAULT_CITY, this);

        this.db.openDatabase();
        this.forecast = this.db.getForecast(cityId);
        this.forecast.setTemperatureUnit(getTemperatureUnit());
        this.todaysWeather = forecast.getTodaysWeather();
        this.today = (MainDetailedForecastFragment) getFragmentManager().findFragmentById(R.id.day1);
        this.db.closeDatabase();
        this.today.setWeather(this.forecast.getTodaysWeather(), this, false);
        initFragments(this.forecast.getFutureWeather());

    }
}
