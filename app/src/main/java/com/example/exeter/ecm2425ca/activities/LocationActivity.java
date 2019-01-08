package com.example.exeter.ecm2425ca.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.exeter.ecm2425ca.R;
import com.example.exeter.ecm2425ca.weather.City;
import com.example.exeter.ecm2425ca.utils.Database;
import com.example.exeter.ecm2425ca.utils.NetworkUtil;
import com.example.exeter.ecm2425ca.utils.Preferences;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jonas on 03/03/2018.
 */

public class LocationActivity extends AppCompatActivity {

    private List<City> cities;
    private final String DEFAULT_CITY = "London";
    private final String PREFERENCES_KEY = "city";
    private final String DB_PATH = "data/data/com.example.jonas.ecm2425ca/weatherDB";
    public static final int DEFAULT_CITY_ID = 2643743;
    private City currentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        String[] cityArray;

        /*If an internet connection is present,
        * load all of the cities in the ListView*/
        if (NetworkUtil.checkInternetConnection(this)) {
            cityArray = getCities(false);
        }
        /*If no internet connection is present,
        * only load the cities for which data is saved
        * in the database*/
        else {
            cityArray = getCities(true);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  cityArray);
        ListView locationList = (ListView) findViewById(R.id.locationListView);
        locationList.setAdapter(adapter);

        /*Attach a listener to each item in the ListView*/
        locationList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        /*Get the name of the clicked city */
                        String city = String.valueOf(parent.getItemAtPosition(position));

                        /*Set the TextView and current city field */
                        setCurrentCityView(getCityObject(city));
                        setCurrentCity(getCityObject(city));
                    }
                });
        /*Get the id of the city saved in shared preferences*/
        int id = Preferences.getSharedPreferences(PREFERENCES_KEY, DEFAULT_CITY_ID, this);
        City city = getCity(id);
        setCurrentCity(city);
        setCurrentCityView(city);



    }

    @Override
    public void onPause() {
        super.onPause();
        Preferences.setSharedPreference(PREFERENCES_KEY, this.currentCity.getId(), this);
    }


    /**
     * Method set the TextView containing the current city name
     * @param city
     */
    private void setCurrentCityView(City city) {
        TextView view = (TextView) findViewById(R.id.currentCity);
        view.setText(city.getName());

    }

    /**
     * Method to set the currentCity to a City object
     * @param city
     */
    private void setCurrentCity(City city) {
        this.currentCity = city;
    }

    /**
     * Method to get an array of city names.
     * If available is specified as true,
     * only cities which have forecast data
     * stored will be returned. If specified as
     * false, all cities will be returned.
     * @param available
     * @return
     */
    private String[] getCities(boolean available) {
        Database db = new Database(this, DB_PATH);
        db.openDatabase();
        if (available) {
            this.cities = db.getAvailableCities();
        }
        else {
            this.cities = db.getCities();
        }
        db.closeDatabase();
        String[] cityNames = new String[this.cities.size()];

        for (int i = 0; i < cityNames.length; i++) {
            cityNames[i] = this.cities.get(i).getName();
        }
        Arrays.sort(cityNames);
        return cityNames;

    }

    /**
     * Helper method to find the corresponding id to a
     * city name
     * @param cityName
     * @return
     */
    private int getCityID(String cityName) {

        for (City city : this.cities) {
            if (city.getName().equals(cityName)) {
                return city.getId();
            }
        }
        return 0;
    }

    /**
     * Helper method to find the corresponding city object
     * to a city name. Returns null if no match can be found.
     * @param cityName
     * @return
     */
    private City getCityObject(String cityName) {
        for (City city : this.cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }


    /**
     * Method to get the city object of a corresponding
     * id. Returns null if no match can be found.
     * @param cityId
     * @return
     */
    private City getCity(int cityId) {
        for (City city : this.cities) {
            if (city.getId() == cityId) {
                return city;
            }
        }
        return null;
    }


}


