package com.example.exeter.ecm2425ca.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exeter.ecm2425ca.R;
import com.example.exeter.ecm2425ca.weather.DailyWeather;
import com.example.exeter.ecm2425ca.weather.HourlyWeather;

/**
 * Created by Jonas on 26/03/2018.
 */

public class MainDetailedForecastFragment extends Fragment {

    private TextView mainCity;
    private TextView mainTempHigh;
    private TextView mainWeather;
    private TextView mainDate;
    private ImageView mainImage;
    private TextView mainWeekday;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.fragment_main_detailed_forecast, container, false);
    }

    /**
     * Method to initialised all of the views
     * associated with this fragment object
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mainCity = (TextView) getView().findViewById(R.id.mainFragmentCity);
        this.mainTempHigh = (TextView) getView().findViewById(R.id.mainTemperature);
        this.mainWeather = (TextView) getView().findViewById(R.id.mainWeatherDescription);
        this.mainDate = (TextView) getView().findViewById(R.id.mainDate);
        this.mainImage = (ImageView) getView().findViewById(R.id.mainWeatherImage);
        this.mainWeekday = (TextView) getView().findViewById(R.id.mainFragmentWeekday);
    }

    /**
     * Method to set the data of the views of this
     * fragment using a DailyWeather object
     * @param dailyWeather
     * @param activity
     * @param maxTemp If true, the maximum temperature for the day will be displayed
     */
    public void setWeather(DailyWeather dailyWeather, AppCompatActivity activity, boolean maxTemp) {
        if (dailyWeather == null) {
            return;
        }
        HourlyWeather hourlyWeather = null;
        if (maxTemp) {
            hourlyWeather = dailyWeather.getMaxTemp();
        }
        else {
            hourlyWeather = dailyWeather.getCurrent();
        }

        String unit = hourlyWeather.getTemperature().getUnit().getUnit();
        String tempHigh = hourlyWeather.getTemperature().getMaxStringFormat() + " °" + unit;
        String weekday = hourlyWeather.getWeekDay().getFullDay();

        /*Get the corresponding image*/
        String image = "w" + hourlyWeather.getWeather().getIcon();
        int imageId = activity.getResources().getIdentifier(image , "mipmap", activity.getPackageName());

        /*Populate all of the views  */
        populateFields(hourlyWeather.getCity().getName() + ", " + hourlyWeather.getCity().country(), tempHigh, hourlyWeather.getWeather().getDetailedDescription(), hourlyWeather.getDateString(), imageId, weekday);

    }

    /**
     * Method to set the data of the views in this object
     * using a HourlyWeather object
     * @param hourlyWeather
     */
    public void setWeather(HourlyWeather hourlyWeather) {
        String city = hourlyWeather.getCity().getName();
        String country = hourlyWeather.getCity().country();
        String unit = hourlyWeather.getTemperature().getUnit().getUnit();
        String temp = hourlyWeather.getTemperature().getMaxStringFormat() + " °" + unit;
        String weather = hourlyWeather.getWeather().getDetailedDescription();
        String date = hourlyWeather.getDateString();
        String image = "w" + hourlyWeather.getWeather().getIcon();
        int imageId = getActivity().getResources().getIdentifier(image, "mipmap", getActivity().getPackageName());
        String weekday = hourlyWeather.getWeekDay().getFullDay();
        populateFields(city + ", " + country, temp, weather, date, imageId, weekday);
    }

    /**
     * Helper method to populate the fields
     * of the views of this class.
     * @param city
     * @param temp
     * @param weather
     * @param date
     * @param imageId
     * @param weekday
     */
    private void populateFields(String city, String temp, String weather, String date, int imageId, String weekday) {
        this.mainCity.setText(city);
        this.mainTempHigh.setText(temp);
        this.mainWeather.setText(weather);
        this.mainDate.setText(date);
        this.mainImage.setImageResource(imageId);
        this.mainWeekday.setText(weekday);
    }
}
