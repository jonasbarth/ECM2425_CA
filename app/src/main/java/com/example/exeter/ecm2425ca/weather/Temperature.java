package com.example.exeter.ecm2425ca.weather;

/**
 * Created by Jonas on 03/03/2018.
 */

public class Temperature {

    private double min;
    private double max;
    private double avg;
    private TemperatureUnit unit;

    public Temperature(double min, double max, double avg, TemperatureUnit unit) {
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.unit = unit;
    }

    /**
     * Method to get the minimum temperature
     * as a double
     * @return
     */
    public double getMin() {
        return this.min;
    }

    /**
     * Method to get the maximum temperature
     * as a double
     * @return
     */
    public double getMax() {
        return this.max;
    }

    /**
     * Method to get the average temperature
     * as a double
     * @return
     */
    public double getAvg() {
        return this.avg;
    }

    /**
     * Method to get the minimum temperature
     * as a String
     * @return
     */
    public String getMinString() {
        return Double.toString(this.min);
    }

    /**
     * Method to get the maximum temperature
     * as a String
     * @return
     */
    public String getMaxString() {
        return Double.toString(this.max);
    }


    /**
     * Method to get the average temperature
     * as a String
     * @return
     */
    public String getAvgString() {
        return Double.toString(this.avg);
    }

    /**
     * Method to get the minimum temperature
     * as a String truncated after the first
     * decimal
     * @return
     */
    public String getMinStringFormat() {
        String min = String.format("%.1f", this.min);
        return min;
    }

    /**
     * Method to get the maximum temperature
     * as a String truncated after the first
     * decimal
     * @return
     */
    public String getMaxStringFormat() {
        String max = String.format("%.1f", this.max);
        return max;
    }

    /**
     * Method to get the average temperature
     * as a String truncated after the first
     * decimal
     * @return
     */
    public String getAvgStringFormat(){
        String avg = String.format("%.1f", this.avg);
        return avg;
    }

    /**
     * Method to get the temperature unit
     * @return
     */
    public TemperatureUnit getUnit() {
        return this.unit;
    }

    /**
     * Method to convert all temperatures
     * to Celsius
     */
    public void toCelsius() {
        if (this.unit == TemperatureUnit.Fahrenheit) {
            setFToC();
        }
        else if (this.unit == TemperatureUnit.Kelvin){
            setKToC();
        }
    }

    /**
     * Method to convert all temperatures
     * to Fahrenheit
     */
    public void toFahrenHeit() {
        if (this.unit == TemperatureUnit.Celsius) {
            setCToF();
        }
        else if (this.unit == TemperatureUnit.Kelvin) {
            setKToC();
            setCToF();
        }
    }

    /**
     * Method to convert all temperatures
     * to Kelvin
     */
    public void toKelvin() {
        if (this.unit == TemperatureUnit.Celsius) {
            setCToK();
        }
        else if (this.unit == TemperatureUnit.Fahrenheit) {
            setFToC();
            setCToK();
        }
    }

    /**
     * Method to return a String representing
     * the data inside the object
     * @return
     */
    @Override
    public String toString() {
        String min = Double.toString(this.min) + this.unit;
        String max = Double.toString(this.max) + this.unit;
        String temp = Double.toString(this.avg) + this.unit;
        String temperature = "Min: " + min + " Max: " + max + " Temp:" + temp;
        return temperature;
    }

    /**
     * Helper method to convert
     * all fields from Fahrenheit to
     * Celsius
     */
    private void setFToC() {
        this.avg = FToC(this.avg);
        this.min = FToC(this.min);
        this.max = FToC(this.max);
        this.unit = TemperatureUnit.Celsius;
    }

    /**
     * Helper method to convert
     * all fields from Kelvin to
     * Celsius
     */
    private void setKToC() {
        this.avg = KToC(this.avg);
        this.min = KToC(this.min);
        this.max = KToC(this.max);
        this.unit = TemperatureUnit.Celsius;
    }

    /**
     * Helper method to convert
     * all fields from Celsius to
     * Fahrenheit
     */
    private void setCToF() {
        this.avg = CToF(this.avg);
        this.min = CToF(this.min);
        this.max = CToF(this.max);
        this.unit = TemperatureUnit.Fahrenheit;
    }

    /**
     * Helper method to convert
     * all fields from Celsius to
     * Kelvin
     */
    private void setCToK() {
        this.avg = CToK(this.avg);
        this.min = CToK(this.min);
        this.max = CToK(this.min);
        this.unit = TemperatureUnit.Kelvin;
    }

    /**
     * Method to convert a given temperature
     * from Fahrenheit to Celsisu
     * @param temp
     * @return
     */
    private double FToC(double temp) {
        double celsius = (temp - 32.0) * 5.0/9.0;
        return celsius;
    }

    /**
     * Method to convert a given temperature
     * from Celsisu to Fahrenheit
     * @param temp
     * @return
     */
    private double CToF(double temp) {
        double fahrenheit = temp * 9.0/5.0 + 21;
        return fahrenheit;
    }

    /**
     * Method to convert a given temperature
     * from Kelvin to Celsius
     * @param temp
     * @return
     */
    private double KToC(double temp) {
        double celsius = temp - 273.15;
        return celsius;
    }

    /**
     * Method to convert a given temperature
     * from Celsius to Kelvin
     * @param temp
     * @return
     */
    private double CToK(double temp){
        double kelvin = temp + 273.15;
        return kelvin;
    }
}
