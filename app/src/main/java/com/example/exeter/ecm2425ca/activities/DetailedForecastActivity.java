package com.example.exeter.ecm2425ca.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.exeter.ecm2425ca.fragments.MainDetailedForecastFragment;
import com.example.exeter.ecm2425ca.R;
import com.example.exeter.ecm2425ca.weather.DailyWeather;
import com.example.exeter.ecm2425ca.weather.HourInterval;
import com.example.exeter.ecm2425ca.weather.HourlyWeather;
import com.google.gson.Gson;

public class DetailedForecastActivity extends AppCompatActivity {

    private DailyWeather dailyWeather;
    private MainDetailedForecastFragment fragment;
    private TextView wind;
    private TextView humidity;
    private TextView clouds;
    private TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_forecast);
        Bundle bundle = getIntent().getExtras();

        /*If the bundle sent to this activity is not empty*/
        if (!bundle.isEmpty()) {

            Gson gson = new Gson();

            /*Reinflate the DailyWeather object passed to this activity using gson*/
            this.dailyWeather = gson.fromJson(bundle.getString("json"), DailyWeather.class);

            /*Perform a null check*/
            if (dailyWeather == null) {
                return;
            }

            this.fragment = (MainDetailedForecastFragment) getFragmentManager().findFragmentById(R.id.topFragment);
            String day = bundle.getString("day");

            /*If the detailed forecast in the main
            * activity was pressed, display the current
            * temperature*/
            if (day.equals("day1")) {
                this.fragment.setWeather(dailyWeather, this, false);
            }

            /*If another forecast was clicked,
             * display the highest temperature for
              * that day*/
            else {
                this.fragment.setWeather(dailyWeather, this, true);
            }
            initFields();
            setFields(dailyWeather.getMaxTemp());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (this.dailyWeather == null) {
            return false;
        }
        /*Dynamically add the available forecast time intervals to the options menu */
        for (HourInterval interval : this.dailyWeather.getWeatherMap().keySet()) {
            menu.add(interval.getHourString());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        HourlyWeather hourlyWeather = null;

        /*Depending on the time selected in the options menu
        * show the corresponding weather forecast*/
        switch (title) {

            case "0:00":
                hourlyWeather = this.dailyWeather.getHourlyWeather(HourInterval.ZERO);
                break;

            case "3:00":
                hourlyWeather = this.dailyWeather.getHourlyWeather(HourInterval.THREE);
                break;

            case "6:00":
                hourlyWeather = this.dailyWeather.getHourlyWeather(HourInterval.SIX);
                break;

            case "9:00":
                hourlyWeather = this.dailyWeather.getHourlyWeather(HourInterval.NINE);
                break;

            case "12:00":
                hourlyWeather = this.dailyWeather.getHourlyWeather(HourInterval.TWELVE);
                break;

            case "15:00":
                hourlyWeather = this.dailyWeather.getHourlyWeather(HourInterval.FIFTEEN);
                break;

            case "18:00":
                hourlyWeather = this.dailyWeather.getHourlyWeather(HourInterval.EIGHTEEN);
                break;

            case "21:00":
                hourlyWeather = this.dailyWeather.getHourlyWeather(HourInterval.TWENTY_ONE);
                break;

        }
        this.fragment.setWeather(hourlyWeather);
        setFields(hourlyWeather);
        return true;
    }

    /**
     * Method to initialise the fields
     * of this class
     */
    private void initFields() {
        this.clouds = (TextView) findViewById(R.id.detailedCloudsValue);
        this.humidity = (TextView) findViewById(R.id.detailedHumidityValue);
        this.wind = (TextView) findViewById(R.id.detailedWindValue);
        this.time = (TextView) findViewById(R.id.detailedTimeValue);
    }

    /**
     * Method to populate the views
     * of this activity with the data
     * from the hourlyweather object
     * @param hourlyWeather
     */
    private void setFields(HourlyWeather hourlyWeather) {
        /*Get the necessary data*/
        String clouds = Integer.toString(hourlyWeather.getClouds().getCloudiness()) + " %";
        String wind = hourlyWeather.getWind().getSpeedString() + " " + hourlyWeather.getWind().getSpeedName();
        String humidity = hourlyWeather.getHumidity().getValueString() + " %";
        String time = hourlyWeather.getInterval().getHourString();

        /*Populate the views with the data*/
        this.clouds.setText(clouds);
        this.wind.setText(wind);
        this.humidity.setText(humidity);
        this.time.setText(time);

    }








}
