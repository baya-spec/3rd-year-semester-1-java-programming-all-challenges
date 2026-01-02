package com.example.weatherwidget;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class WeatherWidgetApp extends Application {

    private WeatherService weatherService;
    private Label tempLabel;
    private Label conditionLabel;
    private Label airQuality;
    private Label gardeningTip;

    @Override
    public void start(Stage primaryStage) {
        weatherService = new WeatherService();

        // Root Layout
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane");

        // --- Top Section: City Input and Heading ---
        VBox topContainer = new VBox(10);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPadding(new Insets(20));

        Label cityHeading = new Label("EcoLife Weather");
        cityHeading.getStyleClass().add("heading-text");

        HBox inputContainer = new HBox(10);
        inputContainer.setAlignment(Pos.CENTER);
        
        TextField cityInput = new TextField();
        cityInput.setPromptText("Enter City Name");
        cityInput.getStyleClass().add("city-input");

        Button refreshButton = new Button("Refresh");
        refreshButton.getStyleClass().add("refresh-button");
        
        // Requirement: Disable "Refresh" Button if city TextField is empty
        refreshButton.disableProperty().bind(cityInput.textProperty().isEmpty());

        // Action for Refresh Button
        refreshButton.setOnAction(e -> fetchWeather(cityInput.getText()));

        inputContainer.getChildren().addAll(cityInput, refreshButton);
        topContainer.getChildren().addAll(cityHeading, inputContainer);
        
        root.setTop(topContainer);

        // --- Center Section: Current Weather ---
        VBox centerContainer = new VBox(15);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setPadding(new Insets(10));

        tempLabel = new Label("--°C");
        tempLabel.getStyleClass().add("temp-text");

        conditionLabel = new Label("Enter a city");
        conditionLabel.getStyleClass().add("condition-text");

        // Company Shape: Leaf (SVG Path)
        SVGPath leafShape = new SVGPath();
        // Better Leaf Path:
        leafShape.setContent("M17,8C8,10,5.9,16.17,3.82,21.34L5.71,22l1-2.3A4.49,4.49,0,0,0,8,20C19,20,22,3,22,3,21,5,14,5.25,9,6.25S2,11.5,2,13.5a6.22,6.22,0,0,0,1.75,3.75C7,8,17,8,17,8Z");
        leafShape.getStyleClass().add("company-shape");
        
        // Animation: Subtle pulsing/growing animation for the leaf
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), leafShape);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

        // EcoLife Specific: Gardening Tip & Air Quality
        gardeningTip = new Label("Gardening Tip: Waiting for data...");
        gardeningTip.getStyleClass().add("tip-text");
        
        airQuality = new Label("AQI: --");
        airQuality.getStyleClass().add("aqi-text");

        centerContainer.getChildren().addAll(leafShape, tempLabel, conditionLabel, airQuality, gardeningTip);
        root.setCenter(centerContainer);

        // --- Bottom Section: 3-Day Forecast ---
        HBox forecastContainer = new HBox(20);
        forecastContainer.setAlignment(Pos.CENTER);
        forecastContainer.setPadding(new Insets(20));
        forecastContainer.getStyleClass().add("forecast-container");

        // Placeholder forecast data
        forecastContainer.getChildren().addAll(
            createForecastCard("Mon", "23°C", "Sunny"),
            createForecastCard("Tue", "21°C", "Cloudy"),
            createForecastCard("Wed", "19°C", "Rain")
        );

        root.setBottom(forecastContainer);

        Scene scene = new Scene(root, 400, 600);
        // Load CSS
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        
        primaryStage.setTitle("EcoLife Solutions Weather");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchWeather(String city) {
        if (city == null || city.trim().isEmpty()) return;

        conditionLabel.setText("Loading...");
        
        weatherService.getWeather(city).thenAccept(data -> {
            Platform.runLater(() -> {
                if (data != null) {
                    tempLabel.setText(String.format("%.1f°C", data.getTemperature()));
                    conditionLabel.setText(data.getDescription()); // Using description for more detail
                    airQuality.setText(String.format("AQI: %d (%s)", data.getAqi(), getAqiDescription(data.getAqi())));
                    gardeningTip.setText(getGardeningTip(data.getCondition(), data.getTemperature()));
                } else {
                    conditionLabel.setText("City not found");
                    tempLabel.setText("--°C");
                    airQuality.setText("AQI: --");
                    gardeningTip.setText("Gardening Tip: --");
                }
            });
        }).exceptionally(ex -> {
            Platform.runLater(() -> {
                conditionLabel.setText("Error fetching data");
                ex.printStackTrace();
            });
            return null;
        });
    }

    private String getAqiDescription(int aqi) {
        if (aqi <= 50) return "Good";
        if (aqi <= 100) return "Moderate";
        if (aqi <= 150) return "Unhealthy for Sensitive Groups";
        return "Unhealthy";
    }

    private String getGardeningTip(String condition, double temp) {
        condition = condition.toLowerCase();
        if (condition.contains("rain") || condition.contains("drizzle")) {
            return "Gardening Tip: No need to water today!";
        } else if (temp > 25) {
            return "Gardening Tip: Water your plants early in the morning.";
        } else if (temp < 5) {
            return "Gardening Tip: Protect sensitive plants from frost.";
        } else {
            return "Gardening Tip: Great day for pruning and weeding.";
        }
    }

    private VBox createForecastCard(String day, String temp, String condition) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("forecast-card");
        
        Label dayLabel = new Label(day);
        dayLabel.getStyleClass().add("forecast-day");
        
        Label tempLabel = new Label(temp);
        tempLabel.getStyleClass().add("forecast-temp");
        
        Label condLabel = new Label(condition);
        condLabel.getStyleClass().add("forecast-cond");
        
        card.getChildren().addAll(dayLabel, tempLabel, condLabel);
        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
