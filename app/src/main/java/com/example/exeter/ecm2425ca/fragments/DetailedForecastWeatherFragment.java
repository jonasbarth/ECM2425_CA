package com.example.exeter.ecm2425ca.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exeter.ecm2425ca.R;

/**
 * Created by Jonas on 26/03/2018.
 */

public class DetailedForecastWeatherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.fragment_detailed_forecast_weather, container, false);
    }
}
