package com.cfs.Weather_App.dto;

import java.util.List;

public class ForecastResponse {
	private WeatherResponse weatherResponse;
	private List<DayTemp> daytemp;
	public WeatherResponse getWeatherResponse() {
		return weatherResponse;
	}
	public void setWeatherResponse(WeatherResponse weatherResponse) {
		this.weatherResponse = weatherResponse;
	}
	public List<DayTemp> getDaytemp() {
		return daytemp;
	}
	public void setDaytemp(List<DayTemp> temp) {
		this.daytemp = temp;
	}
	

}
