package com.cfs.Weather_App.service;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cfs.Weather_App.dto.DayTemp;
import com.cfs.Weather_App.dto.Forecast;
import com.cfs.Weather_App.dto.ForecastResponse;
import com.cfs.Weather_App.dto.Forecastday;
import com.cfs.Weather_App.dto.Root;
import com.cfs.Weather_App.dto.WeatherResponse;

@Service
public class WeatherService {
	private String apiUrl="http://api.weatherapi.com/v1/current.json";
	private String apiUrl2="http://api.weatherapi.com/v1/forecast.json";
	private String key="83c033da9eea49bebc5102019260910";
    public String test(){
        return "good";
    }
    RestTemplate restTemplate=new RestTemplate();
    public WeatherResponse weatherInfo(String city) {
    	String url=apiUrl+"?key="+key+"&q="+city;
    	@Nullable
		Root response = restTemplate.getForObject(url,Root.class);
    	WeatherResponse weatherResponse=new WeatherResponse();
    	weatherResponse.setCity(response.location.name);
    	weatherResponse.setCountry(response.location.country);
    	weatherResponse.setRegion(response.location.region);
    	weatherResponse.setCondition(response.current.condition.text);
    	weatherResponse.setTemprature(response.current.temp_c);
    	return weatherResponse;
    }
    public ForecastResponse forecastInfo(String city,Integer days) {
    	WeatherResponse weatherResponse = weatherInfo(city);
    	ForecastResponse forecastResponse=new ForecastResponse();
    	forecastResponse.setWeatherResponse(weatherResponse);
    	List<DayTemp>temp=new ArrayList<>();
    	String url=apiUrl2+"?key="+key+"&q="+city+"&days="+days;
    	@Nullable
		Root response = restTemplate.getForObject(url,Root.class);
    	Forecast forecast = response.getForecast();
    	ArrayList<Forecastday> forecastDay = forecast.getForecastday();
    	for(Forecastday forecastday:forecastDay) {
    		DayTemp d=new DayTemp();
    		d.setDate(forecastday.getDate());
    		d.setMinTemp(forecastday.getDay().getMintemp_c());
    		d.setAvgTemp(forecastday.getDay().getAvgtemp_c());
    		d.setMaxTemp(forecastday.getDay().getMaxtemp_c());
    		temp.add(d);
    		
    	}
    	forecastResponse.setDaytemp(temp);
    	
		return forecastResponse;
    	
    }
}
    
