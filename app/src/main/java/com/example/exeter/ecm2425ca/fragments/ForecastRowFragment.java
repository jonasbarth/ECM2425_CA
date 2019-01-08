package com.example.exeter.ecm2425ca.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exeter.ecm2425ca.R;
import com.example.exeter.ecm2425ca.weather.DailyWeather;
import com.example.exeter.ecm2425ca.weather.HourlyWeather;

import java.util.GregorianCalendar;

/**
 * Created by Jonas on 06/03/2018.
 */

public class ForecastRowFragment extends Fragment {

    private TextView date;
    private TextView temperature;
    private ImageView image;

    private DailyWeather dailyWeather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.forecast_row_fragment, container, false);
    }

    /**
     * Method to instantiate the fields
     * of this class
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.date = (TextView) getView().findViewById(R.id.rowFragmentDate);
        this.temperature = (TextView) getView().findViewById(R.id.rowFragmentTempMax);
        this.image = (ImageView) getView().findViewById(R.id.rowFragmentImage);
    }

    /**
     * Method popualate set the DailyWeather
     * object of this fragment and populate the views
     * of this fragment
     * @param dailyWeather
     */
    public void setWeather(DailyWeather dailyWeather) {
        this.dailyWeather = dailyWeather;
        initViews(dailyWeather.getMaxTemp());
    }

    /**
     * Method to get the DailyWeather object
     * associated with this fragment
     * @return
     */
    public DailyWeather getDailyWeather() {
        return this.dailyWeather;
    }


    /**
     * Method to assign values to all of the views
     * associated with this fragment
     * @param hourlyWeather
     */
    private void initViews(HourlyWeather hourlyWeather) {
        /*Get the data to be displayed*/
        String unit = hourlyWeather.getTemperature().getUnit().getUnit();
        double high = hourlyWeather.getTemperature().getMax();
        String tempHigh = hourlyWeather.getTemperature().getMinStringFormat() + " °" + unit;
        int date = hourlyWeather.getDate().get(GregorianCalendar.DAY_OF_MONTH);
        String day = hourlyWeather.getWeekDay().getDay() + " " + Integer.toString(date);

        /*Get the image string*/
        String image = "w" + hourlyWeather.getWeather().getIcon();

        /*Find the id associated with the image string*/
        int imageId = getResources().getIdentifier(image , "mipmap", getActivity().getPackageName());

        this.date.setText(day);
        this.temperature.setText(tempHigh);
        this.image.setImageResource(imageId);

    }


}
