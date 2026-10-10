package com.cfs.Weather_App.dto;

public class Forecastday{
    public String date;
    public int date_epoch;
    public Day day;
    public Astro astro;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDate_epoch() {
		return date_epoch;
	}
	public void setDate_epoch(int date_epoch) {
		this.date_epoch = date_epoch;
	}
	public Day getDay() {
		return day;
	}
	public void setDay(Day day) {
		this.day = day;
	}
	public Astro getAstro() {
		return astro;
	}
	public void setAstro(Astro astro) {
		this.astro = astro;
	}

	}
  

