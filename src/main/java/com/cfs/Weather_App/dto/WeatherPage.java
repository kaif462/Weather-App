package com.cfs.Weather_App.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Everything the Thymeleaf page needs, already formatted.
 * Keeps the template free of logic: it just prints these fields.
 */
public record WeatherPage(
        String city,
        String where,
        String temp,
        String unit,
        String condition,
        String theme,   // sun | partly | cloud | rain | thunder | snow | mist
        List<Row> days) {

    /** One forecast day. left/width/tick are percentages on a scale shared by all days. */
    public record Row(String label, String lo, String avg, String hi,
                      double left, double width, double tick, String from, String to) {
    }

    public static WeatherPage from(ForecastResponse forecast, String unit) {
        WeatherResponse w = forecast.getWeatherResponse();
        boolean f = "F".equals(unit);

        List<DayTemp> src = forecast.getDaytemp() == null ? List.of() : forecast.getDaytemp();
        double lo = src.stream().mapToDouble(DayTemp::getMinTemp).min().orElse(0);
        double hi = src.stream().mapToDouble(DayTemp::getMaxTemp).max().orElse(1);
        double span = Math.max(hi - lo, 1);

        List<Row> rows = new ArrayList<>();
        DateTimeFormatter label = DateTimeFormatter.ofPattern("EEE d MMM", Locale.ENGLISH);
        for (int i = 0; i < src.size(); i++) {
            DayTemp d = src.get(i);
            double left = pct(d.getMinTemp(), lo, span);
            double right = pct(d.getMaxTemp(), lo, span);
            rows.add(new Row(
                    i == 0 ? "Today" : LocalDate.parse(d.getDate()).format(label),
                    fmt(d.getMinTemp(), f), fmt(d.getAvgTemp(), f), fmt(d.getMaxTemp(), f),
                    left, Math.max(right - left, 3), pct(d.getAvgTemp(), lo, span),
                    color(d.getMinTemp()), color(d.getMaxTemp())));
        }

        String where = joinNonBlank(w.getRegion(), w.getCountry());
        return new WeatherPage(w.getCity(), where, fmt(w.getTemprature(), f), f ? "F" : "C",
                w.getCondition(), themeOf(w.getCondition()), rows);
    }

    /* ---------- helpers ---------- */

    private static double pct(double v, double lo, double span) {
        return Math.round((v - lo) / span * 1000) / 10.0;
    }

    private static String fmt(double c, boolean fahrenheit) {
        long r = Math.round(fahrenheit ? c * 9 / 5 + 32 : c);
        return r < 0 ? "\u2212" + (-r) : Long.toString(r);
    }

    /** Cold blue to warm red, so the bars read at a glance. */
    private static String color(double c) {
        double t = (Math.max(-5, Math.min(40, c)) + 5) / 45;
        return "hsl(" + Math.round(215 - t * 205) + " 78% 56%)";
    }

    private static String joinNonBlank(String a, String b) {
        boolean ha = a != null && !a.isBlank(), hb = b != null && !b.isBlank();
        return ha && hb ? a + ", " + b : ha ? a : hb ? b : "";
    }

    static String themeOf(String text) {
        String t = text == null ? "" : text.toLowerCase(Locale.ROOT);
        if (t.matches(".*(thunder|storm).*")) return "thunder";
        if (t.matches(".*(snow|sleet|ice|blizzard|freezing|pellets).*")) return "snow";
        if (t.matches(".*(rain|drizzle|shower).*")) return "rain";
        if (t.matches(".*(mist|fog|haze|smoke).*")) return "mist";
        if (t.contains("partly")) return "partly";
        if (t.matches(".*(cloud|overcast).*")) return "cloud";
        if (t.matches(".*(sunny|clear).*")) return "sun";
        return "cloud";
    }
}
