package com.example.exeter.ecm2425ca.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import com.example.exeter.ecm2425ca.R;
import com.example.exeter.ecm2425ca.utils.Preferences;
import com.example.exeter.ecm2425ca.weather.TemperatureUnit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonas on 03/03/2018.
 */

public class TemperatureUnitAcivity extends AppCompatActivity {


    private Map<String, Switch> switches;
    private Switch currentSwitch;
    public static final String PREFERENCE_KEY = "temperatureUnit";
    public static final TemperatureUnit DEFAULT_UNIT = TemperatureUnit.Celsius;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_unit);

        this.switches = new HashMap<String, Switch>();
        addSwitches();
        addListeners();

        /*If the preferences are not set */
        if (!Preferences.preferencesContains(PREFERENCE_KEY, this)) {

            /*Set the switches to the default position */
            setSwitches(this.switches.get(DEFAULT_UNIT.getUnit()));
        }
        /*If preferences have been set*/
        else {
            /*Get the unit saved*/
            String unit = Preferences.getSharedPreferences(PREFERENCE_KEY, DEFAULT_UNIT.getUnit(), this);

            /*Set switches to the correct position*/
            setSwitches(this.switches.get(unit));
        }

    }


    /**
     * Once the activity is invisible,
     * set the shared preferences to the
     * current temperature unit as determined
     * by the switch
     */
    @Override
    public void onPause() {
        super.onPause();
        Preferences.setSharedPreference(PREFERENCE_KEY, getCurrentUnit(), this);
    }


    /**
     * Method to add the 3 switches
     * to the Map attribute
     */
    private void addSwitches() {
        /*Get the switches*/
        Switch celsius = (Switch) findViewById(R.id.celsiusSwitch);
        Switch fahrenheit = (Switch) findViewById(R.id.fahrenheitSwitch);
        Switch kelvin = (Switch) findViewById(R.id.kelvinSwitch);

        /*Put the switches in the Map*/
        this.switches.put(TemperatureUnit.Celsius.getUnit(), celsius);
        this.switches.put(TemperatureUnit.Fahrenheit.getUnit(), fahrenheit);
        this.switches.put(TemperatureUnit.Kelvin.getUnit(), kelvin);
    }


    /**
     * Method attach listeners to the 3
     * switches
     */
    private void addListeners() {
        for (Switch s : this.switches.values()) {
            s.setOnClickListener(new View.OnClickListener() {

                /**
                 * Once a switch is clicked,
                 * set all switches to the correct
                 * position
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    Switch unit = (Switch) view;
                    TemperatureUnitAcivity.this.setSwitches(unit);
                }
            });
        }
    }


    /**
     * Method to set the switches to their correct
     * position, i.e. only one switch can be switched
     * on at a given point in time
     * @param unit
     */
    private void setSwitches(Switch unit) {
        for (Switch s : this.switches.values()) {
            /*Set other switches to unchecked*/
            if (s != unit) {
                s.setChecked(false);
            }
            /*Set the passed in switch to checked */
            else {
                s.setChecked(true);
                this.currentSwitch = s;
            }
        }
    }


    /**
     * Method to get the currently selected
     * temperature unit. Returns the default unit
     * if no switch is selected.
     * @return
     */
    private String getCurrentUnit() {
        for (String s : this.switches.keySet()) {
            if (this.switches.get(s).isChecked()) {
                return s;
            }
        }
        return DEFAULT_UNIT.getUnit();
    }






}
