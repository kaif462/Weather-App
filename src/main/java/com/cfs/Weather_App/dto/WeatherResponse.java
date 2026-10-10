 package com.cfs.Weather_App.dto;

public class WeatherResponse {
    private String city;
    private String region;
    private String country;
    private Double temprature;
    private String condition;

    public WeatherResponse() {
    }

    public WeatherResponse(String city, String region, String country, Double temprature, String condition) {
        this.city = city;
        this.region = region;
        this.country = country;
        this.temprature = temprature;
        this.condition = condition;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getTemprature() {
        return temprature;
    }

    public void setTemprature(Double temprature) {
        this.temprature = temprature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
