package com.cfs.Weather_App.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import com.cfs.Weather_App.dto.ForecastResponse;
import com.cfs.Weather_App.dto.WeatherPage;
import com.cfs.Weather_App.service.WeatherService;

/** Serves the Thymeleaf page. The JSON API in Controller.java stays as it is. */
@Controller
public class PageController {

    private static final List<String> SUGGESTIONS = List.of("Delhi", "London", "Tokyo", "New York");
    private static final List<Integer> DAY_OPTIONS = List.of(3, 5, 7);

    private final WeatherService weatherService;

    public PageController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home(@RequestParam(name = "city", required = false) String city,
                       @RequestParam(name = "days", defaultValue = "3") int days,
                       @RequestParam(name = "unit", defaultValue = "C") String unit,
                       Model model) {

        int d = DAY_OPTIONS.contains(days) ? days : 3;
        String u = "F".equalsIgnoreCase(unit) ? "F" : "C";

        model.addAttribute("days", d);
        model.addAttribute("unit", u);
        model.addAttribute("dayOptions", DAY_OPTIONS);
        model.addAttribute("suggestions", SUGGESTIONS);
        model.addAttribute("theme", "neutral");

        if (city != null && !city.isBlank()) {
            String q = city.trim();
            model.addAttribute("city", q);
            try {
                ForecastResponse forecast = weatherService.forecastInfo(q, d);
                WeatherPage page = WeatherPage.from(forecast, u);
                model.addAttribute("page", page);
                model.addAttribute("theme", page.theme());
            } catch (HttpClientErrorException e) {
                model.addAttribute("error", "notFound");        // WeatherAPI rejected the city
            } catch (RestClientException | NullPointerException e) {
                model.addAttribute("error", "unreachable");     // network or unexpected response
            }
        }
        return "index";
    }
}
