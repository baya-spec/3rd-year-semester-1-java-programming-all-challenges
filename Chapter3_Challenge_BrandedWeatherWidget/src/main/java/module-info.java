module com.example.weatherwidget {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;

    opens com.example.weatherwidget to javafx.fxml, com.google.gson;
    exports com.example.weatherwidget;
}