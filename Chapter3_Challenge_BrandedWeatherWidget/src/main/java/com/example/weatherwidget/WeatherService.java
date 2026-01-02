package com.example.weatherwidget;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WeatherService {
    private static final String API_KEY = "9e1dc5cd71b67bc9790decaf552bf82c";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    private final HttpClient httpClient;
    private final Gson gson;

    public WeatherService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public CompletableFuture<WeatherData> getWeather(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", BASE_URL, city, API_KEY);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseWeatherData);
    }

    private WeatherData parseWeatherData(String json) {
        try {
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            
            // OpenWeatherMap can return cod as number or string
            if (jsonObject.has("cod")) {
                String codStr = jsonObject.get("cod").getAsString();
                if (!"200".equals(codStr)) {
                    System.err.println("API Error: " + json);
                    return null; 
                }
            }

            double temp = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
            
            String condition = "Unknown";
            String description = "Unknown";
            
            if (jsonObject.has("weather") && jsonObject.getAsJsonArray("weather").size() > 0) {
                JsonObject weatherObj = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
                condition = weatherObj.get("main").getAsString();
                description = weatherObj.get("description").getAsString();
            }
            
            // Mocking AQI since it requires a different API endpoint usually
            int aqi = (int) (Math.random() * 100); 

            return new WeatherData(temp, condition, description, aqi);
        } catch (Exception e) {
            System.err.println("Error parsing weather data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static class WeatherData {
        private final double temperature;
        private final String condition;
        private final String description;
        private final int aqi;

        public WeatherData(double temperature, String condition, String description, int aqi) {
            this.temperature = temperature;
            this.condition = condition;
            this.description = description;
            this.aqi = aqi;
        }

        public double getTemperature() {
            return temperature;
        }

        public String getCondition() {
            return condition;
        }
        
        public String getDescription() {
            return description;
        }

        public int getAqi() {
            return aqi;
        }
    }
}
