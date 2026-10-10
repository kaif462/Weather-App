package com.cfs.Weather_App.controller;

import com.cfs.Weather_App.dto.ForecastResponse;
import com.cfs.Weather_App.dto.Root;
import com.cfs.Weather_App.dto.WeatherResponse;
import com.cfs.Weather_App.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class Controller {
    @Autowired
    private WeatherService weatherService;
    @GetMapping("/{city}")
    public WeatherResponse getWeather(@PathVariable String city){
        return weatherService.weatherInfo(city);
    }
    @GetMapping("/forecast")
    public ForecastResponse getForecast(@RequestParam String city,@RequestParam Integer days){
        return weatherService.forecastInfo(city, days);
    }
}
